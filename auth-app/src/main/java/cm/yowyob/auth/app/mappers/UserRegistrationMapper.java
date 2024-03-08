package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.UserRegistration;
import cm.yowyob.auth.app.infrastructure.entities.users.UserRegistrationEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TreeSet;


@Component
public class UserRegistrationMapper extends Mapper<UserRegistration, UserRegistrationEntity> {


    @Autowired
    UserMapper userMapper;


    @Override
    public UserRegistration toObject(UserRegistrationEntity entity) {

        if (entity == null)
            return null;

        return UserRegistration.builder()
                .id(entity.getId())
                .user(userMapper.toObject(entity.getUser()))
                .applicationId(entity.getApplicationId())
                .insertInstant(entity.getInsertInstant())
                .authenticationToken(entity.getAuthenticationToken())
                .lastUpdateInstant(entity.getLastUpdateInstant())
                .lastLoginInstant(entity.getLastLoginInstant())
                .roles(entity.getRoles() == null ? null : new TreeSet<>(entity.getRoles()))
                .build();
    }



    @Override
    public UserRegistrationEntity toEntity(UserRegistration object) {

        if (object == null)
            return null;

        return UserRegistrationEntity.builder()
                .id(object.getId())
                .user(userMapper.toEntity(object.getUser()))
                .applicationId(object.getApplicationId())
                .insertInstant(object.getInsertInstant())
                .authenticationToken(object.getAuthenticationToken())
                .lastUpdateInstant(object.getLastUpdateInstant())
                .lastLoginInstant(object.getLastLoginInstant())
                .roles(object.getRoles())
                .build();
    }


}
