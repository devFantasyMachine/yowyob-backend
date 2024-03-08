package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.driver.DriverAvailability;
import cm.yowyob.letsgo.driver.domain.model.driver.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.*;


@Data
@Builder
@AllArgsConstructor
public class DriverAvailabilityDTO {

    private Boolean isAvailable;

    private Set<DayRangeDTO> ranges;


    public DriverAvailability toDomainObject(){

        Map<DayOfWeek, TreeSet<TimeRange>> ranges = null;

        if (this.ranges != null){

            ranges = new HashMap<>();

            for (DayRangeDTO dayRangeDTO : this.ranges) {

                ranges.put(DayOfWeek.of(dayRangeDTO.day), new TreeSet<>(dayRangeDTO.times));
            }

        }

        return DriverAvailability.builder()
                .timeRangePerDay(ranges)
                .isAvailable(isAvailable)
                .build();
    }


    public static DriverAvailabilityDTO fromDomainObject(DriverAvailability driverAvailability){

        if (driverAvailability  ==  null)
            return null;


        Set<DayRangeDTO> ranges = new HashSet<>();

        for (DayOfWeek dayOfWeek : driverAvailability.getTimeRangePerDay().keySet()) {

            ranges.add(DayRangeDTO.builder()
                    .day(dayOfWeek.getValue())
                    .times(driverAvailability.getTimeRangePerDay().get(dayOfWeek))
                    .build()
            );
        }

        return DriverAvailabilityDTO.builder()
                .isAvailable(driverAvailability.getIsAvailable())
                .ranges(ranges)
                .build();
    }


}
