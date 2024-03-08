/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.exceptions.PlannerNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.PollerNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.PlannerInformation;
import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;

public interface BusinessService {



    /**
     * get planner information
     *
     * @param plannerSub plannerSub
     * @return {@link PlannerInformation}
     * @see PlannerInformation
     * @throws PlannerNotFoundException planner not found
     */
    PlannerInformation getPlannerInformation(String plannerSub) throws PlannerNotFoundException;


    /**
     * get poller information
     *
     * @param poolerId the id of the pooler
     * @return {@link PoolerInformation}
     * @see PoolerInformation
     * @throws PollerNotFoundException means that pooler is not found
     */
    PoolerInformation getPoolerInformation(String poolerId) throws PollerNotFoundException;
}
