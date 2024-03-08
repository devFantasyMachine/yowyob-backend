/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.events.NotificationEvent;
import cm.yowyob.letsgo.trip.domain.events.NotificationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class PulsarNotificationEventPublisher implements NotificationEventPublisher {


    @Override
    public void publish(NotificationEvent letsgoNotificationEvent) {

    }



}
