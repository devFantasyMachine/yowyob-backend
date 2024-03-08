package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.token.APIKey;

public interface APIKeyService {

    APIKey save(APIKey apiKey);
    APIKey getByKey(String key);
}
