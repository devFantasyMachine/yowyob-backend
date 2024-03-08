/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
@AllArgsConstructor
public class JobExecutorResponse {

    public static final JobExecutorResponse DELETE_JOB = new JobExecutorResponse(Status.DELETE_JOB);


    private Status status;

    public enum Status {

        DELETE_JOB,

        CONTINUE_JOB;
    }


}
