package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.notification.Notification;

public interface NotificationProducer {
    default void produce(Notification notification){
        System.out.println(notification);
    }
}
