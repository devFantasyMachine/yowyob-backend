/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import java.util.EnumSet;

public enum TripType {

    ALONE("ALONE"),
    COURSE("COURSE"),
    COLLECT("COLLECT"),
    DEPOT("DEPOT"),

    TOURISM("TOURISM");


    private final String type;

    TripType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }


    public static final EnumSet<TripType> NON_SINGLE_POOLER = EnumSet.of(COLLECT, TOURISM);

    public boolean isNonSinglePooler() {

        return NON_SINGLE_POOLER.contains(this);
    }


}
