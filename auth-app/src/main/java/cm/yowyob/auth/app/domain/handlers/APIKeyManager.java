package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.code.Base64String;
import cm.yowyob.auth.app.domain.model.code.Base64StringGenerator;
import cm.yowyob.auth.app.domain.model.token.APIKey;
import cm.yowyob.auth.app.domain.model.token.Permission;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.APIKeyService;
import cm.yowyob.auth.app.domain.port.UserService;
import cm.yowyob.auth.app.domain.util.SimpleCache;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
public class APIKeyManager {

    private final SimpleCache<String, APIKey> apiKeySimpleCache = new SimpleCache<>(50);


    private UserService userService;
    private APIKeyService apiKeyService;

    private final Base64StringGenerator base64StringGenerator = new Base64StringGenerator(50);



    public Optional<APIKey> getKey(String key){

        return Optional.ofNullable(apiKeySimpleCache.get(key,
                (_key) -> apiKeyService.getByKey(_key)));
    }



    public APIKey createAPIKey(UserId creator,
                               UUID tenantId,
                               Set<Permission> permissions,
                               Set<String> ipAccessControlList
    ) throws UserNotFoundException {

        User developer = userService.getDeveloper(tenantId, creator);

        Base64String base64String = base64StringGenerator.generate();

        APIKey apiKey = APIKey.builder()
                .tenantId(tenantId)
                .timestamp(Instant.now())
                .key(base64String.getValue())
                .creator(developer.getUserId())
                .ipAccessControlList(ipAccessControlList)
                .permissions(permissions)
                .enabled(true)
                .build();

        return saveKey(apiKey);
    }


    private APIKey saveKey(APIKey apiKey) {
        APIKey savedKey = apiKeyService.save(apiKey);
        apiKeySimpleCache.put(savedKey.getKey(), savedKey);
        return savedKey;
    }


}
