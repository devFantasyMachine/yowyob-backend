package cm.yowyob.auth.app.domain.api;


import cm.yowyob.auth.app.domain.auth.AuthenticationCredentials;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import lombok.Getter;


@Getter
public class ValidateResetPasswordRequest extends AuthenticationCredentials {

    private final String verificationId;
    private final String password;

    public ValidateResetPasswordRequest(RequestDetails details,
                                        String verificationId,
                                        String password) {
        super(details);
        this.verificationId = verificationId;
        this.password = password;
    }


    @Override
    public Object getId() {
        return verificationId;
    }

    @Override
    public Object getChallenge() {
        return password;
    }
}
