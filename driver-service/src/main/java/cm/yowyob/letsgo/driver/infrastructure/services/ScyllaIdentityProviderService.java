package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.ports.IdentityProviderService;
import cm.yowyob.letsgo.driver.domain.model.identities.IdentityProvider;
import cm.yowyob.letsgo.driver.infrastructure.entities.IdentityProviderEntity;
import cm.yowyob.letsgo.driver.infrastructure.repositories.IdentityProviderEntityRepository;
import cm.yowyob.letsgo.driver.mappers.IdentityProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ScyllaIdentityProviderService implements IdentityProviderService {

    @Autowired
    IdentityProviderEntityRepository identityProviderEntityRepository;

    private final IdentityProviderMapper identityProviderMapper = new IdentityProviderMapper();

    @Override
    public IdentityProvider getById(UUID providerId) {

        IdentityProviderEntity entity = identityProviderEntityRepository.findByProviderId(providerId);

        return identityProviderMapper.toObject(entity);
    }

    @Override
    public IdentityProvider save(IdentityProvider provider) {

        IdentityProviderEntity providerEntity = identityProviderEntityRepository.save(identityProviderMapper.toEntity(provider));

        return identityProviderMapper.toObject(providerEntity);
    }


}
