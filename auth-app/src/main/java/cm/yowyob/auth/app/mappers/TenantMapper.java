package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.tenant.TenantSessionConfig;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.infrastructure.entities.TenantEntity;
import cm.yowyob.auth.app.infrastructure.entities.UsernamePolicyEntity;
import cm.yowyob.auth.app.infrastructure.entities.tenant.EmailConfigurationEntity;
import cm.yowyob.auth.app.infrastructure.entities.tenant.TenantRegistrationConfigEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;



@Component
public class TenantMapper extends Mapper<Tenant, TenantEntity> {

    private static final OrganisationMapper organisationMapper = new OrganisationMapper();


    @Override
    public Tenant toObject(TenantEntity entity) {

        if (Objects.isNull(entity))
            return null;


        return  Tenant.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .enabled(entity.getEnabled())
                .configured(entity.getConfigured())
                .issuer(entity.getIssuer())
                .creator(entity.getCreator() == null ? null : new UserId(entity.getCreator()))
                .organisation(organisationMapper.toObject(entity.getOrganisation()))
                .usernameConfig(entity.getUsernamePolicyEntity() == null ? null: entity.getUsernamePolicyEntity().toUsernamePolicy())
                .sessionConfig(TenantSessionConfig.builder()
                        .sessionDuration(entity.getSessionDuration() == null ? null : Duration.ofSeconds(entity.getSessionDuration()))
                        .rememberTokenDuration(entity.getRememberTokenDuration() == null ? null: Duration.ofSeconds(entity.getRememberTokenDuration()))
                        .useRememberToken(entity.getUseRememberToken() != null && entity.getUseRememberToken())
                        .build()
                )
                .build();
    }




    @Override
    public TenantEntity toEntity(Tenant object) {


        if (Objects.isNull(object))
            return null;

        return TenantEntity.builder()
                .id(object.getId())
                .issuer(object.getIssuer())
                .createdAt(object.getCreatedAt())
                .enabled(object.getEnabled())
                .configured(object.getConfigured())
                .creator(object.getCreator() == null ?  null : object.getCreator().getId())
                .organisation(organisationMapper.toEntity(object.getOrganisation()))
                .tenantRegistrationConfig(TenantRegistrationConfigEntity.from(object.getRegistrationConfig()))
                .emailConfiguration(EmailConfigurationEntity.from(object.getEmailConfiguration()))
                .usernamePolicyEntity(UsernamePolicyEntity.from(object.getUsernameConfig()))
                .sessionDuration(object.getSessionConfig().getSessionDuration().toSeconds())
                .rememberTokenDuration(object.getSessionConfig().getRememberTokenDuration().toSeconds())
                .useRememberToken(object.getSessionConfig().isUseRememberToken())
                .build();
    }



}
