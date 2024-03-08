package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.infrastructure.entities.VehicleEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ScoreEntity;


public class VehicleMapper extends Mapper<Vehicle, VehicleEntity>{

    private final SeatMapper seatMapper = new SeatMapper();
    private final LuggageBoxMapper luggageBoxMapper = new LuggageBoxMapper();

    @Override
    public Vehicle toObject(VehicleEntity entity) {

        if (entity == null) {
            return null;
        }

        return Vehicle.builder()
                .ownerId(entity.getOwnerId())
                .vehicleId(entity.getVehicleId())
                .comment(entity.getComment())
                .grayCard(entity.getGrayCard())
                .mark(entity.getMark())
                .insuranceNumber(entity.getInsuranceNumber())
                .images(entity.getImages())
                .insuranceValidityDate(entity.getInsuranceValidityDate())
                .matriculation(entity.getMatriculation())
                .version(entity.getVersion())
                .placeCount(entity.getPlaceCount())
                .createdAt(entity.getCreatedAt())
                .model(entity.getModel())
                .transportMode(entity.getTransportMode())
                .score(ScoreEntity.toScore(entity.getScore()))
                .seats(seatMapper.toObjectSet(entity.getSeats()))
                .comforts(seatMapper.getComfortMapper().toObjectSet(entity.getComforts()))
                .luggageBoxes(luggageBoxMapper.toObjectSet(entity.getLuggageBoxes()))
                .build();
    }

    @Override
    public VehicleEntity toEntity(Vehicle object) {

        if (object  == null)
            return null;

        return VehicleEntity.builder()
                .version(object.getVersion())
                .vehicleId(object.getVehicleId())
                .comment(object.getComment())
                .transportMode(object.getTransportMode())
                .model(object.getModel())
                .mark(object.getMark())
                .insuranceNumber(object.getInsuranceNumber())
                .placeCount(object.getPlaceCount())
                .ownerId(object.getOwnerId())
                .insuranceValidityDate(object.getInsuranceValidityDate())
                .matriculation(object.getMatriculation())
                .images(object.getImages())
                .grayCard(object.getGrayCard())
                .createdAt(object.getCreatedAt())
                .score(ScoreEntity.fromScore(object.getScore()))
                .seats(seatMapper.toEntitySet(object.getSeats()))
                .comforts(seatMapper.getComfortMapper().toEntitySet(object.getComforts()))
                .luggageBoxes(luggageBoxMapper.toEntitySet(object.getLuggageBoxes()))
                .build();
    }

}
