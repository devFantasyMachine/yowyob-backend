package cm.yowyob.auth.app.domain.auth.invitation;

import cm.yowyob.auth.app.domain.auth.AuthErrors;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.handlers.InvitationManager;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.auth.Authenticator;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;

import java.util.Objects;






public class InvitationAuthenticator extends Authenticator<AcceptInvitationRequest> {

    private final InvitationManager invitationManager;


    public InvitationAuthenticator(InvitationManager invitationManager,
                                   DeviceManager deviceManager,
                                   SessionTokenGenerator sessionTokenGenerator,
                                   TenantManager tenantManager,
                                   UserManager userManager) {
        super(deviceManager, tenantManager, sessionTokenGenerator, userManager);
        this.invitationManager = Objects.requireNonNull(invitationManager);
    }



    @Override
    protected AuthenticationResult attemptAuthenticate(Tenant tenant, AcceptInvitationRequest challenge)
            throws AuthenticationException {

        try {

            InvitationResult result = invitationManager.acceptInvitation(challenge);

            return AuthenticationResult.builder()
                    .user(result.getUser())
                    .build();

        } catch (InvitationNotFoundException e) {
            throw new AuthenticationException(AuthErrors.INVITATION_NOT_FOUND);
        } catch (InvitationExpiredException e) {
            throw new AuthenticationException(AuthErrors.INVITATION_EXPIRED);
        } catch (InvalidChallengeException e) {
            throw new AuthenticationException(AuthErrors.CHALLENGE_NOT_VALID);
        } catch (UserNotFoundException e) {
            throw new AuthenticationException(AuthErrors.USER_NOT_FOUND);
        }


    }

}
