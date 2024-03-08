/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.Money;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LuggageBoxDTO {

    private final String luggageId;
    private final Integer boxNumber;
    private Money unitCost;
    private Float quantity;





}
