/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleItinerary extends Itinerary<Leg> {

    public  SimpleItinerary(Collection<Leg> legs) {
        super(legs);
    }


    public static SimpleItinerary from(Collection<Leg> legs) {

        if (legs == null)
            throw new IllegalArgumentException();

        return new SimpleItinerary(legs);
    }


}
