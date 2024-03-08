/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;



import java.util.Set;




public record SharedPolicy(SharedPolicyLevel policyLevel,
                           Set<SubscriberType> subscriberType) {

    public static final SharedPolicy AGENCY_POLICY = new SharedPolicy(SharedPolicyLevel.PUBLIC, Set.of(SubscriberType.POLLER));

}

