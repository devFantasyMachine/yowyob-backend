/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.DefaultTripPlanContext;
import cm.yowyob.letsgo.trip.domain.model.DefaultTripPlanIdentifierGenerator;
import cm.yowyob.letsgo.trip.domain.model.LetsgoIdentifierGenerator;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.model.publish.*;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;
import cm.yowyob.letsgo.trip.domain.model.schedule.LetsgoScheduler;
import cm.yowyob.letsgo.trip.domain.ports.ItineraryProductRecord;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




@AllArgsConstructor
public class PublishManager {

    PlannerPlanManager plannerPlanManager;
    PoolerPlanManager poolerPlanManager;
    ItineraryProductRecord itineraryProductRecord;

    //LetsgoScheduler pricingPolicyScheduler;

    final ItineraryProductHelper itineraryProductHelper = new ItineraryProductHelper();




    private static final PlannerPlanPublisherChecker publisherChecker =
            new PlannerPlanPublisherChecker();

    private static final PoolerPlanPublisherChecker poolerPublisherChecker =
            new PoolerPlanPublisherChecker();


    private static final LetsgoIdentifierGenerator<DefaultTripPlanContext> identifierGenerator
            = new DefaultTripPlanIdentifierGenerator();





    public PlannerPlan publishPlannerTripPlan(UUID tripPlanId)
            throws PlannedTripNotFoundException, UnPublishableTripException, NoSuchPrincipalItineraryException, PublishedTripNotFoundException, IllegalPublishNonPlannedTripException {

        PlannerPlan plannerPlan = plannerPlanManager.getPlannerPlan(tripPlanId);

        CheckerResponse checkerResponse =
                publisherChecker.doCheck(plannerPlan, SharedPolicy.AGENCY_POLICY);

        if (checkerResponse.checkerProblems().contains(CheckerProblem.ALREADY_PUBLISHED))
            return plannerPlan;

        if (checkerResponse.isNonPublishable())
            throw new UnPublishableTripException(checkerResponse.checkerProblems());

        String publishTripId = identifierGenerator.generate(new DefaultTripPlanContext(
                plannerPlan.getTripType(),
                plannerPlan.getServiceType(),
                plannerPlan.getTripPrestige(),
                plannerPlan.getDeparture(),
                plannerPlan.getPlannerInformation().code()));

        plannerPlan.setSharedPolicy(SharedPolicy.AGENCY_POLICY);
        plannerPlan.publish(publishTripId);

        // create Itinerary Product

        List<ItineraryProduct> itineraryProducts = new ArrayList<>();

        for (TripResource plannerPlanResource : plannerPlan.getResources()) {

            itineraryProducts.addAll(itineraryProductHelper.generate(plannerPlan.getPlanId(),
                    plannerPlanResource,
                    plannerPlan.getItinerary().getStops()));
        }

        itineraryProductRecord.saveAll(itineraryProducts);

        return plannerPlanManager.save(plannerPlan);
    }



    public PoolerPlan publishPoolerTripPlan(UUID tripPlanId, SharedPolicy sharedPolicy)
            throws UnPublishableTripException, IllegalPublishNonPlannedTripException, PoolerPlanNotFoundException {

        PoolerPlan poolerPlan = poolerPlanManager.getPoolerPlan(tripPlanId);

        CheckerResponse checkerResponse =
                poolerPublisherChecker.doCheck(poolerPlan, sharedPolicy);

        if (checkerResponse.checkerProblems().contains(CheckerProblem.ALREADY_PUBLISHED))
            return poolerPlan;

        if (checkerResponse.isNonPublishable())
            throw new UnPublishableTripException(checkerResponse.checkerProblems());


        String publishTripId = identifierGenerator.generate(new DefaultTripPlanContext(
                poolerPlan.getTripType(),
                poolerPlan.getServiceType(),
                poolerPlan.getTripPrestige(),
                poolerPlan.getDeparture(), null));

        poolerPlan.setSharedPolicy(sharedPolicy);
        poolerPlan.publish(publishTripId);

        return poolerPlanManager.save(poolerPlan);
    }



    public PlannerPlan cancelPublishedTrip(@NonNull UUID publishedTripId,@NonNull String publisherId)
            throws UnauthorizedCancelTripException, PlannedTripNotFoundException, IllegalCancelTripException {

        PlannerPlan plannerPlan = plannerPlanManager.getPlannerPlan(publishedTripId);

        if (plannerPlan.isNonAuthorized(publisherId))
            throw new UnauthorizedCancelTripException();

        plannerPlan.cancel();

        return plannerPlanManager.save(plannerPlan);
    }





}
