/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchPrincipalItineraryException;
import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.UserInformation;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;




/**
 * <h1>The {@link PoolerPlan} </h1>
 * Cette classe hérite de {@link AbstractTripPlan}
 *
 *
 * <p>Le pooler plan est réservé par un chauffeur, une agence de location, ou un planner uniquement
 * (le cas du covoiturage, c'est un cas planner)
 * <p></p>
 *
 * <h2>Scénarios possibles :</h2>
 *
 * <p>
 * - je veux me déplacer de A ({@link  StopLocation}) à B ({@link  StopLocation}) maintenant ({@link  ServiceType})
 * pour un depot ({@link  TripType}), je désire 4 places et 30Kg de bagages pour un montant total de 5000 FCFA.
 * J'aimerais un déplacement à moto ({@link  TransportMode}), en co-voiturage ou en taxi et le Wifi disponible.
 * Je paye par OM ({@link  PaymentSetting}). Je suis un client VIP({@link  TripPrestige}).
 *
 * <p>
 * - je veux me déplacer de A à B le 23 janvier à 12h pour une course (course) qui durera 5h on passera par C et D
 * et on fera une pause de 50min à E, je veux 4 places et 30Kg de bagages pour
 * un montant total de 50.000 FCFA. J'aimerais un déplacement à moto ou en taxi et le Wifi disponible.
 *  Je paye par OM
 *
 * <p>
 * - je veux me déplacer de A à B le 23 janvier à 12h, je désire 2 places, 30Kg de bagages pour
 * un montant total de 500 FCFA et un niveau de confort classic. J'aimerais un déplacement à moto ou
 * en taxi ou co-voiturage (collect). Je paye CASH.
 *
 * <p>
 * - je veux me déplacer de A à {@code B} le 23 janvier à 12h pour une course (course) qui durera 5h on passera par C et D
 * et on fera une pause de 50min à E. Nous sommes 4, j'ai un vehicle de marque toyota et
 * je désire un chauffeur compétent pour un montant total de 50.000 FCFA.
 * Je paye par OM
 *
 * <p>
 * - je veux me déplacer seul de A à B le 23 janvier à 12h. pour une course (course) qui durera 5h
 * on passera par C et D et on fera une pause de 50min à E.
 * Nous sommes 4. [J'ai un vehicle de marque toyota].
 */
@Getter
public final class PoolerPlan extends AbstractTripPlan<Leg, PoolerPlan> {

    private final Integer placeCount;
    private final Float luggageBoxQuantity;
    private final Float totalCost;
    private final Vehicle vehicle;
    private final Set<TransportMode> transportModes;
    private final PaymentSetting paymentSetting;

    AbstractPoolerPlanState planState;


    @Override
    public PoolerInformation getPlannerInformation() {
        return (PoolerInformation) super.getPlannerInformation();
    }



    public PoolerPlan(UUID draftId, ZonedDateTime departure, StopLocation fromLocation, StopLocation toLocation, Set<Comfort> comforts, TripType tripType, TripPrestige tripPrestige, PoolerInformation plannerInformation, Itinerary<Leg> principalItinerary, PaymentSetting paymentSetting, Float luggageBoxQuantity, Float totalCost, Set<TransportMode> transportModes, Integer placeCount, Vehicle vehicle, ServiceType serviceType) throws NoSuchPrincipalItineraryException {
        super(draftId, departure, fromLocation, toLocation, tripType, tripPrestige, serviceType, plannerInformation, comforts, true, principalItinerary, 0);
        this.paymentSetting = paymentSetting;
        this.luggageBoxQuantity = luggageBoxQuantity;
        this.totalCost = totalCost;
        this.transportModes = vehicle == null ? transportModes : Set.of(vehicle.getTransportMode());
        this.placeCount = placeCount;
        this.vehicle = vehicle;

        this.planState = new PoolerPlanStatePlanned(this);
    }



    public PoolerPlan(UUID draftId, UUID planId, ZonedDateTime departure, ZonedDateTime plannedAt, StopLocation fromLocation, StopLocation toLocation, TripType tripType, TripPrestige tripPrestige, ServiceType serviceType, UserInformation plannerInformation, Set<Comfort> comforts, Itinerary<Leg> itineraries, ZonedDateTime publishedAt, String publisherId, SharedPolicy sharedPolicy, Boolean isBookable, PlanStatus planStatus, ZonedDateTime resolvedAt, ZonedDateTime canceledAt, ZonedDateTime executedAt, String publishedTripCode, PaymentSetting paymentSetting, Integer placeCount, Float luggageBoxQuantity, Float totalCost, Set<TransportMode> transportModes, Vehicle vehicle) {
        super(draftId, planId, departure, plannedAt, fromLocation, toLocation, tripType, tripPrestige, serviceType, plannerInformation, comforts, true, itineraries, publishedAt, publisherId, sharedPolicy, isBookable, planStatus, resolvedAt, canceledAt, executedAt, publishedTripCode, 0);
        this.paymentSetting = paymentSetting;
        this.placeCount = placeCount;
        this.luggageBoxQuantity = luggageBoxQuantity;
        this.totalCost = totalCost;
        this.transportModes = transportModes;
        this.vehicle = vehicle;

        if (planStatus == PlanStatus.PLANNED) {
            this.planState = new PoolerPlanStatePlanned(this);
        }
        else if (planStatus == PlanStatus.PUBLISHED) {
            this.planState = new PoolerPlanStatePublished(this);
        }
        else if (planStatus == PlanStatus.CANCELED) {
            this.planState = new PoolerPlanStateCanceled(this);
        }
        else if (planStatus == PlanStatus.RESOLVED) {
            this.planState = new PoolerPlanStateResolved(this);
        }
        else if (planStatus == PlanStatus.EXECUTED) {
            this.planState = new PoolerPlanStateExecuted(this);
        }


    }


    @Override
    public AbstractPoolerPlanState getTripState() {
        return planState;
    }


    public static PoolerPlanBuilder builder() {
        return new PoolerPlanBuilder();
    }


    public static class PoolerPlanBuilder {
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
        private Itinerary<Leg> itineraries;
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
        private Integer placeCount;
        private Float luggageBoxQuantity;
        private Float totalCost;
        private Set<TransportMode> transportMode;
        private Vehicle vehicle;

        PoolerPlanBuilder() {
        }

        public PoolerPlanBuilder draftId(UUID draftId) {
            this.draftId = draftId;
            return this;
        }

        public PoolerPlanBuilder planId(UUID planId) {
            this.planId = planId;
            return this;
        }

        public PoolerPlanBuilder departure(ZonedDateTime departure) {
            this.departure = departure;
            return this;
        }

        public PoolerPlanBuilder paymentSetting(PaymentSetting paymentSetting) {
            this.paymentSetting = paymentSetting;
            return this;
        }

        public PoolerPlanBuilder placeCount(Integer placeCount) {
            this.placeCount = placeCount;
            return this;
        }

        public PoolerPlanBuilder luggageBoxQuantity(Float luggageBoxQuantity) {
            this.luggageBoxQuantity = luggageBoxQuantity;
            return this;
        }

        public PoolerPlanBuilder transportModes(Set<TransportMode> transportMode) {
            this.transportMode = transportMode;
            return this;
        }

        public PoolerPlanBuilder totalCost(Float totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public PoolerPlanBuilder plannedAt(ZonedDateTime plannedAt) {
            this.plannedAt = plannedAt;
            return this;
        }

        public PoolerPlanBuilder fromLocation(StopLocation fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public PoolerPlanBuilder toLocation(StopLocation toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public PoolerPlanBuilder tripType(TripType tripType) {
            this.tripType = tripType;
            return this;
        }

        public PoolerPlanBuilder tripPrestige(TripPrestige tripPrestige) {
            this.tripPrestige = tripPrestige;
            return this;
        }

        public PoolerPlanBuilder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public PoolerPlanBuilder plannerInformation(UserInformation plannerInformation) {
            this.plannerInformation = plannerInformation;
            return this;
        }

        public PoolerPlanBuilder comforts(Set<Comfort> comforts) {
            this.comforts = comforts;
            return this;
        }



        public PoolerPlanBuilder vehicleInfo(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public PoolerPlanBuilder itineraries(Itinerary<Leg> itineraries) {
            this.itineraries = itineraries;
            return this;
        }

        public PoolerPlanBuilder publishedAt(ZonedDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public PoolerPlanBuilder publisherId(String publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public PoolerPlanBuilder sharedPolicy(SharedPolicy sharedPolicy) {
            this.sharedPolicy = sharedPolicy;
            return this;
        }

        public PoolerPlanBuilder isBookable(Boolean isBookable) {
            this.isBookable = isBookable;
            return this;
        }

        public PoolerPlanBuilder planStatus(PlanStatus planStatus) {
            this.planStatus = planStatus;
            return this;
        }

        public PoolerPlanBuilder resolvedAt(ZonedDateTime resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public PoolerPlanBuilder canceledAt(ZonedDateTime canceledAt) {
            this.canceledAt = canceledAt;
            return this;
        }

        public PoolerPlanBuilder executedAt(ZonedDateTime executedAt) {
            this.executedAt = executedAt;
            return this;
        }

        public PoolerPlanBuilder publishedTripCode(String publishedTripCode) {
            this.publishedTripCode = publishedTripCode;
            return this;
        }

        public PoolerPlan build() throws NoSuchPrincipalItineraryException {


            return new PoolerPlan(
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
                    paymentSetting,
                    placeCount,
                    luggageBoxQuantity,
                    totalCost,
                    transportMode,
                    vehicle);

        }



        public String toString() {
            return "AbstractTripPlan.AbstractTripPlanBuilder(draftId=" + this.draftId + ", planId=" + this.planId + ", departure=" + this.departure + ", plannedAt=" + this.plannedAt + ", fromLocation=" + this.fromLocation + ", toLocation=" + this.toLocation + ", tripType=" + this.tripType + ", tripPrestige=" + this.tripPrestige + ", serviceType=" + this.serviceType + ", plannerInformation=" + this.plannerInformation + ", comforts=" + this.comforts + ", itineraries=" + this.itineraries + ", publishedAt=" + this.publishedAt + ", publisherId=" + this.publisherId + ", sharedPolicy=" + this.sharedPolicy + ", isBookable=" + this.isBookable + ", planStatus=" + this.planStatus + ", resolvedAt=" + this.resolvedAt + ", canceledAt=" + this.canceledAt + ", executedAt=" + this.executedAt + ", publishedTripCode=" + this.publishedTripCode + ")";
        }



    }



}

