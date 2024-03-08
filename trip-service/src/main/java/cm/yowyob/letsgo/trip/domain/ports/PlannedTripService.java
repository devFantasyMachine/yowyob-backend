/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;





public interface PlannedTripService {

    PlannerPlan save(PlannerPlan plannedTrip);

    Optional<PlannerPlan> getPlannerPlanById(UUID tripPlanId);

    Set<PlannerPlan> getAllByPlannerId(String plannerId, boolean isPublished);

    Set<PlannerPlan> getRangeByPlannerId(String plannerId, ZonedDateTime planAtFrom, ZonedDateTime planAtTo);

    PoolerPlan save(PoolerPlan poolerPlan);

    Optional<PoolerPlan> getPoolerPlanById(UUID poolerPlanId);

    Set<PoolerPlan> getAllByPoolerId(String poolerId);

    Set<TripResource> getPlannerPlanResource(UUID tripPlanId);
}
