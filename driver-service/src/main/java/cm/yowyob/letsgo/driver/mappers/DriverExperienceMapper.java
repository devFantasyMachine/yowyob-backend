package cm.yowyob.letsgo.driver.mappers;


import cm.yowyob.letsgo.driver.domain.model.driver.DriverExperience;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DriverExperienceEntity;

public class DriverExperienceMapper extends Mapper<DriverExperience, DriverExperienceEntity> {

    @Override
    public DriverExperience toObject(DriverExperienceEntity entity) {

        if (entity == null)
            return null;

        return DriverExperience.builder()
                .attachments(entity.getAttachments())
                .desc(entity.getDesc())
                .label(entity.getLabel())
                .starAt(entity.getStarAt())
                .endAt(entity.getEndAt())
                .build();
    }


    @Override
    public DriverExperienceEntity toEntity(DriverExperience object) {

        if (object == null)
            return null;

        return DriverExperienceEntity.builder()
                .attachments(object.getAttachments())
                .desc(object.getDesc())
                .label(object.getLabel())
                .starAt(object.getStarAt())
                .endAt(object.getEndAt())
                .build();
    }


}
