package cm.yowyob.letsgo.driver.domain.handler;

import cm.yowyob.letsgo.driver.domain.exception.InvalidPlannerAttachmentException;
import cm.yowyob.letsgo.driver.domain.exception.InvalidTaskStatusException;
import cm.yowyob.letsgo.driver.domain.exception.TaskNotFoundException;
import cm.yowyob.letsgo.driver.domain.exception.UnAttachedPlannerDriverException;
import cm.yowyob.letsgo.driver.domain.model.ObjectUtils;
import cm.yowyob.letsgo.driver.domain.model.TaskStatus;
import cm.yowyob.letsgo.driver.domain.model.driver.PlannerAttachment;
import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;
import cm.yowyob.letsgo.driver.domain.ports.DriverPlanningRecord;
import cm.yowyob.letsgo.driver.domain.ports.PlannerAttachmentService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
public class PlanningHandler {

    DriverPlanningRecord driverPlanningRecord;
    PlannerAttachmentService plannerAttachmentService;



    public Set<ScheduleTask> getPlanning(String driverId, LocalDateTime startAt){

        return driverPlanningRecord.getDriverPlanningAfter(driverId,  startAt);
    }



    public ScheduleTask addPlanningEntry(@NonNull ScheduleTask taskRequest, String driverId,
                                         String ownerId)
            throws UnAttachedPlannerDriverException, InvalidPlannerAttachmentException {


        if (!Objects.equals(ownerId, driverId)) {

            Optional<PlannerAttachment> optionalPlannerAttachment =
                    plannerAttachmentService.getByPlannerIdAndDriverId(ownerId, driverId);

            if (optionalPlannerAttachment.isEmpty())
                throw new UnAttachedPlannerDriverException();

            PlannerAttachment attachment = optionalPlannerAttachment.get();

            if (!attachment.getIsAttached())
                throw new InvalidPlannerAttachmentException();
        }

        ScheduleTask newTask = ScheduleTask.builder()
                .id(UUID.randomUUID())
                .ownerId(ownerId)
                .driverId(driverId)
                .status(TaskStatus.SCHEDULED)
                .createdAt(LocalDateTime.now())

                .refId(taskRequest.getRefId())
                .from(taskRequest.getFrom())
                .to(taskRequest.getTo())
                .fromDate(taskRequest.getFromDate())
                .toDate(taskRequest.getToDate())
                .priority(taskRequest.getPriority())
                .type(taskRequest.getType())
                .note(taskRequest.getNote())
                .isFrequencyTask(taskRequest.getIsFrequencyTask())
                .frequency(taskRequest.getFrequency())

                .build();


        return driverPlanningRecord.save(newTask);

    }


    public ScheduleTask updatePlanningEntry(@NonNull ScheduleTask taskRequest,
                                            String ownerId,
                                            UUID taskId)
            throws InvalidPlannerAttachmentException, TaskNotFoundException {

        ScheduleTask oldScheduleTask = getPlanningEntry(taskId);

         if (!Objects.equals(oldScheduleTask.getOwnerId(), ownerId))
            throw new InvalidPlannerAttachmentException();

         if (oldScheduleTask.getStatus() != TaskStatus.SCHEDULED)
             throw new IllegalArgumentException();


        ScheduleTask newTask = ScheduleTask.builder()
                .id(oldScheduleTask.getId())
                .refId(oldScheduleTask.getRefId())
                .status(oldScheduleTask.getStatus())
                .ownerId(oldScheduleTask.getOwnerId())
                .driverId(oldScheduleTask.getDriverId())

                .from(ObjectUtils.getOrDefault(oldScheduleTask.getFrom(), taskRequest.getFrom()))
                .to(ObjectUtils.getOrDefault(oldScheduleTask.getTo(), taskRequest.getTo()))
                .fromDate(ObjectUtils.getOrDefault(oldScheduleTask.getFromDate(), taskRequest.getFromDate()))
                .toDate(ObjectUtils.getOrDefault(oldScheduleTask.getToDate(), taskRequest.getToDate()))
                .priority(ObjectUtils.getOrDefault(oldScheduleTask.getPriority(), taskRequest.getPriority()))
                .type(ObjectUtils.getOrDefault(oldScheduleTask.getType(), taskRequest.getType()))
                .note(ObjectUtils.getOrDefault(oldScheduleTask.getNote(), taskRequest.getNote()))
                .isFrequencyTask(ObjectUtils.getOrDefault(oldScheduleTask.getIsFrequencyTask(), taskRequest.getIsFrequencyTask()))
                .frequency(ObjectUtils.getOrDefault(oldScheduleTask.getFrequency(), taskRequest.getFrequency()))

                .build();


        return driverPlanningRecord.save(newTask);

    }



    public ScheduleTask changePlanningEntryStatus(String ownerId, UUID taskId, TaskStatus newStatus)
            throws InvalidPlannerAttachmentException, TaskNotFoundException, InvalidTaskStatusException {

        ScheduleTask oldScheduleTask = getPlanningEntry(taskId);

        if (!Objects.equals(oldScheduleTask.getOwnerId(), ownerId) ||
                !Objects.equals(oldScheduleTask.getDriverId(), ownerId))
            throw new InvalidPlannerAttachmentException();

        oldScheduleTask.changeStatus(newStatus);

        return driverPlanningRecord.save(oldScheduleTask);

    }


    public ScheduleTask getPlanningEntry(UUID taskId) throws TaskNotFoundException {

        Optional<ScheduleTask> optionalScheduleOldTask = driverPlanningRecord.getTaskById(taskId);

        if (optionalScheduleOldTask.isEmpty())
            throw new TaskNotFoundException();

        return optionalScheduleOldTask.get();
    }


    public void deletePlanningEntry(String ownerId, UUID taskId) throws TaskNotFoundException, InvalidPlannerAttachmentException {

        ScheduleTask oldScheduleTask = getPlanningEntry(taskId);

        if (!Objects.equals(oldScheduleTask.getOwnerId(), ownerId))
            throw new InvalidPlannerAttachmentException();

        driverPlanningRecord.delete(taskId);
    }
}
