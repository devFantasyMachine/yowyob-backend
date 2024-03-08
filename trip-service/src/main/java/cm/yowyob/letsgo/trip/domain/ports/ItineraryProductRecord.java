package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface ItineraryProductRecord {

    Set<ItineraryProduct> getAll(UUID planId, Set<UUID> productIds);

    void saveAll(List<ItineraryProduct> itineraryProducts);

    Set<ItineraryProduct> getAll(UUID planId);

}
