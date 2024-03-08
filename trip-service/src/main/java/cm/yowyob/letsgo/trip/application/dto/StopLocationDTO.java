/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;


/**
 * A Place is where a journey starts or ends, or a transit stop along the way.
 * @see StopLocation
 */
@Data
@Builder
public class StopLocationDTO {

    @Nonnull String name;
    @Nonnull String city;
    @Nonnull String timezone;
    double lat;
    double lon;


    public StopLocation toStopLocation(){

        return StopLocation.builder()
                .name(name)
                .city(city)
                .timezone(ZoneId.of(timezone))
                .coordinate(LetsGoCoordinate.createOptionalCoordinate(lat, lon))
                .build();
    }

    public static StopLocationDTO fromStopLocation(StopLocation stopLocation){

        if(stopLocation == null)
            return null;

        return StopLocationDTO.builder()
                .name(stopLocation.name())
                .city(stopLocation.city())
                .lat(stopLocation.coordinate().latitude())
                .lon(stopLocation.coordinate().longitude())
                .timezone(stopLocation.timezone().getId())
                .build();
    }

 
}
