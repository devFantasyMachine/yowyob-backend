package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.users.UserStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStateEntityRepository extends JpaRepository<UserStateEntity, UUID> {
}
