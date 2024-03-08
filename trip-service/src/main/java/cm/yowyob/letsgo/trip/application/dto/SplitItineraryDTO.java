/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import jakarta.annotation.Nonnull;
import lombok.Data;

import java.util.Set;


@Data
public class SplitItineraryDTO {

    @Nonnull Integer stopIndex;
    @Nonnull StopLocationDTO location;

    Set<String> placeIds;
    Float newCost;
}
