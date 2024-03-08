/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;


import lombok.Getter;


@Getter
public enum SubscriberType {


    PLANNER("PLANNER"),
    POLLER("POOLER"),
    DRIVER("DRIVER");


    private final String type;

    SubscriberType(String type) {
        this.type = type;
    }
}
