/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.stats;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Stat {

    private Long plannedTripCount;
    private Long publishedTripCount;
    private Long cancelledTripCount;
    private Set<StopLocationStat> departureLocationCount;
    private Set<StopLocationStat> arrivalLocationCount;
    private Set<CourseStat> courseStats;

}
