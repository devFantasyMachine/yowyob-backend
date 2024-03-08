/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.policies;


import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import cm.yowyob.letsgo.trip.domain.model.schedule.ScheduledObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
public final class PricingPolicy extends ScheduledObject {

    public static PricingPolicy NONE = null;

    private final UUID policyId;

    /** the overflow cost. We add overflow cost on standard cost when policy is executed. can be negative
     */
    private Float overflowCost;


    public PricingPolicy(LocalDateTime beginAt, LocalDateTime endAt, LocalDateTime nextActivation, Duration repeatInterval, Boolean isCron, String cron, Long repeatCount, Long repeatCountMax, JobStatus jobStatus, UUID policyId, Float overflowCost) {
        super(beginAt, endAt, nextActivation, repeatInterval, isCron, cron, repeatCount, repeatCountMax, jobStatus);
        this.policyId = policyId;
        this.overflowCost = overflowCost;
    }


    @Override
    public String groupJob() {
        return "pricing.policy";
    }

    @Override
    public String getId() {
        return policyId.toString();
    }


}
