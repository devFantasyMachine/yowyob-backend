/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.stops;

import lombok.NonNull;

import java.time.ZoneId;
import java.util.Objects;



/**
 * A Place is where a journey starts or ends, or a transit stop along the way.
 */
public record StopLocation(String name,
                           String city,
                           ZoneId timezone,
                           LetsGoCoordinate coordinate) {


    public static final StopLocation NONE =
            new StopLocation("","", ZoneId.systemDefault(), LetsGoCoordinate.GREENWICH);



    public boolean isSameLocation(StopLocation other) {

        if (coordinate == null)
            return false;

        return name.equals(other.name) &&
                coordinate.sameLocation(other.coordinate);
    }


    public @NonNull static StopLocationBuilder builder() {
        return new StopLocationBuilder();
    }

    public static class StopLocationBuilder {

        private String name;
        private String code;
        private String city;
        private ZoneId timezone;
        private LetsGoCoordinate coordinate;

        StopLocationBuilder() {
        }

        public StopLocationBuilder city(String city) {
            this.city = city;
            return this;
        }

        public StopLocationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StopLocationBuilder code(String code) {
            this.code = code;
            return this;
        }

        public StopLocationBuilder timezone(ZoneId timezone) {
            this.timezone = timezone;
            return this;
        }

        public StopLocationBuilder coordinate(LetsGoCoordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

        public StopLocation build() {
            Objects.requireNonNull(name);
            Objects.requireNonNull(city);
            Objects.requireNonNull(timezone);
            Objects.requireNonNull(coordinate);

            return new StopLocation(name, city, timezone, coordinate);
        }

        public String toString() {
            return "StopPlace.StopPlaceBuilder(name=" + this.name + ", code=" + this.code + ", timezone=" + this.timezone + ", coordinate=" + this.coordinate + ")";
        }
    }


}
