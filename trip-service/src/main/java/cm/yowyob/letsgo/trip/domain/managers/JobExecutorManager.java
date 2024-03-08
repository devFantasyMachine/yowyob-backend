package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.model.schedule.AccessorEntry;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobExecutorProvider;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobExecutorResponse;
import cm.yowyob.letsgo.trip.domain.model.schedule.UnLockTripResourcesExecutorProvider;

import java.util.*;


public class JobExecutorManager {

    public Set<JobExecutorProvider<?>> executorProviders;

    public JobExecutorManager(JobExecutorProvider<?>... jobExecutorProviders) {
        Objects.requireNonNull(jobExecutorProviders);

        executorProviders = new HashSet<>(Arrays.asList(jobExecutorProviders));
    }





    public JobExecutorResponse execute(AccessorEntry entry) throws Exception {

        Optional<JobExecutorProvider<?>> executorProvider = executorProviders
                .stream()
                .filter(jobExecutorProvider -> jobExecutorProvider.acceptGroup().equals(entry.getGroup()))
                .findFirst();

        if (executorProvider.isEmpty())
            return JobExecutorResponse.DELETE_JOB;

        return executorProvider.get().executeJob(entry);
    }

}
