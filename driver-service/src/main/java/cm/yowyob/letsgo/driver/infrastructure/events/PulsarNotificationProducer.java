package cm.yowyob.letsgo.driver.infrastructure.events;

import cm.yowyob.letsgo.driver.domain.model.Notification;
import cm.yowyob.letsgo.driver.domain.model.NotificationSeverity;
import cm.yowyob.letsgo.driver.domain.model.NotificationType;
import cm.yowyob.letsgo.driver.domain.ports.NotificationEventProducer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class PulsarNotificationProducer implements NotificationEventProducer {

    private static final String NORMAL_NOTIFICATION_TOPIC = "normal-incoming-notifications-topic";
    private static final String HIGHER_NOTIFICATION_TOPIC = "higher-incoming-notifications-topic";



    @Autowired
    private PulsarTemplate<Notification> notificationPulsarTemplate;


    @Override
    public void publish(Notification notification) throws PulsarClientException {

        notificationPulsarTemplate.send(HIGHER_NOTIFICATION_TOPIC,  notification, Schema.JSON(Notification.class));

        notificationPulsarTemplate.send(HIGHER_NOTIFICATION_TOPIC, notification);
    }



}
