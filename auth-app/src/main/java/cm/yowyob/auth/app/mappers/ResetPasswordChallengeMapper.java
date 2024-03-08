package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.infrastructure.entities.ResetPasswordEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ResetPasswordChallengeMapper extends Mapper<ResetPasswordChallenge, ResetPasswordEntity> {

    private final ContactFactory contactFactory = ContactFactory.getInstance();

    @Autowired
    UserMapper userMapper;



    @Override
    public ResetPasswordChallenge toObject(ResetPasswordEntity entity) {
        return entity == null ? null :  new ResetPasswordChallenge(
                entity.getResetId(),
                entity.getTenantId(),
                entity.getIssueAt(),
                entity.getExpireAt(),
                entity.getUsed(),
                entity.getAccepted(),
                entity.getExpireAt(),
                userMapper.toObject(entity.getTarget())
        );
    }



    @Override
    public ResetPasswordEntity toEntity(ResetPasswordChallenge object) {
        return object == null ? null : ResetPasswordEntity.builder()
                .resetId(object.getId())
                .target(userMapper.toEntity(object.getTarget()))
                .issueAt(object.getIssueAt())
                .expireAt(object.getExpireAt())
                .used(object.getUsed())
                .accepted(object.getAccepted())
                .tenantId(object.getTenantId())
                .build();
    }



}
