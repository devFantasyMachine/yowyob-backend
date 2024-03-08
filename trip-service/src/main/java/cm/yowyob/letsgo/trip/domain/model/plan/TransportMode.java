/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan;

import java.util.EnumSet;
import java.util.Set;

public enum TransportMode {

    UNKNOWN("UNKNOWN"),
    ANY_STREET_MODES("ANY_STREET_MODES"),
    RAIL("RAIL"),
    COACH("COACH"),
    SUBWAY("SUBWAY"),
    BUS("BUS"),
    TRAM("TRAM"),
    FERRY("FERRY"),
    AIRPLANE("AIRPLANE"),
    CABLE_CAR("CABLE_CAR"),
    GONDOLA("GONDOLA"),
    FUNICULAR("FUNICULAR"),
    TROLLEYBUS("TROLLEYBUS"),
    MONORAIL("MONORAIL"),

    CARPOOL("CARPOOL"),

    TAXI("TAXI");

    private static final Set<TransportMode> ON_STREET_MODES = EnumSet.of(
            COACH,
            BUS,
            TROLLEYBUS,
            CARPOOL,
            TAXI
    );

    private static final Set<TransportMode> NO_AIRPLANE_MODES = EnumSet.complementOf(
            EnumSet.of(AIRPLANE)
    );

    private static final Set<TransportMode> NO_CARPOOL_MODES = EnumSet.complementOf(
            EnumSet.of(CARPOOL)
    );

    public static Set<TransportMode> transitModesExceptAirplane() {
        return NO_AIRPLANE_MODES;
    }

    /**
     * This method returns the list of modes that are considered 'transit' by users, removing
     * carpool.
     */
    public static TransportMode[] modesConsideredTransitByUsers() {
        return NO_CARPOOL_MODES.toArray(TransportMode[]::new);
    }

    public boolean onStreet() {
        return ON_STREET_MODES.contains(this);
    }



    private final String mode;

    TransportMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return mode;
    }
}
