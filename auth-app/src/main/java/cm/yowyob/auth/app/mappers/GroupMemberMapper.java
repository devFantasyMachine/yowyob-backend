package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.GroupMember;
import cm.yowyob.auth.app.infrastructure.entities.users.GroupMemberEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GroupMemberMapper extends Mapper<GroupMember, GroupMemberEntity> {


    @Autowired
    UserMapper userMapper;


    @Override
    public GroupMember toObject(GroupMemberEntity entity) {

        if (entity == null)
            return null;

        return GroupMember.builder()
                .id(entity.getId())
                .groupId(entity.getGroupId())
                .addAt(entity.getAddAt())
                .memberType(entity.getMemberType())
                .appId(entity.getAppId())
                .user(userMapper.toObject(entity.getUser()))
                .build();
    }



    @Override
    public GroupMemberEntity toEntity(GroupMember object) {
        if (object == null)
            return null;

        return GroupMemberEntity.builder()
                .id(object.getId())
                .groupId(object.getGroupId())
                .memberType(object.getMemberType())
                .appId(object.getAppId())
                .addAt(object.getAddAt())
                .user(userMapper.toEntity(object.getUser()))
                .build();
    }




}
