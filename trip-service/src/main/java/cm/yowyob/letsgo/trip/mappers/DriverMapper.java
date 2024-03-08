package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.Driver;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.DriverEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ScoreEntity;

public class DriverMapper extends Mapper<Driver, DriverEntity> {

    @Override
    public Driver toObject(DriverEntity entity) {

        if (entity == null)
            return null;

        return Driver.builder()
                .driverId(entity.getDriverId())
                .ownerId(entity.getOwnerId())
                .version(entity.getVersion())
                .score(ScoreEntity.toScore(entity.getScore()))
                .about(entity.getAbout())
                .avatar(entity.getAvatar())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .picture(entity.getPicture())
                .build();
    }



    @Override
    public DriverEntity toEntity(Driver object) {

        if (object == null)
            return null;

        return DriverEntity.builder()
                .driverId(object.getDriverId())
                .ownerId(object.getOwnerId())
                .version(object.getVersion())
                .score(ScoreEntity.fromScore(object.getScore()))
                .about(object.getAbout())
                .avatar(object.getAvatar())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .gender(object.getGender())
                .picture(object.getPicture())
                .build();
    }


}
