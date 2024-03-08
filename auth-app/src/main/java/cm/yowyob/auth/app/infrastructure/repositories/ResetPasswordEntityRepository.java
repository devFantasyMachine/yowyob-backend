package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.ResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordEntityRepository extends JpaRepository<ResetPasswordEntity, String> {
}
