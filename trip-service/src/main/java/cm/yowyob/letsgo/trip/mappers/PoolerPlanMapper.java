/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.mappers;



import cm.yowyob.letsgo.trip.domain.model.PaymentSetting;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;
import cm.yowyob.letsgo.trip.infrastructure.entities.*;
import lombok.SneakyThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;



public class PoolerPlanMapper extends Mapper<PoolerPlan, PoolerTripPlanEntity> {


    private final VehicleMapper vehicleMapper = new VehicleMapper();
    private final LegMapper legMapper = new LegMapper();
    private final StopLocationMapper stopLocationMapper = new StopLocationMapper();
    private final ComfortMapper comfortMapper = new ComfortMapper();
    private final PoolerInfoMapper poolerInfoMapper = new PoolerInfoMapper();


    @SneakyThrows
    @Override
    public PoolerPlan toObject(PoolerTripPlanEntity entity) {

        if (entity == null)
            return null;

        return PoolerPlan.builder()
                .plannedAt(entity.getPlannedAt() == null ? null : ZonedDateTime.of(entity.getPlannedAt(), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(entity.getTimezone())))
                .departure(entity.getDeparture() == null ? null : ZonedDateTime.of(entity.getDeparture(), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(entity.getTimezone())))
                .totalCost(entity.getTotalCost())
                .placeCount(entity.getPlaceCount())
                .planId(entity.getTripPlanId())
                .luggageBoxQuantity(entity.getLuggageBoxQuantity())
                .tripPrestige(entity.getTripPrestige())
                .luggageBoxQuantity(entity.getLuggageBoxQuantity())
                .tripType(entity.getTripType())
                .planStatus(entity.getPlanStatus())
                .serviceType(entity.getServiceType())
                .plannerInformation(poolerInfoMapper.toObject(entity.getPlannerInformation()))
                .draftId(entity.getDraftId())
                .publisherId(entity.getPublisherId())
                .isBookable(entity.getIsBookable())
                .transportModes(entity.getTransportMode())
                .vehicleInfo(vehicleMapper.toObject(entity.getVehicleInfo()))
                .itineraries(new Itinerary<>(legMapper.toObjects(entity.getLegs())))
                .publishedTripCode(entity.getPublishCode())
                .sharedPolicy(new SharedPolicy(entity.getPolicyLevel(), entity.getSubscriberType()))

                .paymentSetting(new PaymentSetting(entity.getCurrencyCode(), entity.getPaymentMethods()))


                .canceledAt(entity.getCanceledAt() == null ? null: ZonedDateTime.of(entity.getCanceledAt(), ZoneId.of(entity.getTimezone())))

                .fromLocation(stopLocationMapper.toObject(entity.getFromLocation()))
                .toLocation(stopLocationMapper.toObject(entity.getToLocation()))
                .comforts(comfortMapper.toObjectSet(entity.getComforts()))
                .build();
    }

    @Override
    public PoolerTripPlanEntity toEntity(PoolerPlan object) {

        if (object == null)
            return null;

        return PoolerTripPlanEntity.builder()
                .timezone(object.getPlannedAt() == null ? null : object.getPlannedAt().getZone().getId())
                .plannedAt(object.getPlannedAt() == null ? null : object.getPlannedAt().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .departure(object.getDeparture() == null ? null : object.getDeparture().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .totalCost(object.getTotalCost())
                .placeCount(object.getPlaceCount())
                .tripPlanId(object.getPlanId())
                .policyLevel(object.getSharedPolicy() == null ? null : object.getSharedPolicy().policyLevel())
                .subscriberType(object.getSharedPolicy() == null ? null : object.getSharedPolicy().subscriberType())
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
                .plannerInformation(poolerInfoMapper.toEntity(object.getPlannerInformation()))
                .draftId(object.getDraftId())
                .transportMode(object.getTransportModes())
                .publisherId(object.getPublisherId())
                .publishCode(object.getPublishedTripCode())
                .isBookable(object.getIsBookable())
                .vehicleInfo(vehicleMapper.toEntity(object.getVehicle()))
                .publishCode(object.getPublishedTripCode())

                .paymentMethods(object.getPaymentSetting() == null ? null : object.getPaymentSetting().getMethods())
                .currencyCode(object.getPaymentSetting() == null ? null : object.getPaymentSetting().getCurrencyCode())

                .fromLocation(stopLocationMapper.toEntity(object.getFromLocation()))
                .toLocation(stopLocationMapper.toEntity(object.getToLocation()))
                .comforts(comfortMapper.toEntitySet(object.getComforts()))

                .build();
    }



}
