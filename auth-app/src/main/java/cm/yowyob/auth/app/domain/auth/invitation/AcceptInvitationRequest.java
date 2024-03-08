package cm.yowyob.auth.app.domain.auth.invitation;

import cm.yowyob.auth.app.domain.auth.AuthenticationCredentials;
import cm.yowyob.auth.app.domain.model.RequestDetails;

public class AcceptInvitationRequest extends AuthenticationCredentials {

    private final String invitationId;


    public AcceptInvitationRequest(RequestDetails details, String invitationId) {
        super(details);
        this.invitationId = invitationId;
    }


    @Override
    public String getId() {
        return invitationId;
    }

    @Override
    public Object getChallenge() {
        return null;
    }
}
