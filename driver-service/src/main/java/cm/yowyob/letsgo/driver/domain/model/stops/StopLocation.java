/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model.stops;

import lombok.NonNull;

import java.util.Objects;



/**
 * A Place is where a journey starts or ends, or a transit stop along the way.
 */
public record StopLocation(String name,
                           String city) {


    public static final StopLocation NONE =
            new StopLocation("","");



    public @NonNull static StopLocationBuilder builder() {
        return new StopLocationBuilder();
    }


    public static class StopLocationBuilder {

        private String name;
        private String city;
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


        public StopLocationBuilder coordinate(LetsGoCoordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

        public StopLocation build() {
            Objects.requireNonNull(name);
            return new StopLocation(name, city);
        }

        public String toString() {
            return "StopPlace.StopPlaceBuilder(name=" + this.name + ", coordinate=" + this.coordinate + ")";
        }
    }


}
