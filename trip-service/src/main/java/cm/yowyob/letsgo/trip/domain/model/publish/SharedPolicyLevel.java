/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;


public enum SharedPolicyLevel {

    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");


    private final String policyName;

    SharedPolicyLevel(String policyName){
        this.policyName = policyName;
    }

    public String getPolicyName() {
        return policyName;
    }
}
