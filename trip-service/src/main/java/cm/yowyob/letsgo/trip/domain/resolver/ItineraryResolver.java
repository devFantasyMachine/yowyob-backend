package cm.yowyob.letsgo.trip.domain.resolver;

import cm.yowyob.letsgo.trip.domain.model.plan.core.AbstractTripPlan;

public abstract class ItineraryResolver<T extends AbstractTripPlan<?, T>> {

    private NotificationSender notificationSender;


    abstract void attemptResolution(T plan);




}
