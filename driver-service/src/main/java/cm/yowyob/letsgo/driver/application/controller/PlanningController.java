package cm.yowyob.letsgo.driver.application.controller;


import cm.yowyob.letsgo.driver.application.dto.ScheduleTaskDTO;
import cm.yowyob.letsgo.driver.domain.exception.InvalidPlannerAttachmentException;
import cm.yowyob.letsgo.driver.domain.exception.InvalidTaskStatusException;
import cm.yowyob.letsgo.driver.domain.exception.TaskNotFoundException;
import cm.yowyob.letsgo.driver.domain.exception.UnAttachedPlannerDriverException;
import cm.yowyob.letsgo.driver.domain.handler.PlanningHandler;
import cm.yowyob.letsgo.driver.domain.model.TaskStatus;
import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;


import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v0/drivers/plannings")
public class PlanningController {


    @Autowired
    PlanningHandler planningHandler;



    @GetMapping(value = "")
    public Set<ScheduleTaskDTO> getScheduleTasks(@NonNull Authentication authentication,
                                                 @RequestParam(required = false) @Nullable LocalDateTime afterDate) {

        Set<ScheduleTask> tasks = planningHandler.getPlanning(authentication.getName(), afterDate);

        return tasks.stream()
                .map(ScheduleTaskDTO::fromDomainObject)
                .collect(Collectors.toSet());
    }



    @GetMapping(value = "/{taskId}")
    public ScheduleTaskDTO getScheduleTask(@PathVariable @NonNull String taskId)
            throws TaskNotFoundException {

        ScheduleTask task = planningHandler.getPlanningEntry(UUID.fromString(taskId));

        return ScheduleTaskDTO.fromDomainObject(task);
    }


    @PostMapping("")
    public ScheduleTaskDTO createTask(@NonNull Authentication authentication,
                                      @RequestParam(value = "owner", required = false) String owner,
                                      @RequestBody @Valid ScheduleTaskDTO request)
            throws InvalidPlannerAttachmentException, UnAttachedPlannerDriverException {


        ScheduleTask scheduleTask = ScheduleTask.builder()
                .refId(request.getRefId())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .priority(request.getPriority())
                .type(request.getType())
                .note(request.getNote())
                .isFrequencyTask(request.getIsCronTask())
                .frequency(request.getCron())
                .executedAt(request.getExecutedAt())
                .abortedAt(request.getAbortedAt())

                .from(request.getFrom() == null ? null : request.getFrom().toDomainObject())
                .to(request.getTo() == null ? null : request.getTo().toDomainObject())
                .build();


        ScheduleTask task =
                planningHandler.addPlanningEntry(scheduleTask,
                        authentication.getName(),
                        owner == null ? authentication.getName() : owner);

        return ScheduleTaskDTO.fromDomainObject(task);
    }


    @PutMapping("/{taskId}")
    public ScheduleTaskDTO updatePlanningEntry(
            @NonNull Authentication authentication,
            @PathVariable String taskId,
            @RequestBody ScheduleTaskDTO request)
            throws InvalidPlannerAttachmentException, TaskNotFoundException {

        ScheduleTask scheduleTask = ScheduleTask.builder()
                .refId(request.getRefId())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .priority(request.getPriority())
                .type(request.getType())
                .note(request.getNote())
                .isFrequencyTask(request.getIsCronTask())
                .frequency(request.getCron())
                .executedAt(request.getExecutedAt())
                .abortedAt(request.getAbortedAt())

                .from(request.getFrom() == null ? null : request.getFrom().toDomainObject())
                .to(request.getTo() == null ? null : request.getTo().toDomainObject())
                .build();


        ScheduleTask task =
                planningHandler.updatePlanningEntry(scheduleTask,
                        authentication.getName(), UUID.fromString(taskId));

        return ScheduleTaskDTO.fromDomainObject(task);

    }



    @PutMapping("/{taskId}/status")
    public ScheduleTaskDTO changePlanningEntryStatus(
            @NonNull Authentication authentication,
            @PathVariable String taskId,
            @RequestParam(value = "value") TaskStatus status)
            throws InvalidPlannerAttachmentException, TaskNotFoundException, InvalidTaskStatusException {

        ScheduleTask task =
                planningHandler.changePlanningEntryStatus(authentication.getName(),
                        UUID.fromString(taskId),
                        status);

        return ScheduleTaskDTO.fromDomainObject(task);

    }


    @DeleteMapping("/{taskId}")
    public void deletePlanningEntry(
            @NonNull Authentication authentication,
            @PathVariable String taskId)
            throws InvalidPlannerAttachmentException, TaskNotFoundException {

        planningHandler.deletePlanningEntry(authentication.getName(), UUID.fromString(taskId));

    }


}
