package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalCancelTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalPublishNonPlannedTripException;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

public class PoolerPlanStatePublished extends AbstractPoolerPlanState{


    public PoolerPlanStatePublished(PoolerPlan tripPlan) {
        super(tripPlan);
    }

    @Override
    public void publish(String publishTripId) throws IllegalPublishNonPlannedTripException {
        throw new IllegalPublishNonPlannedTripException();
    }

    @Override
    public void resolve() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void cancel() throws IllegalCancelTripException {
        super.cancel();
        this.tripPlan.planState = new PoolerPlanStateCanceled(tripPlan);
    }

    @Override
    public void addItinerary(Itinerary<Leg> otherItinerary) {

    }

    @Override
    public Itinerary<Leg> removeItinerary(int index) {
        return null;
    }
}
