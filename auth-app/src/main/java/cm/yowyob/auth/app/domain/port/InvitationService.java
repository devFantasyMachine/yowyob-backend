package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.Invitation;

import java.util.Optional;

public interface InvitationService {
    Invitation save(Invitation invitation);

    Optional<Invitation> getByInvitationId(String invitationId);
}
