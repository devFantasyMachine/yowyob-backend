package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.GeneralAppInfo;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.infrastructure.entities.ApplicationEntity;
import cm.yowyob.auth.app.infrastructure.entities.FamilyConfigEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ApplicationMapper extends Mapper<Application, ApplicationEntity> {


    private final ContactFactory contactFactory = ContactFactory.getInstance();


    @Autowired
    UserMapper userMapper;

    @Autowired
    ApplicationRoleMapper applicationRoleMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    ScopeMapper scopeMapper;


    @Override
    public Application toObject(ApplicationEntity entity) {
        if (entity == null)
            return null;

        return Application.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .verifyRegistration(entity.getVerifyRegistration())
                .creator(userMapper.toObject(entity.getCreator()))
                .active(entity.getActive())
                .isCountryBased(entity.getIsCountryBased())
                .availableCountriesCodes(entity.getAvailableCountriesCodes())
                .familyConfig(FamilyConfigEntity.to(entity.getFamilyConfig()))
                .roles(applicationRoleMapper.toObjects(entity.getRoles()))
                .groups(groupMapper.toObjects(entity.getGroups()))
                .info(GeneralAppInfo.builder()
                        .name(entity.getName())
                        .icon(entity.getIcon())
                        .shortName(entity.getShortName())
                        .description(entity.getDescription())
                        .homePage(entity.getHomePage())
                        .termOfUseLink(entity.getTermOfUseLink())
                        .privacyPolicyLink(entity.getPrivacyPolicyLink())
                        .support(entity.getSupportEmail() == null ? null :
                                contactFactory.createEmailAddress(entity.getSupportEmail())
                        )
                        .build()
                )
                .scopes(scopeMapper.toObjects(entity.getScopes()))
                .registrationDeletePolicy(TimeBasedDeletePolicy.builder()
                        .isEnabled(entity.getDeleteRegistrationPolicyEnabled())
                        .numberOfDaysToRetain(entity.getNumberOfDaysToRetain())
                        .build()
                )
                .build();
    }

    @Override
    public ApplicationEntity toEntity(Application object) {

        if (object == null)
            return null;

        return ApplicationEntity.builder()
                .id(object.getId())
                .tenantId(object.getTenantId())
                .verifyRegistration(object.getVerifyRegistration())
                .creator(userMapper.toEntity(object.getCreator()))
                .active(object.getActive())
                .isCountryBased(object.getIsCountryBased())
                .availableCountriesCodes(object.getAvailableCountriesCodes())
                .familyConfig(FamilyConfigEntity.from(object.getFamilyConfig()))
                .roles(applicationRoleMapper.toEntitySet(object.getRoles()))
                .groups(groupMapper.toEntities(object.getGroups()))
                .name(object.getInfo().getName())
                .icon(object.getInfo().getIcon())
                .shortName(object.getInfo().getShortName())
                .description(object.getInfo().getDescription())
                .homePage(object.getInfo().getHomePage())
                .termOfUseLink(object.getInfo().getTermOfUseLink())
                .privacyPolicyLink(object.getInfo().getPrivacyPolicyLink())
                .scopes(scopeMapper.toEntitySet(object.getScopes()))
                .deleteRegistrationPolicyEnabled(object.getRegistrationDeletePolicy().getIsEnabled())
                .numberOfDaysToRetain(object.getRegistrationDeletePolicy().getNumberOfDaysToRetain())
                .passwordLessEnabled(object.isPasswordLessEnabled())
                .supportEmail(object.getInfo().getSupport() == null ? null : object.getInfo().getSupport().getValue())
                .build();
    }


}
