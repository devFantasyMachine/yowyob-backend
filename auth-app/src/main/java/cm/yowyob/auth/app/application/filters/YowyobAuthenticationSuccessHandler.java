/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;


import cm.yowyob.auth.app.domain.auth.DeviceTokenGenerator;
import cm.yowyob.auth.app.domain.port.DeviceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class YowyobAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final YowyobAuthConfig yowyobAuthConfig;
    private final DeviceService deviceService;
    private final DeviceTokenGenerator deviceJwtGenerator;
    private final RequestCache requestCache;


    public YowyobAuthenticationSuccessHandler(YowyobAuthConfig yowyobAuthConfig, DeviceService deviceService, DeviceTokenGenerator deviceJwtGenerator) {
        this.yowyobAuthConfig = Objects.requireNonNull(yowyobAuthConfig);
        this.deviceService = deviceService;
        this.deviceJwtGenerator = Objects.requireNonNull(deviceJwtGenerator);
        this.requestCache = new CookieRequestCache();
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("enter success authentication");

        var auth = (UserDeviceAuthentication) authentication;
        // principal is a security user instance
        internalSuccessHandler(request, response, auth);

        try {

            auth.getDevice().setUserId(auth.getUserId());
            auth.getDevice().setLastSeen(ZonedDateTime.now());
            deviceService.save(auth.getDevice());

        } catch (Exception e) {
            log.error("failure operation: save device ", e);
        }

    }


    public void internalSuccessHandler(HttpServletRequest request, HttpServletResponse response, UserDeviceAuthentication auth) throws IOException {

        TokenGeneratorContext context = TokenGeneratorContext.builder()
                .roles(auth.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .scopes(Set.of())
                .deviceId(auth.getDeviceId())
                .deviceType(DevicePlatform.WEB.getTagValue())
                .userId(auth.getPrincipal().toString())
                .build();

        String accessToken = deviceJwtGenerator.generateAccessToken(context);

        var jwt = JwtTokenUtil.generateToken(auth.getPrincipal().toString(),
                auth.getDeviceId(),
                Set.of("USER"),
                yowyobAuthConfig.getIssuer(),
                yowyobAuthConfig.getJwtSecretKey(),
                yowyobAuthConfig.getExpireDuration());

        byte[] cookie = EncryptionHelper.encrypt(yowyobAuthConfig.getCookieSecretKey(), jwt.getBytes(StandardCharsets.UTF_8));

        String rememberToken = Base64.getUrlEncoder().encodeToString(cookie);

        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        String redirectUri = null;

        if (savedRequest == null || savedRequest.getRedirectUrl() == null) {

            redirectUri = yowyobAuthConfig.getDefaultTargetUrl();
        }
        else {

            redirectUri = savedRequest.getRedirectUrl();
        }

        response.setHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateCookie(YowyobAuthConfig.REMEMBER_ME_COOKIE_NAME, rememberToken, Duration.ofDays(yowyobAuthConfig.getExpireDuration()) , request));

        response.getWriter().write("{ \"access_token\": \""+ accessToken + "\", \"token_type\": \"Bearer\", \"redirect_uri\": \" " + redirectUri + "\" }");
        response.setStatus(200);
    }



}
*/
