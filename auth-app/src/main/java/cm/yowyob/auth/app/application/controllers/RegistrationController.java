package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.RegistrationRequestDTO;
import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.api.RegistrationRequest;
import cm.yowyob.auth.app.domain.api.RegistrationResponse;
import cm.yowyob.auth.app.domain.exceptions.TenantNotEnabledException;
import cm.yowyob.auth.app.domain.exceptions.TenantNotFoundException;
import cm.yowyob.auth.app.domain.handlers.RegistrationManager;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.UUID;



@RestController
@RequestMapping("/registrations")
public class RegistrationController {


    private final RegistrationManager registrationManager;

    @Autowired
    public RegistrationController(RegistrationManager registrationManager) {
        this.registrationManager = registrationManager;
    }


    @PostMapping("/email")
    public ResponseEntity<RegistrationResponse> registerByEmail(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid RegistrationRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws TenantNotFoundException, TenantNotEnabledException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);

        RegistrationResponse response = register(userAgent,
                ipAddress,
                tenantId,
                requestDTO,
                LoginMethod.EMAIL_PASSWORD);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/username")
    public ResponseEntity<RegistrationResponse> registerByUserName(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid RegistrationRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws TenantNotFoundException, TenantNotEnabledException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);

        RegistrationResponse response = register(userAgent,
                ipAddress,
                tenantId,
                requestDTO,
                LoginMethod.USERNAME_PASSWORD);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/phone-password")
    public ResponseEntity<RegistrationResponse> registerByPhone(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid RegistrationRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws TenantNotFoundException, TenantNotEnabledException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);


        RegistrationResponse response = register(userAgent,
                ipAddress,
                tenantId,
                requestDTO,
                LoginMethod.PHONE_PASSWORD);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/phone-otp")
    public ResponseEntity<RegistrationResponse> registerByPhoneOtp(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestBody @Valid RegistrationRequestDTO requestDTO,
            HttpServletRequest httpServletRequest)
            throws TenantNotFoundException, TenantNotEnabledException {

        String ipAddress = HttpHelper.getIpAddress(httpServletRequest);
        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);


        RegistrationResponse response = register(userAgent,
                ipAddress,
                tenantId,
                requestDTO,
                LoginMethod.PHONE_OTP);

        return ResponseEntity.ok(response);
    }



    private RegistrationResponse register(String userAgent,
                                          String ipAddress,
                                          UUID tenantId,
                                          @NonNull RegistrationRequestDTO requestDTO,
                                          LoginMethod method)
            throws TenantNotFoundException, TenantNotEnabledException {

        RequestDetails details = RequestDetails.builder()
                .userAgent(userAgent)
                .tenantId(tenantId)
                .userIp(ipAddress)
                .build();

        RegistrationRequest request = new RegistrationRequest(
                details,
                requestDTO.getUsername(),
                requestDTO.getPassword(),
                requestDTO.getEmail(),
                requestDTO.getPhoneNumber(),
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getGender(),
                requestDTO.getBirthdate(),
                requestDTO.getTimezone() == null ? null : ZoneId.of(requestDTO.getTimezone()),
                method
        );

        return registrationManager.registerTenantUser(request);
    }












}
