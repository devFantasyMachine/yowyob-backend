/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.exceptions.IllegalCancelTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalExecuteNonResolvedTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalPublishNonPlannedTripException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalResolveNonPublishedTripException;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.UserInformation;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;



/**
 * Base class for trip plan. organized by one planner for one trip
 */


@Setter
@Getter
public abstract class AbstractTripPlan<L extends Leg, P extends AbstractTripPlan<L, P>> implements Serializable {


    /**
     * The draft id
     */
    private final UUID draftId;

    /**
     * The trip plan id
     */
    private final UUID planId;

    /**
     * The time and date of travel
     */
    private final ZonedDateTime departure;

    /**
     * The time and date of plan
     */
    private final ZonedDateTime plannedAt;

    /**
     * The origin
     */
    protected StopLocation fromLocation;

    /**
     * The destination
     */
    protected StopLocation toLocation;


    /**
     * The trip type : COURSE, COLLECT, DEPOT, TOURISM, CARPOOLING
     */
    private final TripType tripType;

    /**
     * The trip prestige CLASSIC, FAMILY, GOLD, NORMAL, VIP, PREMIUM
     */
    private final TripPrestige tripPrestige;

    /**
     * The service type : DAY, NIGHT, EXPRESS
     */
    private final ServiceType serviceType;

    /**
     * The planner information, can be a pooler's information or a planner's information
     */
    private final UserInformation plannerInformation;

    /**
     * The comforts of trip
     */
    private final Set<Comfort> comforts;

    /**
     * True if plan is a static plan
     */
    private final Boolean isStaticPlan;

    /**
     * All itineraries
     * -- GETTER --
     *  Principal itinerary to use

     */
    @Getter
    private Itinerary<L> itinerary;

    /**
     * date and time of publication
     */
    ZonedDateTime publishedAt;

    /**
     * the id of the user who posted the trip plan
     */
    String publisherId;

    /**
     * post sharing policy
     */
    SharedPolicy sharedPolicy;

    /**
     * indicates whether the publication can be reserved
     */
    Boolean isBookable;

    /**
     * indicates whether the planned trip is totally booked for a planner trip plan,
     * or resolved for a pooler request plan
     */
    PlanStatus planStatus;

    /**
     * date and time of reservation, null if isResolved is false (@link isResolved)
     */
    ZonedDateTime resolvedAt;

    /**
     * date and time of cancellation, null if (@see isCanceled) is false
     */
    ZonedDateTime canceledAt;

    /**
     * date and time of execution, null if isExecuted is set to false
     */
    ZonedDateTime executedAt;


    String publishedTripCode;


    Integer reservationCount;


    public abstract AbstractTripState<L, P> getTripState();


    public AbstractTripPlan(UUID draftId, UUID planId, ZonedDateTime departure, StopLocation fromLocation, StopLocation toLocation, TripType tripType, TripPrestige tripPrestige, ServiceType serviceType, UserInformation plannerInformation, Set<Comfort> comforts, Boolean isStaticPlan, Itinerary<L> itinerary, ZonedDateTime publishedAt, String publisherId, SharedPolicy sharedPolicy, Boolean isBookable, PlanStatus planStatus, ZonedDateTime resolvedAt, ZonedDateTime canceledAt, ZonedDateTime executedAt, String publishedTripCode, Integer reservationCount) {
        this.draftId = draftId;
        this.publishedAt = publishedAt;
        this.publisherId = publisherId;
        this.sharedPolicy = sharedPolicy;
        this.isBookable = isBookable;
        this.planStatus = Objects.requireNonNullElse(planStatus, PlanStatus.PLANNED);
        this.resolvedAt = resolvedAt;
        this.canceledAt = canceledAt;
        this.executedAt = executedAt;

        this.itinerary = Objects.requireNonNull(itinerary, "principalItinerary must not be null");


        this.publishedTripCode = publishedTripCode;

        this.reservationCount = Objects.requireNonNullElse(reservationCount, 0);

        this.departure = Objects.requireNonNull(departure, " departure must not be null")
                .withZoneSameInstant(fromLocation.timezone());

        this.plannedAt = ZonedDateTime.now(this.departure.getZone());

        this.fromLocation = Objects.requireNonNull(fromLocation, "fromLocation must not be null");
        this.toLocation = Objects.requireNonNull(toLocation, "toLocation must not be null");

        this.comforts = Objects.requireNonNullElse(comforts, new HashSet<>());
        this.tripType = tripType == null ? TripType.COLLECT : tripType;
        this.tripPrestige = Objects.requireNonNullElse(tripPrestige, TripPrestige.NORMAL);
        this.plannerInformation = plannerInformation;

        this.isStaticPlan = Objects.requireNonNullElse(isStaticPlan, true);
        this.planId = Objects.requireNonNullElse(planId, UUID.randomUUID());
        this.itinerary.setPlanId(this.planId);


        ServiceType serviceType1 = serviceType;
        if (serviceType1 == null) {

            if (departure.toLocalTime().isAfter(LocalTime.of(17, 0))) {

                serviceType1 = ServiceType.NIGHT;

            }else {

                serviceType1 = ServiceType.DAY;
            }
        }

        this.serviceType = serviceType1;

    }


    public AbstractTripPlan(UUID draftId, UUID planId, ZonedDateTime departure, ZonedDateTime plannedAt, StopLocation fromLocation, StopLocation toLocation, TripType tripType, TripPrestige tripPrestige, ServiceType serviceType, UserInformation plannerInformation, Set<Comfort> comforts, Boolean isStaticPlan, Itinerary<L> itinerary, ZonedDateTime publishedAt, String publisherId, SharedPolicy sharedPolicy, Boolean isBookable, PlanStatus planStatus, ZonedDateTime resolvedAt, ZonedDateTime canceledAt, ZonedDateTime executedAt, String publishedTripCode, Integer reservationCount) {
        this.draftId = draftId;
        this.planId = planId;
        this.departure = departure;
        this.plannedAt = plannedAt;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.tripType = tripType;
        this.tripPrestige = tripPrestige;
        this.serviceType = serviceType;
        this.plannerInformation = plannerInformation;
        this.comforts = comforts;
        this.isStaticPlan = isStaticPlan;
        this.itinerary = Objects.requireNonNull(itinerary);
        this.publishedAt = publishedAt;
        this.publisherId = publisherId;
        this.sharedPolicy = sharedPolicy;
        this.isBookable = isBookable;
        this.planStatus = Objects.requireNonNull(planStatus);
        this.resolvedAt = resolvedAt;
        this.canceledAt = canceledAt;
        this.executedAt = executedAt;
        this.publishedTripCode = publishedTripCode;
        this.reservationCount = Objects.requireNonNullElse(reservationCount, 0);
        this.itinerary.setPlanId(planId);
    }


    public AbstractTripPlan(UUID draftId, ZonedDateTime departure, StopLocation fromLocation, StopLocation toLocation, TripType tripType, TripPrestige tripPrestige, ServiceType serviceType, PoolerInformation plannerInformation, Set<Comfort> comforts, boolean isStaticPlan, Itinerary<L> itinerary, Integer reservationCount) {

        this(draftId, null, departure, fromLocation, toLocation, tripType, tripPrestige, serviceType, plannerInformation, comforts, isStaticPlan, itinerary, null, null, null, false, PlanStatus.PLANNED, null, null, null, null, reservationCount);
    }


    public final void publish(String publishTripId) throws IllegalPublishNonPlannedTripException {
        getTripState().publish(publishTripId);
    }

    public final void resolve() throws IllegalResolveNonPublishedTripException {
        getTripState().resolve();
    }

    public final void execute() throws IllegalExecuteNonResolvedTripException {
        getTripState().execute();
    }

    public final void cancel() throws IllegalCancelTripException {
        getTripState().cancel();
    }


    public void replaceItinerary(final Itinerary<L> itinerary) {

        if (itinerary == null || itinerary.isEmpty())
            throw new IllegalArgumentException();

        this.itinerary = itinerary;
        this.fromLocation = itinerary.getFromLocation();
        this.toLocation = itinerary.getToLocation();
        this.itinerary.setPlanId(planId);
    }


    public abstract Set<TransportMode> getTransportModes();


    public boolean canUpdateItinerary() {
        return getTripState().canUpdateItinerary();
    }


    public boolean canUpdate() {
        return getTripState().canUpdate();
    }

    public boolean isNonAuthorized(String publisherId) {
        return !this.plannerInformation.getUserId().equals(publisherId);
    }


    public boolean isPublished() {
        return planStatus == PlanStatus.PUBLISHED;
    }

}

