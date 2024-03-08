/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 *//*


package cm.yowyob.letsgo.trip.domain.model.schedule;

import cm.yowyob.letsgo.trip.domain.managers.PricingPolicyService;
import cm.yowyob.letsgo.trip.domain.model.RoutineTrip;
import lombok.NonNull;


public final class RoutineTripExecutor extends JobExecutorProvider<RoutineTrip> {


    private final PublishedTripService publishedTripService;
    private final PricingPolicyService pricingPolicyService;

    public RoutineTripExecutor(PublishedTripService publishedTripService,
                               PricingPolicyService pricingPolicyService) {
        this.publishedTripService = publishedTripService;
        this.pricingPolicyService = pricingPolicyService;
    }


    @Override
    public RoutineTrip getEntity(String entityId) {
        return null;
    }

    @Override
    public JobExecutorResponse executeInternal(@NonNull RoutineTrip current) throws Exception {

        return JobExecutorResponse.CONTINUE_JOB;
    }



}
*/
