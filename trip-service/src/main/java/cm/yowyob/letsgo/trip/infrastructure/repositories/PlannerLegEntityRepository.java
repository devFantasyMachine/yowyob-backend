/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;

import cm.yowyob.letsgo.trip.infrastructure.entities.LegEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface PlannerLegEntityRepository extends CassandraRepository<LegEntity, UUID> {

    List<LegEntity> findAllByPlanId(UUID planId);

    List<LegEntity> findAllByPlannerId(String plannerId);
}
