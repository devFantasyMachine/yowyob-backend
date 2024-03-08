/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.api.PoolerTripPlanRecords;
import cm.yowyob.letsgo.trip.domain.events.PoolerPlanEventPublisher;
import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.model.ObjectUtils;
import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlanStatus;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlanFactory;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlanRequest;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolver;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolveContext;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.ports.BusinessService;
import cm.yowyob.letsgo.trip.domain.ports.DraftTripService;
import cm.yowyob.letsgo.trip.domain.ports.PlannedTripService;
import cm.yowyob.letsgo.trip.domain.ports.PlannerResourceService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;




/**
 *   pooler plan manager
 */
@AllArgsConstructor
public class PoolerPlanManager implements PoolerTripPlanRecords {


    @NonNull private PlannedTripService plannedTripService;
    @NonNull private PlannerResourceService plannerResourceService;
    @NonNull private BusinessService businessService;
    @NonNull private ItinerarySolver itinerarySolver;
    @NonNull private DraftTripService draftTripService;

    private PoolerPlanEventPublisher poolerPlanEventPublisher;



    @Override
    public PoolerPlan getPoolerPlan(UUID tripPlanId) throws PoolerPlanNotFoundException {

        return plannedTripService.getPoolerPlanById(tripPlanId)
                .orElseThrow(PoolerPlanNotFoundException::new);
    }



    @Override
    public Set<PoolerPlan> getByPoolerId(String poolerId) {
        return plannedTripService.getAllByPoolerId(poolerId);
    }



    public PoolerPlan createPoolerPlan(String poolerId, PoolerPlanRequest request)

            throws PollerNotFoundException, VehicleNotFoundException,
            NoSuchPrincipalItineraryException, DraftTripNotFoundException {


        DraftTrip draftTrip;

        if (request.isBasedOnExistingDraftTrip()){

            draftTrip = draftTripService
                    .getDraftByIdAndOwnerId(request.getDraftId(), poolerId)
                    .orElseThrow(DraftTripNotFoundException::new);
        }
        else {

            // new draft
            draftTrip = Objects.requireNonNull(request.getDraftTrip(), "draftTrip must not be null");

            draftTrip = DraftTrip.simpleDraftBuilder()
                    .ownerId(poolerId)
                    .draftId(UUID.randomUUID())
                    .toLocation(draftTrip.getToLocation())
                    .fromLocation(draftTrip.getFromLocation())
                    .comforts(draftTrip.getComforts())
                    .tripType(draftTrip.getTripType())
                    .serviceType(draftTrip.getServiceType())
                    .tripPrestige(draftTrip.getTripPrestige())
                    .intermediateStops(draftTrip.getIntermediateStops())
                    .build();

            draftTrip = draftTripService.save(draftTrip);

        }

        request.setDraftTrip(draftTrip);

        PoolerInformation poolerInformation = businessService.getPoolerInformation(poolerId);

        ItinerarySolveContext context = ItinerarySolveContext.builder()
                .fromLocation(draftTrip.getFromLocation())
                .toLocation(draftTrip.getToLocation())
                .solveType(ItinerarySolveContext.SolveType.SINGLE_LEG)
                .stops(draftTrip.getIntermediateStops())
                .build();

        Itinerary<Leg> simpleItinerary = itinerarySolver.solve(context);

        PoolerPlanFactory poolerPlanFactory =
                new PoolerPlanFactory(request, poolerInformation, simpleItinerary);

        if (request.hasVehicles()) {

            Vehicle vehicle =
                    plannerResourceService.getPlannerVehicle(poolerId, request.getVehicleId());
            poolerPlanFactory.withVehicle(vehicle);
        }

        PoolerPlan poolerPlan = poolerPlanFactory.createTripPlan();

        draftTrip.increaseUsageCount();
        draftTripService.save(draftTrip);

        return plannedTripService.save(poolerPlan);
    }



    public PoolerPlan updatePoolerPlan(String poolerId,
                                       UUID planId,
                                       PoolerPlanRequest request)

            throws VehicleNotFoundException, NoSuchPrincipalItineraryException,
            PoolerPlanNotFoundException, IllegalUpdateNonPlannedTripException {


        PoolerPlan poolerPlan = getPoolerPlan(planId);

        if (!poolerPlan.canUpdate()) {
            throw new IllegalUpdateNonPlannedTripException();
        }

        PoolerPlan.PoolerPlanBuilder poolerPlanBuilder = PoolerPlan.builder();

        if (request.hasVehicles()) {

            Vehicle vehicle =
                    plannerResourceService.getPlannerVehicle(poolerId, request.getVehicleId());

            poolerPlanBuilder.vehicleInfo(vehicle);
        }

        if (request.hasDraft()){

            DraftTrip draftTrip = request.getDraftTrip();

            if (request.hasItinerary()){

                StopLocation toLocation = ObjectUtils.getOrDefault(draftTrip.getToLocation(),
                        poolerPlan.getToLocation());
                StopLocation fromLocation = ObjectUtils.getOrDefault(draftTrip.getFromLocation(),
                        poolerPlan.getFromLocation());

                ItinerarySolveContext context = ItinerarySolveContext.builder()
                        .fromLocation(draftTrip.getFromLocation())
                        .toLocation(draftTrip.getToLocation())
                        .stops(draftTrip.getIntermediateStops())
                        .build();

                Itinerary<Leg> simpleItinerary = itinerarySolver.solve(context);

                poolerPlanBuilder.fromLocation(fromLocation)
                        .toLocation(toLocation)
                        .itineraries(simpleItinerary);
            }

            poolerPlanBuilder
                    .comforts(ObjectUtils.getOrDefault(draftTrip.getComforts(),
                            poolerPlan.getComforts()))
                    .tripPrestige(ObjectUtils.getOrDefault(draftTrip.getTripPrestige(),
                            poolerPlan.getTripPrestige()))
                    .tripType(ObjectUtils.getOrDefault(draftTrip.getTripType(),
                            poolerPlan.getTripType()))
                    .serviceType(ObjectUtils.getOrDefault(draftTrip.getServiceType(),
                            poolerPlan.getServiceType()));

        }

        PoolerPlan newPoolerPlan = poolerPlanBuilder
                .planId(poolerPlan.getPlanId())
                .draftId(poolerPlan.getDraftId())
                .plannedAt(poolerPlan.getPlannedAt())
                .plannerInformation(poolerPlan.getPlannerInformation())
                .planStatus(poolerPlan.getPlanStatus())

                .departure(ObjectUtils.getOrDefault(request.getDeparture(), poolerPlan.getDeparture()))
                .luggageBoxQuantity(ObjectUtils.getOrDefault(request.getLuggageBoxQuantity(), poolerPlan.getLuggageBoxQuantity()))
                .paymentSetting(ObjectUtils.getOrDefault(request.getPaymentSetting(), poolerPlan.getPaymentSetting()))
                .totalCost(ObjectUtils.getOrDefault(request.getTotalCost(), poolerPlan.getTotalCost()))
                .placeCount(ObjectUtils.getOrDefault(request.getSeatCount(), poolerPlan.getPlaceCount()))
                .transportModes(ObjectUtils.getOrDefault(request.getTransportModes(), poolerPlan.getTransportModes()))
                .build();

        PoolerPlan savedPlan = plannedTripService.save(newPoolerPlan);

        if (savedPlan.getPlanStatus() == PlanStatus.PUBLISHED)
            poolerPlanEventPublisher.updatedPoolerPlan(savedPlan);

        return savedPlan;
    }



    public PoolerPlan updateItinerary(String userId, UUID tripPlanId, List<Stop> intermediatesStops)
            throws PoolerPlanNotFoundException, UnauthorizedTripAccessException {

        PoolerPlan poolerPlan = getPoolerPlan(tripPlanId);

        if (poolerPlan.isNonAuthorized(userId))
            throw new UnauthorizedTripAccessException();

        if (poolerPlan.canUpdateItinerary()){

            ItinerarySolveContext context = ItinerarySolveContext.builder()
                    .fromLocation(poolerPlan.getFromLocation())
                    .toLocation(poolerPlan.getToLocation())
                    .stops(intermediatesStops)
                    .build();

            Itinerary<Leg> simpleItinerary = itinerarySolver.solve(context);

            poolerPlan.replaceItinerary(simpleItinerary);
        }

        PoolerPlan savedPlan = plannedTripService.save(poolerPlan);

        if (savedPlan.getPlanStatus() == PlanStatus.PUBLISHED) {
            poolerPlanEventPublisher.updatedPoolerPlan(savedPlan);
        }

        return savedPlan;
    }


    public PoolerPlan save(PoolerPlan poolerPlan) {
        return plannedTripService.save(poolerPlan);
    }

}
