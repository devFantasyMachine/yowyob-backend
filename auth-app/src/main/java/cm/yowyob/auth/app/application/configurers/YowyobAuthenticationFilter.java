/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.configurers;


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

import java.time.ZonedDateTime;


@Slf4j
public final class YowyobAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/web/login", "POST");

    private static final String usernameParameter = "username";
    private static final String passwordParameter = "password";
    private static final String deviceIdParameter = "deviceId";
    private static final String deviceManufacturerParameter = "deviceManufacturer";
    private static final String deviceModelParameter = "deviceModel";
    private static final String userAgentParameter = "userAgent";
    private static final String deviceOsParameter = "deviceOs";


    private boolean postOnly = true;


    public YowyobAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String username = this.obtainUsernameParameter(request);
            String password = this.obtainPasswordParameter(request);
            String deviceId = this.obtainDeviceIdParameter(request);
            String deviceOs = this.obtainDeviceOsParameter(request);
            String userAgent = this.obtainUserAgentParameter(request);
            String deviceModel = this.obtainDeviceModelParameter(request);
            String deviceManufacturer = this.obtainDeviceManufacturerParameter(request);

            if (username == null || password == null || userAgent == null)
                throw new AuthenticationServiceException("phone, userAgent and password must not be null ");

            UserDeviceAuthentication authRequest = UserDeviceAuthentication.unauthenticated(username.trim(), password.trim(), Device.builder()
                    .withDeviceId(deviceId)
                            .withUserAgent(userAgent)
                            .withDeviceName(userAgent)
                            .withLastSeen(ZonedDateTime.now())
                            .withDeviceModel(deviceModel)
                            .withDeviceManufacturer(deviceManufacturer)
                            .withDevicePlatform(DevicePlatform.resolve(deviceOs))
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
        return request.getParameter(YowyobAuthenticationFilter.passwordParameter);
    }

    @Nullable
    private String obtainUsernameParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.usernameParameter);
    }

    @Nullable
    private String obtainDeviceManufacturerParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.deviceManufacturerParameter);
    }

    @Nullable
    private String obtainDeviceModelParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.deviceModelParameter);
    }

    @Nullable
    private String obtainUserAgentParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.userAgentParameter);
    }

    @Nullable
    private String obtainDeviceOsParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.deviceOsParameter);
    }


    @Nullable
    private String obtainDeviceIdParameter(HttpServletRequest request) {
        return request.getParameter(YowyobAuthenticationFilter.deviceIdParameter);
    }

    public void withAuthenticationSuccessHandler(AuthenticationSuccessHandler handler) {
        setAuthenticationSuccessHandler(handler);
    }
}
*/
