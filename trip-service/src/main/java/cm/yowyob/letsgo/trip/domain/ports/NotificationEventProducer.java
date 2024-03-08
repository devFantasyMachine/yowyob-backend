package cm.yowyob.letsgo.trip.domain.ports;


import cm.yowyob.letsgo.trip.domain.model.Notification;

public interface NotificationEventProducer {
    default void publish(Notification notification) throws Exception {
        throw new UnsupportedOperationException();
    }
}
