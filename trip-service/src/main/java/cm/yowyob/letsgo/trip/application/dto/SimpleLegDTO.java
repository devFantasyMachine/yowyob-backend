/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.SortedSet;

@Data
@Builder
public class SimpleLegDTO {

    private StopDTO fromLocation;
    private StopDTO toLocation;
    private SortedSet<StopArrival> intermediateStops;
    private Duration transitDuration;
    private Duration totalDuration;
    private Double distanceMeters;
    private Double directDistanceMeters;

    // TODO: 29/08/2023 add geojson line string

    public static SimpleLegDTO fromLeg(Leg leg){

        if (leg == null)
            return null;

        return SimpleLegDTO.builder()
                .fromLocation(StopDTO.fromStop(leg.getFromLocation()))
                .toLocation(StopDTO.fromStop(leg.getToLocation()))
                .totalDuration(leg.getTotalDuration())
                .transitDuration(leg.getTransitDuration())
                .directDistanceMeters(leg.getDirectDistanceMeters())
                .distanceMeters(leg.getDistanceMeters())
                .intermediateStops(leg.getIntermediateStops())
                .build();
    }


}
