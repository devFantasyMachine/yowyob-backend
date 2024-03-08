/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
public final class OTPAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final RequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher("/web/otp/verify", "POST");

    private static final String verificationIdParameter = "verificationId";
    private static final String deviceIdParameter = "deviceId";
    private static final String otpParameter = "verificationCode";
     private final UserHandler userHandler;


    public OTPAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserHandler userHandler,
                                   AuthenticationSuccessHandler authenticationSuccessHandler) {

        super(DEFAULT_REQUEST_MATCHER, authenticationManager);
        this.userHandler = Objects.requireNonNull(userHandler);

        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(OTPAuthenticationFilter::accessDenied);
    }


    @SneakyThrows
    private static void accessDenied(HttpServletRequest request, HttpServletResponse response, Exception authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\", \"message\": \" " + authException.getMessage()  + "\" }");
    }

    @Nullable
    private String obtainVerificationId(@NonNull HttpServletRequest request) {
        return request.getParameter(verificationIdParameter);
    }

    @Nullable
    private String obtainDeviceId(@NonNull HttpServletRequest request) {
        return request.getParameter(deviceIdParameter);
    }


    @Nullable
    private Integer obtainOtpCode(@NonNull HttpServletRequest request) {
        return request.getParameter(otpParameter) == null ? null : Integer.valueOf(request.getParameter(otpParameter).trim());
    }



    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws DeviceNotFoundException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String verificationId = this.obtainVerificationId(request);
            String deviceId = this.obtainDeviceId(request);
            Integer verificationCode = this.obtainOtpCode(request);

            if(verificationId == null || verificationCode == null)throw new AuthenticationServiceException("verificationId and otpCode must not be null ");


            DeviceAuthRequest deviceAuthRequest = DeviceAuthRequest.builder()
                    .verificationId(verificationId)
                    .verificationCode(verificationCode)
                    .deviceId(deviceId)
                    .build();

            Pair<User, Device> userAndDevice = userHandler.verifyVerificationCode(deviceAuthRequest);

            User user = userAndDevice.left();
            Device device = userAndDevice.right();

            UserDeviceAuthentication userDeviceAuthentication = UserDeviceAuthentication.authenticated(user.getUserId(), null, getAuthorities(user), device);

            this.setDetails(request, userDeviceAuthentication);
            return userDeviceAuthentication;
        }
    }


    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {

        return user.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


}
*/
