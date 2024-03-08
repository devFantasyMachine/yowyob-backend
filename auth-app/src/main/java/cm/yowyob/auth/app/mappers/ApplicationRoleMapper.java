package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.infrastructure.entities.ApplicationRoleEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;

import org.springframework.stereotype.Component;


@Component
public class ApplicationRoleMapper extends Mapper<ApplicationRole, ApplicationRoleEntity> {



    @Override
    public ApplicationRole toObject(ApplicationRoleEntity entity) {

        if (entity == null)
            return null;

        return ApplicationRole.builder()
                .addInstant(entity.getCreatedAt())
                .lastUpdateInstant(entity.getUpdatedAt())
                .description(entity.getRoleDesc())
                .name(entity.getRoleName())
                .isSuperRole(entity.getIsSuperRole())
                .isDefault(entity.getIsDefault())
                .isBusiness(entity.getIsBusiness())
                .appId(entity.getAppId())
                .tenantId(entity.getTenantId())
                .build();
    }



    @Override
    public ApplicationRoleEntity toEntity(ApplicationRole object) {

        if (object == null)
            return null;

        return ApplicationRoleEntity.builder()
                .appId(object.getAppId())
                .isSuperRole(object.getIsSuperRole())
                .isDefault(object.getIsDefault())
                .isBusiness(object.getIsBusiness())
                .roleName(object.getName())
                .roleDesc(object.getDescription())
                .tenantId(object.getTenantId())
                .createdAt(object.getAddInstant())
                .updatedAt(object.getLastUpdateInstant())
                .build();

    }




}
