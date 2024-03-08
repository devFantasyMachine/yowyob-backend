package cm.yowyob.letsgo.driver.domain.model.driver;

import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.SortedSet;



@Data
@Builder
@AllArgsConstructor
public class DriverPlanning {

    private String driverId;
    private SortedSet<ScheduleTask> tasks;

}
