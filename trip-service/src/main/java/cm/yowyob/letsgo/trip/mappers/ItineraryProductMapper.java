package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.infrastructure.entities.ItineraryProductEntity;

public class ItineraryProductMapper extends Mapper<ItineraryProduct, ItineraryProductEntity>{


    private final TripResourceMapper tripResourceMapper = new TripResourceMapper();
    private final StopPointMapper stopPointMapper = new StopPointMapper();


    @Override
    public ItineraryProduct toObject(ItineraryProductEntity entity) {

        return entity == null ? null : ItineraryProduct.builder()
                .productId(entity.getProductId())
                .cost(entity.getCost())
                .planId(entity.getPlanId())
                .quantity(entity.getQuantity())
                .status(entity.getStatus())
                .arrival(entity.getArrival())
                .departure(entity.getDeparture())
                .resource(tripResourceMapper.toObject(entity.getResource()))
                .fromLocation(stopPointMapper.toObject(entity.getFromLocation()))
                .toLocation(stopPointMapper.toObject(entity.getToLocation()))
                .build();
    }


    @Override
    public ItineraryProductEntity toEntity(ItineraryProduct object) {

        return object == null ? null : ItineraryProductEntity.builder()
                .cost(object.getCost())
                .planId(object.getPlanId())
                .status(object.getStatus())
                .arrival(object.getArrival())
                .quantity(object.getQuantity())
                .departure(object.getDeparture())
                .productId(object.getProductId())
                .resourceId(object.getResource().getId())
                .resource(tripResourceMapper.toEntity(object.getResource()))
                .toLocation(stopPointMapper.toEntity(object.getToLocation()))
                .fromLocation(stopPointMapper.toEntity(object.getFromLocation()))
                .build();
    }


}
