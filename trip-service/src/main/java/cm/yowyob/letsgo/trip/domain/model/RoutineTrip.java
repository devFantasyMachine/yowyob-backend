/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;


import cm.yowyob.letsgo.trip.domain.model.plan.core.AbstractTripPlan;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import cm.yowyob.letsgo.trip.domain.model.schedule.ScheduledObject;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;




@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class RoutineTrip<P extends AbstractTripPlan<?, P>> extends ScheduledObject {

    public static final String GROUP = "routine.trip";

    /**
     *  the routine's identifier
     */
    private UUID routineId;

    /**
     *  the id of user who have created the routine
     */
    private String userId;

    /**
     *  The base information that is used to schedule new TripPlan
     */
    private P baseTripPlan;
    private ZonedDateTime deleteAt;
    private ZonedDateTime updateAt;

    public RoutineTrip(LocalDateTime beginAt, LocalDateTime endAt, LocalDateTime nextActivation, Duration repeatInterval, Boolean isCron, String cron, Long repeatCount, Long repeatCountMax, JobStatus jobStatus) {
        super(beginAt, endAt, nextActivation, repeatInterval, isCron, cron, repeatCount, repeatCountMax, jobStatus);
    }


    @Override
    public String groupJob() {
        return GROUP;
    }

    @Override
    public String getId() {
        return routineId.toString();
    }


}
