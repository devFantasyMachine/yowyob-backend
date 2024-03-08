package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.token.APIKey;
import cm.yowyob.auth.app.domain.port.APIKeyService;
import cm.yowyob.auth.app.infrastructure.entities.APIKeyEntity;
import cm.yowyob.auth.app.infrastructure.repositories.APIKeyEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class APIKeyServiceImpl implements APIKeyService {


    @Autowired
    APIKeyEntityRepository apiKeyEntityRepository;


    @Override
    public APIKey save(APIKey apiKey) {

        APIKeyEntity apiKeyEntity = apiKeyEntityRepository.save(APIKeyEntity.fromKey(apiKey));
        return APIKeyEntity.toKey(apiKeyEntity);
    }

    @Override
    public APIKey getByKey(String key) {

        Optional<APIKeyEntity> optionalAPIKeyEntity = apiKeyEntityRepository.findByKey(key);
        return optionalAPIKeyEntity.map(APIKeyEntity::toKey).orElse(null);
    }


}
