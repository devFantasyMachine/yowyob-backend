package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationEntityRepository extends JpaRepository<RegistrationEntity, String> {
}
