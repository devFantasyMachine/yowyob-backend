package cm.yowyob.letsgo.driver.mappers;


import cm.yowyob.letsgo.driver.domain.model.driver.DriverAvailability;
import cm.yowyob.letsgo.driver.domain.model.driver.TimeRange;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DayRangeEntity;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DriverAvailabilityEntity;

import java.time.DayOfWeek;
import java.util.*;

public class DriverAvailabilityMapper extends Mapper<DriverAvailability, DriverAvailabilityEntity>{

    private final TimeRangeMapper timeRangeMapper = new TimeRangeMapper();


    @Override
    public DriverAvailability toObject(DriverAvailabilityEntity entity) {

        if (entity == null)
            return null;

        Map<DayOfWeek, TreeSet<TimeRange>> ranges = new HashMap<>();

        if (entity.getRanges() != null){

            for (DayRangeEntity dayRangeDTO : entity.getRanges()) {

                ranges.put(DayOfWeek.of(dayRangeDTO.getDay()),
                        new TreeSet<>(timeRangeMapper.toObjects(dayRangeDTO.getTimes())));
            }
        }

        return DriverAvailability.builder()
                .timeRangePerDay(ranges)
                .isAvailable(entity.getIsAvailable())
                .build();
    }



    @Override
    public DriverAvailabilityEntity toEntity(DriverAvailability object) {

        if (object  ==  null)
            return null;

        Set<DayRangeEntity> ranges = new HashSet<>();

        if (object.getTimeRangePerDay() != null ) {

            for (DayOfWeek dayOfWeek : object.getTimeRangePerDay().keySet()) {

                ranges.add(DayRangeEntity.builder()
                        .day(dayOfWeek.getValue())
                        .times(timeRangeMapper.toEntitySet(object.getTimeRangePerDay().get(dayOfWeek)))
                        .build()
                );
            }
        }

        return DriverAvailabilityEntity.builder()
                .isAvailable(object.getIsAvailable())
                .ranges(ranges)
                .build();
    }

}
