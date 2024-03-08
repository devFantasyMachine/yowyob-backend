/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;


import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.utils.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;



@Setter
@Getter
public final class PairOfStop implements Comparable<PairOfStop>  {

    private final Pair<Stop, Stop> stopPoints;
    private Float placeCost;

    public PairOfStop(Stop firstStopPoints, Stop lastStopPoint, Float cost) {
        this.stopPoints = Pair.of(firstStopPoints, lastStopPoint);
        this.placeCost = Objects.requireNonNull(cost);
    }

    public Stop getBegin(){

        return stopPoints.getLeft();
    }

    public Stop getEnd(){

        return stopPoints.getRight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PairOfStop that)) return false;
        return getBegin().equals(that.getBegin()) && getEnd().equals(that.getEnd())&& getPlaceCost().equals(that.getPlaceCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStopPoints(), getPlaceCost());
    }

    @Override
    public int compareTo(PairOfStop o) {
        return 0;
    }


}
