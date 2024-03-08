/*
 * Copyright (c) 2023. Create by Yowyob
 */


package cm.yowyob.auth.app.application.configurers.invitation;


import cm.yowyob.auth.app.application.configurers.UserAuthentication;
import cm.yowyob.auth.app.domain.auth.invitation.InvitationAuthenticator;
import cm.yowyob.auth.app.domain.auth.invitation.AcceptInvitationRequest;
import cm.yowyob.auth.app.domain.exceptions.AuthenticationException;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Objects;



@Slf4j
public final class InvitationAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final RequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher("/web/otp/verify", "POST");

    private static final String invitationIdParameter = "invitationId";
    private static final String invitationChallengeParameter = "invitationChallenge";
    private static final String tenantShortNameParameter = "tenantShortName";


    private final InvitationAuthenticator authenticationProvider;


    public InvitationAuthenticationFilter(AuthenticationManager authenticationManager,
                                          InvitationAuthenticator authenticationProvider,
                                          AuthenticationSuccessHandler authenticationSuccessHandler) {

        super(DEFAULT_REQUEST_MATCHER, authenticationManager);
        this.authenticationProvider = Objects.requireNonNull(authenticationProvider);

        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(InvitationAuthenticationFilter::accessDenied);
    }


    @SneakyThrows
    private static void accessDenied(HttpServletRequest request, HttpServletResponse response, Exception authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\", \"message\": \" " + authException.getMessage()  + "\" }");
    }

    @Nullable
    private String obtainInvitationId(@NonNull HttpServletRequest request) {
        return request.getParameter(invitationIdParameter);
    }

    @Nullable
    private String obtainInvitationChallenge(@NonNull HttpServletRequest request) {
        return request.getParameter(invitationChallengeParameter);
    }


    @Nullable
    private String obtainTenantShortName(@NonNull HttpServletRequest request) {
        return request.getParameter(tenantShortNameParameter);
    }



    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String invitationId = this.obtainInvitationId(request);
            String invitationChallenge = this.obtainInvitationChallenge(request);
            String shortName = this.obtainTenantShortName(request);

            if(invitationId == null || invitationChallenge == null)throw new AuthenticationServiceException("verificationId and otpCode must not be null ");


            AcceptInvitationRequest challenge = new AcceptInvitationRequest(
                    null,
                    invitationId
            );


            try {

                AuthenticationResult authenticationResult = authenticationProvider.authenticate(challenge);

                UserAuthentication userAuthentication = new UserAuthentication(authenticationResult);
                this.setDetails(request, userAuthentication);
                return userAuthentication;

            } catch (AuthenticationException e) {
                throw new AuthenticationServiceException(e.getMessage());
            }

        }
    }


    private void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }



}

