/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.lock;


import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LockResourceRequest {

    private UUID productId;
    private ResourceType resourceType;
    private Float quantity;
}



