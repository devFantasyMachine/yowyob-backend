package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.TaskPriority;
import cm.yowyob.letsgo.driver.domain.model.TaskStatus;
import cm.yowyob.letsgo.driver.domain.model.TaskType;
import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class ScheduleTaskDTO {

    private UUID id;
    private String ownerId;
    private String refId;

    @NonNull
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private StopLocationDTO from;
    private StopLocationDTO to;

    private Boolean isCronTask;
    private String cron;

    @Nullable
    private String note;
    private String label;

    @NonNull
    private TaskType type;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private LocalDateTime abortedAt;

    public static ScheduleTaskDTO fromDomainObject(ScheduleTask task) {

        return task == null ? null : ScheduleTaskDTO.builder()
                .id(task.getId())
                .ownerId(task.getOwnerId())
                .status(task.getStatus())
                .refId(task.getRefId())
                .fromDate(task.getFromDate())
                .toDate(task.getToDate())
                .priority(task.getPriority())
                .type(task.getType())
                .note(task.getNote())
                .isCronTask(task.getIsFrequencyTask())
                .cron(task.getFrequency())
                .executedAt(task.getExecutedAt())
                .abortedAt(task.getAbortedAt())

                .from(StopLocationDTO.fromDomainObject(task.getFrom()))
                .to(StopLocationDTO.fromDomainObject(task.getTo()))
                .build();
    }



}
