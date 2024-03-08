/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;

import cm.yowyob.letsgo.trip.infrastructure.entities.PlannerTripPlanEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.TripPlanEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Set;
import java.util.UUID;



public interface PlannerPlanRepository extends CassandraRepository<PlannerTripPlanEntity, UUID> {

    PlannerTripPlanEntity findByTripPlanId(UUID tripPlanId);

    Set<PlannerTripPlanEntity> findAllByPlannerId(String plannerId);

}
