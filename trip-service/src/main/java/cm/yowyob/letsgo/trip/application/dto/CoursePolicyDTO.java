/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.policies.CoursePolicy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
public class CoursePolicyDTO {

    private Long maxWaitingDurationOnStop;
    private Long maxDepartureWaitingDuration;

    public CoursePolicy toCoursePolicy(){

        return new CoursePolicy(Duration.ofMinutes(maxWaitingDurationOnStop), Duration.ofMinutes(maxDepartureWaitingDuration));

    }

    public static CoursePolicyDTO fromCoursePolicy(CoursePolicy coursePolicy){

        if (coursePolicy == null)
            return null;

        return new CoursePolicyDTO(coursePolicy.maxWaitingDurationOnStop().toSeconds(),
                coursePolicy.maxDepartureWaitingDuration().toSeconds());
    }

}
