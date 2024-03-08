package cm.yowyob.auth.app.domain.api;


import cm.yowyob.auth.app.domain.auth.AuthenticationCredentials;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import lombok.Getter;


@Getter
public class ValidateRegistrationRequest extends AuthenticationCredentials {

    private final String verificationId;
    private final String verificationCode;

    public ValidateRegistrationRequest(RequestDetails details,
                                          String verificationId,
                                          String verificationCode) {
        super(details);
        this.verificationId = verificationId;
        this.verificationCode = verificationCode;
    }


    @Override
    public Object getId() {
        return verificationId;
    }

    @Override
    public Object getChallenge() {
        return verificationCode;
    }
}
