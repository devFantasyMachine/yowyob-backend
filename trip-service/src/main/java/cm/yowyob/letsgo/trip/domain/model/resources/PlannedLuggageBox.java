package cm.yowyob.letsgo.trip.domain.model.resources;

import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlannedLuggageBox extends TripResource {

    public PlannedLuggageBox(UUID id, String label, Float quantity, Float unitQuantity, Float defaultUnitCost) {
        super(id, label, ResourceType.LUGGAGE, defaultUnitCost, quantity, unitQuantity, "Kg", PricingPolicy.NONE, 0);
    }


}


