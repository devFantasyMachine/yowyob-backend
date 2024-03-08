package cm.yowyob.auth.app.application.configurers;

import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserAuthentication extends AbstractAuthenticationToken implements Serializable {

    private final AuthenticationResult authenticationResult;

    public UserAuthentication(AuthenticationResult authenticationResult) {
        super(authenticationResult == null ? null: getGrantedAuthorities(authenticationResult.getUser().getRoles()));
        this.authenticationResult = authenticationResult;
    }


    @Override
    public String getName() {
        return authenticationResult.getUser().getUserId().getId();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticationResult != null;
    }

    @Override
    public Object getCredentials() {
        return authenticationResult.getSessionToken();
    }


    @Override
    public Object getPrincipal() {
        return authenticationResult;
    }


    public static List<SimpleGrantedAuthority> getGrantedAuthorities(Collection<String> authorities) {

         return authorities
                 .stream()
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList());
    }

}
