/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;



@Getter
@AllArgsConstructor
public abstract class LetsgoContext {

    private final TripType tripType;
    private final ServiceType serviceType;
    private final TripPrestige tripPrestige;
    private final ZonedDateTime contextDatetime;

}
