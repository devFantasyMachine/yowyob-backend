/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

public enum TripPrestige {

    /**
     *
     */
    CLASSIC("CLASSIC"), FAMILY("FAMILY"), GOLD("GOLD"), NORMAL("NORMAL"), PREMIUM("PREMIUM"), VIP("VIP");

    private final String prestige;

    TripPrestige(String prestige) {
        this.prestige = prestige;
    }

    @Override
    public String toString() {
        return prestige;
    }
}
