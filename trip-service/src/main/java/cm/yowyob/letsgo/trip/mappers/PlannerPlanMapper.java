/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import cm.yowyob.letsgo.trip.infrastructure.entities.PlannerTripPlanEntity;
import lombok.SneakyThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class PlannerPlanMapper extends Mapper<PlannerPlan, PlannerTripPlanEntity> {

    private final PlannerLegMapper legMapper = new PlannerLegMapper();
    private final StopLocationMapper stopLocationMapper = new StopLocationMapper();
    private final ComfortMapper comfortMapper = new ComfortMapper();
    private final PlannerInfoMapper plannerInfoMapper = new PlannerInfoMapper();
    private final TripResourceMapper tripResourceMapper = new TripResourceMapper();



    @SneakyThrows
    @Override
    public PlannerPlan toObject(PlannerTripPlanEntity entity) {

        if (entity == null)
            return null;

        List<TripResource> tripResourceList = tripResourceMapper.toObjects(entity.getTripResourceEntities());
        return PlannerPlan.builder()
                .seatCost(entity.getSeatCost())
                .seatCount(entity.getSeatCount())
                .planId(entity.getTripPlanId())
                .luggageBoxQuantity(entity.getLuggageBoxQuantity())
                .tripPrestige(entity.getTripPrestige())
                .luggageBoxQuantity(entity.getLuggageBoxQuantity())
                .tripType(entity.getTripType())
                .planStatus(entity.getPlanStatus())
                .serviceType(entity.getServiceType())
                .plannerInformation(plannerInfoMapper.toObject(entity.getPlannerInformation()))
                .draftId(entity.getDraftId())
                .publisherId(entity.getPublisherId())
                .isBookable(entity.getIsBookable())
                .itineraries(new Itinerary<>(legMapper.toObjects(entity.getLegs())))
                .publishedTripCode(entity.getPublishCode())
                .canceledAt(entity.getCanceledAt() == null ? null: ZonedDateTime.of(entity.getCanceledAt(), ZoneId.of(entity.getTimezone())))

                .fromLocation(stopLocationMapper.toObject(entity.getFromLocation()))
                .toLocation(stopLocationMapper.toObject(entity.getToLocation()))
                .comforts(comfortMapper.toObjectSet(entity.getComforts()))
                .resources(tripResourceList == null ? null : new ArrayList<>(tripResourceList))
                .build();
    }



    @Override
    public PlannerTripPlanEntity toEntity(PlannerPlan object) {


        if (object == null)
            return null;

        return PlannerTripPlanEntity.builder()
                .seatCost(object.getSeatCost())
                .seatCount(object.getSeatCount())
                .tripPlanId(object.getPlanId())
                .luggageBoxQuantity(object.getLuggageBoxQuantity())
                .timezone(object.getDeparture().getZone().getId())
                .departure(object.getDeparture().toLocalDateTime())
                .planStatus(object.getPlanStatus())
                .plannerId(object.getPlannerInformation().userId())
                .tripType(object.getTripType())
                .plannedAt(object.getPlannedAt().toLocalDateTime())
                .tripPrestige(object.getTripPrestige())
                .serviceType(object.getServiceType())
                .luggageBoxQuantity(object.getLuggageBoxQuantity())
                .plannerInformation(plannerInfoMapper.toEntity(object.getPlannerInformation()))
                .draftId(object.getDraftId())
                .publisherId(object.getPublisherId())
                .isBookable(object.getIsBookable())
                .publishCode(object.getPublishedTripCode())

                .fromLocation(stopLocationMapper.toEntity(object.getFromLocation()))
                .toLocation(stopLocationMapper.toEntity(object.getToLocation()))
                .comforts(comfortMapper.toEntitySet(object.getComforts()))

                .build();
    }


}
