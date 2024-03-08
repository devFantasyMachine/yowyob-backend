package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.users.UserRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRegistrationEntityRepository extends JpaRepository<UserRegistrationEntity, UUID> {

    Optional<UserRegistrationEntity> findByApplicationIdAndUserUserId(UUID appId, String userId);


    List<UserRegistrationEntity> findAllByApplicationId(UUID appId);
}
