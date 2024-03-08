/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model;

import lombok.Getter;

import java.util.EnumSet;


@Getter
public enum TaskType {

    SLEEP("SLEEP"), OTHER("OTHER"), NONE("NONE"),
    COURSE("COURSE"), COLLECT("COLLECT"), DEPOT("DEPOT"),
    CO("CO"), TOURISM("TOURISM");

    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }


    public static final EnumSet<TaskType> NON_SINGLE_POOLER = EnumSet.of(COLLECT, TOURISM, CO);

    public boolean isNonSinglePooler() {

        return NON_SINGLE_POOLER.contains(this);
    }
}
