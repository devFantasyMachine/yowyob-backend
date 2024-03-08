/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.vehicle;

import cm.yowyob.letsgo.trip.domain.model.Score;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public final class Vehicle implements Serializable {

    private static final Integer UNKNOWN = -1;

    public static final Vehicle NONE = Vehicle.builder().version(-1).build();

    @Serial
    private static final long serialVersionUID = 0L;

    private final String vehicleId;
    private final String ownerId;
    private final Integer version;
    private final String matriculation;
    private final String grayCard;
    private final String insuranceNumber;
    private final Instant insuranceValidityDate;
    private final Integer placeCount;
    private final String mark;
    private final String model;
    private final String comment;
    private final Set<LuggageBox> luggageBoxes;
    private final Score score;
    private final Set<String> images;
    private final TransportMode transportMode;
    private final Set<Comfort> comforts;
    private final Set<Seat> seats;
    private final LocalDateTime createdAt;


}

