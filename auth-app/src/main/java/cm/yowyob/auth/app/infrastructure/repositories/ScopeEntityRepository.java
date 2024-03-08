package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.ScopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScopeEntityRepository extends JpaRepository<ScopeEntity, ScopeEntity.ScopeId> {

}
