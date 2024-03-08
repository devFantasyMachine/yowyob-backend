/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.stats;


import lombok.Builder;
import lombok.Data;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;


@Data
@Builder
public class PlannerStat {

    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private Stat globalStat;
    private List<MonthStat> monthStats;
    private List<YearStat> yearsStats;
}
