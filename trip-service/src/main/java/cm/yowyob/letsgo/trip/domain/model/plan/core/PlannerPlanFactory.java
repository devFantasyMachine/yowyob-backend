/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchPrincipalItineraryException;
import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.TripPresentation;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.*;


/**
 *  planner plan factory
 *
 */
@Slf4j
public class PlannerPlanFactory extends AbstractTripPlanFactory<PlannerPlanRequest, PlannerInformation> {

    private final List<Vehicle> vehicles;
    private final List<Driver> drivers;



    public PlannerPlanFactory(PlannerPlanRequest draftTrip, PlannerInformation userInformation, Itinerary<Leg> simpleItinerary) throws NoSuchPrincipalItineraryException {
        super(draftTrip, userInformation, simpleItinerary);
        this.drivers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }


    public void withDrivers(Set<Driver> drivers) {
        this.drivers.addAll(Objects.requireNonNull(drivers));
    }

    public void withVehicles(Set<Vehicle> vehicles) {
        this.vehicles.addAll(Objects.requireNonNull(vehicles));
    }



    /**
     * create trip plan for planner
     *
     * @return {@link PlannerPlan}
     * @see PlannerPlan
     * @throws NoSuchPrincipalItineraryException cm.yowyob.letsgo.trip.domain.exceptions. no such principal itinerary exception
     */
    @Override
    public PlannerPlan createTripPlan() throws NoSuchPrincipalItineraryException {

        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getDeparture());

        log.info("make one trip with draft " + request.getDraftId() + " for planner ");


        Set<PlannerLeg> legs = new HashSet<>();

        for (Leg leg : simpleItinerary.getLegs()) {

            PlannerLeg plannerLeg = PlannerLeg.fromSimpleLeg(leg);

            Driver driverLeg = getDriverInfoLeg(drivers, request.getDriverIdByLeg(), leg.getIndex());
            Vehicle vehicleLeg = getVehicleInfoLeg(vehicles, request.getVehicleIdByLeg(), leg.getIndex());

            legs.add(plannerLeg.changeTransportInformation(vehicleLeg, driverLeg));
        }


        List<TripResource> seats = TripResource.generateSeat(request.getSeatCount(), request.getSeatCost());

        List<TripResource> resources = new ArrayList<>(seats);


        DraftTrip draftTrip = request.getDraftTrip();


        return new PlannerPlan(
                request.getDraftId(),
                UUID.randomUUID(),
                request.getDeparture(),
                ZonedDateTime.now(),
                draftTrip.getFromLocation(),
                draftTrip.getToLocation(),
                draftTrip.getTripType(),
                draftTrip.getTripPrestige(),
                draftTrip.getServiceType(),
                userInformation,
                draftTrip.getComforts(),
                false,
                new Itinerary<>(legs),
                null,
                null,
                null,
                null,
                PlanStatus.PLANNED,
                null,
                null,
                null,
                null,
                TripPresentation.NONE,
                request.getPaymentSetting(),
                request.getSeatCost(),
                request.getSeatCount(),
                request.getLuggageCost(),
                request.getLuggageBoxQuantity(),
                null,
                null,
                null,
                null,
                null,
                resources,
                Set.of());

    }




    public static Vehicle getVehicleInfoLeg(@NonNull Collection<Vehicle> vehicles,
                                            @NonNull Map<Integer, String> vehicleIdByLeg,
                                            int legIndex) {

        if (legIndex < 0)
            return null;

        Vehicle vehicleLeg = null;

        for (Vehicle vehicle : vehicles) {

            if (Objects.equals(vehicle.getVehicleId(),vehicleIdByLeg.get(legIndex))) {

                vehicleLeg = vehicle;
                break;
            }
        }

        return vehicleLeg == null ?
                getVehicleInfoLeg(vehicles, vehicleIdByLeg, legIndex - 1) : vehicleLeg;
    }



    public static Driver getDriverInfoLeg(@NonNull Collection<Driver> drivers,
                                          @NonNull Map<Integer, String> driverIdByLeg,
                                          Integer legIndex) {

        if (legIndex < 0)
            return null;

        Driver driverLeg = null;

        for (Driver driver : drivers) {

            if (Objects.equals(driver.getDriverId(),driverIdByLeg.get(legIndex))) {

                driverLeg = driver;
                break;
            }
        }

        return driverLeg == null ?
                getDriverInfoLeg(drivers, driverIdByLeg, legIndex - 1) : driverLeg;

    }


}
