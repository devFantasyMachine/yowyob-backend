/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.reservation;

import lombok.Getter;

@Getter
public enum UnLockReason {

    POOLER_REQUEST("POOLER-REQUEST"),

    PLANNER_REQUEST("PLANNER-REQUEST"),

    TIMEOUT("TIMEOUT"),

    OTHER("OTHER"),
    ;

    private final String value;

    UnLockReason(String value) {
        this.value = value;
    }

}
