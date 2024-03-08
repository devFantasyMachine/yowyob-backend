package cm.yowyob.letsgo.driver.domain.model.resources;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserClaimFactory {

    public static Claim makeClaim(@NonNull String userId,
                                  @NonNull UserResource userResource,
                                  @NonNull ClaimType claimType){

        return Claim.builder()
                .claimId(UUID.randomUUID())
                .userId(userId)
                .isActive(true)
                .resourceId(userResource.getResourceId())
                .userResourceType(userResource.getResourceType())
                .takeAt(LocalDateTime.now())
                .beginAt(LocalDateTime.now())
                .claimType(claimType)
                .lostAt(null)
                .claimDuration(null)
                .build();

    }


    public static Claim makeAnonymousClaim(String targetId, ClaimType claimType) {

        return Claim.builder()
                .claimId(UUID.randomUUID())
                .userId("anonymous")
                .isActive(true)
                .resourceId(targetId)
                .userResourceType(UserResourceType.POOLER_ACTIVITY)
                .takeAt(LocalDateTime.now())
                .beginAt(LocalDateTime.now())
                .claimType(claimType)
                .lostAt(null)
                .claimDuration(null)
                .build();
    }
}
