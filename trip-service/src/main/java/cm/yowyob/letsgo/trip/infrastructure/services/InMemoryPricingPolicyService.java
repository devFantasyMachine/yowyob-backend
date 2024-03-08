/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.managers.PricingPolicyService;
import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class InMemoryPricingPolicyService implements PricingPolicyService {

    @Override
    public PricingPolicy save(PricingPolicy current) {
        return null;
    }

    @Override
    public @NonNull Set<TripResource> findAllAvailableResources(UUID publishedTripId, Set<UUID> resourcesIds) {
        return null;
    }

    @Override
    public PricingPolicy findById(String entityId) {
        return null;
    }
}
