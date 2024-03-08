package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

public class PlannerRoutine extends RoutineTrip<PlannerPlan>{


    public PlannerRoutine(LocalDateTime beginAt, LocalDateTime endAt, LocalDateTime nextActivation, Duration repeatInterval, Boolean isCron, String cron, Long repeatCount, Long repeatCountMax, JobStatus jobStatus) {
        super(beginAt, endAt, nextActivation, repeatInterval, isCron, cron, repeatCount, repeatCountMax, jobStatus);
    }

}
