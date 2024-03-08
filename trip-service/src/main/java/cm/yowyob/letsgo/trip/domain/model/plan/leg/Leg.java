/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.leg;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalMergeAttemptException;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryUtils;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;



/**
 * One leg of a trip -- that is, a temporally continuous piece of the journey that takes place on a
 * particular vehicle using mainly a single mode
 */
@Setter
@Getter
public class Leg implements Comparable<Leg> {

    /** For unknown value */
    public static final int UNKNOWN = -1;

    /** The Stop Place where the leg originates. */
    private final Stop fromLocation;

    /** The Stop Place where the leg begins. */
    private final Stop toLocation;

    /**
     * Intermediate stops between the Place where the leg originates and the Place
     * where the leg ends.
     */
    private SortedSet<StopArrival> intermediateStops;

    /** Duration of transit */
    private final Duration transitDuration;

    /** Total duration */
    private final Duration totalDuration;

    /** The distance traveled while traversing the leg in meters. */
    private final Double distanceMeters;

    /** The direct distance traveled while traversing the leg in meters. */
    private final Double directDistanceMeters;

    /**  The estimate departure of the leg */
    private ZonedDateTime estimateDeparture;



    public Leg(Stop fromLocation,
               Stop toLocation,
               SortedSet<StopArrival> intermediateStops,
               Duration transitDuration,
               Double distanceMeters,
               Double directDistanceMeters,
               ZonedDateTime estimateDeparture) {


        this.fromLocation = Objects.requireNonNull(fromLocation, "fromLocation must not be null");
        this.toLocation = Objects.requireNonNull(toLocation, "toLocation must not be null");

        this.intermediateStops = Objects.requireNonNullElse(intermediateStops, new TreeSet<>());

        this.transitDuration = transitDuration;
        this.totalDuration = transitDuration == null ? null : transitDuration.plus(getToLocation().delay());
        this.distanceMeters = distanceMeters == null ? UNKNOWN : distanceMeters;
        this.estimateDeparture = estimateDeparture;

        this.directDistanceMeters =
                Objects.requireNonNullElse(directDistanceMeters, ItineraryUtils.computeDirectDistanceMeters(fromLocation, toLocation));
    }


    public Leg(Stop fromLocation, Stop toLocation) {
        this(fromLocation, toLocation, null, null, null, null, null);
    }


    @Override
    public int compareTo(@NonNull Leg o) {

        return Objects.compare(fromLocation, o.fromLocation, Stop::compareTo);
    }


    /**
     * shift pos to currentPos + deltaPos
     *
     * @param deltaPos the delta position to add on current position
     */
    public Leg withPositionShift(Integer deltaPos) {

        return new Leg(fromLocation.addDeltaPos(deltaPos),
                toLocation.addDeltaPos(deltaPos),
                intermediateStops,
                transitDuration,
                distanceMeters,
                directDistanceMeters,
                estimateDeparture);
    }


    public List<LetsGoCoordinate> getIntermediateCoordinates() {

        return intermediateStops.stream()
                .sorted(Comparator.naturalOrder())
                .map(StopArrival::place)
                .map(StopLocation::coordinate)
                .collect(Collectors.toList());
    }


    /**
     * The leg's geometry.
     */
    public List<LetsGoCoordinate> getLegGeometry() {

        List<LetsGoCoordinate> coordinates =
                new ArrayList<>(List.of(getFromLocation().getLetsGoCoordinate()));

        coordinates.addAll(getIntermediateCoordinates());
        coordinates.add(getToLocation().getLetsGoCoordinate());

        return coordinates;
    }


    @Override
    public String toString() {

        return "Leg{" +
                "fromLocation=" + fromLocation +
                ", toLocation=" + toLocation +
                '}';
    }

    /**
     * replace to location
     *
     * @param toLocation toLocation
     * @return {@link Leg}
     * @see Leg
     */
    public Leg replaceToLocation(Stop toLocation) {

        return new Leg(fromLocation,
                toLocation,
                intermediateStops,
                transitDuration,
                distanceMeters,
                directDistanceMeters,
                estimateDeparture);
    }


    /**
     * Get the index of a leg when you want to reference it in an API response, for example when you
     * want to say that a fare is valid for legs 3.
     * <p>
     * Return {@code #Leg.UNKNOWN}. if not available
     */
    public Integer getIndex() {
        return fromLocation.stopPos() == null ? Leg.UNKNOWN : fromLocation.stopPos();
    }


    /**
     * is non partially same legs
     *
     * @param otherLeg otherLeg
     * @return {@link Boolean}
     */
    public boolean isNonPartiallySameLegs(Leg otherLeg) {

        return !this.getClass().isInstance(otherLeg);
    }

    public void setEstimateDeparture(ZonedDateTime estimateDeparture) {

        this.estimateDeparture = estimateDeparture;
    }

    public ZonedDateTime getEstimateArrival() {

        if (canEstimateArrivalDatetime())
            return estimateDeparture.plus(transitDuration);

        return null;
    }

    public boolean canEstimateArrivalDatetime() {
        return transitDuration != null;
    }


    public <L extends Leg> Leg merge(L leg2) throws IllegalMergeAttemptException {


        SortedSet<StopArrival> stopArrivals = new TreeSet<>(intermediateStops);
        stopArrivals.addAll(leg2.getIntermediateStops());

        return new Leg(
                fromLocation,
                leg2.getToLocation(),
                stopArrivals,
                transitDuration == null ? null :
                        transitDuration.plus(Objects.requireNonNullElse(leg2.getTransitDuration(), Duration.ZERO)),
                distanceMeters == null ? null :
                        distanceMeters + Objects.requireNonNullElse(leg2.getDistanceMeters(), (double) 0),
                ItineraryUtils.computeDirectDistanceMeters(fromLocation, leg2.getToLocation()),
                estimateDeparture
        );
    }

    public boolean isReserved() {
        return false;
    }



    public Leg createNext(Stop newStop) {

        return  new Leg(
                toLocation,
                newStop,
                null,
                null,
                null,
                ItineraryUtils.computeDirectDistanceMeters(toLocation, newStop),
                estimateDeparture
        );
    }


    public Leg subLeg(Stop newStop) {

        return new Leg(fromLocation,
                newStop,
                null,
                null,
                null,
                ItineraryUtils.computeDirectDistanceMeters(fromLocation, newStop),
                null);
    }


    public void addArrivals(List<StopArrival> stopArrivals) {

        if (intermediateStops == null)
            intermediateStops = new TreeSet<>();

        intermediateStops.addAll(stopArrivals);
    }

}
