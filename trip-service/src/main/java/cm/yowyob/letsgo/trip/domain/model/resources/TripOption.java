/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */


package cm.yowyob.letsgo.trip.domain.model.resources;


import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TripOption extends TripResource {

    public TripOption(UUID id, String label, Float defaultUnitCost, Float totalQuantity, Float unitQuantity, String unitTag) {
        super(id, label, ResourceType.OPTION, defaultUnitCost, totalQuantity, unitQuantity, unitTag, PricingPolicy.NONE, 0);
    }

    public TripOption(UUID id, String label, Float defaultUnitCost, Float totalQuantity, Float unitQuantity, String unitTag, PricingPolicy pricingPolicy, Integer reservationCount) {
        super(id, label, ResourceType.OPTION, defaultUnitCost, totalQuantity, unitQuantity, unitTag, pricingPolicy, reservationCount);
    }


}

