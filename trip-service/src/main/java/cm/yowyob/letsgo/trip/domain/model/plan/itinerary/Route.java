/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.model.Money;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;


/**
 * StopLocation fromLocation The origin
 * <p>
 * StopLocation toLocation The destination
 */
@Data
@Builder
public final class Route {

    private final UUID routeId;
    private final StopLocation fromLocation;
    private final StopLocation toLocation;
    private final Double directDistanceMeters;
    private final Money standardCost;
    private final Long tripCount;
    private final Set<SimpleItinerary> itineraries;
    private final Set<TransportMode> transportMode;


    public Route(UUID routeId,
                 StopLocation fromLocation,
                 StopLocation toLocation,
                 Double directDistanceMeters,
                 Money standardCost,
                 Long tripCount,
                 Set<SimpleItinerary> itineraries,
                 Set<TransportMode> transportMode) {

        this.routeId = routeId == null ? UUID.randomUUID() : routeId;
        this.fromLocation = Objects.requireNonNull(fromLocation, "fromLocation must not be null");
        this.toLocation = Objects.requireNonNull(toLocation, "toLocation must not be null");
        this.standardCost = standardCost;
        this.tripCount = tripCount;
        this.itineraries = itineraries;
        this.transportMode = transportMode;
        this.directDistanceMeters = directDistanceMeters != null ? directDistanceMeters :
                ItineraryUtils.computeDirectDistanceMeters(fromLocation.coordinate(), toLocation.coordinate());
    }



}
