/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.events.core.EventPriority;
import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEvent;
import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEventType;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.search.SearchContext;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
public class PoolerRequestEvent implements LetsgoEvent {

    String userId;
    Integer placeCount;
    ZonedDateTime departure;
    StopLocation fromLocation;
    StopLocation toLocation;
    Set<StopLocation> intermediateStops;


    String timezone;
    TripType tripType;
    TripPrestige tripPrestige;
    Float cost;
    Set<TransportMode> transportModes;
    Float luggageQuantity;
    String plannerName;
    EventPriority eventPriority;

    public SearchContext getSearchContext() {

        return SearchContext.builder()
                .departure(departure)
                .fromLocation(fromLocation)
                .toLocation(toLocation)
                .intermediateStops(intermediateStops)
                .placeCount(placeCount)
                .build();
    }




    @Override
    public LetsgoEventType getType() {
        return null;
    }

    @Override
    public String getReason() {
        return null;
    }

    @Override
    public String group() {
        return "pooler.request";
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public EventPriority priority() {
        return eventPriority;
    }


}
