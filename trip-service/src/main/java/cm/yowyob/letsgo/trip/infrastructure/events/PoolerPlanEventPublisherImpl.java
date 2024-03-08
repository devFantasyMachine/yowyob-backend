package cm.yowyob.letsgo.trip.infrastructure.events;

import cm.yowyob.letsgo.trip.domain.events.PoolerPlanEventPublisher;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import org.springframework.stereotype.Component;


@Component
public class PoolerPlanEventPublisherImpl implements PoolerPlanEventPublisher {

    @Override
    public void newPoolerPlan(PoolerPlan savedPlan) {

    }

    @Override
    public void updatedPoolerPlan(PoolerPlan savedPlan) {

    }

}
