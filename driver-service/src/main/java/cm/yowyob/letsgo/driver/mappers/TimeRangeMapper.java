package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.TimeRange;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.TimeRangeEntity;

public class TimeRangeMapper extends Mapper<TimeRange, TimeRangeEntity>{

    @Override
    public TimeRange toObject(TimeRangeEntity entity) {
        return entity == null ? null : new TimeRange(entity.getStart(), entity.getEnd());
    }

    @Override
    public TimeRangeEntity toEntity(TimeRange object) {
        return object == null ? null : new TimeRangeEntity(object.getStartTime(), object.getEndTime());
    }

}
