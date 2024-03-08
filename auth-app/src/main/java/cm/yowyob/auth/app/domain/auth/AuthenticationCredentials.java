package cm.yowyob.auth.app.domain.auth;


import cm.yowyob.auth.app.domain.api.BaseRequest;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class AuthenticationCredentials extends BaseRequest {

    protected AuthenticationCredentials(RequestDetails details) {
        super(details);
    }

    public abstract Object getId();
    public abstract Object getChallenge();

}
