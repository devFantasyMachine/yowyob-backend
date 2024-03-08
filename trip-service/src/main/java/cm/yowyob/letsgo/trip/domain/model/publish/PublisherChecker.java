/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;

import cm.yowyob.letsgo.trip.domain.model.plan.core.AbstractTripPlan;


public interface PublisherChecker<T extends AbstractTripPlan<?, T>> {

    CheckerResponse doCheck(T tripPlan, SharedPolicy sharedPolicy);
}
