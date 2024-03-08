/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.stops;


import lombok.NonNull;


import java.time.Duration;
import java.util.Objects;

/**
 * Lieu d'arrêt provisoire ou changement de vehicle
 */
public record StopArrival(StopLocation place,
                          Duration delay,
                          Integer stopPos) implements Comparable<StopArrival> {

    @Override
    public int compareTo(@NonNull StopArrival o) {

        return this.stopPos
                .compareTo(o.stopPos);
    }


    public @NonNull static StopArrivalBuilder builder() {

        return new StopArrivalBuilder();
    }

    public static class StopArrivalBuilder {

        StopLocation place;
        Duration delay;
        Integer stopPos;


        StopArrivalBuilder(){}


        public StopArrivalBuilder delay(Duration delay) {
            this.delay = delay;
            return this;
        }

        public StopArrivalBuilder withPos(Integer stopPos){
            this.stopPos = Objects.requireNonNull(stopPos);
            return this;
        }

        public StopArrivalBuilder withPlace(StopLocation stopLocation) {
            place = stopLocation;
            return this;
        }

        public StopArrival build() {
            Objects.requireNonNull(stopPos);
            Objects.requireNonNull(place);

            return new StopArrival(place, delay, stopPos);
        }

        public String toString() {
            return "Stop.StopBuilder()";
        }

    }




}
