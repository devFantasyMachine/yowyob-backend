package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.tenant.Organisation;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.tenant.TenantRegistrationConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;



@Data
@Builder
@AllArgsConstructor
public class TenantPublicDTO {

    private Organisation organisation;
    private Boolean isEnabled;
    private Instant createdAt;
    private TenantRegistrationConfig tenantRegistrationConfig;


    public static TenantPublicDTO from(Tenant tenant) {

        if (tenant == null)
            return null;

        return TenantPublicDTO.builder()
                .organisation(tenant.getOrganisation())
                .isEnabled(tenant.getEnabled())
                .createdAt(tenant.getCreatedAt())
                .tenantRegistrationConfig(tenant.getRegistrationConfig())
                .build();
    }




}

