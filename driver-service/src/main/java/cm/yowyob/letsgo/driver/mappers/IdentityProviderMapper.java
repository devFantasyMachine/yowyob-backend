package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.identities.IdentityProvider;
import cm.yowyob.letsgo.driver.infrastructure.entities.IdentityProviderEntity;

public class IdentityProviderMapper extends Mapper<IdentityProvider, IdentityProviderEntity>{

    @Override
    public IdentityProvider toObject(IdentityProviderEntity entity) {

        if (entity == null)
            return null;

        return IdentityProvider.builder()
                .providerId(entity.getProviderId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .isDeleted(entity.getIsDeleted())
                .isTrusted(entity.getIsTrusted())
                .isTrustedAt(entity.getIsTrustedAt())
                .providerCountry(entity.getProviderCountry())
                .providerLocation(entity.getProviderLocation())
                .providerCreatorId(entity.getProviderCreatorId())
                .providerDescription(entity.getProviderDescription())
                .build();
    }



    @Override
    public IdentityProviderEntity toEntity(IdentityProvider object) {

        if (object == null)
            return null;

        return IdentityProviderEntity.builder()
                .providerId(object.getProviderId())
                .createdAt(object.getCreatedAt())
                .deletedAt(object.getDeletedAt())
                .isDeleted(object.getIsDeleted())
                .isTrusted(object.getIsTrusted())
                .isTrustedAt(object.getIsTrustedAt())
                .providerCountry(object.getProviderCountry())
                .providerLocation(object.getProviderLocation())
                .providerCreatorId(object.getProviderCreatorId())
                .providerDescription(object.getProviderDescription())
                .build();
    }


}
