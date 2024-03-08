/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.Driver;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.SortedSet;

@Data
@Builder
public class PlannerLegDTO {

    private StopArrival fromLocation;
    private StopDTO toLocation;
    private SortedSet<StopArrival> intermediateStops;
    private List<LetsGoCoordinate> intermediateCoordinates;
    private ZonedDateTime estimateDeparture;
    public ZonedDateTime estimateArrival;
    private Duration transitDuration;
    private Duration totalDuration;
    private Double distanceMeters;
    private Double directDistanceMeters;
    private TransportMode transportMode;

    private Vehicle vehicle;
    private Driver driver;


    public static PlannerLegDTO fromPlannerLeg(PlannerLeg plannerLeg){

        if (plannerLeg == null)
            return null;

        return PlannerLegDTO.builder()
                .fromLocation(new StopArrival(plannerLeg.getFromLocation().place(), null, plannerLeg.getFromLocation().stopPos()))
                .toLocation(StopDTO.fromStop(plannerLeg.getToLocation()))
                .totalDuration(plannerLeg.getTotalDuration())
                .transitDuration(plannerLeg.getTransitDuration())
                .directDistanceMeters(plannerLeg.getDirectDistanceMeters())
                .transportMode(plannerLeg.getTransportMode())
                .distanceMeters(plannerLeg.getDistanceMeters())
                .estimateDeparture(plannerLeg.getEstimateDeparture())
                .estimateArrival(plannerLeg.getEstimateArrival())

                .vehicle(plannerLeg.getVehicle())
                .driver(plannerLeg.getDriver())
                .build();
    }


}
