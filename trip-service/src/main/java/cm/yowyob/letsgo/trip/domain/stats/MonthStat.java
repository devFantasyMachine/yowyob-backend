/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.stats;

import java.time.Month;

public record MonthStat(Month month, Stat stat) implements Comparable<MonthStat> {


    @Override
    public int compareTo(MonthStat o) {
        return month.compareTo(o.month);
    }

}
