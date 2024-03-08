package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.WorkZone;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.WorkZoneEntity;

public class WorkZoneMapper extends Mapper<WorkZone, WorkZoneEntity>{

    @Override
    public WorkZone toObject(WorkZoneEntity entity) {
        return entity == null ? null : WorkZone.builder()
                .country(entity.getCountry())
                .cities(entity.getCities())
                .build();
    }

    @Override
    public WorkZoneEntity toEntity(WorkZone object) {
        return object == null ? null : WorkZoneEntity.builder()
                .country(object.getCountry())
                .cities(object.getCities())
                .build();
    }

}
