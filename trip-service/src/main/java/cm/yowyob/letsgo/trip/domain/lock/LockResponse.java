/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.lock;

import cm.yowyob.letsgo.trip.domain.managers.ReservationResourceResponse;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class LockResponse {

    private boolean isSuccess;

    private String requestId;
    private String tokenHash;

    private List<ReservationResourceResponse> resourceResponses;
    private Stop fromLocation;
    private Stop toLocation;

    public LockResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.resourceResponses = new ArrayList<>();
    }

    public void addResourceResponse(ReservationResourceResponse place) {

        resourceResponses.add(place);
    }
    
}



