package cm.yowyob.letsgo.driver.infrastructure.repositories;

import cm.yowyob.letsgo.driver.infrastructure.entities.IdentityProviderEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface IdentityProviderEntityRepository extends CassandraRepository<IdentityProviderEntity, UUID> {

    IdentityProviderEntity findByProviderId(UUID providerId);
}
