package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.OrganisationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationEntityRepository extends JpaRepository<OrganisationEntity, String> {
}
