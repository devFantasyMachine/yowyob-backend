/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.config;/*
package cm.yowyob.letsgo.trip.application.config;


import cm.enspy.gi.project.trip_service.models.Notification;
import lombok.SneakyThrows;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {


    @Value("${pulsar.service.url:pulsar://localhost:6650}")
    String pulsarUrl;

    @Value("${pulsar.service.notification.topic:notification_topic}")
    String notification_topic;

    @Value("${pulsar.service.producer_name:notification_producer}")
    String producer_name;


    @SneakyThrows
    @Bean
    PulsarClient buildClient() {
        PulsarClient client = PulsarClient.simpleDraftBuilder()
                .serviceUrl(pulsarUrl)
                .build();
        return client;
    }


    @Bean
    Producer<Notification> producer(PulsarClient client) throws PulsarClientException {

        return client.newProducer(Schema.AVRO(Notification.class))
                .topic(notification_topic)
                .producerName(producer_name)
                .create();
    }




}




 */
