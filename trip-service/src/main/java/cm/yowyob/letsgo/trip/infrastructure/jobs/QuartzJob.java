/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.jobs;

import cm.yowyob.letsgo.trip.domain.managers.JobExecutorManager;
import cm.yowyob.letsgo.trip.domain.model.schedule.AccessorEntry;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobExecutorResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

// TODO: 29/08/2023 remove comment on @Component

@Slf4j
// @Component
@AllArgsConstructor
public class QuartzJob extends QuartzJobBean {

    private JobEngine jobEngine;
    private JobExecutorManager jobExecutorManager;


    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) {
        log.info("Executing Job with key {}", context.getJobDetail().getKey());

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String entityId = jobDataMap.getString("entityId");
        String jobGroup = jobDataMap.getString("jobGroup");

        JobExecutorResponse response;

        try {

            try {

                AccessorEntry entry = AccessorEntry.builder()
                        .group(jobGroup)
                        .id(entityId)
                        .build();

                response = jobExecutorManager.execute(entry);

            } catch (Exception e) {
                throw new JobExecutionException();
            }

            log.info("success");

            if (response == JobExecutorResponse.DELETE_JOB){

                jobEngine.deleteJob(entityId, jobGroup);
            }

        } catch (JobExecutionException e) {

            log.error("error");
            jobEngine.deleteJob(entityId, jobGroup);

        }
    }
}
