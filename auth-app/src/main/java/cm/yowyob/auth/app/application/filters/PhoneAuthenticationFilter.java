/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;


import cm.yowyob.letsgo.auth.service.application.authentication.UserDeviceAuthentication;
import cm.yowyob.letsgo.auth.service.domain.entities.registration.Device;
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
import org.springframework.util.Assert;


@Slf4j
public final class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/phone/login", "POST");
    private String phoneParameter = "phone";
    private String deviceIdParameter = "deviceId";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    public PhoneAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public PhoneAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String phoneNumber = this.obtainPhoneParameter(request);
            String password = this.obtainPasswordParameter(request);
            String deviceId = this.obtainDeviceIdParameter(request);

            if (phoneNumber == null || password == null)
                throw new AuthenticationServiceException("phone and password must not be null ");

            UserDeviceAuthentication authRequest = UserDeviceAuthentication.unauthenticated(phoneNumber.trim(), password.trim(), Device.builder()
                    .withDeviceId(deviceId)
                    .build());

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().attemptAuthenticate(authRequest);
        }
    }


    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    @Nullable
    private String obtainPasswordParameter(HttpServletRequest request) {
        return request.getParameter(this.getPasswordParameter());
    }

    @Nullable
    private String obtainPhoneParameter(HttpServletRequest request) {
        return request.getParameter(this.getPhoneParameter());
    }

    @Nullable
    private String obtainDeviceIdParameter(HttpServletRequest request) {
        return request.getParameter(this.deviceIdParameter);
    }

    public String getPhoneParameter() {
        return this.phoneParameter;
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "verificationIdParameter parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }

    public String getPasswordParameter() {
        return this.passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "otpCodeParameter parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void withAuthenticationSuccessHandler(AuthenticationSuccessHandler handler) {
        setAuthenticationSuccessHandler(handler);
    }
}
*/
