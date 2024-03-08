/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlanStatus;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlanFactory;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlanRequest;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolver;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolveContext;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.policies.*;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.ports.DraftTripService;
import cm.yowyob.letsgo.trip.domain.ports.PlannedTripService;
import cm.yowyob.letsgo.trip.domain.ports.PlannerResourceService;
import cm.yowyob.letsgo.trip.domain.ports.BusinessService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.Duration;
import java.util.*;


/**
 *   planner manager
 */
@AllArgsConstructor
public class PlannerPlanManager {


    @NonNull private PlannedTripService plannedTripService;
    @NonNull private PlannerResourceService plannerResourceService;
    @NonNull private BusinessService businessService;
    @NonNull private ItinerarySolver itinerarySolver;
    @NonNull private DraftTripService draftTripService;



    public PlannerPlan getPlannerPlan(UUID tripPlanId) throws PlannedTripNotFoundException {

        return plannedTripService.getPlannerPlanById(tripPlanId)
                .orElseThrow(PlannedTripNotFoundException::new);
    }


    public Set<PlannerPlan> getByPlannerId(String plannerId) {
        return plannedTripService.getAllByPlannerId(plannerId, false);
    }
    

    public PlannerPlan createPlannerPlan(String plannerId, PlannerPlanRequest request)
            throws PlannerNotFoundException, DriverNotFoundException,
            VehicleNotFoundException, NoSuchPrincipalItineraryException, DraftTripNotFoundException {

        Objects.requireNonNull(request, "draft trip must not be null");

        DraftTrip draftTrip;

        if (request.isBasedOnExistingDraftTrip()){

            draftTrip = draftTripService.getDraftById(request.getDraftId())
                    .orElseThrow(DraftTripNotFoundException::new);
        }
        else {

            // new draft
            draftTrip = Objects.requireNonNull(request.getDraftTrip());

            draftTrip = DraftTrip.simpleDraftBuilder()
                    .ownerId(plannerId)
                    .draftId(UUID.randomUUID())
                    .toLocation(draftTrip.getToLocation())
                    .fromLocation(draftTrip.getFromLocation())
                    .comforts(draftTrip.getComforts())
                    .tripType(draftTrip.getTripType())
                    .tripPrestige(draftTrip.getTripPrestige())
                    .intermediateStops(draftTrip.getIntermediateStops())
                    .build();

            draftTrip = draftTripService.save(draftTrip);
        }


        PlannerInformation plannerInformation = businessService.getPlannerInformation(plannerId);

        ItinerarySolveContext context = ItinerarySolveContext.builder()
                .fromLocation(draftTrip.getFromLocation())
                .toLocation(draftTrip.getToLocation())
                .stops(draftTrip.getIntermediateStops())
                .build();

        Itinerary<Leg> simpleItinerary = itinerarySolver.solve(context);

        request.setDraftId(draftTrip.getDraftId());
        request.setDraftTrip(draftTrip);

        PlannerPlanFactory tripPlanFactory
                = new PlannerPlanFactory (request, plannerInformation, simpleItinerary);

        if (request.hasDrivers()) {

            Set<Driver> drivers =
                    plannerResourceService.getAllDrivers(plannerId, request.getDriverIds());

            tripPlanFactory.withDrivers(drivers);
        }

        if (request.hasVehicles()) {

            Set<Vehicle> vehicles =
                    plannerResourceService.getAllVehicles(plannerId, request.getVehicleIds());

            tripPlanFactory.withVehicles(vehicles);
        }


        PlannerPlan plannerPlan = tripPlanFactory.createTripPlan();

        return plannedTripService.save(plannerPlan);
    }





    public PlannerPlan setupPolicy(String tripPlanId,PlannerPlanRequest request) 
            throws PlannedTripNotFoundException, IllegalSetupPolicyException {

        PlannerPlan plannerPlan = getPlannerPlan(UUID.fromString(tripPlanId));

        if (plannerPlan.getPlanStatus() == PlanStatus.PUBLISHED)
            throw new IllegalSetupPolicyException();

        CoursePolicy newCoursePolicy =
                ObjectUtils.getOrDefault(request.getCoursePolicy(), plannerPlan.getCoursePolicy());

        CancellationPolicy newCancellationPolicy =
                ObjectUtils.getOrDefault(request.getCancellationPolicy(), plannerPlan.getCancellationPolicy());

        ReservationPolicy newReservationPolicy =
                ObjectUtils.getOrDefault(request.getReservationPolicy(), plannerPlan.getReservationPolicy());

        ConfirmationPolicy newConfirmationPolicy = ObjectUtils.getOrDefault(request.getConfirmationPolicy(), plannerPlan.getConfirmationPolicy());


        plannerPlan.setCoursePolicy(newCoursePolicy);
        plannerPlan.setConfirmationPolicy(newConfirmationPolicy);
        plannerPlan.setCancellationPolicy(newCancellationPolicy);
        plannerPlan.setReservationPolicy(newReservationPolicy);

        return plannedTripService.save(plannerPlan);
    }






    public PlannerPlan extendsItinerary(String tripPlanId,
                                        @NonNull StopLocation newStopLocation,
                                        Duration stopDuration)

            throws PlannedTripNotFoundException, IllegalOperationOnStaticItineraryPlanException, IllegalReplaceLegOperationException, LegNotFoundException {

        PlannerPlan plannerPlan = getPlannerPlan(UUID.fromString(tripPlanId));

        if (plannerPlan.isPublished() && plannerPlan.getIsStaticPlan())
            throw new IllegalOperationOnStaticItineraryPlanException();

        Itinerary<PlannerLeg> itinerary = plannerPlan.getItinerary();

        ItineraryProductHelper itineraryProductHelper = new ItineraryProductHelper();

        itinerary = itineraryProductHelper.applySigma(itinerary,
                Stop.of(newStopLocation, itinerary.length() - 1, stopDuration), itinerary.length() - 1);

        plannerPlan.replaceItinerary(itinerary);

        return plannedTripService.save(plannerPlan);
    }


 




    /**
     * change vehicles and drivers
     *
     * @param tripPlanId tripPlanId
     * @param vehicleMapIds vehicleMapIds map of legIndex : vehicleId
     * @param driverMapIds driverMapIds map of legIndex : driverId
     * @return {@link PlannerPlan}
     * @see PlannerPlan
     * @throws PlannedTripNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. planned trip not found exception
     * @throws DriverNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. driver not found exception
     * @throws VehicleNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. vehicle not found exception
     */
    public PlannerPlan changeVehiclesAndDrivers(String tripPlanId,
                                                Map<Integer, String> vehicleMapIds,
                                                Map<Integer, String> driverMapIds) throws PlannedTripNotFoundException, DriverNotFoundException, VehicleNotFoundException, IllegalReplaceLegOperationException, LegNotFoundException {

        if (vehicleMapIds == null && driverMapIds == null)
            throw new IllegalArgumentException();

        PlannerPlan plannerPlan = getPlannerPlan(UUID.fromString(tripPlanId));

        Itinerary<PlannerLeg> plannerItinerary = plannerPlan.getItinerary();

        if (driverMapIds != null) {

            Set<Driver> allDrivers = plannerPlan.getAllDrivers();

            Map<Integer, String> allDriverIdByLeg = plannerPlan.getAllDriverIdByLeg();
            allDriverIdByLeg.putAll(driverMapIds);

            // Query all driver
            Set<Driver> othersDrivers =
                    plannerResourceService.getAllDrivers(plannerPlan.getPlannerInformation().userId(), driverMapIds.values());

            allDrivers.addAll(othersDrivers);

            for (Integer legIndex : driverMapIds.keySet()) {

                // TODO: 12/06/2023 check nullability of planner leg on whole project
                PlannerLeg plannerLeg = plannerItinerary.getLeg(legIndex);

                Driver driverLeg =
                        PlannerPlanFactory.getDriverInfoLeg(allDrivers, allDriverIdByLeg, plannerLeg.getIndex());

                if (driverLeg != null)
                    plannerItinerary.replaceLeg(plannerLeg, plannerLeg.changeTransportInformation(driverLeg));

            }
        }

        if (vehicleMapIds != null) {

            Set<Vehicle> allVehicles = plannerPlan.getAllVehicles();

            Map<Integer, String> allVehicleIdByLeg = plannerPlan.getAllVehicleIdByLeg();
            allVehicleIdByLeg.putAll(vehicleMapIds);

            // Query all vehicle
            Set<Vehicle> othersVehicles =
                    plannerResourceService.getAllVehicles(plannerPlan.getPlannerInformation().userId(), vehicleMapIds.values());

            allVehicles.addAll(othersVehicles);


            for (Integer legIndex : vehicleMapIds.keySet()) {

                PlannerLeg plannerLeg = plannerItinerary.getLeg(legIndex);

                Vehicle vehicleLeg =
                        PlannerPlanFactory.
                                getVehicleInfoLeg(allVehicles, vehicleMapIds, legIndex);

                if (vehicleLeg != null)
                    plannerItinerary.replaceLeg(plannerLeg, plannerLeg.changeTransportInformation(vehicleLeg));

            }
        }


        return plannedTripService.save(plannerPlan);
    }









    public PlannerPlan splitLeg(String tripPlanId, Integer legIndex, Stop intermediateStop)

            throws PlannedTripNotFoundException, IllegalOperationOnStaticItineraryPlanException {

        PlannerPlan plannerPlan = getPlannerPlan(UUID.fromString(tripPlanId));

        if (plannerPlan.isPublished() && plannerPlan.getIsStaticPlan())
            throw new IllegalOperationOnStaticItineraryPlanException();


        Itinerary<PlannerLeg> itinerary = plannerPlan.getItinerary();

        ItineraryProductHelper itineraryProductHelper = new ItineraryProductHelper();
        itinerary = itineraryProductHelper.applySigma(itinerary, intermediateStop, legIndex);

        plannerPlan.replaceItinerary(itinerary);

        return plannedTripService.save(plannerPlan);
    }


    public PlannerPlan save(PlannerPlan plannerPlan) {
        return plannedTripService.save(plannerPlan);
    }



}
