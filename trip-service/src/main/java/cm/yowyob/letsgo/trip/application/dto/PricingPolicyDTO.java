/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.model.schedule.IntervalUnit;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class PricingPolicyDTO {

    @Nonnull
    private Set<String> resourcesIds;
    @Nonnull
    private Float overflowCost;
    @Nonnull
    private Long repeatInterval;

    private IntervalUnit intervalUnit;

    private Long repeatCountMax;

    @Nonnull
    private Long beginDelay;

    @Nullable
    private Integer repeatCount;

}
