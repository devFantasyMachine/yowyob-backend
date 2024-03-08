package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.GroupMember;
import cm.yowyob.auth.app.domain.model.user.UserId;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GroupService {
    Group save(Group group);

    GroupMember save(GroupMember groupMember);

    void saveAll(Set<Group> groups);

    List<GroupMember> getGroupMembers(UUID appId, UserId userId);

    List<GroupMember> getGroupMembers(UserId userId);
}
