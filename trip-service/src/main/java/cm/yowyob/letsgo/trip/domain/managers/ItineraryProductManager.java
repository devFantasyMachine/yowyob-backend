package cm.yowyob.letsgo.trip.domain.managers;


import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.ports.ItineraryProductRecord;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class ItineraryProductManager {

    ItineraryProductRecord itineraryProductRecord;


    public Set<ItineraryProduct> getAll(UUID planId, Set<UUID> productIds){

        return itineraryProductRecord.getAll(planId, productIds);
    }

    public Set<ItineraryProduct> getAll(UUID planId){

        return itineraryProductRecord.getAll(planId);
    }


}
