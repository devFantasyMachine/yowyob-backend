/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import jakarta.annotation.Nonnull;
import lombok.Data;




@Data
public class ExtendItineraryDTO {

    @Nonnull StopLocationDTO newStopLocation;

    long stopDuration;
    Float newPLaceCost;
    Float newLuggageCost;
}
