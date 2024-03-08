package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserState;
import cm.yowyob.auth.app.infrastructure.entities.users.UserStateEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;

public class UserStateMapper extends Mapper<UserState, UserStateEntity> {

    private static UserStateMapper userStateMapper;

    public static UserStateMapper getInstance() {

        if (userStateMapper == null)
            userStateMapper = new UserStateMapper();

        return userStateMapper;
    }


    @Override
    public UserState toObject(UserStateEntity entity) {

        if (entity == null)
            return null;

        return new UserState(entity.getStateId(),
                entity.getState(),
                entity.getReason(),
                entity.getInstant(),
                entity.getStateAuthorId() == null ? null: new UserId(entity.getStateAuthorId()));
    }


    @Override
    public UserStateEntity toEntity(UserState object) {

        if (object==null)
            return null;

        return new UserStateEntity(object.getId(),
                object.getState(),
                object.getReason(),
                object.getInstant(),
                object.getAuthor() == null ? null: object.getAuthor().getId());
    }
}
