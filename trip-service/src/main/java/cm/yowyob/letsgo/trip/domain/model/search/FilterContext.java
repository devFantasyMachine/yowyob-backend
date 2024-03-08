/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.search;


import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FilterContext {

    private Set<TransportMode> transportModes;
    private final Set<Comfort> comforts;
    private TripPrestige tripPrestige;
    private String plannerName;
    private TripType tripType;
    private Float totalCost;

    private Float plannerScore;


}
