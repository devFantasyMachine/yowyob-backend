/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 *//*


package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryStatus;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.reservation.*;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import cm.yowyob.letsgo.trip.domain.model.schedule.LetsgoScheduler;
import cm.yowyob.letsgo.trip.domain.ports.PublishedTripService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Synchronized;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ReservationManager {

    ReservationService reservationService;
    PublishedTripService publishedTripService;
    LetsgoScheduler cancelReservationScheduler;
    PlannerPlanManager plannerPlanManager;


    @Synchronized
    public synchronized LockResponse reserveResources (
            @NonNull LockRequest request) throws PublishedTripNotBookableException,
            InvalidReservationRequestException, PlannedTripNotFoundException {


        if (request.getResourcesRequest() == null ||
                request.getResourcesRequest().get(ResourceType.SEAT) == null ||
                request.getResourcesRequest().get(ResourceType.SEAT).isEmpty())

            throw new InvalidReservationRequestException();

        PlannerPlan plannerPlan = plannerPlanManager.getPlannerPlan(UUID.fromString(request.getTripId()));

        Itinerary<PlannerLeg> itinerary = plannerPlan.getItinerary();

        Set<ItineraryProduct> products = itinerary.getProducts();

        Set<ItineraryProduct> availableProducts = products
                .stream()
                .filter(ItineraryProduct::isAvailable)
                .collect(Collectors.toSet());


        if (itinerary.getStatus() == ItineraryStatus.RESOLVED || availableProducts.isEmpty())
            throw new PublishedTripNotBookableException();

        Map<UUID, ItineraryProduct> tripResourcesByIds = new HashMap<>();

        availableProducts.forEach(product -> tripResourcesByIds.put(product.getProductId(), product));


        // create response
        LockResponse response = new LockResponse(true);
        Map<ResourceType, Map<UUID, Float>> reservedResources = new HashMap<>();

        TreeSet<PlannerLeg> plannerLegs = new TreeSet<>();

        for (ResourceType resourceType : request.getResourcesRequest().keySet()) {

            reservedResources.put(resourceType, new HashMap<>());

            Map<UUID, Float> quantityByResource = request.getResourcesRequest().get(resourceType);

            for (UUID resourceId : quantityByResource.keySet()) {

                final Float quantity = quantityByResource.get(resourceId);

                ItineraryProduct tripResource =
                        tripResourcesByIds.computeIfPresent(resourceId, (resId, resource) -> {

                            if (resource.isAvailable() && resource.getQuantity() >= quantity) {

                                response.addResourceResponse(ReservationResourceResponse.builder()
                                        .isSuccess(true)
                                        .quantity(quantity)
                                        .resourceId(resId)
                                        .resourceType(resourceType)
                                        .unitCost(resource.getCost())
                                        .resourceLabel(resource.getLabel())
                                        .build());

                                try {
                                    resource.lock(quantity);
                                } catch (NoSushResourceAvailableException ignored) {
                                }

                                reservedResources.get(resourceType).put(resId, quantity);

                            }
                            else {

                                response.setSuccess(false);
                                response.addResourceResponse(ReservationResourceResponse.builder()
                                        .isSuccess(false)
                                        .resourceId(resId)
                                        .resourceType(resource.getType())
                                        .quantity(resource.getQuantity())
                                        .unitCost(resource.getCost())
                                        .resourceLabel(resource.getLabel())
                                        .build());
                            }

                            return resource;

                        });

            }
        }


        if (response.isSuccess()) {

            // TODO: 02/06/2023 add planner legs on reservation

            response.setFromLocation(plannerLegs.first().getFromLocation());
            response.setToLocation(plannerLegs.last().getToLocation());

            ReservationContext reservationContext = ReservationContext.builder()
                    .userId(request.getUserId())
                    .tripType(plannerPlan.getTripType())
                    .plannerCode(plannerPlan.getPlannerInformation().code())
                    .tripPrestige(plannerPlan.getTripPrestige())
                    .contextDatetime(ZonedDateTime.now())
                    .plannedTripId(plannerPlan.getDraftId().toString())
                    .publishedTripId(plannerPlan.getPublishedTripCode())
                    .noReservation(plannerPlan.getNewReservationNumber())
                    .reservationPolicy(plannerPlan.getReservationPolicy())
                    .build();

            ReservationBuilder reservationBuilder = ReservationBuilder.createBuilder(reservationContext);
            reservationBuilder.setTripResources(reservedResources);

            String token = SaltedTokenHash.generatePlainText();
            LockedTripResources reservation = reservationBuilder.build(token);

            response.setToken(token);
            response.setReservationId(reservation.getReservationId());

            if(!publishedTrip.isResourcesAvailable()){

                publishedTrip.setIsResolved(true);
                publishedTrip.setResolvedAt(ZonedDateTime.now(plannerPlan.getPublishedAt().getZone()));
            }

            reservation = reservationService.save(reservation);

            // TODO: 29/04/2023 pause policy and apply max duration of token
            if (!cancelReservationScheduler.scheduleNewJob(reservation)) {

                throw new IllegalStateException();
            }

            plannerPlanManager.save(plannerPlan);

        }

        return response;
    }

    public void abortReservation(@NonNull String reservationId,@NonNull String reservationTokenPlainText) throws ReservationNotFoundException, PublishedTripNotFoundException, ConfirmationNotAuthorizedException, IllegalCancelReservationException {

        LockedTripResources reservation = reservationService.getById(reservationId);

        if (reservation.isNonAuthorized(reservationTokenPlainText) || reservation.getLockStatus().isNonConfirmable())
            throw new ConfirmationNotAuthorizedException();

        if (reservation.getLockStatus() == LockStatus.ABORTED){

            if (reservation.getJobStatus() != JobStatus.DELETED)
                cancelReservationScheduler.deleteJob(reservation);

        }else {

            reservation.doCancel(UnLockReason.TIMEOUT);
            reservationService.save(reservation);
            freeUpResources(reservation);
        }


    }

    public boolean confirmResource(@NonNull String reservationId,@NonNull String reservationTokenPlainText) throws PublishedTripNotFoundException, ReservationNotFoundException, ConfirmationTimeoutException, ConfirmationNotAuthorizedException, IllegalTripResourceUpdateException, PlannedTripNotFoundException {

        LockedTripResources reservation = reservationService.getById(reservationId);

        if (reservation.isNonAuthorized(reservationTokenPlainText) || reservation.getLockStatus().isNonConfirmable())
            throw new ConfirmationNotAuthorizedException();

        if (reservation.getLockStatus() == LockStatus.CONFIRMED){

            if (reservation.getJobStatus() != JobStatus.DELETED)
                cancelReservationScheduler.deleteJob(reservation);

            return true;
        }

        PlannerPlan publishedTrip = plannerPlanManager.getPlannerPlan(UUID.fromString(reservation.getPublishedTripId()));

        Map<String, TripResource> tripResources = new HashMap<>();

        publishedTrip
                .getAllResources()
                .forEach(resource -> tripResources.put(resource.getId(), resource));


        if (reservation.getLockStatus() == LockStatus.RESERVED  &&
                reservation.getMaxTokenDuration().compareTo(Duration.between(reservation.getReservedAt(), LocalDateTime.now())) < 0) {

            freeUpResources(reservation, publishedTrip.getPlanId().toString(), tripResources);

            try {
                reservation.doCancel(UnLockReason.TIMEOUT);
            } catch (IllegalCancelReservationException ignored) {

            }
            reservationService.save(reservation);

            // perform task and throw ConfirmationTimeoutException
            // we must return availability of resources
            throw new ConfirmationTimeoutException();
        }

        Set<TripResource> updatedResources = new HashSet<>();

        for (ResourceType resourceType : reservation.getReservedResources().keySet()) {

            for (String resourceId : reservation.getReservedResources().get(resourceType).keySet()) {

                TripResource tripResource = tripResources.get(resourceId);

                if (tripResource.isConfirmable()){
                    tripResource.markConfirmed();
                }
            }
        }

        publishedTripService.saveAllResources(publishedTrip.getPublishedTripId(), updatedResources);

        try {
            reservation.doConfirm();
        } catch (IllegalConfirmReservationException ignored) {
        }

        reservationService.save(reservation);

        if (reservation.getJobStatus() != JobStatus.DELETED)
            cancelReservationScheduler.deleteJob(reservation);

        return true;
    }

    public Boolean cancelReservation(String reservationId, String reservationTokenPlainText)
            throws ReservationNotFoundException, CancellationNotAuthorizedException,
            PublishedTripNotFoundException, IllegalCancelReservationException {

        LockedTripResources reservation = reservationService.getById(reservationId);

        if (reservation.isNonAuthorized(reservationTokenPlainText) || reservation.getLockStatus() == LockStatus.ABORTED)
            throw new CancellationNotAuthorizedException();

        if (reservation.getLockStatus().isNonConfirmable())
            return true;


        PublishedPlannerTrip publishedTrip = publishedTripService.getByPublishedId(reservation.getPublishedTripId());
        Map<String, TripResource> tripResources = new HashMap<>();

        publishedTrip.getPlannedTrip()
                .getAllResources()
                .forEach(resource -> tripResources.put(resource.getId(), resource));

        freeUpResources(reservation, publishedTrip.getPublishedTripId(), tripResources);

        publishedTripService.save(publishedTrip);

        reservation.doCancel(UnLockReason.POOLER_REQUEST);
        reservationService.save(reservation);

        cancelReservationScheduler.deleteJob(reservation);

        return true;
    }



    public void freeUpResources(@NonNull LockedTripResources reservation) throws PublishedTripNotFoundException {

        Map<String, TripResource> tripResources = new HashMap<>();

        publishedTripService.getAllResources(reservation.getPublishedTripId())
                .forEach(resource -> tripResources.put(resource.getId(), resource));

        freeUpResources(reservation, reservation.getPublishedTripId(), tripResources);

        // make sure that published trip is not booked
        publishedTripService.setPublishedTripNotBooked(reservation.getPublishedTripId());
    }

    private void freeUpResources(@NonNull LockedTripResources reservation, String publishedTripId, Map<String, TripResource> tripResources) {

        Set<TripResource> updatedResources = new HashSet<>();

        for (ResourceType resourceType : reservation.getReservedResources().keySet()) {

            for (String resourceId : reservation.getReservedResources().get(resourceType).keySet()) {

                TripResource tripResource = tripResources.get(resourceId);

                tripResource.freeUpResource(reservation.getReservedResources().get(resourceType).get(resourceId));
                updatedResources.add(tripResource);

            }
        }

        publishedTripService.saveAllResources(publishedTripId, updatedResources);
    }


    public LockedTripResources getReservation(String entityId) throws ReservationNotFoundException {
        return reservationService.getById(entityId);
    }

    public LockedTripResources save(LockedTripResources reservation) {
        return reservationService.save(reservation);
    }
}
*/
