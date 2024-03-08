/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.policies;



import java.time.Duration;


public record CoursePolicy (Duration maxWaitingDurationOnStop,
                            Duration maxDepartureWaitingDuration) {


    public static final CoursePolicy DEFAULT
            = new CoursePolicy(Duration.ofMinutes(10), Duration.ofMinutes(10));


}
