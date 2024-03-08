/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;


import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;





/**
 * An is one complete way of getting from the start location to the end location. see Leg, {@link Leg}
 */
@Setter
@Getter
public class Itinerary<L extends Leg> {

    private UUID planId;

    /** The set of leg */
    final TreeSet<L> legs;

    /** direct distance in meter */
    private double directDistanceMeters = Leg.UNKNOWN;

    /** the mean coordinate of itinerary, see {@link LetsGoCoordinate} */
    private LetsGoCoordinate meanCoordinate = LetsGoCoordinate.NONE;

    /** Total distance in meters. */
    private double distanceMeters = Leg.UNKNOWN;

    /** Duration of the trip on this itinerary. */
    private Duration totalDuration = null;

    /** Duration of the trip on this itinerary (only for transit leg). */
    private Duration totalTransitDuration = null;


    private ItineraryStatus status;


    ItineraryState<L> state;


    public Itinerary() {
        this.legs = new TreeSet<>();
        this.state = new ItineraryStateDrafted<L>(this);
    }

    public Itinerary(Collection<L> legs) {
        this(legs, null);
    }

    public Itinerary(Collection<L> legs, ZonedDateTime departure) {

        this.legs = new TreeSet<>(Objects.requireNonNullElse(legs, new TreeSet<>()));

        if (departure != null)
            setDeparture(departure);
    }

    private void setDeparture(ZonedDateTime departure) {

        if (departure == null)
            return;

        Iterator<L> lIterator = this.legs.iterator();

        if (!lIterator.hasNext())
            return;

        Leg prev = lIterator.next();
        prev.setEstimateDeparture(departure);

        while (lIterator.hasNext()) {

            Leg follow = lIterator.next();

            if (!prev.canEstimateArrivalDatetime())
                break;

            follow.setEstimateDeparture(prev.getEstimateArrival().plus(prev.getToLocation().delay()));

            prev = follow;
        }
    }


    public L firstLeg() {
        return legs.first();
    }

    public L lastLeg() {
        return legs.last();
    }

    public void addLeg(@NonNull L leg) {
        legs.add(leg);
        resetDetails();
    }

    public void addLegs(@NonNull Set<L> legs) {

        this.legs.addAll(legs);
        resetDetails();
    }



    public void replaceLeg(L oldLeg, L newLeg) throws IllegalReplaceLegOperationException, LegNotFoundException {

        this.state.replaceLeg(oldLeg, newLeg);
    }


    /**
     * merge legs
     *
     * @param legIndex1 legIndex1, index of first leg
     * @param legIndex2 legIndex2, index of second leg
     * <p></p>
     * Note that : index2 must be greater than index1
     * @throws IllegalMergeAttemptException cm.yowyob.letsgo.trip.domain.exceptions. illegal merge attempt exception
     */
    public void mergeLegs(Integer legIndex1, Integer legIndex2) throws IllegalMergeAttemptException {

        this.state.mergeLegs(legIndex1, legIndex2);
    }

    public void publish() throws IllegalItineraryPublicationException, ItineraryAlreadyPublishedException {
        this.state.publish();
    }

    public void plan() throws ItineraryAlwaysPlannedException {
        this.state.plan();
    }


    public void removeLeg(@NonNull L otherLeg) throws IllegalRemoveLegOperationException, LegNotFoundException {
        this.state.removeLeg(otherLeg);
    }


    public void removeLegs(Collection<L> otherLegs){
        this.state.removeLegs(otherLegs);
    }



    public Itinerary<L> addItinerary(@NonNull Itinerary<L>  otherItinerary) {
        addLegs(otherItinerary.getLegs());
        resetDetails();
        return this;
    }

    void resetDetails() {
        this.meanCoordinate = LetsGoCoordinate.NONE;
        this.distanceMeters = Leg.UNKNOWN;
        this.directDistanceMeters = Leg.UNKNOWN;
        this.totalDuration = Duration.ZERO;
        this.totalTransitDuration = Duration.ZERO;
    }

    public Integer length() {
        return legs.size();
    }



    public LetsGoCoordinate getMeanCoordinate() {

        if (this.meanCoordinate == LetsGoCoordinate.NONE)
            this.meanCoordinate = ItineraryUtils.getMeanCoordinate(this);

        return this.meanCoordinate;

    }

    public double getDirectDistanceMeters() {

        if (this.directDistanceMeters == Leg.UNKNOWN){

            this.directDistanceMeters = ItineraryUtils.computeDirectDistanceMeters(firstLeg(), lastLeg());
        }

        return this.directDistanceMeters;
    }

    public double getDistanceMeters() {

        if (this.distanceMeters == Leg.UNKNOWN)
            this.distanceMeters = ItineraryUtils.getDistanceMeters(this);

        return this.distanceMeters;
    }

    public Duration getTotalDuration() {

        if (this.totalDuration == null)
            this.totalDuration = ItineraryUtils.getTotalDuration(this, true);

        return this.totalDuration;
    }

    public Duration getTotalTransitDuration() {

        if (this.totalTransitDuration == null)
            this.totalTransitDuration = ItineraryUtils.getTotalDuration(this, false);

        return this.totalTransitDuration;
    }

    /** The leg's waiting duration in seconds */
    public Duration getWaitingDuration() {
        return getTotalDuration() == null ? null : getTotalDuration().minus(getTotalTransitDuration());
    }


    /**
     * Number of transfers.
     * @return Integer
     */
    public int getNumberOfTransfers() {

        Iterator<L> lIterator = this.getLegs().descendingIterator();

        if (!lIterator.hasNext())
            return 0;

        int numberOfTransfers = 0; Leg prev = lIterator.next();

        while (lIterator.hasNext()) {

            Leg follow = lIterator.next();

            if (prev.isNonPartiallySameLegs(follow))
                ++numberOfTransfers;

            prev = follow;
        }

        return numberOfTransfers;
    }




    /**
     * The geometry leg
     *
     * @return {@code LineString}
     */
    public List<LetsGoCoordinate> getLineString() {

        return getLegs()
                .stream()
                .sorted(Comparator.naturalOrder())
                .map(Leg::getLegGeometry)
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }


    /**
     * @return all stop place, {@link StopLocation}
     */
    public TreeSet<Stop> getStops() {

        List<Stop> stops = new ArrayList<>();
        stops.add(firstLeg().getFromLocation());

        this.getLegs()
                .stream()
                .sorted(Comparator.naturalOrder())
                .map(Leg::getToLocation)
                .forEach(stops::add);

        return new TreeSet<>(stops);
    }

    /**
     * Used to convert an itinerary to a SHORT human-readable string - including just a few of the
     * most important fields. It is much shorter and easier to read then the {@link
     * Itinerary#toString()}.
     * <p>
     * It is great for comparing to itineraries in a test: {@code assertEquals(toStr(it1),
     * toStr(it2))}.
     * <p>
     * Example: {@code A ~ Walk 2m ~ B ~ BUS 55 12:04 12:14 ~ C [cost: 1066]}
     * <p>
     * Reads: Start at A, walk 2 minutes to stop B, take bus 55, board at 12:04 and alight at 12:14
     * ...
     */
    public String toStr() {

        return ItineraryUtils.toStr(this);
    }

    public L getLeg(Integer integer) {

        return ItineraryUtils.getLegByPosition(this, integer)
                .orElse(null);
    }


    /**
     * with position shift. new position = old position + pos
     *
     * @param pos position to add
     * @return {@link Itinerary}
     * @see Itinerary
     * @see Leg
     */
    @SuppressWarnings("unchecked")
    public Itinerary<L> shiftPosition(Integer pos) {

        Set<L> legs = getLegs()
                .stream()
                .map(leg -> (L)leg.withPositionShift(pos))
                .collect(Collectors.toSet());

        return new Itinerary<L>(legs);
    }


    public StopLocation getFromLocation() {
        return firstLeg().getFromLocation().place();
    }


    public StopLocation getToLocation() {
        return lastLeg().getToLocation().place();
    }


    public boolean isEmpty() {
        return legs.isEmpty();
    }

    public boolean contains(Leg plannerLeg) {
        return legs.contains(plannerLeg) || getLeg(plannerLeg.getIndex()) != null;
    }



}
