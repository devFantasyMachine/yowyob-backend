/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */


package cm.yowyob.letsgo.trip.domain.model.schedule;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.ports.PlannedTripService;


public class ApplyPricingPolicyExecutorProvider
        extends JobExecutorProvider<PricingPolicy> {


    PlannedTripService publishedTripService;



    public ApplyPricingPolicyExecutorProvider(
            ScheduledObjectAccessor<PricingPolicy> scheduledObjectAccessor) {
        super(scheduledObjectAccessor);

    }


    @Override
    protected JobExecutorResponse executeInternal(PricingPolicy entity) throws Exception {



        return null;
    }

    @Override
    public String acceptGroup() {
        return "pricing.policy";
    }


}

