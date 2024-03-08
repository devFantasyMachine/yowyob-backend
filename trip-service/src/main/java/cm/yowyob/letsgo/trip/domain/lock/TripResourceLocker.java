package cm.yowyob.letsgo.trip.domain.lock;


import cm.yowyob.letsgo.trip.domain.exceptions.InvalidReservationRequestException;
import cm.yowyob.letsgo.trip.domain.exceptions.PlannedTripNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.PlannedTripNotYetPublishedException;
import cm.yowyob.letsgo.trip.domain.ports.ItineraryProductRecord;
import cm.yowyob.letsgo.trip.domain.ports.LockedTripResourceRecord;
import cm.yowyob.letsgo.trip.domain.managers.PlannerPlanManager;
import cm.yowyob.letsgo.trip.domain.managers.ReservationResourceResponse;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;
import cm.yowyob.letsgo.trip.domain.model.reservation.SaltedTokenHash;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
public class TripResourceLocker {


    PlannerPlanManager planManager;
    LockedTripResourceRecord lockedTripResourceRecord;
    ItineraryProductRecord itineraryProductRecord;


    public LockResponse lock (LockRequest request)
            throws PlannedTripNotFoundException, PlannedTripNotYetPublishedException {

        synchronized (request.getTripId()){

            // TODO: 28/08/2023 verify request integrity

            PlannerPlan plannerPlan = planManager.getPlannerPlan(request.getTripId());

            if (!plannerPlan.isPublished())
                throw new PlannedTripNotYetPublishedException();

            Optional<LockedTripResources> optionalLockedTripResources =
                    lockedTripResourceRecord.getByRequestId(request.getRequestId());

            if (optionalLockedTripResources.isPresent()){

                // TODO: 28/08/2023 compute response and return
            }

            // precompute response
            LockResponse response = new LockResponse(true);

            Set<LockResourceRequest> lockResourceRequests = request.getResources();

            Set<UUID> productIds = lockResourceRequests
                    .stream()
                    .map(LockResourceRequest::getProductId)
                    .collect(Collectors.toSet());

            Set<ItineraryProduct> products =
                    itineraryProductRecord.getAll(request.getTripId(), productIds);

            for (LockResourceRequest resourceRequest : lockResourceRequests) {

                Optional<ItineraryProduct> productOptional = products
                        .stream()
                        .filter(product -> product.getProductId().equals(resourceRequest.getProductId()))
                        .findFirst();

                if (productOptional.isEmpty()){

                    ReservationResourceResponse resourceResponse =
                            ReservationResourceResponse.builder()
                                    .resourceId(resourceRequest.getProductId())
                                    .resourceType(null)
                                    .isSuccess(false)
                                    .status(ReservationResourceResponse.ResourceResponseStatus.NOT_FOUND)
                            .build();

                    response.addResourceResponse(resourceResponse);
                    response.setSuccess(false);
                }
                else {

                    ItineraryProduct itineraryProduct = productOptional.get();

                    try {

                        itineraryProduct.lock(resourceRequest.getQuantity());

                        ReservationResourceResponse resourceResponse =
                                ReservationResourceResponse.builder()
                                        .resourceId(resourceRequest.getProductId())
                                        .resourceType(itineraryProduct.getType())
                                        .isSuccess(true)
                                        .quantity(itineraryProduct.getQuantity())
                                        .unitCost(itineraryProduct.getCost())
                                        .resourceLabel(itineraryProduct.getLabel())
                                        .status(ReservationResourceResponse
                                                .ResourceResponseStatus
                                                .FOUND_AVAILABLE
                                        )
                                        .build();

                        response.addResourceResponse(resourceResponse);

                        // TODO: 28/08/2023 add itineraryProduct to the saved list

                        ItineraryProduct product = ItineraryProduct.builder()
                                .productId(itineraryProduct.getProductId())
                                .resource(itineraryProduct.getResource())
                                .quantity(resourceRequest.getQuantity())
                                .planId(itineraryProduct.getPlanId())
                                .build();

                    }
                    catch (InvalidReservationRequestException e) {

                        ReservationResourceResponse resourceResponse =
                                ReservationResourceResponse.builder()
                                        .resourceId(resourceRequest.getProductId())
                                        .resourceType(itineraryProduct.getType())
                                        .isSuccess(false)
                                        .quantity(itineraryProduct.getQuantity())
                                        .unitCost(itineraryProduct.getCost())
                                        .resourceLabel(itineraryProduct.getLabel())
                                        .status(ReservationResourceResponse
                                                .ResourceResponseStatus
                                                .FOUND_NOT_AVAILABLE
                                        )
                                        .build();

                        response.addResourceResponse(resourceResponse);
                        response.setSuccess(false);
                    }


                }


            }

            if (response.isSuccess()){

                SaltedTokenHash saltedTokenHash =
                        SaltedTokenHash.generateFor(request.getChallengeToken());

                response.setTokenHash(saltedTokenHash.hash());

                LockedTripResources lockedTripResources = LockedTripResources.builder()
                        .issueAt(LocalDateTime.now())
                        .id(UUID.randomUUID())
                        .requestId(request.getRequestId())
                        .userId(request.getUserId())
                        .jobStatus(JobStatus.UNSCHEDULED)
                        .planId(request.getTripId())
                        .lockGroup(request.getGroup())
                        .challenge(saltedTokenHash)
                        .build();
















                return null;

            }else {

                return response;
            }

        }

    }










}
