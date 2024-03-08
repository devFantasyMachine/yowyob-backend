package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.*;
import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.stereotype.Component;





@Component
public class UserMapper extends Mapper<User, UserEntity> {


    private final ProfileMapper profileMapper = new ProfileMapper();

    private final UserRegistrationMapper userRegistrationMapper = new UserRegistrationMapper();




    @Override
    public User toObject(UserEntity entity) {

        if (entity == null)
            return null;

        return User.builder()
                .userId(new UserId(entity.getUserId()))
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .emailVerified(entity.getEmailVerified())
                .phoneVerified(entity.getPhoneVerified())
                .roles(entity.getRoles())
                .profile(profileMapper.toObject(entity.getProfile()))
                .requireActions(entity.getRequireActions())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .tenantId(entity.getTenantId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userState(entity.getUserState())
                .parentEmail(entity.getParentEmail())
                .creator(entity.getCreator() == null ? null: new UserId(entity.getCreator()))
                .registrations(userRegistrationMapper.toObjects(entity.getRegistrations()))
                .build();

    }



    @Override
    public UserEntity toEntity(User object) {

        if (object == null)
            return null;

        return UserEntity.builder()
                .email(object.getEmail())
                .phone(object.getPhone())
                .emailVerified(object.getEmailVerified())
                .phoneVerified(object.getPhoneVerified())
                .parentEmail(object.getParentEmail())
                .roles(object.getRoles())
                .profile(profileMapper.toEntity(object.getProfile()))
                .requireActions(object.getRequireActions())
                .password(object.getPassword())
                .username(object.getUsername())
                .userId(object.getUserId().getId())
                .tenantId(object.getTenantId())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())
                .userState(object.getUserState())
                .creator(object.getCreator() == null ? null : object.getCreator().getId())
                .registrations(userRegistrationMapper.toEntities(object.getRegistrations()))
                .build();
    }


}
