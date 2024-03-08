/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;


import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

/**
 * itinerary solver. It is object that solve itinerary for one planned trip.
 */
public interface ItinerarySolver {

    Itinerary<Leg> solve(ItinerarySolveContext context);

}

