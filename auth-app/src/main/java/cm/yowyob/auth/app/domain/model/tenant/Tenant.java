package cm.yowyob.auth.app.domain.model.tenant;

import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.user.UserId;


import lombok.*;

import java.time.Instant;
import java.util.*;


@Data
@Builder
@AllArgsConstructor
public class Tenant {

    public static UUID DEFAULT_TENANT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @NonNull
    private final UUID id;
    @NonNull
    private final String issuer;
    @NonNull
    private Organisation organisation;

    private Boolean enabled;
    private Boolean configured;


    @Builder.Default
    private EmailConfiguration emailConfiguration = new EmailConfiguration();
    @Builder.Default
    private TenantMultiFactorConfig multiFactorConfig = new TenantMultiFactorConfig();
    @Builder.Default
    private TenantPasswordConfig passwordConfig = new TenantPasswordConfig();
    @Builder.Default
    private TenantUsernameConfig usernameConfig = new TenantUsernameConfig();
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private TimeBasedDeletePolicy userDeletePolicy = new TimeBasedDeletePolicy();
    @Builder.Default
    private TenantCaptchaConfig captchaConfiguration = new TenantCaptchaConfig();
    @Builder.Default
    private TenantRateLimitConfig rateLimitConfig = new TenantRateLimitConfig();
    @Builder.Default
    private ExternalIdentifierConfig externalIdentifierConfig = new ExternalIdentifierConfig();
    @Builder.Default
    private TenantRegistrationConfig registrationConfig = new TenantRegistrationConfig();

    @Builder.Default
    private TenantSessionConfig sessionConfig = new TenantSessionConfig();



    private UserId creator;


    public boolean isCreator(UserId userId) {
        return creator != null && creator.equals(userId);
    }

    public Tenant updateInfo(Organisation info) {
        this.organisation = Objects.requireNonNull(info);
        return this;
    }

    public Tenant activate() {
        this.enabled = true;
        return this;
    }

    public Tenant deactivate() {
        this.enabled = false;
        return this;
    }

    public Tenant withCaptchaConfiguration(TenantCaptchaConfig captchaConfiguration) {
        this.captchaConfiguration = captchaConfiguration;
        return this;
    }

    public Tenant withRegistrationConfiguration(TenantRegistrationConfig registrationConfiguration) {

        this.registrationConfig = registrationConfiguration;
        return this;
    }




}


