/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DefaultItinerarySolver implements ItinerarySolver {


    public Itinerary<Leg> resolveSingleLegItinerary(StopLocation fromPlace,
                                                    StopLocation toPlace,
                                                    List<Stop> stops) {

        Leg firstLeg = new Leg(Stop.of(fromPlace, 0), Stop.of(toPlace, 1));

        if (stops != null && !stops.isEmpty()) {

            firstLeg
                    .addArrivals(stops
                            .stream()
                            .map(Stop::toStopArrival)
                            .collect(Collectors.toList()));
        }

        HashSet<Leg> plannerLegs = new HashSet<>();
        plannerLegs.add(firstLeg);

        return new Itinerary<>(plannerLegs);
    }


    @Override
    public Itinerary<Leg> solve(ItinerarySolveContext context) {

        if (context.stops == null || context.stops.isEmpty() || context.solveType == ItinerarySolveContext.SolveType.SINGLE_LEG)
            return resolveSingleLegItinerary(context.fromLocation, context.toLocation, context.stops);


        TreeSet<Leg> legs = new TreeSet<>();

        Leg firstLeg = new Leg(Stop.of(context.fromLocation, 0), context.stops.get(0).withPos(1));
        legs.add(firstLeg);

        for (int i = 0; i < context.stops.size() - 1; i++) {

            Leg leg = new Leg(legs.last().getToLocation(), context.stops.get(i + 1).withPos(i + 2));
            legs.add(leg);
        }

        Leg lastLeg = new Leg(legs.last().getToLocation(),
                Stop.of(context.toLocation, context.stops.size() + 1));
        legs.add(lastLeg);

        return new Itinerary<>(legs);
    }





}

