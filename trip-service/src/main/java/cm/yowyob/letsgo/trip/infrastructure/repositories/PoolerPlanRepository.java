/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;


import cm.yowyob.letsgo.trip.infrastructure.entities.PoolerTripPlanEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Set;
import java.util.UUID;



public interface PoolerPlanRepository extends CassandraRepository<PoolerTripPlanEntity, UUID> {

    PoolerTripPlanEntity findByTripPlanId(UUID tripPlanId);

    Set<PoolerTripPlanEntity> findAllByPlannerId(String plannerId);
}
