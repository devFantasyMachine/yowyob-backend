package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.infrastructure.entities.GroupEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class GroupMapper extends Mapper<Group, GroupEntity> {


    @Autowired
    ApplicationRoleMapper applicationRoleMapper;


    @Override
    public Group toObject(GroupEntity entity) {

        if (entity == null)
            return null;

        return Group.builder()
                .tenantId(entity.getTenantId())
                .groupId(entity.getGroupId())
                .members(entity.getMembers() == null ? null : entity.getMembers()
                        .stream()
                        .map(UUID::fromString)
                        .collect(Collectors.toList())
                )
                .name(entity.getName())
                .desc(entity.getDescription())
                .insertAt(entity.getInsertAt())
                .roles(applicationRoleMapper.toObjects(entity.getRoles()))
                .creator(entity.getCreator() == null ? null: new UserId(entity.getCreator()))
                .updateAt(entity.getUpdateAt())
                .appId(entity.getAppId())
                .groupType(entity.getGroupType())
                .build();
    }



    @Override
    public GroupEntity toEntity(Group object) {

        if (object == null)
            return null;

        return GroupEntity.builder()
                .tenantId(object.getTenantId())
                .groupId(object.getGroupId())
                .appId(object.getAppId())
                .members(object.getMembers() == null ? null : object.getMembers()
                        .stream()
                        .map(UUID::toString)
                        .collect(Collectors.toList())
                )
                .name(object.getName())
                .description(object.getDesc())
                .insertAt(object.getInsertAt())
                .roles(applicationRoleMapper.toEntities(object.getRoles()))
                .creator(object.getCreator() == null ? null: object.getCreator().getId())
                .updateAt(object.getUpdateAt())
                .groupType(object.getGroupType())
                .build();
    }
}
