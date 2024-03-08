/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.lock;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockRequest {

    private String userId;
    private UUID tripId;
    private UUID requestId;
    private String group;
    private String challengeToken;
    private Boolean isConfirmation;
    private Set<LockResourceRequest> resources;
}
