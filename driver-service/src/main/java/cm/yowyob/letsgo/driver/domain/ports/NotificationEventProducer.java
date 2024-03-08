package cm.yowyob.letsgo.driver.domain.ports;


import cm.yowyob.letsgo.driver.domain.model.Notification;
import org.apache.pulsar.client.api.PulsarClientException;

public interface NotificationEventProducer {
    default void publish(Notification notification) throws Exception {
        throw new UnsupportedOperationException();
    }
}
