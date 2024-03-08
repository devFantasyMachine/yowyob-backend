package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.identities.IdentityProvider;

import java.util.UUID;

public interface IdentityProviderService {
    IdentityProvider getById(UUID providerId);
    IdentityProvider save(IdentityProvider provider);
}
