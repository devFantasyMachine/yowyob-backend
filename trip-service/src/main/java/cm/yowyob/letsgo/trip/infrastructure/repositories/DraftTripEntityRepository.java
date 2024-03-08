/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.repositories;

import cm.yowyob.letsgo.trip.infrastructure.entities.DraftTripEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;


public interface DraftTripEntityRepository extends CassandraRepository<DraftTripEntity, UUID> {


    DraftTripEntity findByDraftId(UUID draftId);

    List<DraftTripEntity> findAllByOwnerId(String userId);

    DraftTripEntity findByDraftIdAndOwnerId(UUID draftId, String userId);
}
