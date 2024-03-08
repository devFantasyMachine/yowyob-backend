/*
 * Copyright (c) 2023-2023. Lorem 
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.graphql;


import cm.yowyob.letsgo.trip.domain.stats.MonthStat;
import cm.yowyob.letsgo.trip.domain.stats.PlannerStat;
import cm.yowyob.letsgo.trip.domain.stats.Stat;
import cm.yowyob.letsgo.trip.domain.stats.YearStat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class StatController {


/*    @SchemaMapping(typeName="YearStat", field="yearsStats")
    public YearStat getYearsStats(PlannerStat plannerStat) {
        // ...

        return null;
    }


    @SchemaMapping(typeName="MonthlyStats", field="monthlyStats")
    public MonthStat getMonthStats(PlannerStat plannerStat) {
        // ...

        return null;
    }*/

    
    @QueryMapping()
    PlannerStat getPlannerStatByPlannerId(@Argument String plannerId){

        return PlannerStat.builder()
                .fromDate(ZonedDateTime.now())
                .toDate(ZonedDateTime.now())
                .monthStats(List.of(new MonthStat(Month.JANUARY, Stat.builder()
                        .publishedTripCount(2L)
                        .plannedTripCount(3L)
                        .cancelledTripCount(1L)
                        .build())))
                .globalStat(Stat.builder()
                        .publishedTripCount(21L)
                        .plannedTripCount(23L)
                        .cancelledTripCount(2L)
                        .build())
                .build();
    }


}
