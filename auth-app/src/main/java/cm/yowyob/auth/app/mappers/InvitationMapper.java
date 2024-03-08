package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.infrastructure.entities.InvitationEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InvitationMapper extends Mapper<Invitation, InvitationEntity> {

    private final ContactFactory contactFactory = ContactFactory.getInstance();

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;


    @Override
    public Invitation toObject(InvitationEntity entity) {
        return entity == null ? null :  new Invitation(
                entity.getInvitationId(),
                entity.getTenantId(),
                entity.getIssueAt(),
                entity.getExpireAt(),
                entity.getUsed(),
                entity.getAccepted(),
                entity.getExpireAt(),
                (EmailAddress) contactFactory.createEmailAddress(entity.getContact()),
                userMapper.toObject(entity.getSender()),
                userMapper.toObject(entity.getTarget()),
                entity.getReason(),
                groupMapper.toObject(entity.getGroup()));
    }



    @Override
    public InvitationEntity toEntity(Invitation object) {
        return object == null ? null : InvitationEntity.builder()
                .invitationId(object.getId())
                .target(userMapper.toEntity(object.getTarget()))
                .issueAt(object.getIssueAt())
                .contact(object.getEmailAddress().getValue())
                .contactType(object.getEmailAddress().getType())
                .expireAt(object.getExpireAt())
                .used(object.getUsed())
                .sender(userMapper.toEntity(object.getSender()))
                .reason(object.getReason())
                .accepted(object.getAccepted())
                .tenantId(object.getTenantId())
                .group(groupMapper.toEntity(object.getGroup()))
                .build();
    }



}
