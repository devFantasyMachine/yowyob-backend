package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.vehicle.Seat;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.SeatEntity;

public class SeatMapper extends Mapper<Seat, SeatEntity>{

    private final ComfortMapper comfortMapper = new ComfortMapper();

    @Override
    public Seat toObject(SeatEntity entity) {
        if (entity == null)
            return null;

        return Seat.builder()
                .seatNumber(entity.getPlaceNumber())
                .seatLabel(entity.getPlaceLabel())
                .comforts(comfortMapper.toObjectSet(entity.getComfort()))
                .build();
    }

    @Override
    public SeatEntity toEntity(Seat object) {

        if (object == null)
            return null;

        return SeatEntity.builder()
                .placeNumber(object.getSeatNumber())
                .placeLabel(object.getSeatLabel())
                .comfort(comfortMapper.toEntitySet(object.getComforts()))
                .build();
    }


    public ComfortMapper getComfortMapper() {
        return comfortMapper;
    }


}
