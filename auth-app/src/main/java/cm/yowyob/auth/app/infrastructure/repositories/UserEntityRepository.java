package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntity.UserEntityId> {

    Optional<UserEntity> findByTenantIdAndUsername(UUID tenantId, String username);
    Optional<UserEntity> findByTenantIdAndUserId(UUID tenantId, String userId);
    Optional<UserEntity> findByTenantIdAndPhone(UUID tenantId, String phone);
    Optional<UserEntity> findByTenantIdAndEmail(UUID tenantId, String email);

}
