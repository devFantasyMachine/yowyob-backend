/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

public interface PricingPolicyService {


    PricingPolicy save(PricingPolicy current);

    @NonNull
    Set<TripResource> findAllAvailableResources(UUID publishedTripId, Set<UUID> resourcesIds);

    PricingPolicy findById(String entityId);
}
