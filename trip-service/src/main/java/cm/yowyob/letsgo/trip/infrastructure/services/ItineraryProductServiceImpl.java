package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.ports.ItineraryProductRecord;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.infrastructure.entities.ItineraryProductEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.TripResourceEntity;
import cm.yowyob.letsgo.trip.infrastructure.repositories.ItineraryProductEntityRepository;
import cm.yowyob.letsgo.trip.infrastructure.repositories.TripResourceEntityRepository;
import cm.yowyob.letsgo.trip.mappers.ItineraryProductMapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ItineraryProductServiceImpl implements ItineraryProductRecord {


    TripResourceEntityRepository tripResourceEntityRepository;
    ItineraryProductEntityRepository itineraryProductEntityRepository;

    private final ItineraryProductMapper itineraryProductMapper = new ItineraryProductMapper();


    @Override
    public Set<ItineraryProduct> getAll(UUID planId, Set<UUID> productIds) {

        List<TripResourceEntity> tripResourceEntities =
                tripResourceEntityRepository.findAllByTripId(planId);

        Map<UUID, List<TripResourceEntity>> tripResourcesById = tripResourceEntities
                .stream()
                .collect(Collectors.groupingBy(TripResourceEntity::getResourceId));

        List<ItineraryProductEntity> itineraryProductEntities =
                itineraryProductEntityRepository.findAllByProductIdIn(productIds);

        for (ItineraryProductEntity itineraryProductEntity : itineraryProductEntities) {

            itineraryProductEntity
                    .setResource(tripResourcesById
                            .get(itineraryProductEntity.getResourceId())
                            .get(0));
        }

        return itineraryProductMapper.toObjectSet(itineraryProductEntities);

    }

    @Override
    public void saveAll(List<ItineraryProduct> itineraryProducts) {

        List<ItineraryProductEntity> productEntities =
                itineraryProductMapper.toEntities(itineraryProducts);

        itineraryProductEntityRepository.saveAll(productEntities);
    }

    @Override
    public Set<ItineraryProduct> getAll(UUID planId) {

        List<TripResourceEntity> tripResourceEntities =
                tripResourceEntityRepository.findAllByTripId(planId);

        Map<UUID, List<TripResourceEntity>> tripResourcesById = tripResourceEntities
                .stream()
                .collect(Collectors.groupingBy(TripResourceEntity::getResourceId));

        List<ItineraryProductEntity> itineraryProductEntities =
                itineraryProductEntityRepository.findAllByPlanId(planId);

        for (ItineraryProductEntity itineraryProductEntity : itineraryProductEntities) {

            itineraryProductEntity
                    .setResource(tripResourcesById
                            .get(itineraryProductEntity.getResourceId())
                            .get(0));
        }

        return itineraryProductMapper.toObjectSet(itineraryProductEntities);
    }


}
