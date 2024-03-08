/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;

import java.time.ZonedDateTime;
import java.util.Set;

public interface TripPlanResolver {


    Set<PlannerPlan> resolve(DraftTrip draftTrip, ZonedDateTime departure);


}
