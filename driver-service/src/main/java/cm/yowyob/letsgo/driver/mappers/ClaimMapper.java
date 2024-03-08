package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.resources.Claim;
import cm.yowyob.letsgo.driver.domain.model.resources.ClaimType;
import cm.yowyob.letsgo.driver.infrastructure.entities.ClaimEntity;

import java.time.Duration;

public class ClaimMapper extends Mapper<Claim, ClaimEntity>{

    @Override
    public Claim toObject(ClaimEntity entity) {

        if (entity == null)
            return null;

        return Claim.builder()
                .userId(entity.getUserId())
                .resourceId(entity.getResourceId())
                .claimDuration(entity.getClaimDuration() == null ? null : Duration.ofSeconds(entity.getClaimDuration()))
                .userResourceType(entity.getUserResourceType())
                .claimId(entity.getClaimId())
                .isActive(entity.getIsActive())
                .lostReason(entity.getLostReason())
                .takeAt(entity.getTakeAt())
                .beginAt(entity.getBeginAt())
                .lostAt(entity.getLostAt())
                .claimType(entity.getClaimType() == null ? null : ClaimType.values()[entity.getClaimType()])
                .build();
    }


    @Override
    public ClaimEntity toEntity(Claim object) {

        if (object == null)
            return null;

        return ClaimEntity.builder()
                .userId(object.getUserId())
                .resourceId(object.getResourceId())
                .claimDuration(object.getClaimDuration() == null ? null : object.getClaimDuration().toSeconds())
                .userResourceType(object.getUserResourceType())
                .claimId(object.getClaimId())
                .isActive(object.getIsActive())
                .lostReason(object.getLostReason())
                .takeAt(object.getTakeAt())
                .beginAt(object.getBeginAt())
                .lostAt(object.getLostAt())
                .claimType(object.getClaimType() == null ? null : object.getClaimType().ordinal())
                .build();
    }
}
