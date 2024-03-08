/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.PoolerInformation;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PoolerInformationDTO {

    String poolerId;
    String poolerName;
    Integer poolerVersion;
    Integer poolerScore;
    String poolerPicture;
    Set<String> phones;

    public static PoolerInformationDTO fromPoolerInformation(PoolerInformation poolerInformation){

        if (poolerInformation == null)
            return null;

        return PoolerInformationDTO.builder()
                .poolerId(poolerInformation.userId())
                .poolerName(poolerInformation.name())
                .poolerVersion(poolerInformation.version())
                .poolerPicture(poolerInformation.picture())
                .phones(poolerInformation.phones())
                .poolerScore(poolerInformation.score())
                .build();
    }
}
