/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;



import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


/**
 * An Itinerary is one complete way of getting from the start location to the end location.
 */
@Data
@Builder
public class SimpleItineraryDTO {

    private List<SimpleLegDTO> legs;
    private double directDistanceMeters;
    private double distanceMeters;
    private LetsGoCoordinate meanCoordinate;
    private List<StopDTO> stops;
    private Duration totalDuration;
    private Duration totalTransitDuration;
    private Duration waitingDuration;
    private int numberOfTransfers;
    private String str;


    public static SimpleItineraryDTO fromSimpleItinerary(Itinerary<Leg> poolerItinerary){

        if (poolerItinerary == null || poolerItinerary.isEmpty())
            return null;

        return SimpleItineraryDTO.builder()
                .legs(poolerItinerary.getLegs()
                        .stream()
                        .map(SimpleLegDTO::fromLeg)
                        .collect(Collectors.toList()) )
                .totalTransitDuration(poolerItinerary.getTotalTransitDuration())
                .directDistanceMeters(poolerItinerary.getDirectDistanceMeters())
                .distanceMeters(poolerItinerary.getDistanceMeters())
                .totalDuration(poolerItinerary.getTotalDuration())
                .waitingDuration(poolerItinerary.getWaitingDuration())
                .meanCoordinate(poolerItinerary.getMeanCoordinate())
                .numberOfTransfers(poolerItinerary.getNumberOfTransfers())
                .str(poolerItinerary.toStr())
                .stops(poolerItinerary.getStops()
                        .stream()
                        .map(StopDTO::fromStop)
                        .collect(Collectors.toList()))
                .build();
    }


}
