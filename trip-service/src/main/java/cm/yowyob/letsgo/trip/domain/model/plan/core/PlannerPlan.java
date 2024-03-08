/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchPrincipalItineraryException;
import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.TripPresentation;
import cm.yowyob.letsgo.trip.domain.model.plan.UserInformation;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.policies.CancellationPolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.ConfirmationPolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.CoursePolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.ReservationPolicy;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;
import cm.yowyob.letsgo.trip.domain.model.resources.DiscountTicket;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;

import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;




/**
 * The trip plan by agency
 */
@Setter
@Getter
public final class PlannerPlan extends AbstractTripPlan<PlannerLeg, PlannerPlan> {


    private TripPresentation presentation;
    private PaymentSetting paymentSetting;

    private Float seatCost;
    private Integer seatCount;
    private Float luggageCost;
    private Float luggageBoxQuantity;

    private Integer discountTicketCount;
    private Set<DiscountTicket> discountTickets;

    private CoursePolicy coursePolicy;
    private ReservationPolicy reservationPolicy;
    private ConfirmationPolicy confirmationPolicy;
    private CancellationPolicy cancellationPolicy;

    private final List<TripResource> resources;


    AbstractPlannerPlanState planState;


    public PlannerPlan(UUID draftId, UUID planId, ZonedDateTime departure, ZonedDateTime plannedAt, StopLocation fromLocation, StopLocation toLocation, TripType tripType, TripPrestige tripPrestige, ServiceType serviceType, UserInformation plannerInformation, Set<Comfort> comforts, Boolean isStaticPlan, Itinerary<PlannerLeg> itineraries, ZonedDateTime publishedAt, String publisherId, SharedPolicy sharedPolicy, Boolean isBookable, PlanStatus planStatus, ZonedDateTime resolvedAt, ZonedDateTime canceledAt, ZonedDateTime executedAt, String publishedTripCode, TripPresentation presentation, PaymentSetting paymentSetting, Float seatCost, Integer seatCount, Float luggageCost, Float luggageBoxQuantity, Integer discountTicketCount, CoursePolicy coursePolicy, ReservationPolicy reservationPolicy, ConfirmationPolicy confirmationPolicy, CancellationPolicy cancellationPolicy, List<TripResource> resources, Set<DiscountTicket> discountTickets) {
        super(draftId, planId, departure, plannedAt, fromLocation, toLocation, tripType, tripPrestige, serviceType, plannerInformation, comforts, isStaticPlan, itineraries, publishedAt, publisherId, sharedPolicy, isBookable, planStatus, resolvedAt, canceledAt, executedAt, publishedTripCode, 0);
        this.presentation = presentation;
        this.paymentSetting = paymentSetting;

        this.seatCost = seatCost;
        this.seatCount = seatCount;
        this.luggageCost = luggageCost;
        this.luggageBoxQuantity = luggageBoxQuantity;
        this.discountTicketCount = discountTicketCount;

        this.coursePolicy = Objects.requireNonNullElse(coursePolicy, CoursePolicy.DEFAULT);
        this.reservationPolicy = Objects.requireNonNullElse(reservationPolicy, ReservationPolicy.DEFAULT);
        this.confirmationPolicy = Objects.requireNonNullElse(confirmationPolicy, ConfirmationPolicy.DEFAULT);

        this.cancellationPolicy = cancellationPolicy;
        this.resources = resources;
        this.discountTickets = discountTickets;

        planState = new PlannerPlanStatePlanned(this);

        if (planStatus == PlanStatus.PLANNED) {
            this.planState = new PlannerPlanStatePlanned(this);
        }
        else if (planStatus == PlanStatus.PUBLISHED) {
            this.planState = new PlannerPlanStatePublished(this);
        }
        else if (planStatus == PlanStatus.CANCELED) {
            this.planState = new PlannerPlanStateCancelled(this);
        }
        else if (planStatus == PlanStatus.RESOLVED) {
            this.planState = new PlannerPlanStateResolved(this);
        }
        else if (planStatus == PlanStatus.EXECUTED) {
            this.planState = new PlannerPlanStateExecuted(this);
        }


    }




    @Override
    public PlannerInformation getPlannerInformation() {
        return (PlannerInformation) super.getPlannerInformation();
    }



    public @NonNull Map<Integer, String> getAllDriverIdByLeg() {

        Map<Integer, String> allDriverIdByLeg = new HashMap<>();

        for (PlannerLeg leg : getItinerary().getLegs()) {

            if (leg.getDriver() != null && leg.getDriver().getDriverId() != null)
                allDriverIdByLeg.put(leg.getIndex(), leg.getDriver().getDriverId());
        }

        return allDriverIdByLeg;
    }



    public Set<Driver> getAllDrivers() {

        return getItinerary().getLegs()
                .stream()
                .map(PlannerLeg::getDriver)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public @NonNull Map<Integer, String> getAllVehicleIdByLeg() {

        Map<Integer, String> allVehicleIdByLeg = new HashMap<>();

        for (PlannerLeg leg : getItinerary().getLegs()) {

            if (leg.getVehicle() != null && leg.getVehicle().getVehicleId() != null)
                allVehicleIdByLeg.put(leg.getIndex(), leg.getVehicle().getVehicleId());
        }

        return allVehicleIdByLeg;
    }

    public Set<Vehicle> getAllVehicles() {

        return getItinerary().getLegs()
                .stream()
                .map(PlannerLeg::getVehicle)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    @Override
    public AbstractTripState<PlannerLeg, PlannerPlan> getTripState() {
        return planState;
    }


    @Override
    public Set<TransportMode> getTransportModes() {

        return getItinerary().getLegs()
                .stream()
                .map(PlannerLeg::getTransportMode)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


    public static PlannerPlanBuilder builder() {
        return new PlannerPlanBuilder();
    }

    public static class PlannerPlanBuilder {
        private UUID draftId;
        private UUID planId;
        private ZonedDateTime departure;
        private ZonedDateTime plannedAt;
        private StopLocation fromLocation;
        private StopLocation toLocation;
        private TripType tripType;
        private TripPrestige tripPrestige;
        private ServiceType serviceType;
        private UserInformation plannerInformation;
        private Set<Comfort> comforts;
        private Itinerary<PlannerLeg> itineraries;
        private ZonedDateTime publishedAt;
        private String publisherId;
        private SharedPolicy sharedPolicy;
        private Boolean isBookable;
        private PlanStatus planStatus;
        private ZonedDateTime resolvedAt;
        private ZonedDateTime canceledAt;
        private ZonedDateTime executedAt;
        private String publishedTripCode;
        private PaymentSetting paymentSetting;
        private Float luggageBoxQuantity;
        private TripPresentation presentation;
        private Float seatCost;
        private Integer seatCount;

        private Boolean isStaticPlan = true;
        private Float luggageCost;
        private Integer discountTicketCount;
        private Set<DiscountTicket> discountTickets;

        private CoursePolicy coursePolicy;
        private ReservationPolicy reservationPolicy;
        private ConfirmationPolicy confirmationPolicy;
        private CancellationPolicy cancellationPolicy;

        private List<TripResource> resources;


        PlannerPlanBuilder() {
        }

        public PlannerPlanBuilder draftId(UUID draftId) {
            this.draftId = draftId;
            return this;
        }

        public PlannerPlanBuilder planId(UUID planId) {
            this.planId = planId;
            return this;
        }

        public PlannerPlanBuilder resources(List<TripResource> resources) {
            this.resources = resources;
            return this;
        }

        public PlannerPlanBuilder departure(ZonedDateTime departure) {
            this.departure = departure;
            return this;
        }

        public PlannerPlanBuilder cancellationPolicy(CancellationPolicy cancellationPolicy) {
            this.cancellationPolicy = cancellationPolicy;
            return this;
        }

        public PlannerPlanBuilder confirmationPolicy(ConfirmationPolicy confirmationPolicy) {
            this.confirmationPolicy = confirmationPolicy;
            return this;
        }

        public PlannerPlanBuilder reservationPolicy(ReservationPolicy reservationPolicy) {
            this.reservationPolicy = reservationPolicy;
            return this;
        }

        public PlannerPlanBuilder coursePolicy(CoursePolicy coursePolicy) {
            this.coursePolicy = coursePolicy;
            return this;
        }

        public PlannerPlanBuilder presentation(TripPresentation presentation) {
            this.presentation = presentation;
            return this;
        }

        public PlannerPlanBuilder luggageCost(Float luggageCost) {
            this.luggageCost = luggageCost;
            return this;
        }

        public PlannerPlanBuilder discountTicketCount(Integer discountTicketCount) {
            this.discountTicketCount = discountTicketCount;
            return this;
        }

        public PlannerPlanBuilder discountTickets(Set<DiscountTicket> discountTickets) {
            this.discountTickets = discountTickets;
            return this;
        }


        public PlannerPlanBuilder paymentSetting(PaymentSetting paymentSetting) {
            this.paymentSetting = paymentSetting;
            return this;
        }

        public PlannerPlanBuilder luggageBoxQuantity(Float luggageBoxQuantity) {
            this.luggageBoxQuantity = luggageBoxQuantity;
            return this;
        }


        public PlannerPlanBuilder seatCost(Float seatCost) {
            this.seatCost = seatCost;
            return this;
        }

        public PlannerPlanBuilder plannedAt(ZonedDateTime plannedAt) {
            this.plannedAt = plannedAt;
            return this;
        }

        public PlannerPlanBuilder fromLocation(StopLocation fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public PlannerPlanBuilder toLocation(StopLocation toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public PlannerPlanBuilder tripType(TripType tripType) {
            this.tripType = tripType;
            return this;
        }

        public PlannerPlanBuilder tripPrestige(TripPrestige tripPrestige) {
            this.tripPrestige = tripPrestige;
            return this;
        }

        public PlannerPlanBuilder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public PlannerPlanBuilder plannerInformation(UserInformation plannerInformation) {
            this.plannerInformation = plannerInformation;
            return this;
        }

        public PlannerPlanBuilder comforts(Set<Comfort> comforts) {
            this.comforts = comforts;
            return this;
        }


        public PlannerPlanBuilder itineraries(Itinerary<PlannerLeg> itineraries) {
            this.itineraries = itineraries;
            return this;
        }

        public PlannerPlanBuilder publishedAt(ZonedDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public PlannerPlanBuilder publisherId(String publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public PlannerPlanBuilder sharedPolicy(SharedPolicy sharedPolicy) {
            this.sharedPolicy = sharedPolicy;
            return this;
        }

        public PlannerPlanBuilder isBookable(Boolean isBookable) {
            this.isBookable = isBookable;
            return this;
        }

        public PlannerPlanBuilder planStatus(PlanStatus planStatus) {
            this.planStatus = planStatus;
            return this;
        }

        public PlannerPlanBuilder resolvedAt(ZonedDateTime resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public PlannerPlanBuilder canceledAt(ZonedDateTime canceledAt) {
            this.canceledAt = canceledAt;
            return this;
        }

        public PlannerPlanBuilder executedAt(ZonedDateTime executedAt) {
            this.executedAt = executedAt;
            return this;
        }

        public PlannerPlanBuilder publishedTripCode(String publishedTripCode) {
            this.publishedTripCode = publishedTripCode;
            return this;
        }

        public PlannerPlan build() throws NoSuchPrincipalItineraryException {


            return new PlannerPlan(
                    draftId,
                    planId,
                    departure,
                    plannedAt,
                    fromLocation,
                    toLocation,
                    tripType,
                    tripPrestige,
                    serviceType,
                    plannerInformation,
                    comforts,
                    isStaticPlan,
                    itineraries,
                    publishedAt,
                    publisherId,
                    sharedPolicy,
                    isBookable,
                    planStatus,
                    resolvedAt,
                    canceledAt,
                    executedAt,
                    publishedTripCode,
                    presentation,
                    paymentSetting,
                    seatCost,
                    seatCount,
                    luggageBoxQuantity,
                    luggageBoxQuantity,
                    discountTicketCount,
                    coursePolicy,
                    reservationPolicy,
                    confirmationPolicy,
                    cancellationPolicy,
                    resources,
                    discountTickets);

        }


        public String toString() {
            return "AbstractTripPlan.AbstractTripPlanBuilder(draftId=" + this.draftId + ", planId=" + this.planId + ", departure=" + this.departure + ", plannedAt=" + this.plannedAt + ", fromLocation=" + this.fromLocation + ", toLocation=" + this.toLocation + ", tripType=" + this.tripType + ", tripPrestige=" + this.tripPrestige + ", serviceType=" + this.serviceType + ", plannerInformation=" + this.plannerInformation + ", comforts=" + this.comforts + ", itineraries=" + this.itineraries + ", publishedAt=" + this.publishedAt + ", publisherId=" + this.publisherId + ", sharedPolicy=" + this.sharedPolicy + ", isBookable=" + this.isBookable + ", planStatus=" + this.planStatus + ", resolvedAt=" + this.resolvedAt + ", canceledAt=" + this.canceledAt + ", executedAt=" + this.executedAt + ", publishedTripCode=" + this.publishedTripCode + ")";
        }


        public PlannerPlanBuilder seatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }
    }


}
