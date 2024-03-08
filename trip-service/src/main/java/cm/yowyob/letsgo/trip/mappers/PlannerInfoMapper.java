package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.PlannerInformation;
import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.PlannerInfoEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.PoolerInfoEntity;



public class PlannerInfoMapper extends Mapper<PlannerInformation, PlannerInfoEntity>{


    @Override
    public PlannerInformation toObject(PlannerInfoEntity entity) {
        return entity == null ? null :  PlannerInformation.fromPlannerDetailsId(entity.getUserId())
                .informationVersion(entity.getVersion())
                .plannerScore(entity.getScore())
                .plannerName(entity.getName())
                .withContactsPhones(entity.getPhones())
                .plannerPicture(entity.getPicture())
                .build();
    }


    @Override
    public PlannerInfoEntity toEntity(PlannerInformation object) {
        return object == null ? null : PlannerInfoEntity.builder()
                .name(object.getName())
                .score(object.getScore())
                .version(object.getVersion())
                .picture(object.getPicture())
                .phones(object.phones())
                .userId(object.getUserId())
                .build();
    }



}
