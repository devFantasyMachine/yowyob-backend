package cm.yowyob.auth.app.domain.port;


import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface OAuthClientService {

    OAuthClient save(OAuthClient client);

    Optional<OAuthClient> getByClientId(String clientId);

    Optional<OAuthClient> getById(String id);

    Set<OAuthClient> getByApplicationId(UUID appId);

}
