/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
public class StopDTO {

    @Nonnull
    private StopLocationDTO place;
    private Integer pos;
    @Nonnull
    private Long duration;


    public Stop toStop(){

        return Stop.builder()
                .withDelay(Duration.ofMinutes(duration))
                .withPlace(place.toStopLocation())
                .withPos(pos)
                .build();

    }

    public static StopDTO fromStop(Stop stop){

        if(stop == null)
            return null;

        return StopDTO.builder()
                .duration(stop.delay().toSeconds())
                .place(StopLocationDTO.fromStopLocation(stop.place()))
                .pos(stop.stopPos())
                .build();
    }

}
