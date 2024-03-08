package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.port.InvitationService;
import cm.yowyob.auth.app.infrastructure.entities.InvitationEntity;
import cm.yowyob.auth.app.infrastructure.repositories.InvitationEntityRepository;
import cm.yowyob.auth.app.mappers.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    InvitationMapper invitationMapper;

    @Autowired
    InvitationEntityRepository invitationEntityRepository;

    @Override
    public Invitation save(Invitation invitation) {

        InvitationEntity invitationEntity =
                invitationEntityRepository.save(invitationMapper.toEntity(invitation));

        return invitationMapper.toObject(invitationEntity);
    }

    @Override
    public Optional<Invitation> getByInvitationId(String invitationId) {
        return invitationEntityRepository.findById(invitationId).map(invitationMapper::toObject);
    }


}
