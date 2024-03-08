/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.TripPresentation;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PlannerPlanDTO {

    private UUID draftId;
    private Boolean isPublished;
    private ZonedDateTime plannedAt;
    private UUID plannedTripId;
    private ZonedDateTime departure;
    private StopLocationDTO fromLocation;
    private StopLocationDTO toLocation;
    private PlannerItineraryDTO itinerary;
    private Set<Comfort> comforts;
    private TripPrestige tripPrestige;
    private TripType tripType;
    private PlannerInformationDTO plannerInformation;
    private PaymentSetting paymentSetting;
    private Set<TransportMode> transportModes;
    private Set<Vehicle> vehicles;
    private Set<Driver> drivers;
    private TripPresentation presentation;

    private ReservationPolicyDTO reservationPolicy;
    private ConfirmationPolicyDTO confirmPolicy;
    private CoursePolicyDTO coursePolicy;
    private PricingPolicyDTO pricingPolicy;
    private CancellationPolicyDTO cancellationPolicy;


    private String publishedTripId;
    private ZonedDateTime publishedAt;
    private String publisherId;
    private SharedPolicy sharedPolicy;
    private Boolean isBookable;
    private Boolean isResolved;
    private ZonedDateTime resolvedAt;
    private Boolean isCanceled;
    private ZonedDateTime canceledAt;
    private Boolean isExecuted;
    private ZonedDateTime executedAt;
    public boolean isResourcesAvailable;
    private Integer reservationsCount;



    public static PlannerPlanDTO mapFrom(PlannerPlan plannerPlan) {

        if (plannerPlan == null)
            return null;

        return PlannerPlanDTO.builder()
                .draftId(plannerPlan.getDraftId())
                .fromLocation(StopLocationDTO.fromStopLocation(plannerPlan.getFromLocation()))
                .toLocation(StopLocationDTO.fromStopLocation(plannerPlan.getToLocation()))
                .departure(plannerPlan.getDeparture())
                .plannedAt(plannerPlan.getPlannedAt())
                .isPublished(plannerPlan.isPublished())
                .transportModes(plannerPlan.getTransportModes())
                .plannedTripId(plannerPlan.getPlanId())
                .comforts(plannerPlan.getComforts())
                .tripType(plannerPlan.getTripType())
                .paymentSetting(plannerPlan.getPaymentSetting())

                .reservationPolicy(ReservationPolicyDTO.fromReservationPolicy(plannerPlan.getReservationPolicy()))
                .confirmPolicy(ConfirmationPolicyDTO.fromConfirmationPolicy(plannerPlan.getConfirmationPolicy()))
                .coursePolicy(CoursePolicyDTO.fromCoursePolicy(plannerPlan.getCoursePolicy()))
                .cancellationPolicy(CancellationPolicyDTO.fromCancellationPolicy(plannerPlan.getCancellationPolicy()))

                .drivers(plannerPlan.getAllDrivers())
                .vehicles(plannerPlan.getAllVehicles())
                .tripPrestige(plannerPlan.getTripPrestige())
                .plannerInformation(PlannerInformationDTO.fromPlannerInformation(plannerPlan.getPlannerInformation()))
                .itinerary(PlannerItineraryDTO.fromPlannerItinerary(plannerPlan.getItinerary(), plannerPlan.getPaymentSetting().getCurrency()))
                .build();
    }

}
