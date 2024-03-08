package cm.yowyob.letsgo.driver.domain.model.driver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeSet;



@Data
@Builder
@AllArgsConstructor
public final class DriverAvailability {


    public static final DriverAvailability NONE = new DriverAvailability(false, Map.of());

    private Boolean isAvailable;
    private Map<DayOfWeek, TreeSet<TimeRange>> timeRangePerDay;

}

