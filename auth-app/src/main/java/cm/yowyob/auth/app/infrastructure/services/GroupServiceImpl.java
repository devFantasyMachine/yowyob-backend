package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.GroupMember;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.GroupService;
import cm.yowyob.auth.app.infrastructure.entities.GroupEntity;
import cm.yowyob.auth.app.infrastructure.entities.users.GroupMemberEntity;
import cm.yowyob.auth.app.infrastructure.repositories.GroupEntityRepository;
import cm.yowyob.auth.app.infrastructure.repositories.GroupMemberEntityRepository;
import cm.yowyob.auth.app.mappers.GroupMapper;
import cm.yowyob.auth.app.mappers.GroupMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    GroupEntityRepository groupEntityRepository;

    @Autowired
    GroupMemberEntityRepository groupMemberEntityRepository;


    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupMemberMapper groupMemberMapper;


    @Override
    public Group save(Group group) {

        GroupEntity entity = groupMapper.toEntity(group);
        return groupMapper.toObject(groupEntityRepository.save(entity));
    }

    @Override
    public GroupMember save(GroupMember groupMember) {

        GroupMemberEntity groupMemberEntity = groupMemberEntityRepository.save(groupMemberMapper.toEntity(groupMember));
        return groupMemberMapper.toObject(groupMemberEntity);
    }


    @Override
    public void saveAll(Set<Group> groups) {

        groupEntityRepository.saveAll(groupMapper.toEntities(groups));
    }

    @Override
    public List<GroupMember> getGroupMembers(UUID appId, UserId userId) {

        List<GroupMemberEntity> memberEntities = groupMemberEntityRepository
                .findAllByAppIdAndUserUserId(appId, userId.getId());

        return groupMemberMapper.toObjects(memberEntities);
    }

    @Override
    public List<GroupMember> getGroupMembers(UserId userId) {

        List<GroupMemberEntity> memberEntities = groupMemberEntityRepository
                .findAllByUserUserId(userId.getId());

        return groupMemberMapper.toObjects(memberEntities);
    }


}
