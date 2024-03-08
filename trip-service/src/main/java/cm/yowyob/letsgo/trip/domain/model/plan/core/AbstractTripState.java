package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

import java.time.ZonedDateTime;
import java.util.Objects;


// FIXME: 29/08/2023 remove Leg
public abstract class AbstractTripState<L extends Leg, P extends AbstractTripPlan<L, P>>  {

    protected final P tripPlan;

    public AbstractTripState(P tripPlan) {
        this.tripPlan = tripPlan;
    }


    public void publish(String publishTripId) throws IllegalPublishNonPlannedTripException {

        if (this.tripPlan.planStatus != PlanStatus.PLANNED)
            throw new IllegalPublishNonPlannedTripException();

        this.tripPlan.planStatus = PlanStatus.PUBLISHED;
        this.tripPlan.publishedAt = ZonedDateTime.now();
        this.tripPlan.publishedTripCode = Objects.requireNonNull(publishTripId);
    }


    public void resolve() throws IllegalResolveNonPublishedTripException {

        if (this.tripPlan.planStatus != PlanStatus.PUBLISHED)
            throw new IllegalResolveNonPublishedTripException();

        this.tripPlan.planStatus = PlanStatus.RESOLVED;
        this.tripPlan.resolvedAt = ZonedDateTime.now();
    }


    public void execute() throws IllegalExecuteNonResolvedTripException {

        if (this.tripPlan.planStatus != PlanStatus.RESOLVED)
            throw new IllegalExecuteNonResolvedTripException();

        this.tripPlan.planStatus = PlanStatus.EXECUTED;
        this.tripPlan.executedAt = ZonedDateTime.now();
    }


    public void cancel() throws IllegalCancelTripException {

        if (!(this.tripPlan.planStatus == PlanStatus.PUBLISHED ||
                this.tripPlan.planStatus == PlanStatus.RESOLVED))
            throw new IllegalCancelTripException();

        this.tripPlan.planStatus = PlanStatus.CANCELED;
        this.tripPlan.canceledAt = ZonedDateTime.now();
    }



    public abstract void addItinerary(Itinerary<L> otherItinerary);

    public abstract Itinerary<L> removeItinerary(int index);


    // FIXME: 20/08/2023
    public boolean canUpdateItinerary(){

        return canUpdate();
    }

    public boolean canUpdate() {

        return this.tripPlan.planStatus == PlanStatus.PLANNED ||
               ( this.tripPlan.planStatus == PlanStatus.PUBLISHED && this.tripPlan.reservationCount <= 0) ;
    }


}
