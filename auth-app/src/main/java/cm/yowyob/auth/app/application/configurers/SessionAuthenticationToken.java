package cm.yowyob.auth.app.application.configurers;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class SessionAuthenticationToken extends AbstractAuthenticationToken {


    private final String token;
    private final UserWithDevice userWithDevice;


    public SessionAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String token, UserWithDevice userWithDevice) {
        super(authorities);
        this.token = token;
        this.userWithDevice = userWithDevice;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userWithDevice;
    }





}
