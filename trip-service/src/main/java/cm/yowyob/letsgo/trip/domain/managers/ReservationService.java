/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;
import cm.yowyob.letsgo.trip.domain.exceptions.ReservationNotFoundException;

import java.util.List;

public interface ReservationService {

    LockedTripResources save(LockedTripResources lockedTripResources);

    LockedTripResources getById(String reservationId)throws ReservationNotFoundException;

    List<LockedTripResources> getByReservationGroup(String reservationGroup);

}
