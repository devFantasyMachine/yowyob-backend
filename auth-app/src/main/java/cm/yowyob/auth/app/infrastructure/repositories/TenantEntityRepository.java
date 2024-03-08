package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantEntityRepository extends JpaRepository<TenantEntity, UUID> {
    Optional<TenantEntity> findByIssuerIgnoreCase(String shortName);
}
