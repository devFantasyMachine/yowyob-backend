/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;


import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class DefaultTripPlanContext extends LetsgoContext {

    private final String plannerCode;

    public DefaultTripPlanContext(TripType tripType, ServiceType serviceType, TripPrestige tripPrestige, ZonedDateTime contextDatetime, String plannerCode) {
        super(tripType, serviceType, tripPrestige, contextDatetime);
        this.plannerCode = plannerCode;
    }


}
