package cm.yowyob.letsgo.driver.infrastructure.repositories;

import cm.yowyob.letsgo.driver.infrastructure.entities.ClaimEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Set;
import java.util.UUID;

public interface ClaimRepository extends CassandraRepository<ClaimEntity, UUID> {

    Set<ClaimEntity> findAllByResourceIdIn(Iterable<String> resourceId);

    Set<ClaimEntity> findAllByUserId(String userId);

    Set<ClaimEntity> findAllByResourceId(String resourceId);

}
