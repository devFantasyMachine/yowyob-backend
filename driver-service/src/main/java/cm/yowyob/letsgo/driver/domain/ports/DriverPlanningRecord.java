package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DriverPlanningRecord {
     ScheduleTask save(ScheduleTask newTask);

    Set<ScheduleTask> getDriverPlanningAfter(String driverId, LocalDateTime startAt);

    Optional<ScheduleTask> getTaskById(UUID taskRequestId);

    void delete(UUID taskId);
}
