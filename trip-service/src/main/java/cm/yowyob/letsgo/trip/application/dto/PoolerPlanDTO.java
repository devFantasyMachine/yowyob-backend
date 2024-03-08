/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlanStatus;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;

import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;



@Data
@Builder
@AllArgsConstructor
public class PoolerPlanDTO extends RepresentationModel<PoolerPlanDTO>  {

    private UUID draftId;
    private UUID planId;

    private ZonedDateTime plannedAt;
    private String publishCode;
    private ZonedDateTime departure;
    private StopLocationDTO fromLocation;
    private StopLocationDTO toLocation;
    private SimpleItineraryDTO itinerary;
    private Set<Comfort> comforts;
    private TripPrestige tripPrestige;
    private TripType tripType;
    private ServiceType serviceType;
    private PlanStatus status;

    private ZonedDateTime publishedAt;
    private String publisherId;
    private Boolean isBookable;
    private ZonedDateTime resolvedAt;
    private ZonedDateTime canceledAt;
    private ZonedDateTime executedAt;
    private Integer reservationCount;

    private PoolerInformationDTO plannerInformation;
    private PaymentSettingDTO paymentSetting;
    private Set<TransportMode> transportModes;
    private Vehicle vehicle;

    private Integer passengerCount;
    private Float luggageBoxQuantity;
    private Float totalCost;
    private SharedPolicy sharedPolicy;


    public static PoolerPlanDTO mapFrom(PoolerPlan poolerPlan) {

        if (poolerPlan == null)
            return null;

        return PoolerPlanDTO.builder()
                .draftId(poolerPlan.getDraftId())
                .publishCode(poolerPlan.getPublishedTripCode())
                .fromLocation(StopLocationDTO.fromStopLocation(poolerPlan.getFromLocation()))
                .toLocation(StopLocationDTO.fromStopLocation(poolerPlan.getToLocation()))
                .departure(poolerPlan.getDeparture())
                .plannedAt(poolerPlan.getPlannedAt())
                .reservationCount(poolerPlan.getReservationCount())
                .publishedAt(poolerPlan.getPublishedAt())
                .resolvedAt(poolerPlan.getResolvedAt())
                .executedAt(poolerPlan.getExecutedAt())
                .canceledAt(poolerPlan.getCanceledAt())
                .transportModes(poolerPlan.getTransportModes())
                .planId(poolerPlan.getPlanId())
                .comforts(poolerPlan.getComforts())
                .tripType(poolerPlan.getTripType())
                .serviceType(poolerPlan.getServiceType())
                .status(poolerPlan.getPlanStatus())
                .vehicle(poolerPlan.getVehicle())
                .tripPrestige(poolerPlan.getTripPrestige())
                .passengerCount(poolerPlan.getPlaceCount())
                .luggageBoxQuantity(poolerPlan.getLuggageBoxQuantity())
                .paymentSetting(PaymentSettingDTO.fromPayment(poolerPlan.getPaymentSetting()))
                .itinerary(SimpleItineraryDTO.fromSimpleItinerary(poolerPlan.getItinerary()))
                .totalCost(poolerPlan.getTotalCost())
                .sharedPolicy(poolerPlan.getSharedPolicy())
                .plannerInformation(PoolerInformationDTO.fromPoolerInformation(poolerPlan.getPlannerInformation()))
                .build();
    }




}


