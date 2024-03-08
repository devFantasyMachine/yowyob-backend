/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.*;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class DelegateItinerarySolver implements ItinerarySolver {

    ItinerarySolver defaultItinerarySolver = new DefaultItinerarySolver();
    ItinerarySolver localisationServiceItinerarySolver;


    public DelegateItinerarySolver(RestTemplate restTemplate){

        this.localisationServiceItinerarySolver = new LocalisationServiceItinerarySolver(restTemplate);
    }


    @Override
    public Itinerary<Leg> solve(ItinerarySolveContext context) {
        return defaultItinerarySolver.solve(context);
    }

}
