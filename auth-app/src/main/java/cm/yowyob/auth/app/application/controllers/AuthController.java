package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.*;
import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.api.DeviceInfo;
import cm.yowyob.auth.app.domain.api.ValidateRegistrationRequest;
import cm.yowyob.auth.app.domain.api.ValidateResetPasswordRequest;
import cm.yowyob.auth.app.domain.auth.invitation.InvitationAuthenticator;
import cm.yowyob.auth.app.domain.auth.invitation.AcceptInvitationRequest;
import cm.yowyob.auth.app.domain.auth.login.LoginAuthenticator;
import cm.yowyob.auth.app.domain.auth.login.LoginCredentials;
import cm.yowyob.auth.app.domain.auth.resetpassword.ResetPasswordAuthenticator;
import cm.yowyob.auth.app.domain.exceptions.AuthenticationException;
import cm.yowyob.auth.app.domain.auth.registration.RegistrationAuthenticator;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import cm.yowyob.auth.app.domain.model.device.DeviceType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    RegistrationAuthenticator registrationAuthenticator;


    @Autowired
    InvitationAuthenticator invitationAuthenticator;


    @Autowired
    LoginAuthenticator loginAuthenticator;


    @Autowired
    ResetPasswordAuthenticator resetPasswordAuthenticator;


    @PostMapping("/invitation/accept")
    public AuthenticationResultDTO acceptInvitation(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestParam("token") String token,
            HttpServletRequest httpServletRequest)
            throws AuthenticationException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);

        RequestDetails details = RequestDetails.builder()
                .userAgent(userAgent)
                .tenantId(tenantId)
                .userIp(ipAddress)
                .deviceInfo(DeviceInfo.builder()
                        .deviceType(DeviceType.DESKTOP)
                        .build()
                )
                .build();

        AcceptInvitationRequest acceptInvitationRequest
                = new AcceptInvitationRequest(
                details,
                token
        );

        AuthenticationResult result = invitationAuthenticator.authenticate(acceptInvitationRequest);

        return AuthenticationResultDTO.from(result);

    }


    @PostMapping("/login")
    public AuthenticationResultDTO login(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid LoginCredentialsDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws AuthenticationException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);
        UUID deviceId = HttpHelper.getDeviceId(httpServletRequest);

        RequestDetails details = RequestDetails.builder()
                .userAgent(userAgent)
                .tenantId(tenantId)
                .userIp(ipAddress)
                .deviceInfo(DeviceInfo.builder()
                        .deviceType(DeviceType.DESKTOP)
                        .deviceId(deviceId)
                        .build()
                )
                .build();

        LoginCredentials loginCredentials = new LoginCredentials(
                details,
                requestDTO.getUsername(),
                requestDTO.getPassword()
        );

        AuthenticationResult result = loginAuthenticator.authenticate(loginCredentials);

        return AuthenticationResultDTO.from(result);

    }


    @PostMapping("/verify-email")
    public ResponseEntity<AuthenticationResultDTO> verifyEmail(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid VerifyEmailRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws AuthenticationException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);

        RequestDetails details = RequestDetails.builder()
                .userAgent(userAgent)
                .tenantId(tenantId)
                .userIp(ipAddress)
                .deviceInfo(DeviceInfo.builder()
                        .deviceType(DeviceType.DESKTOP)
                        .build()
                )
                .build();

        ValidateRegistrationRequest challenge =
                new ValidateRegistrationRequest(details,
                        requestDTO.getVerificationId(),
                        requestDTO.getVerificationCode());

        AuthenticationResult result = registrationAuthenticator.authenticate(challenge);

        return ResponseEntity.ok(AuthenticationResultDTO.from(result));

    }


    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResultDTO> resetPassword(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid ResetPasswordRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws AuthenticationException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);

        RequestDetails details = RequestDetails.builder()
                .userAgent(userAgent)
                .tenantId(tenantId)
                .userIp(ipAddress)
                .deviceInfo(DeviceInfo.builder()
                        .deviceType(DeviceType.DESKTOP)
                        .build()
                )
                .build();

        ValidateResetPasswordRequest challenge =
                new ValidateResetPasswordRequest(details,
                        requestDTO.getVerificationId(),
                        requestDTO.getPassword());

        AuthenticationResult result = resetPasswordAuthenticator.authenticate(challenge);

        return ResponseEntity.ok(AuthenticationResultDTO.from(result));

    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleError(AuthenticationException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\": \"" + ex.getMessage() + "\", \"message\": \"" + ex.getMessage() + "\" }");
    }


}
