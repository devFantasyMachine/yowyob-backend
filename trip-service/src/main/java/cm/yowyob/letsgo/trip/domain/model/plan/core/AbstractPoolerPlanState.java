package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

public abstract class AbstractPoolerPlanState extends AbstractTripState<Leg, PoolerPlan> {

    public AbstractPoolerPlanState(PoolerPlan tripPlan) {
        super(tripPlan);
    }


}
