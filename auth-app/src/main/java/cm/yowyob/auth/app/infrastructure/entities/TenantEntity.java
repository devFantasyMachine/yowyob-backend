package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.Requirable;
import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.captcha.CaptchaMethod;
import cm.yowyob.auth.app.domain.model.tenant.*;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.infrastructure.entities.tenant.*;
import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantEntity {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @OneToOne(optional = false)
    private OrganisationEntity organisation;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private String issuer;

    private String creator;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean configured;

    @Embedded
    private EmailConfigurationEntity emailConfiguration;

    // TODO: 19/01/2024
    //private TenantMultiFactorConfig multiFactorConfig;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<RateLimitedRequestConfigurationEntity> rateLimitedRequestConfiguration;

    @Embedded
    private ExternalIdentifierConfigEntity externalIdentifierConfig;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<CaptchaMethod> captchaMethods;
    private Boolean captchaThreshold;
    private Boolean captchaEnabled;

    @Embedded
    private UsernamePolicyEntity usernamePolicyEntity;

    @Embedded
    private PasswordValidationRulesEntity passwordValidationRules;
    private Boolean maximumPasswordAgeEnabled;
    private Integer maximumPasswordAgeDays;

    private Integer userDeletePolicyEnabled;
    private Integer userDeletePolicyNumberOfDaysToRetain;

    @Embedded
    private TenantRegistrationConfigEntity tenantRegistrationConfig;

    private Long sessionDuration;

    private Long rememberTokenDuration;

    private Boolean useRememberToken;





}
