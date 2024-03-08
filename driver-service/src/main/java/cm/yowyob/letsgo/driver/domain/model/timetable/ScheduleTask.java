package cm.yowyob.letsgo.driver.domain.model.timetable;

import cm.yowyob.letsgo.driver.domain.exception.InvalidTaskStatusException;
import cm.yowyob.letsgo.driver.domain.model.TaskPriority;
import cm.yowyob.letsgo.driver.domain.model.TaskStatus;
import cm.yowyob.letsgo.driver.domain.model.TaskType;
import cm.yowyob.letsgo.driver.domain.model.stops.StopLocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.*;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class ScheduleTask implements Comparable<ScheduleTask>{

    private UUID id;
    private String ownerId;
    private String driverId;
    private String refId;


    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private StopLocation from;
    private StopLocation to;

    private Boolean isFrequencyTask;
    private String frequency;

    private String label;
    private String note;

    private TaskType type;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private LocalDateTime abortedAt;


    @Override
    public int compareTo(ScheduleTask o) {
        return fromDate
                .compareTo(o.fromDate);
    }

    public void changeStatus(TaskStatus newStatus) throws InvalidTaskStatusException {

        if ((newStatus == TaskStatus.ABORTED || newStatus == TaskStatus.ONGOING)
                && status != TaskStatus.SCHEDULED){

            throw new InvalidTaskStatusException();

        } else if (newStatus == TaskStatus.EXECUTED && status != TaskStatus.ONGOING) {

            throw new InvalidTaskStatusException();
        }

        this.status = newStatus;

        if (newStatus == TaskStatus.ABORTED)
            abortedAt = LocalDateTime.now();

        if (newStatus == TaskStatus.EXECUTED)
            executedAt = LocalDateTime.now();


    }
}
