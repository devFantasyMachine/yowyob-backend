/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.filters;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Slf4j
public final class EmailAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "email";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private final String emailParameter = "email";
    private final String passwordParameter = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/email/login", "POST");
    private boolean postOnly = true;

    public EmailAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public EmailAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String email = this.obtainEmailParameter(request);
            String password = this.obtainPasswordParameter(request);

            if(email == null || password == null)throw new AuthenticationServiceException("email and otpCode must not be null ");

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(email.trim(), password.trim());

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }



    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    public void withAuthenticationSuccessHandler(AuthenticationSuccessHandler handler) {
        setAuthenticationSuccessHandler(handler);
    }

    @Nullable
    private String obtainPasswordParameter(HttpServletRequest request) {
        return request.getParameter(this.getPasswordParameter());
    }

    @Nullable
    private String obtainEmailParameter(HttpServletRequest request) {
        return request.getParameter(this.getEmailParameter());
    }

    public String getEmailParameter() {
        return this.emailParameter;
    }

    public String getPasswordParameter() {
        return this.passwordParameter;
    }


}
