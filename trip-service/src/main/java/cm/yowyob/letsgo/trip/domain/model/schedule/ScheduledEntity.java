/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;





import cm.yowyob.letsgo.trip.domain.exceptions.IllegalCancelReservationException;

import java.time.ZonedDateTime;




public interface ScheduledEntity {

    String getId();

    default Boolean getIsCronJob(){return false;}

    default String getCronExpression() {
        throw new UnsupportedOperationException();
    }

    ZonedDateTime getBeginAt();

    /**
     *  interval unit between two execution of job. {@link IntervalUnit}
     */
    default IntervalUnit getIntervalUnit(){return IntervalUnit.HOURS;}


    JobStatus getJobStatus();

    /**
     *  delay after each execution sauf dans le cas d'un job cron
     * @return Long
     */
    default Long getRepeatInterval() {
        throw new UnsupportedOperationException();
    }

    default Integer getRepeatCountMax() {
        return Integer.MAX_VALUE;
    }


    default ScheduledEntity doAfterApplication() throws Exception  {
        throw new UnsupportedOperationException();
    }

    default ScheduledEntity doBeforeApplication() throws Exception {
        throw new UnsupportedOperationException();
    }


    /**
     *  the number of execution
     */
    Integer getRepeatCount();



    default boolean isApplicable() {

        return getRepeatCountMax() > getRepeatCount();
    }


    void setJobStatus(JobStatus jobStatus);


    default void delete(){
         setJobStatus(JobStatus.DELETED);
    }

    default void resume(){
        setJobStatus(JobStatus.RESUMED);
    }

    default void pause(){
        setJobStatus(JobStatus.PAUSED);
    }

    default void update(){
        setJobStatus(JobStatus.EDITED_SCHEDULED);
    }

    default void scheduleNew(){
        setJobStatus(JobStatus.SCHEDULED);
    }

    default void startNow(){
        setJobStatus(JobStatus.SCHEDULED_STARTED);
    }

    String groupJob();

}


