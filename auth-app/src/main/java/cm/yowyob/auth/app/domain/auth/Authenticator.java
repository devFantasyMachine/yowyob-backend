package cm.yowyob.auth.app.domain.auth;


import cm.yowyob.auth.app.domain.TwoFactorAuthChallenge;
import cm.yowyob.auth.app.domain.api.DeviceInfo;
import cm.yowyob.auth.app.domain.exceptions.AuthenticationException;
import cm.yowyob.auth.app.domain.exceptions.DeviceLockedException;
import cm.yowyob.auth.app.domain.exceptions.DeviceNotFoundException;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.RequestDetails;
import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.code.HexCode;
import cm.yowyob.auth.app.domain.model.code.HexCodeGenerator;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.tenant.TenantSessionConfig;
import cm.yowyob.auth.app.domain.model.user.RequireAction;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserTwoFactorConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Slf4j()
public abstract class Authenticator<T extends AuthenticationCredentials> {

    protected final DeviceManager deviceManager;
    protected final UserManager userManager;
    protected final SessionTokenGenerator sessionTokenGenerator;
    protected final TenantManager tenantManager;


    public Authenticator(DeviceManager deviceManager,
                         TenantManager tenantManager,
                         SessionTokenGenerator sessionTokenGenerator,
                         UserManager userManager) {
        this.deviceManager = Objects.requireNonNull(deviceManager);
        this.sessionTokenGenerator = Objects.requireNonNull(sessionTokenGenerator);
        this.tenantManager = Objects.requireNonNull(tenantManager);
        this.userManager = Objects.requireNonNull(userManager);
    }



    protected abstract AuthenticationResult attemptAuthenticate(Tenant tenant, T challenge)
            throws AuthenticationException;



    public AuthenticationResult authenticate(T challenge) throws AuthenticationException {

        RequestDetails requestDetails = challenge.getDetails();
        DeviceInfo deviceInfo = requestDetails.getDeviceInfo();

        log.info("new authentication request with details {}", requestDetails);

        if (deviceInfo == null) {
            log.error("fatal error: device info not found");
            throw new AuthenticationException("device info required");
        }

        Tenant tenant = tenantManager.getTenant(requestDetails.getTenantId())
                .orElseThrow(() -> new AuthenticationException(AuthErrors.TENANT_NOT_FOUND));

        log.info("attempt authenticate with {}", this.getClass());
        AuthenticationResult result = attemptAuthenticate(tenant, challenge);
        log.info("successfully authenticated");

        User user = result.getUser();
        Set<RequireAction> requireActions = user.getRequireActions();

        if (!requireActions.isEmpty()) {

            if (requireActions.contains(RequireAction.RESET_PASSWORD)) {

                Integer setupPasswordIdTimeToLiveInSeconds
                        = tenant.getExternalIdentifierConfig().getSetupPasswordIdTimeToLiveInSeconds();

                HexCodeGenerator hexCodeGenerator = new HexCodeGenerator();
                HexCode hexCode = hexCodeGenerator.generate();

                ResetPasswordChallenge resetPasswordChallenge =
                        new ResetPasswordChallenge(
                                hexCode.getValue(),
                                tenant.getId(),
                                Instant.now()
                                        .plus(Duration.ofSeconds(setupPasswordIdTimeToLiveInSeconds)),
                                user
                        );

                userManager.save(resetPasswordChallenge);

                return AuthenticationResult.builder()
                        .verificationId(hexCode.getValue())
                        .requireAction(RequireAction.RESET_PASSWORD)
                        .user(user)
                        .build();

            }

        }


        UserTwoFactorConfiguration twoFactorConfiguration = user.getTwoFactorConfiguration();

        if (twoFactorConfiguration != null && twoFactorConfiguration.isEnabled()) {

            TwoFactorAuthChallenge twoFactorAuthChallenge = twoFactorConfiguration.getLastUsedMethod();

            // TODO: 25/01/2024 implement two factor auth

            return AuthenticationResult.from(result)
                    .verificationId(twoFactorAuthChallenge.getId())
                    .requireAction(RequireAction.VALIDATE_2FA)
                    .build();
        }


        Device device;

        try {

            UUID deviceId = deviceInfo.getDeviceId();
            if (deviceId != null) {

                try {

                    device = deviceManager.getDeviceByUserIdAndDeviceId(deviceId, user.getUserId());
                    log.info("old device {}", device);

                } catch (DeviceNotFoundException e) {
                    throw new AuthenticationException(AuthErrors.FATAL_ERROR);
                }
            } else {

                log.info("register user device");
                device = deviceManager.addDevice(
                        user.getUserId(),
                        requestDetails.getTenantId(),
                        requestDetails.getUserAgent(),
                        requestDetails.getUserIp(),
                        deviceInfo.getDeviceType(),
                        deviceInfo.getDeviceName(),
                        deviceInfo.getDeviceModel(),
                        deviceInfo.getDeviceManufacturer()
                );

                log.info("user device {}", device);
            }

            log.info("create session");

            TenantSessionConfig sessionConfig = tenant.getSessionConfig();

            Instant issuedAt = Instant.now();
            Instant expiresAt = issuedAt.plus(sessionConfig.getSessionDuration());

            SessionTokenContext sessionTokenContext = SessionTokenContext.builder()
                    .user(user)
                    .device(device)
                    .expiresAt(expiresAt)
                    .issuedAt(issuedAt)
                    .issuer(tenant.getIssuer())
                    .build();

            String sessionToken = sessionTokenGenerator.generateSessionToken(sessionTokenContext);

            AuthenticationResult.AuthenticationResultBuilder resultBuilder =
                    AuthenticationResult.from(result)
                            .sessionToken(sessionToken)
                            .sessionTokenIssuedAt(issuedAt)
                            .sessionTokenExpireAt(expiresAt)
                            .deviceId(device.getDeviceId());

            if (sessionConfig.isUseRememberToken()) {

                log.info("remember session");

                Instant rememberTokenExpiresAt = issuedAt.plus(sessionConfig.getRememberTokenDuration());

                SessionTokenContext rememberContext = SessionTokenContext.builder()
                        .user(user)
                        .isRememberToken(true)
                        .device(device)
                        .expiresAt(rememberTokenExpiresAt)
                        .issuer(tenant.getIssuer())
                        .issuedAt(issuedAt)
                        .build();

                String rememberToken = sessionTokenGenerator.generateSessionToken(rememberContext);
                deviceManager.refresh(device, rememberToken, rememberTokenExpiresAt);

                resultBuilder.rememberToken(rememberToken)
                        .rememberTokenExpireAt(rememberTokenExpiresAt);

            }

            log.info("successfully create session");

            return resultBuilder
                    .build();

        } catch (DeviceLockedException e) {
            log.error("fatal error {}", e.getMessage());
            throw new AuthenticationException(AuthErrors.LOCKED_DEVICE);
        }

    }


}
