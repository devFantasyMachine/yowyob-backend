package cm.yowyob.auth.app.domain.auth.login;


import cm.yowyob.auth.app.domain.auth.AuthenticationCredentials;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import lombok.Getter;


@Getter
public class LoginCredentials extends AuthenticationCredentials {

    private final String username;
    private final String password;

    public LoginCredentials(RequestDetails details, String username, String password) {
        super(details);
        this.username = username;
        this.password = password;
    }


    @Override
    public Object getId() {
        return username;
    }

    @Override
    public Object getChallenge() {
        return password;
    }


}
