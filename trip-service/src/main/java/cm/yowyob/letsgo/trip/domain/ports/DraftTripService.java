/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DraftTripService {

    Optional<DraftTrip> getDraftById(UUID draftId);

    DraftTrip save(DraftTrip newDraftTrip);

    Optional<DraftTrip> getDraftByIdAndOwnerId(UUID draftId, String userId);

    List<DraftTrip> getAllByOwnerId(String userId);
}
