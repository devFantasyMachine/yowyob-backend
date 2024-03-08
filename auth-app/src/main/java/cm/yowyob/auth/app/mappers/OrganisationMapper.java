package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.tenant.Organisation;
import cm.yowyob.auth.app.infrastructure.entities.OrganisationEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;

public class OrganisationMapper extends Mapper<Organisation, OrganisationEntity> {

    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private final ContactMapper contactMapper = ContactMapper.getInstance();



    @Override
    public Organisation toObject(OrganisationEntity entity) {

        if (entity == null)
            return null;

        return Organisation.builder()
                .name(entity.getName())
                .icon(entity.getIcon())
                .privacyPolicyUrl(entity.getPrivacyPolicyUrl())
                .termOfUseUrl(entity.getTermOfUseUrl())
                .support(contactMapper.toObject(entity.getSupport()))
                .shortName(entity.getShortName())
                .location(addressMapper.toObject(entity.getLocation()))
                .description(entity.getDescription())
                .build();
    }

    @Override
    public OrganisationEntity toEntity(Organisation object) {

        if (object == null)
            return null;

        return OrganisationEntity.builder()
                .name(object.getName())
                .icon(object.getIcon())
                .privacyPolicyUrl(object.getPrivacyPolicyUrl())
                .termOfUseUrl(object.getTermOfUseUrl())
                .support(contactMapper.toEntity(object.getSupport()))
                .shortName(object.getShortName())
                .location(addressMapper.toEntity(object.getLocation()))
                .description(object.getDescription())
                .build();
    }
}
