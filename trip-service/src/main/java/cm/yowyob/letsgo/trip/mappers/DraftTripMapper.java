/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.infrastructure.entities.DraftTripEntity;



public class DraftTripMapper extends Mapper<DraftTrip, DraftTripEntity> {

    private final StopPointMapper stopPointMapper = new StopPointMapper();
    private final StopLocationMapper stopLocationMapper = new StopLocationMapper();
    private final ComfortMapper comfortMapper = new ComfortMapper();


    @Override
    public DraftTrip toObject(DraftTripEntity entity) {

        return entity == null ? null : DraftTrip.builder()
                .tripType(entity.getTripType())
                .tripPrestige(entity.getTripPrestige())
                .serviceType(entity.getServiceType())
                .draftId(entity.getDraftId())
                .ownerId(entity.getOwnerId())
                .usageCount(entity.getUsageCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())

                .intermediateStops(stopPointMapper.toObjects(entity.getIntermediateStops()))
                .fromLocation(stopLocationMapper.toObject(entity.getFromLocation()))
                .toLocation(stopLocationMapper.toObject(entity.getToLocation()))
                .comforts(comfortMapper.toObjectSet(entity.getComforts()))
                .build();
    }


    @Override
    public DraftTripEntity toEntity(DraftTrip object) {

        return object == null ? null : DraftTripEntity.builder()
                .tripType(object.getTripType())
                .tripPrestige(object.getTripPrestige())
                .serviceType(object.getServiceType())
                .draftId(object.getDraftId())
                .ownerId(object.getOwnerId())
                .usageCount(object.getUsageCount())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())

                .intermediateStops(stopPointMapper.toEntities(object.getIntermediateStops()))
                .fromLocation(stopLocationMapper.toEntity(object.getFromLocation()))
                .toLocation(stopLocationMapper.toEntity(object.getToLocation()))
                .comforts(comfortMapper.toEntitySet(object.getComforts()))
                .build();

    }


}
