package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalCancelTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalExecuteNonResolvedTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalPublishNonPlannedTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalResolveNonPublishedTripException;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

public class PoolerPlanStatePlanned extends AbstractPoolerPlanState{


    public PoolerPlanStatePlanned(PoolerPlan tripPlan) {
        super(tripPlan);
    }

    @Override
    public void publish(String publishTripId) throws IllegalPublishNonPlannedTripException {
        super.publish(publishTripId);
        this.tripPlan.planState = new PoolerPlanStatePublished(tripPlan);
    }

    @Override
    public void resolve() throws IllegalResolveNonPublishedTripException {
        throw new IllegalResolveNonPublishedTripException();
    }

    @Override
    public void execute() throws IllegalExecuteNonResolvedTripException {
        throw new IllegalExecuteNonResolvedTripException();
    }

    @Override
    public void cancel() throws IllegalCancelTripException {
        throw new IllegalCancelTripException();
    }

    @Override
    public void addItinerary(Itinerary<Leg> otherItinerary) {

    }

    @Override
    public Itinerary<Leg> removeItinerary(int index) {
        return null;
    }
}
