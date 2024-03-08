/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events.core;

public interface LetsgoEventPublisher<E extends LetsgoEvent> {

    void publish(E letsgoEvent);

}
