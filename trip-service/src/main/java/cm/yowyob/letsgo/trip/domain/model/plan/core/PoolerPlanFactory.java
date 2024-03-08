/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchPrincipalItineraryException;
import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import lombok.Setter;



@Setter
public class PoolerPlanFactory extends AbstractTripPlanFactory<PoolerPlanRequest, PoolerInformation> {

    private Vehicle vehicle;

    public PoolerPlanFactory(PoolerPlanRequest request,
                             PoolerInformation userInformation,
                             Itinerary<Leg> simpleItinerary) throws NoSuchPrincipalItineraryException {
        super(request, userInformation, simpleItinerary);
    }


    public void withVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    @Override
    public PoolerPlan createTripPlan() throws NoSuchPrincipalItineraryException {

        DraftTrip draftTrip = request.getDraftTrip();

        return new PoolerPlan(
                request.getDraftId(),
                request.getDeparture(),
                draftTrip.getFromLocation(),
                draftTrip.getToLocation(),
                draftTrip.getComforts(),
                draftTrip.getTripType(),
                draftTrip.getTripPrestige(),
                userInformation,
                simpleItinerary,
                request.getPaymentSetting(),
                request.getLuggageBoxQuantity(),
                request.getTotalCost(),
                request.getTransportModes(),
                request.getSeatCount(),
                vehicle,
                draftTrip.getServiceType());
    }


}
