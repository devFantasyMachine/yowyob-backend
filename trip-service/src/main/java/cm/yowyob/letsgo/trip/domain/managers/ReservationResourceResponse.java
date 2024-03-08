/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.managers;

import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservationResourceResponse {

    private String resourceLabel;
    private ResourceType resourceType;
    private UUID resourceId;
    private Float unitCost;
    private Float quantity;
    private ResourceResponseStatus status;

    private boolean isSuccess;


    public enum ResourceResponseStatus {

        NOT_FOUND,

        FOUND_NOT_AVAILABLE,

        FOUND_AVAILABLE
    }

}



