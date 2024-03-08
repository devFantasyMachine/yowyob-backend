package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.ApplicationRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ApplicationRoleEntityRepository extends JpaRepository<ApplicationRoleEntity, ApplicationRoleEntity.RoleId> {

}
