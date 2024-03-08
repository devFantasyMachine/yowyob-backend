/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;


import cm.yowyob.letsgo.trip.infrastructure.entities.TripResourceEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TripResourceEntityRepository extends CassandraRepository<TripResourceEntity, UUID> {

    List<TripResourceEntity> findAllByTripId(UUID tripId);

    List<TripResourceEntity> findAllByPlannerId(String plannerId);

}
