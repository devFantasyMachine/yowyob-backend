/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolver;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolveContext;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;


@AllArgsConstructor
public class LocalisationServiceItinerarySolver implements ItinerarySolver {

    private final RestTemplate restTemplate;


    @Override
    public Itinerary<Leg> solve(ItinerarySolveContext context) {
        return null;
    }



}
