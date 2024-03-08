/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.search.SearchResult;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface SearchTripService {
    List<SearchResult> softSearch(ZonedDateTime departure, StopLocation fromLocation, StopLocation toLocation, Integer placeCount);

    List<SearchResult> fullSearch(ZonedDateTime departure, StopLocation fromLocation, Set<StopLocation> intermediateStops, StopLocation toLocation, Integer placeCount);

}
