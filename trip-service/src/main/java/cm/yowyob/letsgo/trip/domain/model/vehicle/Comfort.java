/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.vehicle;


import java.util.Locale;
import java.util.Objects;

public record Comfort(String name, String description) {

    public String getName() {
        return name.toUpperCase(Locale.ROOT);
    }

    public static Comfort of(String name){

        return new Comfort(Objects.requireNonNull(name), name);
    }

    @Override
    public String toString() {
        return "Comfort{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comfort) {
            return getName().equals(((Comfort) obj).name);
        }
        return false;
    }
}
