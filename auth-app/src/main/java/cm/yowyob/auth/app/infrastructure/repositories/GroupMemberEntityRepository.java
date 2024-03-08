package cm.yowyob.auth.app.infrastructure.repositories;

import cm.yowyob.auth.app.infrastructure.entities.users.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupMemberEntityRepository extends JpaRepository<GroupMemberEntity, UUID> {

    List<GroupMemberEntity> findAllByAppIdAndUserUserId(UUID appId, String id);

    List<GroupMemberEntity> findAllByUserUserId(String id);
}
