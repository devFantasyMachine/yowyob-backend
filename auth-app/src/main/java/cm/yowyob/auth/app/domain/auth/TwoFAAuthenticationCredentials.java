package cm.yowyob.auth.app.domain.auth;

import cm.yowyob.auth.app.domain.model.RequestDetails;

public class TwoFAAuthenticationCredentials extends AuthenticationCredentials {


    protected TwoFAAuthenticationCredentials(RequestDetails details) {
        super(details);
    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public Object getChallenge() {
        return null;
    }
}
