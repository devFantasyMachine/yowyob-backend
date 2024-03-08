/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;


import cm.yowyob.letsgo.trip.domain.exceptions.DraftTripNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.model.ObjectUtils;
import cm.yowyob.letsgo.trip.domain.ports.DraftTripService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;





@AllArgsConstructor
public class DraftTripManager {


    @NonNull
    private DraftTripService draftTripService;


    public DraftTrip createDraftTrip(DraftTrip request, String userId){

        DraftTrip draftTrip = DraftTrip.simpleDraftBuilder()
                .intermediateStops(request.getIntermediateStops())
                .tripPrestige(request.getTripPrestige())
                .tripType(request.getTripType())
                .draftId(UUID.randomUUID())
                .comforts(request.getComforts())
                .ownerId(userId)
                .serviceType(request.getServiceType())
                .fromLocation(request.getFromLocation())
                .toLocation(request.getToLocation())
                .build();

        return draftTripService.save(draftTrip);
    }



    public DraftTrip updateDraftTrip(@NonNull final DraftTrip request, @NonNull String userId, UUID draftId)
            throws DraftTripNotFoundException {

        return draftTripService.getDraftByIdAndOwnerId(draftId, userId)
                .map(draftTrip -> DraftTrip.simpleDraftBuilder()
                        .ownerId(draftTrip.getOwnerId())
                        .draftId(draftTrip.getDraftId())
                        .usageCount(draftTrip.getUsageCount())
                        .createdAt(draftTrip.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .intermediateStops(request.getIntermediateStops())
                        .serviceType(ObjectUtils.getOrDefault(request.getServiceType(), draftTrip.getServiceType()))
                        .tripPrestige(ObjectUtils.getOrDefault(request.getTripPrestige(), draftTrip.getTripPrestige()))
                        .tripType(ObjectUtils.getOrDefault(request.getTripType(), draftTrip.getTripType()))
                        .comforts(ObjectUtils.getOrDefault(request.getComforts(), draftTrip.getComforts()))
                        .fromLocation(ObjectUtils.getOrDefault(request.getFromLocation(), draftTrip.getFromLocation()))
                        .toLocation(ObjectUtils.getOrDefault(request.getToLocation(), draftTrip.getToLocation()))
                        .build()
                )
                .map(draftTripService::save)
                .orElseThrow(DraftTripNotFoundException::new);
    }


    public DraftTrip getDraftTrip(String userId, String draftId)
            throws DraftTripNotFoundException {

        return draftTripService
                .getDraftByIdAndOwnerId(UUID.fromString(draftId), userId)
                .filter(draftTrip -> userId.equals(draftTrip.getOwnerId()))
                .orElseThrow(DraftTripNotFoundException::new);
    }


    public List<DraftTrip> getUserDraftTrips(String userId) {

        return draftTripService.getAllByOwnerId(userId);
    }



}
