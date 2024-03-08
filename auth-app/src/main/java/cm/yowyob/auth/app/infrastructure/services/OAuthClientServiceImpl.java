package cm.yowyob.auth.app.infrastructure.services;


import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.port.OAuthClientService;
import cm.yowyob.auth.app.infrastructure.entities.oauth.OAuthClientEntity;
import cm.yowyob.auth.app.infrastructure.repositories.OAuthClientEntityRepository;
import cm.yowyob.auth.app.mappers.OAuthClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class OAuthClientServiceImpl implements OAuthClientService {


    @Autowired
    OAuthClientMapper mapper;

    @Autowired
    OAuthClientEntityRepository repository;



    @Override
    public OAuthClient save(OAuthClient client) {

        OAuthClientEntity saved = repository.save(mapper.toEntity(client));
        return mapper.toObject(saved);
    }



    @Override
    public Optional<OAuthClient> getByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .map(mapper::toObject);
    }

    @Override
    public Optional<OAuthClient> getById(String id) {
        return repository.findById(id)
                .map(mapper::toObject);
    }

    @Override
    public Set<OAuthClient> getByApplicationId(UUID appId) {

        return mapper.toObjectSet(repository.findAllByApplicationId(appId));
    }


}
