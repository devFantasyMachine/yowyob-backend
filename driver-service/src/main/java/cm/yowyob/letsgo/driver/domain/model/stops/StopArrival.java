/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model.stops;


import lombok.NonNull;

import java.util.Objects;


public record StopArrival(StopLocation place,
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
        Integer stopPos;

        StopArrivalBuilder(){}

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

            return new StopArrival(place, stopPos);
        }


        public String toString() {
            return "Stop.StopBuilder()";
        }


    }




}
