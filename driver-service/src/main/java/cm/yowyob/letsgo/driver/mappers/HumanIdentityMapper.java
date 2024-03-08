package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.HumanIdentityEntity;

import java.time.LocalDate;


public class HumanIdentityMapper extends Mapper<HumanIdentity, HumanIdentityEntity> {

    @Override
    public HumanIdentity toObject(HumanIdentityEntity entity) {

        if (entity == null)
            return null;

        return HumanIdentity.builder()
                .identityUId(entity.getIdentityUId())
                .providerId(entity.getProviderId())
                .formattedIdentityProvider(entity.getProviderFormatted())
                .docs(entity.getDocs())
                .isVerified(entity.getIsVerified())
                .expireAt(entity.getExpireAt() == null ? null : LocalDate.from(entity.getExpireAt()))
                .issueAt(entity.getIssueAt() == null ? null : LocalDate.from(entity.getIssueAt()))
                .type(entity.getType())
                .createdAt(entity.getCreatedAt())
                .verifiedAt(entity.getVerifiedAt())
                .build();
    }

    @Override
    public HumanIdentityEntity toEntity(HumanIdentity object) {

        if (object == null)
            return null;

        return HumanIdentityEntity.builder()
                .identityUId(object.getIdentityUId())
                .docs(object.getDocs())
                .isVerified(object.getIsVerified())
                .expireAt(object.getExpireAt() != null ? object.getExpireAt().atStartOfDay() : null)
                .issueAt(object.getIssueAt() != null ? object.getIssueAt().atStartOfDay() : null)
                .type(object.getType())
                .createdAt(object.getCreatedAt())
                .verifiedAt(object.getVerifiedAt())
                .providerId(object.getProviderId())
                .providerFormatted(object.getFormattedIdentityProvider())
                .build();
    }
}
