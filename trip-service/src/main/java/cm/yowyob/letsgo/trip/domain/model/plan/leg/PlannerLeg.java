/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.leg;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalMergeAttemptException;
import cm.yowyob.letsgo.trip.domain.model.Driver;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.SortedSet;

/**
 *  The portion of one itinerary
 */
@Getter
@Setter
public final class PlannerLeg extends Leg {

    private final Vehicle vehicle;
    private final Driver driver;
    private Boolean isInSameVehicleAsPrevious;


    public TransportMode getTransportMode() {
        return vehicle == null ? TransportMode.UNKNOWN : vehicle.getTransportMode();
    }


    public PlannerLeg(Stop fromLocation,
                      Stop toLocation,
                      SortedSet<StopArrival> intermediateStops,
                      Duration transitDuration,
                      Double distanceMeters,
                      Double directDistanceMeters,
                      Vehicle vehicle,
                      Driver driver,
                      ZonedDateTime departure) {

        super(fromLocation, toLocation, intermediateStops, transitDuration, distanceMeters, directDistanceMeters, departure);
        this.driver = driver == null ? Driver.NONE : driver;
        this.vehicle = vehicle == null ? Vehicle.NONE : vehicle;
    }



    public PlannerLeg createNext(Stop newStop) {

        return PlannerLeg.builder()
                .fromLocation(getToLocation())
                .toLocation(newStop)
                .transportMode(getTransportMode())
                .build();
    }

    public PlannerLeg subLeg(Stop newStop) {

        return PlannerLeg.builder()
                .fromLocation(getFromLocation())
                .toLocation(newStop)
                .transportMode(getTransportMode())
                .build();
    }




    public static PlannerLeg.PlannerLegBuilder builder() {
        return new PlannerLegBuilder();
    }

    public static PlannerLeg.PlannerLegBuilder fromPlannerLegBuilder(@NonNull PlannerLeg plannerLeg) {
        return new PlannerLegBuilder(plannerLeg);
    }

    public static PlannerLeg.PlannerLegBuilder fromSimpleLegBuilder(@NonNull Leg leg) {

        return PlannerLeg.builder()
                .fromLocation(leg.getFromLocation())
                .toLocation(leg.getToLocation())
                .distanceMeters(leg.getDistanceMeters())
                .intermediateStops(leg.getIntermediateStops())
                .directDistanceMaters(leg.getDirectDistanceMeters())
                .transitDuration(leg.getTransitDuration())
                .departure(leg.getEstimateDeparture());
    }


    public static PlannerLeg fromSimpleLeg(Leg leg) {

        if (leg == null)
            return null;

        return PlannerLeg.fromSimpleLegBuilder(leg)
                .build();

    }

    @Override
    public boolean isNonPartiallySameLegs(Leg otherLeg) {

        return super.isNonPartiallySameLegs(otherLeg) ||
                !Objects.equals(this.vehicle, ((PlannerLeg) otherLeg).vehicle);
    }


    public PlannerLeg changeTransportInformation(Vehicle vehicle, Driver driver) {

        PlannerLegBuilder plannerLegBuilder = fromPlannerLegBuilder(this);

        plannerLegBuilder.driverInfo(driver);

        if (vehicle != null)

            plannerLegBuilder.vehicleInfo(vehicle)
                        .transportMode(vehicle.getTransportMode());

         return plannerLegBuilder.build();
    }


    public PlannerLeg changeTransportInformation(Driver driver){
        return changeTransportInformation(null, driver);
    }

    public PlannerLeg changeTransportInformation(Vehicle vehicle){
        return changeTransportInformation(vehicle, null);
    }



    @Override
    public PlannerLeg withPositionShift(Integer deltaPos) {

        return PlannerLeg.fromPlannerLegBuilder(this)
                .fromLocation(getFromLocation().addDeltaPos(deltaPos))
                .toLocation(getToLocation().addDeltaPos(deltaPos))
                .build();
    }



    // TODO: 12/06/2023
    public PlannerLeg merge(Leg leg) throws IllegalMergeAttemptException {

        if (!(leg instanceof PlannerLeg plannerLeg))
            throw new IllegalMergeAttemptException();

        return PlannerLeg.fromSimpleLegBuilder(super.merge(plannerLeg))
                .vehicleInfo(vehicle)
                .driverInfo(driver)
                .build();
    }


    @Override
    public PlannerLeg replaceToLocation(Stop toLocation) {

        return PlannerLeg.fromPlannerLegBuilder(this)
                .toLocation(toLocation)
                .build();
    }


    public static class PlannerLegBuilder {

        private Driver driver;
        private Vehicle vehicle;
        private Stop fromLocation;
        private Stop toLocation;
        private SortedSet<StopArrival> intermediateStops;
        private Duration transitDuration;
        private Double distanceMeters;
        private TransportMode transportMode;
        private Double directDistanceMaters;
        private ZonedDateTime departure;

        PlannerLegBuilder() {
        }


        PlannerLegBuilder(@NonNull PlannerLeg plannerLeg) {
            this.vehicle = plannerLeg.vehicle;
            this.driver = plannerLeg.driver;
            this.transportMode = plannerLeg.getTransportMode();
            this.fromLocation = plannerLeg.getFromLocation();
            this.toLocation = plannerLeg.getToLocation();
            this.distanceMeters = plannerLeg.getDistanceMeters();
            this.transitDuration = plannerLeg.getTransitDuration();
            this.intermediateStops = plannerLeg.getIntermediateStops();
            this.directDistanceMaters = plannerLeg.getDirectDistanceMeters();
            this.departure = plannerLeg.getEstimateDeparture();
        }


        public PlannerLegBuilder departure(ZonedDateTime departure) {
            this.departure = departure;
            return this;
        }

        public PlannerLegBuilder transitDuration(Duration transitDuration) {
            this.transitDuration = transitDuration;
            return this;
        }


        public PlannerLegBuilder directDistanceMaters(Double directDistanceMaters) {
            this.directDistanceMaters = directDistanceMaters;
            return this;
        }

        public PlannerLegBuilder intermediateStops(SortedSet<StopArrival> intermediateStops) {
            this.intermediateStops = intermediateStops;
            return this;
        }

        public PlannerLegBuilder fromLocation(Stop fromLocation) {
            this.fromLocation = Objects.requireNonNullElse(fromLocation, this.fromLocation);
            return this;
        }


        public PlannerLegBuilder toLocation(Stop toLocation) {
            this.toLocation = Objects.requireNonNullElse(toLocation, this.toLocation);
            return this;
        }

        public PlannerLegBuilder transportMode(TransportMode transportMode) {
            this.transportMode = Objects.requireNonNullElse(transportMode, this.transportMode);
            return this;
        }

        public PlannerLegBuilder driverInfo(Driver driver) {
            this.driver = Objects.requireNonNullElse(driver, this.driver);
            return this;
        }

        public PlannerLegBuilder distanceMeters(Double distanceMeters){
            this.distanceMeters = distanceMeters;
            return this;
        }

        public PlannerLegBuilder vehicleInfo(Vehicle noVehicle) {
            this.vehicle = Objects.requireNonNullElse(noVehicle, this.vehicle);
            return this;
        }


        public PlannerLeg build() {

            return new PlannerLeg(fromLocation,
                    toLocation,
                    intermediateStops,
                    transitDuration,
                    distanceMeters,
                    directDistanceMaters,
                    vehicle,
                    driver,
                    departure);
        }


        public String toString() {
            return "PlannerLeg.PlannerLegBuilder(noDriver=" + this.driver + ", noVehicle=" + this.vehicle + ")";
        }


    }


}
