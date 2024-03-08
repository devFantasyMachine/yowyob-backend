/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.stops;


import lombok.NonNull;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;


/**
 *  a transit stop along the itinerary - ( lieux de collecte des passagers )
 */
public record Stop(StopLocation place,
                   Duration delay,
                   String platformCode,
                   Integer stopPos) implements Comparable<Stop> {

    public static final Stop NONE =
            new Stop(StopLocation.NONE,  Duration.ZERO, null, Integer.MAX_VALUE);


    public StopArrival toStopArrival(){

        return StopArrival.builder()
                .delay(delay)
                .withPlace(place)
                .withPos(stopPos)
                .build();
    }


    public boolean sameLocation(Stop other) {

        if (this == other) {
            return true;
        }
        if (other.place.coordinate() != null) {
            return other.place().isSameLocation(other.place());
        }
        return stopPos != null && stopPos.equals(other.stopPos);
    }


    public LetsGoCoordinate getLetsGoCoordinate(){

        return place.coordinate();
    }


    public @NonNull static StopBuilder builder() {

        return new StopBuilder();
    }

    public static Stop of(StopLocation stopLocation, Integer pos) {

        if (stopLocation == null)
            return null;

        return Stop.builder()
                .withDelay(Duration.ZERO)
                .withPlace(stopLocation)
                .withPos(pos)
                .build();
    }

    public static Stop of(StopLocation stopLocation, Integer pos, Duration delay) {

        if (stopLocation == null)
            return null;

        return Stop.builder()
                .withDelay(delay)
                .withPlace(stopLocation)
                .withPos(pos)
                .build();
    }


    @Override
    public int compareTo(@NonNull Stop o) {

        return this.stopPos.compareTo(o.stopPos);
    }

    public Stop withDelay(Duration placeDelay) {

        return new StopBuilder(this)
                .withDelay(placeDelay)
                .build();

    }

    public Stop addDeltaPos(Integer deltaPos) {

        return withPos(stopPos + deltaPos);
    }

    public Stop withPos(int pos) {

        return new StopBuilder(this)
                .withPos(pos)
                .build();
    }


    public static class StopBuilder {

        StopLocation place;
        Duration delay;
        String platformCode;
        Integer stopPos;


        StopBuilder(){}


        StopBuilder(@NonNull Stop stop){
            this.stopPos = stop.stopPos;
            this.delay = stop.delay;
            this.place = stop.place;
        }

        public StopBuilder code(String code) {
            this.platformCode = code;
            return this;
        }

        public StopBuilder withPos(Integer stopPos){
            this.stopPos = Objects.requireNonNull(stopPos);
            return this;
        }

        public StopBuilder withDelay(Duration duration){
            this.delay = duration;
            return this;
        }

        public StopBuilder withPlace(StopLocation stopLocation) {
            place = stopLocation;
            return this;
        }

        public Stop build() {
            Objects.requireNonNull(stopPos);
            Objects.requireNonNull(place);

            return new Stop(place, delay, platformCode, stopPos);
        }

        public String toString() {
            return "Stop.StopBuilder()";
        }

    }

    @Override
    public String toString() {
        return "Stop{" +
                "place=" + place +
                ", stopPos=" + stopPos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop stop)) return false;
        return place.equals(stop.place) && stopPos.equals(stop.stopPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, stopPos);
    }
}
