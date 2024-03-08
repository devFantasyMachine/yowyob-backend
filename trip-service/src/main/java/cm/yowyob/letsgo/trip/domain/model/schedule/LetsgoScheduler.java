/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;


public interface LetsgoScheduler {

    default boolean deleteJob(ScheduledObject entity){
        return false;
    }

    default boolean pauseJob(ScheduledObject entity) {
        return false;
    }

    default boolean resumeJob(ScheduledObject entity) {
        return false;
    }

    default boolean startJobNow(ScheduledObject entity){
        return false;
    }

    default boolean scheduleNewJob(ScheduledObject entity) {

        return false;
    }

    default void updateScheduleJob(ScheduledObject entity) {}

}
