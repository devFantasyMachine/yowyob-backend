/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.exceptions.PlannerNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.PollerNotFoundException;
import cm.yowyob.letsgo.trip.domain.ports.BusinessService;
import cm.yowyob.letsgo.trip.domain.model.PlannerInformation;
import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;

import java.util.Set;

/**
 * n memory user service
 *
 */
public class InMemoryBusinessService implements BusinessService {

    /**
     * get planner information
     *
     * @param plannerSub plannerSub
     * @return {@link PlannerInformation}
     * @see PlannerInformation
     * @throws PlannerNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. planner not found exception
     */
    @Override
    public PlannerInformation getPlannerInformation(String plannerSub) throws PlannerNotFoundException {

        PlannerInformation.PlannerInformationBuilder builder = PlannerInformation.fromPlannerDetailsId(plannerSub);

        builder.informationVersion(1);
        builder.plannerCode("GV");
        builder.plannerName("General Voyage");
        builder.plannerScore(3);
        builder.withContactsPhones(Set.of("694567219"));
        builder.plannerDesignation("General Voyage");
        return builder.build();
    }



    /**
     * get poller information
     *
     * @param poolerId poolerId
     * @return {@link PoolerInformation}
     * @see PoolerInformation
     * @throws PollerNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. poller not found exception
     */
    @Override
    public PoolerInformation getPoolerInformation(String poolerId) throws PollerNotFoundException {

        return PoolerInformation.fromPlannerDetailsId(poolerId)
                .informationVersion(2)
                .plannerScore(3)
                .poolerName("Tonzong Christian")
                .withContactsPhones(Set.of("694567219"))
                .poolerPicture("picture")
                .build();
    }

}
