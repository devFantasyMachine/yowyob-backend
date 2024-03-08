/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchPrincipalItineraryException;
import cm.yowyob.letsgo.trip.domain.model.Driver;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.model.plan.UserInformation;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *  abstract trip plan factory
 *
 */
public abstract class AbstractTripPlanFactory<D extends PlanRequest, P extends UserInformation> {

    protected final D request;
    protected final P userInformation;
    protected final Itinerary<Leg> simpleItinerary;



    protected AbstractTripPlanFactory(D request, P userInformation, Itinerary<Leg> simpleItinerary) throws NoSuchPrincipalItineraryException {
        this.request = Objects.requireNonNull(request);
        this.userInformation = Objects.requireNonNull(userInformation);
        this.simpleItinerary =  Optional.ofNullable(simpleItinerary)
                .orElseThrow(NoSuchPrincipalItineraryException::new);
    }


    public void withDrivers(Set<Driver> drivers) {
        throw new UnsupportedOperationException();
    }

    public void withVehicles(Set<Vehicle> vehicles) {
        throw new UnsupportedOperationException();
    }


    /**
     * create trip plan
     *
     * @return {@link AbstractTripPlan }
     * @see AbstractTripPlan<P>
     * @throws NoSuchPrincipalItineraryException cm.yowyob.letsgo.trip.domain.exceptions. no such principal itinerary exception
     */
    public abstract AbstractTripPlan createTripPlan() throws NoSuchPrincipalItineraryException;

}
