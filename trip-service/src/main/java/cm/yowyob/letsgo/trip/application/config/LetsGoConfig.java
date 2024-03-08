/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.config;


import cm.yowyob.letsgo.trip.domain.events.PoolerPlanEventPublisher;
import cm.yowyob.letsgo.trip.domain.managers.*;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItinerarySolver;
import cm.yowyob.letsgo.trip.domain.ports.*;
import cm.yowyob.letsgo.trip.infrastructure.services.InMemoryPlannerResourceService;
import cm.yowyob.letsgo.trip.infrastructure.services.InMemoryBusinessService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Collections;

@Configuration
public class LetsGoConfig {

    public static final String SYSTEM_TIMEZONE_ID = "Africa/Douala";


    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(SYSTEM_TIMEZONE_ID));
    }


    @Bean
    BusinessService userService() {

        return new InMemoryBusinessService();
    }

    @Bean
    PlannerResourceService plannerResourceService() {

        return new InMemoryPlannerResourceService();
    }

    @Bean
    DraftTripManager draftTripManager(DraftTripService draftTripService) {

        return new DraftTripManager(draftTripService);
    }


    @Bean
    PoolerPlanManager poolerPlanManager(PlannedTripService plannedTripService,
                                        PlannerResourceService plannerResourceService,
                                        BusinessService businessService,
                                        ItinerarySolver itinerarySolver,
                                        DraftTripService draftTripService,
                                        PoolerPlanEventPublisher poolerPlanEventPublisher) {

        return new PoolerPlanManager(plannedTripService,
                plannerResourceService,
                businessService,
                itinerarySolver,
                draftTripService,
                poolerPlanEventPublisher);
    }


    @Bean
    PlannerPlanManager plannerPlanManager(PlannedTripService plannedTripService,
                                          PlannerResourceService plannerResourceService,
                                          BusinessService businessService,
                                          ItinerarySolver itinerarySolver,
                                          DraftTripService draftTripService,
                                          PoolerPlanEventPublisher poolerPlanEventPublisher) {

        return new PlannerPlanManager(plannedTripService,
                plannerResourceService,
                businessService,
                itinerarySolver,
                draftTripService);
    }

    @Bean
    ItineraryProductManager itineraryProductManager(ItineraryProductRecord itineraryProductRecord){

        return new ItineraryProductManager(itineraryProductRecord);
    }

    @Bean
    PublishManager publishManager(PlannerPlanManager plannerPlanManager,
                                  PoolerPlanManager poolerPlanManager,
                                  ItineraryProductRecord itineraryProductRecord){

        return new PublishManager(plannerPlanManager, poolerPlanManager, itineraryProductRecord);
    }



    
    @Bean
    RestTemplate restTemplate() {

        var restTemplate = new RestTemplate();

        restTemplate.setInterceptors(
                Collections.singletonList(new AuthenticationInterceptor()));

        return restTemplate;
    }










    // TODO: 17/05/2023  put declaration of executor in provider
/*    @Bean
    ApplyPricingPolicyExecutorProvider pricingPolicyExecutor(PlannedTripService publishedTripService,
                                                ScheduleEntityService scheduleEntityService){

        return new ApplyPricingPolicyExecutorProvider(publishedTripService,scheduleEntityService);
    }

    @Bean
    RoutineTripExecutor routineTripExecutor(PlannedTripService publishedTripService,
                                            PricingPolicyService pricingPolicyService){

        return new RoutineTripExecutor(publishedTripService,pricingPolicyService);
    }*/



/*
    @Bean
    CancelReservationExecutor cancelReservationExecutor(NotificationEventPublisher notificationEventPublisher,
                                                        ReservationManager reservationManager){

        return new CancelReservationExecutor(notificationEventPublisher,reservationManager);
    }
*/

/*

    @Bean
    JobExecutorManager jobExecutorManager(ApplyPricingPolicyExecutorProvider pricingPolicyExecutor,
                                            RoutineTripExecutor routineTripExecutor,
                                            CancelReservationExecutor cancelReservationExecutor){

        JobExecutorManager jobExecutorProvider = new JobExecutorManager();

        return jobExecutorProvider
                .addJobExecutor(routineTripExecutor, "")
                .addJobExecutor(pricingPolicyExecutor, PricingPolicyApplierEntity.GROUP)
                .addJobExecutor(cancelReservationExecutor, LockedTripResources.CANCELER_GROUP);
    }


*/




/*    @Bean
    PublishManager publishManager(PlannedTripService plannedTripService,
                                  PublishedTripService publishedTripService,
                                  LetsgoScheduler letsgoScheduler){

        return new PublishManager(plannedTripService,publishedTripService, letsgoScheduler);
    }*/

/*
    @Bean
    ReservationManager reservationManager(ReservationService reservationService,
                                          PublishedTripService publishedTripService,
                                          LetsgoScheduler letsgoScheduler){

        return new ReservationManager(reservationService,
                publishedTripService,
                letsgoScheduler);
    }
*/


    @Bean
    PlannerPlanManager plannerManager(BusinessService businessService,
                                      PlannedTripService tripService,
                                      DraftTripService draftTripService,
                                      ItinerarySolver itinerarySolver,
                                      PlannerResourceService resourceService
    ) {

        return new PlannerPlanManager(tripService, resourceService, businessService, itinerarySolver, draftTripService);
    }


}
