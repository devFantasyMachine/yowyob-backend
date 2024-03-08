/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.resources;

import java.util.EnumSet;

public enum ResourceType {

    DISCOUNT_TICKET("DISCOUNT_TICKET"),

    LUGGAGE("LUGGAGE"),

    SEAT("SEAT"),

    OPTION("OPTION");

    public static final EnumSet<ResourceType> ALL_TYPES =
            EnumSet.of( DISCOUNT_TICKET, LUGGAGE, SEAT, OPTION);

    private final String type;

    ResourceType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return type;
    }
}
