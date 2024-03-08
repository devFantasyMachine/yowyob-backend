package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;

public class PlannerPlanStatePublished extends AbstractPlannerPlanState {
    public PlannerPlanStatePublished(PlannerPlan tripPlan) {
        super(tripPlan);
    }

    @Override
    public void addItinerary(Itinerary<PlannerLeg> otherItinerary) {

    }

    @Override
    public Itinerary<PlannerLeg> removeItinerary(int index) {
        return null;
    }
}
