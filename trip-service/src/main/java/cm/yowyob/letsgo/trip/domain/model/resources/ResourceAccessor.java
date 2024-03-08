/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.resources;

import java.util.Set;

public interface ResourceAccessor {

    Set<TripResource> getAllResources();

    Set<TripResource> getAllResourcesByType(ResourceType type);

    Set<TripOption> getAllTripOptions();

    Set<PlannedSeat> getAllPassengerPlaces();

    TripResource getResourceById(ResourceType type, String resourceId);


}
