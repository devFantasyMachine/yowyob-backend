package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import cm.yowyob.letsgo.trip.infrastructure.entities.TripResourceEntity;


public class TripResourceMapper extends Mapper<TripResource, TripResourceEntity> {

    @Override
    public TripResource toObject(TripResourceEntity entity) {
        return entity == null ? null : TripResource.builder()
                .id(entity.getResourceId())
                .type(entity.getType())
                .defaultUnitCost(entity.getDefaultUnitCost())
                .label(entity.getLabel())
                .unitTag(entity.getUnitTag())
                .unitQuantity(entity.getUnitQuantity())
                .totalQuantity(entity.getTotalQuantity())
                .reservationCount(entity.getReservationCount())
                .build();
    }

    @Override
    public TripResourceEntity toEntity(TripResource object) {
        return object == null ? null : TripResourceEntity.builder()
                .resourceId(object.getId())
                .type(object.getType())
                .defaultUnitCost(object.getDefaultUnitCost())
                .label(object.getLabel())
                .unitTag(object.getUnitTag())
                .unitQuantity(object.getUnitQuantity())
                .totalQuantity(object.getTotalQuantity())
                .reservationCount(object.getReservationCount())
                .build();
    }




}
