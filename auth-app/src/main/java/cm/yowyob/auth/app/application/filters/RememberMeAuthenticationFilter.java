/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;

import cm.yowyob.letsgo.auth.service.application.authentication.UserDeviceAuthentication;
import cm.yowyob.letsgo.auth.service.utils.CookieHelper;
import cm.yowyob.letsgo.auth.service.utils.EncryptionHelper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


public final class RememberMeAuthenticationFilter extends OncePerRequestFilter {

    private final YowyobAuthenticationSuccessHandler authenticationSuccessHandler;

    private final SecurityContextHolderStrategy securityContextHolderStrategy
            = SecurityContextHolder.getContextHolderStrategy();

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final YowyobAuthConfig cookieConfig;


    public RememberMeAuthenticationFilter(YowyobAuthConfig cookieConfig, YowyobAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.cookieConfig = cookieConfig;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Optional<String> letsgoCookie =
                CookieHelper.retrieve(request.getCookies(), YowyobAuthConfig.REMEMBER_ME_COOKIE_NAME);

        if (letsgoCookie.isEmpty()) {
            chain.doFilter(request, response);
        } else {

            Authentication authentication =
                    this.securityContextHolderStrategy.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()){

                chain.doFilter(request, response);
            }

            try {

                String cookieValue = letsgoCookie.get();

                // decrypt cookie
                var decodedCookie = Base64.getUrlDecoder().decode(cookieValue);
                byte[] cookie = EncryptionHelper.decrypt(cookieConfig.getCookieSecretKey(), decodedCookie);
                String jwt = new String(cookie, StandardCharsets.UTF_8);
                // end decrypt

                Claims claims =
                        JwtTokenUtil.validateToken(cookieConfig.getJwtSecretKey(), jwt);

                if (claims.get(YowyobAuthConfig.AUTHORITIES_CLAIM_NAME) != null) {

                    setUpAuthentication(claims, request, response);

                } else {

                    SecurityContextHolder.clearContext();
                }

                chain.doFilter(request, response);

            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {

                String expiredCookie = CookieHelper.generateExpiredCookie(YowyobAuthConfig.REMEMBER_ME_COOKIE_NAME, request);

                response.setHeader(HttpHeaders.SET_COOKIE, expiredCookie);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }

        }

    }


    private void setUpAuthentication(Claims claims, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String deviceId = (String) claims.get(YowyobAuthConfig.DEVICE_ID_CLAIM_NAME);

        List<SimpleGrantedAuthority> grantedAuthorities =
                JwtTokenUtil.getGrantedAuthorities(claims);

        UserDeviceAuthentication authentication =
                new UserDeviceAuthentication(claims.getSubject(), null, grantedAuthorities,  claims.getSubject(), deviceId);

        authentication.setDetails(this.authenticationDetailsSource.buildDetails(request));
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);

        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

    }


}*/
