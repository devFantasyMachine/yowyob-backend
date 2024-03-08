/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */
package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.UserInformation;
import lombok.NonNull;

import java.util.Objects;
import java.util.Set;


/**
 *  The planner's information. it is used for poller's information, planner's information and driver's information
 */
public record PlannerInformation(String userId,
                                 String name,
                                 String code,
                                 Integer version,
                                 Integer score,
                                 String designation,
                                 String description,
                                 String picture,
                                 Set<String> phones,
                                 Set<Address> addresses) implements UserInformation {

    @NonNull
    public static PlannerInformationBuilder of(@NonNull PlannerInformation plannerInformation){

        return new PlannerInformationBuilder(Objects.requireNonNull(plannerInformation));
    }

    @NonNull
    public static PlannerInformationBuilder fromPlannerDetailsId(String plannerDetailsId){

        return new PlannerInformationBuilder(Objects.requireNonNull(plannerDetailsId));
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public String getPicture() {
        return picture;
    }

    public static class PlannerInformationBuilder{

        String plannerDetailsId;
        String plannerName;
        String plannerCode;
        Integer informationVersion;
        Integer plannerScore;
        String plannerDesignation;
        String plannerDescription;
        String plannerPicture;
        Set<String> phones;

        PlannerInformationBuilder(@NonNull String plannerDetailsId){
            this.plannerDetailsId = Objects.requireNonNull(plannerDetailsId);
        }

        PlannerInformationBuilder(@NonNull PlannerInformation plannerInformation){
            this.plannerDetailsId = plannerInformation.userId;
            this.informationVersion = plannerInformation.version;
            this.plannerName = plannerInformation.name;
            this.plannerCode = plannerInformation.code;
            this.plannerScore = plannerInformation.score;
            this.plannerDesignation = plannerInformation.designation;
            this.plannerDescription = plannerInformation.description;
            this.plannerPicture = plannerInformation.picture;
            this.phones = plannerInformation.phones;
        }

        public PlannerInformationBuilder plannerCode(String plannerCode){
            this.plannerCode = Objects.requireNonNull(plannerCode);
            return this;
        }

        public PlannerInformationBuilder plannerDetailsId(String plannerDetailsId){
            this.plannerDetailsId = Objects.requireNonNull(plannerDetailsId);
            return this;
        }

        public PlannerInformationBuilder plannerName(String plannerName){
            this.plannerName = Objects.requireNonNull(plannerName);
            return this;
        }

        public PlannerInformationBuilder informationVersion(Integer informationVersion){
            this.informationVersion = Objects.requireNonNull(informationVersion);
            return this;
        }

        public PlannerInformationBuilder plannerScore(Integer plannerScore){
            this.plannerScore = Objects.requireNonNull(plannerScore);
            return this;
        }

        public PlannerInformationBuilder plannerDesignation(String plannerDesignation){
            this.plannerDesignation = Objects.requireNonNull(plannerDesignation);
            return this;
        }

        public PlannerInformationBuilder plannerDescription(String plannerDescription){
            this.plannerDescription = Objects.requireNonNull(plannerDescription);
            return this;
        }

        public PlannerInformationBuilder plannerPicture(String plannerPicture){
            this.plannerPicture = Objects.requireNonNull(plannerPicture);
            return this;
        }

        public PlannerInformationBuilder withContactsPhones(Set<String> phones){
            this.phones = Objects.requireNonNull(phones);
            return this;
        }

        @NonNull
        public PlannerInformation build(){

            if(informationVersion == null)
                throw new IllegalArgumentException();

            return new PlannerInformation(plannerDetailsId,
                    plannerName,
                    plannerCode,
                    informationVersion,
                    plannerScore,
                    plannerDesignation,
                    plannerDescription,
                    plannerPicture,
                    phones, null);
        }

    }
}
