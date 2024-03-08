package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationEntityRepository extends JpaRepository<InvitationEntity, String> {
}
