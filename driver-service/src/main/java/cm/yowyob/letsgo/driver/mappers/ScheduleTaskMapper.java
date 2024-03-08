package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.stops.StopLocation;
import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;
import cm.yowyob.letsgo.driver.infrastructure.entities.ScheduleTaskEntity;

public class ScheduleTaskMapper extends Mapper<ScheduleTask, ScheduleTaskEntity> {

    @Override
    public ScheduleTask toObject(ScheduleTaskEntity entity) {
        return entity == null ? null : ScheduleTask.builder()
                .id(entity.getTaskId())
                .ownerId(entity.getOwnerId())
                .driverId(entity.getDriverId())
                .status(entity.getStatus())

                .refId(entity.getRefId())
                .from(new StopLocation(entity.getFrom_name(), entity.getFrom_city()))
                .to(new StopLocation(entity.getTo_name(), entity.getTo_city()))
                .fromDate(entity.getFromDate())
                .toDate(entity.getToDate())
                .priority(entity.getPriority())
                .type(entity.getTaskType())
                .note(entity.getNote())
                .isFrequencyTask(entity.getIsCronTask())
                .frequency(entity.getCron())
                .executedAt(entity.getExecutedAt())
                .abortedAt(entity.getAbortedAt())

                .build();
    }

    @Override
    public ScheduleTaskEntity toEntity(ScheduleTask object) {
        return object == null ? null : ScheduleTaskEntity.builder()
                .taskId(object.getId())
                .ownerId(object.getOwnerId())
                .driverId(object.getDriverId())
                .status(object.getStatus())

                .refId(object.getRefId())
                .from_name(object.getFrom() != null ? object.getFrom().name() : null)
                .from_city(object.getFrom() != null ? object.getFrom().city() : null)

                .to_name(object.getTo() != null ? object.getTo().name() : null)
                .to_city(object.getTo() != null ? object.getTo().city() : null)

                .fromDate(object.getFromDate())
                .toDate(object.getToDate())
                .priority(object.getPriority())
                .taskType(object.getType())
                .note(object.getNote())
                .isCronTask(object.getIsFrequencyTask())
                .cron(object.getFrequency())
                .executedAt(object.getExecutedAt())
                .abortedAt(object.getAbortedAt())

                .build();
    }
}
