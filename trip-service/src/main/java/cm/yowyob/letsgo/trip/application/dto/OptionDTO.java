/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.Money;
import cm.yowyob.letsgo.trip.domain.model.resources.PlannedSeat;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceStatus;
import cm.yowyob.letsgo.trip.domain.model.resources.TripOption;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;


@Data
@Builder
public class OptionDTO {



    private final String optionDetails;
    private final String optionLabel;
    private final String optionId;
    private Money unitCost;
    private ResourceStatus status;
    private Float quantity;



}

