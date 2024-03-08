/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.*;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;


/**
 * An Itinerary is one complete way of getting from the start location to the end location.
 */
@Data
@Builder
public class PlannerItineraryDTO {

    private List<PlannerLegDTO> legs;
    private double directDistanceMeters;
    private double distanceMeters;
    private LetsGoCoordinate meanCoordinate;
    private List<StopDTO> stops;
    private Duration totalDuration;
    private Duration totalTransitDuration;
    private Duration waitingDuration;
    private int numberOfTransfers;
    private String str;


    public static PlannerItineraryDTO fromPlannerItinerary(Itinerary<PlannerLeg> plannerItinerary, Currency currency){

        if (plannerItinerary == null)
            return null;

        return PlannerItineraryDTO.builder()
                .legs(plannerItinerary.getLegs().stream().map(plannerLeg -> PlannerLegDTO.fromPlannerLeg(plannerLeg)).collect(Collectors.toList()) )
                .totalTransitDuration(plannerItinerary.getTotalTransitDuration())
                .directDistanceMeters(plannerItinerary.getDirectDistanceMeters())
                .distanceMeters(plannerItinerary.getDistanceMeters())
                .totalDuration(plannerItinerary.getTotalDuration())
                .waitingDuration(plannerItinerary.getWaitingDuration())
                .meanCoordinate(plannerItinerary.getMeanCoordinate())
                .numberOfTransfers(plannerItinerary.getNumberOfTransfers())
                .str(plannerItinerary.toStr())
                .stops(plannerItinerary.getStops()
                        .stream()
                        .map(StopDTO::fromStop)
                        .collect(Collectors.toList()))
                .build();
    }


}
