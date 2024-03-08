package cm.yowyob.auth.app.domain.port;


import cm.yowyob.auth.app.domain.model.tenant.Tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantService {


    Tenant save(Tenant tenant);

    default Optional<Tenant> getTenant(UUID tenantId) {
        return Optional.empty();
    }

    Optional<Tenant> getTenantByIssuer(String shortName);

}
