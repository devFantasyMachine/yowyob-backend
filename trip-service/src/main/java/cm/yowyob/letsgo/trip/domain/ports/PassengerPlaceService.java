/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.resources.PlannedSeat;

import java.util.Set;
import java.util.UUID;

public interface PassengerPlaceService {

    Set<PlannedSeat> findAllByIds(UUID planId, Set<String> placesIds);

    Set<PlannedSeat> save(UUID planId, String plannerId, Set<PlannedSeat> plannedSeats);
}
