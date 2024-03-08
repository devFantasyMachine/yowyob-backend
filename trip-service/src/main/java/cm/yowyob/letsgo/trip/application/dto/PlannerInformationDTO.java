/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.PlannerInformation;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PlannerInformationDTO {

    String plannerDetailsId;
    String plannerName;
    String plannerCode;
    Integer informationVersion;
    Integer plannerScore;
    String plannerDesignation;
    String plannerDescription;
    String plannerPicture;
    Set<String> phones;

    public static PlannerInformationDTO fromPlannerInformation(PlannerInformation pLannerInformation){

        if (pLannerInformation == null)
            return null;

        return PlannerInformationDTO.builder()
                .informationVersion(pLannerInformation.version())
                .plannerCode(pLannerInformation.code())
                .plannerName(pLannerInformation.name())
                .plannerDetailsId(pLannerInformation.userId())
                .plannerDesignation(pLannerInformation.designation())
                .plannerScore(pLannerInformation.score())
                .build();
    }
}
