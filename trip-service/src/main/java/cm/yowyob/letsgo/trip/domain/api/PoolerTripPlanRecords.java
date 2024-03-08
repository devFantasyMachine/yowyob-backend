package cm.yowyob.letsgo.trip.domain.api;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlanRequest;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;

import java.util.List;
import java.util.Set;
import java.util.UUID;



public interface PoolerTripPlanRecords {


    PoolerPlan getPoolerPlan(UUID tripPlanId) throws PoolerPlanNotFoundException;


    Set<PoolerPlan> getByPoolerId(String poolerId);


    PoolerPlan createPoolerPlan(String poolerId, PoolerPlanRequest request) throws PollerNotFoundException,
            VehicleNotFoundException, NoSuchPrincipalItineraryException, DraftTripNotFoundException;


    PoolerPlan updatePoolerPlan(String poolerId, UUID planId, PoolerPlanRequest request)
            throws VehicleNotFoundException, PoolerPlanNotFoundException,
            UnauthorizedTripAccessException, NoSuchPrincipalItineraryException, IllegalUpdateNonPlannedTripException;


    PoolerPlan updateItinerary(String userId, UUID tripPlanId, List<Stop> intermediatesStops)
            throws PoolerPlanNotFoundException, UnauthorizedTripAccessException;



}
