package cm.yowyob.auth.app.application.configurers.invitation;


//import cm.yowyob.auth.app.application.configurers.YowyobAuthenticationFilter;
import cm.yowyob.auth.app.domain.auth.invitation.InvitationAuthenticator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;


public class InvitationAuthenticationConfigurer extends AbstractHttpConfigurer<InvitationAuthenticationConfigurer, HttpSecurity> {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private InvitationAuthenticator authenticationProvider;


    public InvitationAuthenticationConfigurer authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }


    public InvitationAuthenticationConfigurer() {
        super();
    }


    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http);
        Assert.notNull(this.authenticationProvider, "authenticationProvider must not be null");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        AuthenticationManager authManager = http.getSharedObject(AuthenticationManager.class);

        //var invitationAuthenticationFilter = new InvitationAuthenticationFilter(authManager, authenticationProvider, authenticationSuccessHandler);
        //http.addFilterBefore(invitationAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
