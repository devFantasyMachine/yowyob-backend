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
 *  The pooler 's information.
 */

// TODO: 20/08/2023 refactor to pooler profile
public record PoolerInformation(String userId,
                                String name,
                                Integer version,
                                Integer score,
                                String picture,
                                Set<String> phones) implements UserInformation {


    private static final Integer UNKNOWN_VERSION = -1;


    @NonNull
    public static PoolerInformationBuilder of(@NonNull PoolerInformation plannerInformation){

        return new PoolerInformationBuilder(Objects.requireNonNull(plannerInformation));
    }

    @NonNull
    public static PoolerInformationBuilder fromPlannerDetailsId(String plannerDetailsId){

        return new PoolerInformationBuilder(Objects.requireNonNull(plannerDetailsId));
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

    public static class PoolerInformationBuilder{

        String poolerDetailsId;
        String poolerName;
        Integer informationVersion;
        Integer poolerScore;
        String poolerPicture;
        Set<String> phones;

        PoolerInformationBuilder(@NonNull String poolerDetailsId){
            this.poolerDetailsId = Objects.requireNonNull(poolerDetailsId);
            this.informationVersion = UNKNOWN_VERSION;
        }

        PoolerInformationBuilder(@NonNull PoolerInformation plannerInformation){
            this.poolerDetailsId = plannerInformation.userId;
            this.informationVersion = plannerInformation.version;
            this.poolerName = plannerInformation.name;
            this.poolerScore = plannerInformation.score;
            this.poolerPicture = plannerInformation.picture;
            this.phones = plannerInformation.phones;
        }


        public PoolerInformationBuilder poolerDetailsId(String poolerDetailsId){
            this.poolerDetailsId = Objects.requireNonNull(poolerDetailsId);
            return this;
        }

        public PoolerInformationBuilder poolerName(String poolerName){
            this.poolerName = Objects.requireNonNull(poolerName);
            return this;
        }

        public PoolerInformationBuilder informationVersion(Integer informationVersion){
            this.informationVersion = Objects.requireNonNull(informationVersion);
            return this;
        }

        public PoolerInformationBuilder plannerScore(Integer poolerScore){
            this.poolerScore = Objects.requireNonNull(poolerScore);
            return this;
        }

        public PoolerInformationBuilder poolerPicture(String poolerPicture){
            this.poolerPicture = Objects.requireNonNull(poolerPicture);
            return this;
        }

        public PoolerInformationBuilder withContactsPhones(Set<String> phones){
            this.phones = Objects.requireNonNull(phones);
            return this;
        }

        @NonNull
        public PoolerInformation build(){

            if(Objects.equals(this.informationVersion, UNKNOWN_VERSION))
                throw new IllegalArgumentException();

            return new PoolerInformation(poolerDetailsId,
                    poolerName,
                    informationVersion,
                    poolerScore,
                    poolerPicture,
                    phones);
        }

    }
}
