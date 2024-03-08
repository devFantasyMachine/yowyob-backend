/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.model.reservation.UnLockReason;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CancelReservationEvent extends NotificationEvent {

    private final String userId;
    private final String reservationId;
    private final String publishedTripId;
    private final String plannedTripId;
    private final LocalDateTime reservedAt;
    private final UnLockReason reason;

    public CancelReservationEvent(String userId, String reservationId, String publishedTripId, String plannedTripId, LocalDateTime reservedAt, UnLockReason reason) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.publishedTripId = publishedTripId;
        this.plannedTripId = plannedTripId;
        this.reservedAt = reservedAt;
        this.reason = reason;
    }


    @Override
    public String getReason() {
        return reason.toString();
    }
}
