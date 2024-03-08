package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;

public abstract class AbstractPlannerPlanState extends AbstractTripState<PlannerLeg, PlannerPlan> {

    public AbstractPlannerPlanState(PlannerPlan tripPlan) {
        super(tripPlan);
    }


}
