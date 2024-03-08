package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;

public interface PoolerPlanEventPublisher {

    void newPoolerPlan(PoolerPlan savedPlan);

    void updatedPoolerPlan(PoolerPlan savedPlan);

}
