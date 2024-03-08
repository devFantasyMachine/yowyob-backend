/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.ports.PlannedTripService;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.resources.*;
import cm.yowyob.letsgo.trip.infrastructure.entities.LegEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.PlannerTripPlanEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.PoolerTripPlanEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.TripResourceEntity;
import cm.yowyob.letsgo.trip.infrastructure.repositories.PlannerPlanRepository;
import cm.yowyob.letsgo.trip.infrastructure.repositories.PlannerLegEntityRepository;
import cm.yowyob.letsgo.trip.infrastructure.repositories.PoolerPlanRepository;
import cm.yowyob.letsgo.trip.infrastructure.repositories.TripResourceEntityRepository;
import cm.yowyob.letsgo.trip.mappers.*;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;




@Service
@AllArgsConstructor
public class CassandraPlannedTripService implements PlannedTripService {

    PlannerPlanRepository plannerPlanRepository;
    PoolerPlanRepository poolerPlanRepository;
    PlannerLegEntityRepository plannerLegEntityRepository;
    TripResourceEntityRepository tripResourceEntityRepository;


    private final LegMapper legMapper = new LegMapper();
    private final PlannerLegMapper plannerLegMapper = new PlannerLegMapper();
    private final PoolerPlanMapper poolerPlanMapper = new PoolerPlanMapper();
    private final PlannerPlanMapper plannerPlanMapper = new PlannerPlanMapper();
    private final TripResourceMapper tripResourceMapper = new TripResourceMapper();



    @SneakyThrows
    @Override
    //@CachePut
    public PlannerPlan save(PlannerPlan plannedTrip) {

        PlannerTripPlanEntity tripPlanEntity = plannerPlanMapper.toEntity(plannedTrip);
        tripPlanEntity = plannerPlanRepository.save(tripPlanEntity);

        List<TripResource> allTripResources = plannedTrip.getResources();

        List<LegEntity> legEntities =
                plannerLegMapper.toEntities(plannedTrip.getItinerary().getLegs());

        for (LegEntity legEntity : legEntities) {
            legEntity.setPlannerId(plannedTrip.getPlannerInformation().userId());
            legEntity.setPlanId(plannedTrip.getPlanId());
        }

        List<LegEntity> plannerLegEntities =
                plannerLegEntityRepository.saveAll(legEntities);

        tripPlanEntity.setLegs(plannerLegEntities);

        List<TripResourceEntity> tripResourceEntities =
                tripResourceEntityRepository.saveAll(tripResourceMapper.toEntities(allTripResources));

        tripPlanEntity.setTripResourceEntities(tripResourceEntities);

        return plannerPlanMapper.toObject(tripPlanEntity);
    }



    @SneakyThrows
    @Override
    //@Cache
    public Optional<PlannerPlan> getPlannerPlanById(@NonNull UUID tripPlanId) {

        PlannerTripPlanEntity tripPlanEntity = plannerPlanRepository.findByTripPlanId(tripPlanId);

        if(tripPlanEntity == null)
            return Optional.empty();

        List<LegEntity> plannerLegEntities =
                plannerLegEntityRepository.findAllByPlanId(tripPlanEntity.getTripPlanId());

        List<TripResourceEntity> tripResourceEntities =
                tripResourceEntityRepository.findAllByTripId(tripPlanEntity.getTripPlanId());

        tripPlanEntity.setLegs(plannerLegEntities);
        tripPlanEntity.setTripResourceEntities(tripResourceEntities);

        return Optional.ofNullable(plannerPlanMapper.toObject(tripPlanEntity));
    }



     private Set<PlannerTripPlanEntity> findAllByPlannerId(String plannerId){

        return new HashSet<>(plannerPlanRepository.findAllByPlannerId(plannerId));
     }



    @Override
    public Set<PlannerPlan> getAllByPlannerId(String plannerId, boolean isPublished) {

        Set<PlannerTripPlanEntity> tripPlanEntities =
                findAllByPlannerId(plannerId);

        Map<UUID, List<LegEntity>> plannerLegEntities =
                plannerLegEntityRepository.findAllByPlannerId(plannerId)
                        .stream().collect(Collectors.groupingBy(LegEntity::getPlanId));

        Map<UUID, List<TripResourceEntity>> tripResourceEntities =
                tripResourceEntityRepository.findAllByPlannerId(plannerId)
                        .stream().collect(Collectors.groupingBy(TripResourceEntity::getTripId));

        return plannerPlanMapper.toObjectSet(tripPlanEntities);

    }




    @Override
    public Set<PlannerPlan> getRangeByPlannerId(String plannerId,
                                                ZonedDateTime planAtFrom,
                                                ZonedDateTime planAtTo) {








        return Set.of();
    }



    @SneakyThrows
    @Override
    public PoolerPlan save(PoolerPlan poolerPlan) {

        PoolerTripPlanEntity tripPlanEntity =
                poolerPlanRepository.save(poolerPlanMapper.toEntity(poolerPlan));

        List<LegEntity> legEntities = legMapper.toEntities(poolerPlan.getItinerary().getLegs());

        for (LegEntity legEntity : legEntities) {
            legEntity.setPlannerId(poolerPlan.getPlannerInformation().userId());
            legEntity.setPlanId(poolerPlan.getPlanId());
        }

        List<LegEntity> plannerLegEntities =
                plannerLegEntityRepository.saveAll(legEntities);

        tripPlanEntity.setLegs(plannerLegEntities);

        return poolerPlanMapper.toObject(tripPlanEntity);
    }


    @SneakyThrows
    @Override
    public Optional<PoolerPlan> getPoolerPlanById(UUID tripPlanId) {

        PoolerTripPlanEntity tripPlanEntity = poolerPlanRepository.findByTripPlanId(tripPlanId);

        if(tripPlanEntity == null)
            return Optional.empty();

        List<LegEntity> plannerLegEntities =
                plannerLegEntityRepository.findAllByPlanId(tripPlanEntity.getTripPlanId());

        tripPlanEntity.setLegs(plannerLegEntities);

        return Optional.ofNullable(poolerPlanMapper.toObject(tripPlanEntity));
    }



    @Override
    public Set<PoolerPlan> getAllByPoolerId(String poolerId) {

        Set<PoolerTripPlanEntity> tripPlanEntities = poolerPlanRepository.findAllByPlannerId(poolerId);

        if (tripPlanEntities == null)
            return Set.of();

        Map<UUID, List<LegEntity>> plannerLegEntities =
                plannerLegEntityRepository.findAllByPlannerId(poolerId)
                        .stream().collect(Collectors.groupingBy(LegEntity::getPlanId));

        for (PoolerTripPlanEntity tripPlanEntity : tripPlanEntities) {
            tripPlanEntity.setLegs(plannerLegEntities.get(tripPlanEntity.getTripPlanId()));
        }

        return poolerPlanMapper.toObjectSet(tripPlanEntities);
    }



    @Override
    public Set<TripResource> getPlannerPlanResource(UUID tripPlanId) {

        List<TripResourceEntity> tripResourceEntities =
                tripResourceEntityRepository.findAllByTripId(tripPlanId);

        return tripResourceMapper.toObjectSet(tripResourceEntities);
    }


}

