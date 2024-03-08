package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.api.TenantCreationRequest;
import cm.yowyob.auth.app.domain.exceptions.TenantAlreadyExistsException;
import cm.yowyob.auth.app.domain.exceptions.TenantNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.domain.model.tenant.*;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.TenantService;
import cm.yowyob.auth.app.domain.port.UserService;

import cm.yowyob.auth.app.domain.util.SimpleCache;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
public class TenantManager {

    private final SimpleCache<UUID, Tenant> tenantCache = new SimpleCache<>(4);


    private TenantService tenantService;
    private UserService userService;

    private final ContactFactory contactFactory = ContactFactory.getInstance();


    public Optional<Tenant> getTenant(UUID tenantId) {

        return Optional.ofNullable(tenantCache.get(tenantId,
                        (_tenantId) -> tenantService.getTenant(_tenantId).orElse(null)
                )
        );

    }


    public Tenant enabledTenant(UUID tenantId)
            throws TenantNotFoundException {

        return getTenant(tenantId)
                .map(Tenant::activate)
                .map(this::save)
                .orElseThrow(TenantNotFoundException::new);
    }


    public Tenant disabledTenant(UUID tenantId)
            throws TenantNotFoundException {

        return getTenant(tenantId)
                .map(Tenant::deactivate)
                .map(this::save)
                .orElseThrow(TenantNotFoundException::new);
    }


    public Tenant createDefaultTenant(Organisation organisation,
                                      String issuer) throws TenantAlreadyExistsException {

        if (tenantService.getTenantByIssuer(issuer).isPresent()
        || tenantService.getTenant(Tenant.DEFAULT_TENANT_ID).isPresent()) {
            throw new TenantAlreadyExistsException();
        }

        Tenant tenant = Tenant.builder()
                .id(Tenant.DEFAULT_TENANT_ID)
                .organisation(organisation)
                .createdAt(Instant.now())
                .configured(false)
                .issuer(issuer)
                .enabled(false)
                .build();

        return save(tenant);
    }


    public Tenant createTenant(UserId userId, @NonNull TenantCreationRequest request)
            throws UserNotFoundException, TenantAlreadyExistsException {

        Organisation organisation = Organisation.builder()
                .icon(request.getIcon())
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .shortName(request.getShortName())
                .support(contactFactory.createEmailAddress(request.getSupportEmail()))
                .termOfUseUrl(request.getTermOfUseUrl())
                .privacyPolicyUrl(request.getPrivacyPolicyUrl())
                .build();

        return createTenant(userId, organisation, request.getIssuer());
    }


    protected Tenant createTenant(@NonNull UserId userId,
                               @NonNull Organisation organisation,
                               @NonNull String issuer)
            throws UserNotFoundException, TenantAlreadyExistsException {


        if (tenantService.getTenantByIssuer(issuer).isPresent()) {
            throw new TenantAlreadyExistsException();
        }

        User admin = userService.getByTenantIdAndUserId(Tenant.DEFAULT_TENANT_ID, userId)
                .filter(User::isActive)
                .filter(User::isSuperAdmin)
                .orElseThrow(UserNotFoundException::new);

        Tenant tenant = Tenant.builder()
                .id(UUID.randomUUID())
                .organisation(organisation)
                .createdAt(Instant.now())
                .configured(false)
                .creator(userId)
                .issuer(issuer)
                .enabled(false)
                .build();

        return save(tenant);
    }


    public Tenant updateTenantInfo(@NonNull UUID tenantId,
                                   @NonNull Organisation info)
            throws TenantNotFoundException {

        return getTenant(tenantId)
                .map(tenant -> tenant.updateInfo(info))
                .map(this::save)
                .orElseThrow(TenantNotFoundException::new);

    }


    public Tenant configureCaptcha(@NonNull UUID tenantId,
                                   @NonNull TenantCaptchaConfig captchaConfiguration)
            throws TenantNotFoundException {

        return getTenant(tenantId)
                .map(tenant -> tenant.withCaptchaConfiguration(captchaConfiguration))
                .map(this::save)
                .orElseThrow(TenantNotFoundException::new);

    }


    public Tenant configureRegistration(@NonNull UUID tenantId,
                                        @NonNull TenantRegistrationConfig registrationConfiguration)
            throws TenantNotFoundException {

        return getTenant(tenantId)
                .map(tenant -> tenant.withRegistrationConfiguration(registrationConfiguration))
                .map(this::save)
                .orElseThrow(TenantNotFoundException::new);

    }




    public Tenant save(Tenant tenant) {
        Tenant saved = tenantService.save(tenant);
        tenantCache.put(saved.getId(), saved);
        return saved;
    }


    public Tenant getDefault() {

        return getTenant(Tenant.DEFAULT_TENANT_ID).orElse(null);
    }


}
