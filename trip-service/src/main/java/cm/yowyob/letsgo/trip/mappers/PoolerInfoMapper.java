package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.PoolerInfoEntity;


public class PoolerInfoMapper extends Mapper<PoolerInformation, PoolerInfoEntity>{


    @Override
    public PoolerInformation toObject(PoolerInfoEntity entity) {
        return entity == null ? null :  PoolerInformation.fromPlannerDetailsId(entity.getUserId())
                .informationVersion(entity.getVersion())
                .plannerScore(entity.getScore())
                .poolerName(entity.getName())
                .withContactsPhones(entity.getPhones())
                .poolerPicture(entity.getPicture())
                .build();
    }


    @Override
    public PoolerInfoEntity toEntity(PoolerInformation object) {
        return object == null ? null : PoolerInfoEntity.builder()
                .name(object.getName())
                .score(object.getScore())
                .version(object.getVersion())
                .picture(object.getPicture())
                .phones(object.phones())
                .userId(object.getUserId())
                .build();
    }



}
