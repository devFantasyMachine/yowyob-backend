/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEventPublisher;

public interface NotificationEventPublisher extends LetsgoEventPublisher<NotificationEvent> {

    void publish(NotificationEvent letsgoNotificationEvent);

}
