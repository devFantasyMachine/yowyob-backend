/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model.driver;


import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import cm.yowyob.letsgo.driver.domain.model.resources.UserResource;
import cm.yowyob.letsgo.driver.domain.model.resources.UserResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;



@Data
@Builder
@AllArgsConstructor
public class DriverInfo implements UserResource {

    public static Integer INITIAL_VERSION = 0;

    private final Integer version;
    private final String driverId;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    private final String pseudo;
    private final String picture;
    private final String gender;
    private final Score score;
    private final String ownerId;
    private final String phone;
    private final Set<String> docs;
    private final List<HumanIdentity> driverLicences;


    private final String aboutEn;
    private final String aboutFr;
    private final Boolean isIndependent;
    private final String cv;
    private final Integer yearsOfExperience;
    private final Set<DriverSkill> skills;
    private final List<DriverExperience> experiences;



    @Override
    public UserResourceType getResourceType() {
        return UserResourceType.DRIVER;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public String getResourceId() {
        return driverId;
    }


    public static DriverInfo.DriverInfoBuilder getDriverBuilder(DriverInfo oldDriverInfo) {

        return DriverInfo.builder()
                .driverId(oldDriverInfo.getDriverId())
                .ownerId(oldDriverInfo.getOwnerId())
                .version(oldDriverInfo.getVersion())
                .score(oldDriverInfo.getScore())
                .aboutEn(oldDriverInfo.getAboutEn())
                .avatar(oldDriverInfo.getAvatar())
                .firstName(oldDriverInfo.getFirstName())
                .lastName(oldDriverInfo.getLastName())
                .gender(oldDriverInfo.getGender())
                .picture(oldDriverInfo.getPicture())
                .pseudo(oldDriverInfo.getPseudo())
                .phone(oldDriverInfo.getPhone())
                .docs(oldDriverInfo.docs)
                .driverLicences(oldDriverInfo.getDriverLicences());
    }


}

