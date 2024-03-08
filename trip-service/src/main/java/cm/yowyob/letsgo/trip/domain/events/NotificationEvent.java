/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEvent;
import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEventType;

public class NotificationEvent implements LetsgoEvent {

    @Override
    public LetsgoEventType getType() {
        return null;
    }

    @Override
    public String getReason() {
        return null;
    }
}
