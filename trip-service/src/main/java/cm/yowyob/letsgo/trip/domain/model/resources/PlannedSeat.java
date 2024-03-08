package cm.yowyob.letsgo.trip.domain.model.resources;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class PlannedSeat extends TripResource {

    public PlannedSeat(UUID id, String label, Float defaultUnitCost) {
        super(id, label, ResourceType.SEAT, defaultUnitCost, 1F, 1F, "Pl(s)", PricingPolicy.NONE, 0);
    }

}

