/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 *//*


package cm.yowyob.letsgo.trip.domain.model.schedule;

import cm.yowyob.letsgo.trip.domain.events.CancelReservationEvent;
import cm.yowyob.letsgo.trip.domain.events.NotificationEventPublisher;
import cm.yowyob.letsgo.trip.domain.exceptions.ReservationNotFoundException;
import cm.yowyob.letsgo.trip.domain.managers.ReservationManager;
import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;
import lombok.NonNull;

import java.util.Objects;


public final class CancelReservationExecutor extends JobExecutorProvider<LockedTripResources> {

    private final NotificationEventPublisher notificationEventPublisher;
    private final ReservationManager reservationManager;

    public CancelReservationExecutor(@NonNull NotificationEventPublisher notificationEventPublisher,
                                     @NonNull ReservationManager reservationManager) {
        this.notificationEventPublisher = notificationEventPublisher;
        this.reservationManager = reservationManager;
    }


    @Override
    protected LockedTripResources getEntity(String entityId) throws ReservationNotFoundException {
        return reservationManager.getReservation(entityId);
    }


    @Override
    protected JobExecutorResponse executeInternal(LockedTripResources entity) throws Exception {

        if (entity.isApplicable()) {

            LockedTripResources reservation = entity.doBeforeApplication();
            reservation = reservationManager.save(reservation);

            reservationManager.freeUpResources(reservation);

            CancelReservationEvent letsgoNotificationEvent =
                    new CancelReservationEvent(reservation);

            notificationEventPublisher.publish(letsgoNotificationEvent);
        }

        entity.delete();
        return JobExecutorResponse.DELETE_JOB;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CancelReservationExecutor) obj;
        return Objects.equals(this.notificationEventPublisher, that.notificationEventPublisher) &&
                Objects.equals(this.reservationManager, that.reservationManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationEventPublisher, reservationManager);
    }

    @Override
    public String toString() {
        return "CancelReservationExecutor[" +
                "notificationEventPublisher=" + notificationEventPublisher + ", " +
                "reservationManager=" + reservationManager + ']';
    }

}
*/
