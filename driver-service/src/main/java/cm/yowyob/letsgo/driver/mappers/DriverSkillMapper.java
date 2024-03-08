package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.DriverSkill;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DriverSkillEntity;

public class DriverSkillMapper extends Mapper<DriverSkill, DriverSkillEntity>{

    @Override
    public DriverSkill toObject(DriverSkillEntity entity) {

        if (entity == null)
            return null;

        return new DriverSkill(entity.getName(), entity.getDesc());
    }

    @Override
    public DriverSkillEntity toEntity(DriverSkill object) {

        if (object == null)
            return null;

        return DriverSkillEntity.builder()
                .desc(object.desc())
                .name(object.name())
                .build();
    }




}
