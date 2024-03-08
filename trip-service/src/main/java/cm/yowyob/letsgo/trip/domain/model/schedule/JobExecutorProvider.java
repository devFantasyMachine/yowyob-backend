/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;




/**
 *
 * @param <T> extends ScheduledEntity. {@link ScheduledEntity}
 */
public abstract class JobExecutorProvider<T extends ScheduledObject> {

    public final ScheduledObjectAccessor<T> scheduledObjectAccessor;

    public JobExecutorProvider(ScheduledObjectAccessor<T> scheduledObjectAccessor) {
        this.scheduledObjectAccessor = scheduledObjectAccessor;
    }

    public final JobExecutorResponse executeJob(AccessorEntry entityId) {

        try {

            return scheduledObjectAccessor.getScheduledObject(entityId)
                    .map(entity -> {

                        try {
                            return executeInternal(entity);
                        } catch (Exception ignored) {
                            return JobExecutorResponse.DELETE_JOB;
                        }

                    })
                    .orElse(JobExecutorResponse.DELETE_JOB);

        } catch (Exception ignored) {
            return JobExecutorResponse.DELETE_JOB;
        }
    }


    protected abstract JobExecutorResponse executeInternal(T entity)throws Exception;

    public abstract String acceptGroup();

}
