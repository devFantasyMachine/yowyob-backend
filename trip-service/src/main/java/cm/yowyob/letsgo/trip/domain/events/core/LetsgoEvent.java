/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events.core;

public interface LetsgoEvent {

    LetsgoEventType getType();

    String getReason();

    default String eventId(){return "";}

    default String group(){return "";}

    default String jsonContent(){return "";}

    default String userId(){return "";}

    default EventPriority priority(){return EventPriority.NORMAL;}

}
