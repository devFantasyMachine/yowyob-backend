/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import java.time.ZonedDateTime;

public class PlanResolveRequestEvent {

    private String draftId;
    private ZonedDateTime minStartTime;
    private ZonedDateTime maxStartTime;

}
