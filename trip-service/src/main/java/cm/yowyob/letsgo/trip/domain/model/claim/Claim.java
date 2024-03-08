package cm.yowyob.letsgo.trip.domain.model.claim;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class Claim {

    private final UUID claimId;
    private final String resourceId;
    private final String userId;
    private final UserResourceType userResourceType;

    private final LocalDateTime takeAt;
    private final LocalDateTime beginAt;
    private final Boolean isActive;

    private final LocalDateTime lostAt;
    private final LostReason lostReason;

    private final Duration claimDuration;
    private final ClaimType claimType;



    public Claim revoke(){

        return Claim.builder()
                .claimId(this.getClaimId())
                .claimDuration(this.getClaimDuration())
                .claimType(this.getClaimType())
                .takeAt(this.getTakeAt())
                .beginAt(this.getBeginAt())
                .isActive(false)
                .lostAt(LocalDateTime.now())
                .userId(this.getUserId())
                .resourceId(this.getResourceId())
                .userResourceType(this.getUserResourceType())
                .build();
    }

}
