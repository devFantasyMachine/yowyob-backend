package cm.yowyob.letsgo.trip.domain.model.schedule;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;



public class UnLockTripResourcesExecutorProvider extends JobExecutorProvider<LockedTripResources>{


    public UnLockTripResourcesExecutorProvider(
            ScheduledObjectAccessor<LockedTripResources> scheduledObjectAccessor) {
        super(scheduledObjectAccessor);
    }

    @Override
    protected JobExecutorResponse executeInternal(LockedTripResources entity) throws Exception {
        return null;
    }

    @Override
    public String acceptGroup() {
        return null;
    }

}
