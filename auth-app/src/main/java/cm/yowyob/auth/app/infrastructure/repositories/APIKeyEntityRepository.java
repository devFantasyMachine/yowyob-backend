package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.APIKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface APIKeyEntityRepository extends JpaRepository<APIKeyEntity, String> {

    Optional<APIKeyEntity> findByKey(String key);
}
