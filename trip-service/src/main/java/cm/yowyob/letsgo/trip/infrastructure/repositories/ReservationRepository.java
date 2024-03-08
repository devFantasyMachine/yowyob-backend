/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;


import cm.yowyob.letsgo.trip.infrastructure.entities.LockedTripResourcesEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;


public interface ReservationRepository extends CassandraRepository<LockedTripResourcesEntity, UUID> {


    LockedTripResourcesEntity findByLockedId(UUID reservationId);

    List<LockedTripResourcesEntity> findAllByGroup(String reservationGroup);
}
