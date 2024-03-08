package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;
import cm.yowyob.letsgo.driver.domain.ports.DriverPlanningRecord;
import cm.yowyob.letsgo.driver.infrastructure.entities.ScheduleTaskEntity;
import cm.yowyob.letsgo.driver.infrastructure.repositories.ScheduleTaskEntityRepository;
import cm.yowyob.letsgo.driver.mappers.ScheduleTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;



@Service
public class ScyllaDriverPlanningRecord implements DriverPlanningRecord {


    @Autowired
    ScheduleTaskEntityRepository scheduleTaskEntityRepository;


    private final ScheduleTaskMapper scheduleTaskMapper = new ScheduleTaskMapper();



    @Override
    public ScheduleTask save(ScheduleTask newTask) {

        ScheduleTaskEntity scheduleTaskEntity =
                scheduleTaskEntityRepository.save(scheduleTaskMapper.toEntity(newTask));

        return scheduleTaskMapper.toObject(scheduleTaskEntity);
    }



    @Override
    public Set<ScheduleTask> getDriverPlanningAfter(String driverId, LocalDateTime startAt) {

        Set<ScheduleTaskEntity> entities = scheduleTaskEntityRepository.findByDriverId(driverId);
        return scheduleTaskMapper.toObjectSet(entities);
    }



    @Override
    public Optional<ScheduleTask> getTaskById(UUID taskRequestId) {

        Optional<ScheduleTaskEntity> taskEntity =
                scheduleTaskEntityRepository.findByTaskId(taskRequestId);

        return taskEntity.map(scheduleTaskMapper::toObject);
    }

    @Override
    public void delete(UUID taskId) {
        scheduleTaskEntityRepository.deleteById(taskId);
    }

}
