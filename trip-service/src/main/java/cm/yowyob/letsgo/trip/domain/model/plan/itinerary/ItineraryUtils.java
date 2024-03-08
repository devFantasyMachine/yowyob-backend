/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import cm.yowyob.letsgo.trip.domain.utils.DurationUtils;
import cm.yowyob.letsgo.trip.domain.utils.SphericalDistanceLibrary;
import lombok.NonNull;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;



/**
 *  This class is only for operation
 */
public abstract class ItineraryUtils {


    /**
     * Used to convert an itinerary to a SHORT human-readable string - including just a few of the
     * most important fields. It is much shorter and easier to read then the {@link Itinerary#toString()}.
     * <p>
     * It is great for comparing to itineraries in a test: {@code assertEquals(toStr(it1),
     * toStr(it2))}.
     * <p>
     * Example: {@code A ~ Walk 2m ~ B ~ BUS 55 12:04 12:14 ~ C [cost: 1066]}
     * <p>
     * Reads: Start at A, walk 2 minutes to stop B, take bus 55, board at 12:04 and alight at 12:14
     * ...
     */
    public static String toStr(Itinerary<? extends Leg> itinerary) {

        if (itinerary == null)
            return null;

        if (itinerary.length() == 0)
            return  "";

        StringBuilder buf = new StringBuilder();

        buf.append("[ ");
        buf.append(itinerary.firstLeg().getFromLocation().place().name())
                .append(" ");

        for (Leg oleg : itinerary.getLegs()) {

            buf.append("~");

            if (oleg instanceof PlannerLeg plannerLeg){

                buf.append(plannerLeg.getTransportMode().name());
            }

            buf.append("( ")
                    .append(DurationUtils.durationToStr(oleg.getTotalDuration()))
                    .append(" ) ");

            for (StopArrival intermediateStop : oleg.getIntermediateStops()) {

                buf.append(" ")
                        .append(intermediateStop.place().name())
                        .append("( ")
                        .append(DurationUtils.durationToStr(oleg.getTotalDuration()))
                        .append(" ) ");
            }

            buf.append(oleg.getToLocation().place().name())
                    .append(" ");
        }

        buf.append(']');

        return buf.toString();
    }


    /**
     *
     * @return the mean coordinate of itinerary, see (@code LetsGoCoordinate)
     */
    public static LetsGoCoordinate getMeanCoordinate(Itinerary<? extends Leg> itinerary){

        return LetsGoCoordinate
                .mean(itinerary.getLineString());

    }


    /**
     * Total distance in meters.
     */
    public static double getDistanceMeters (@NonNull Itinerary<? extends Leg> itinerary) {

        return itinerary.getLegs()
                .stream()
                // A none distance is -1
                .filter(l -> l.getDistanceMeters() > Leg.UNKNOWN + 1)
                .mapToDouble(Leg::getDistanceMeters)
                .sum();
    }


    public static <L extends Leg> Optional<L> getLegByPosition(@NonNull Itinerary<L> itinerary,
                                                               Integer pos) {

        if (itinerary.lastLeg().getFromLocation().stopPos() < pos)
            return Optional.empty();

        return itinerary.getLegs()
                .stream()
                .filter(transitLeg -> Objects.equals(transitLeg.getFromLocation().stopPos(), pos))
                .findAny() ;
    }


    /**
     * Duration of this itinerary.
     */
    public static Duration getTotalDuration(@NonNull Itinerary<? extends Leg> itinerary,
                                            boolean isTotalTransitDuration) {

        Duration totalDuration = null;

        for (Duration duration : itinerary.getLegs().stream()
                .map(isTotalTransitDuration ? Leg::getTransitDuration : Leg::getTotalDuration)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())) {

            if (totalDuration == null) totalDuration = Duration.ZERO;
            totalDuration = totalDuration.plus(duration);
        }

        return totalDuration;
    }



    public static double computeDirectDistanceMeters(@NonNull Leg from,
                                                     @NonNull Leg to){

        return computeDirectDistanceMeters(from.getFromLocation(), to.getToLocation());

    }


    public static double computeDirectDistanceMeters(@NonNull Stop from,
                                                     @NonNull Stop to){

        return computeDirectDistanceMeters(from.getLetsGoCoordinate(),
                to.getLetsGoCoordinate());

    }


    /**
     * compute direct distance meters
     *
     * @param from from coordinate
     * @param to to coordinate
     * @return double
     */
    public static double computeDirectDistanceMeters(@NonNull LetsGoCoordinate from,
                                                     @NonNull LetsGoCoordinate to){

        return SphericalDistanceLibrary
                .fastDistance(from.latitude(), from.longitude(), to.latitude(), to.longitude());

    }




}
