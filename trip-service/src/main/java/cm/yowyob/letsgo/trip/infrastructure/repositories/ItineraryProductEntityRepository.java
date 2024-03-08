/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;


import cm.yowyob.letsgo.trip.infrastructure.entities.ItineraryProductEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;



public interface ItineraryProductEntityRepository extends CassandraRepository<ItineraryProductEntity, UUID> {

    List<ItineraryProductEntity> findAllByProductIdIn(Set<UUID> productIds);

    List<ItineraryProductEntity> findAllByPlanId(UUID planId);
}
