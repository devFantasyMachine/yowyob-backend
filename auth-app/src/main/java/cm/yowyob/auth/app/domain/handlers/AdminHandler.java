/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.model.InvitationReason;
import cm.yowyob.auth.app.domain.model.code.Base64String;
import cm.yowyob.auth.app.domain.model.code.Base64StringGenerator;
import cm.yowyob.auth.app.domain.model.code.HexCode;
import cm.yowyob.auth.app.domain.model.code.HexCodeGenerator;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserFactory;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserState;
import cm.yowyob.auth.app.domain.port.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Slf4j
public class AdminHandler {


    public static final Duration ADMIN_INVITATION_EXPIRATION_DURATION = Duration.ofDays(10);
    public static final Duration DEVELOPER_INVITATION_EXPIRATION_DURATION = Duration.ofDays(10);


    private final InvitationManager invitationManager;
    private final UserService userService;
    private final PasswordHelper passwordHelper;
    private final TenantManager tenantManager;


    private final UserFactory userFactory = new UserFactory();
    private final Base64StringGenerator base64StringGenerator = new Base64StringGenerator(50);





    public AdminHandler(PasswordHelper passwordHelper,
                        InvitationManager invitationManager,
                        UserService userService,
                        TenantManager tenantManager) {
        this.passwordHelper = Objects.requireNonNull(passwordHelper);
        this.invitationManager = invitationManager;
        this.userService = Objects.requireNonNull(userService);
        this.tenantManager = tenantManager;
    }


    public User getAdmin(@NonNull Contact contact, @NonNull UUID tenantId) throws UserNotFoundException {

        Optional<User> optionalUser = Optional.empty();

        if (contact.isEmail())
            optionalUser = userService.getByTenantIdAndEmail(tenantId, contact.getValue());

        if (contact.isPhoneNumber())
            optionalUser = userService.getByTenantIdAndPhone(tenantId, contact.getValue());

        return optionalUser
                .filter(User::isAdmin)
                .orElseThrow(UserNotFoundException::new);
    }


    public User createSuperAdmin(UUID tenantId, @NonNull EmailAddress emailAddress)
            throws UserAlreadyExistException, TenantNotFoundException {

        Tenant tenant = tenantManager.getTenant(tenantId)
                .orElseThrow(TenantNotFoundException::new);

        if (userService.exists(tenantId, emailAddress))
            throw new UserAlreadyExistException();

        log.info("attempt to create super admin with emailAddress {}", emailAddress);

        String encodedPassword = passwordHelper.randomPassword();

        User superAdmin = userFactory.createSuperAdmin(tenantId, emailAddress, encodedPassword);
        User savedAdmin = userService.save(superAdmin);

        log.info("super admin created successfully");
        log.info("create and send invitation");

        Base64String hexCode = base64StringGenerator.generate();

        Invitation invitation = new Invitation(
                hexCode.getValue(),
                tenantId,
                Instant.now().plus(ADMIN_INVITATION_EXPIRATION_DURATION),
                emailAddress,
                superAdmin,
                savedAdmin,
                InvitationReason.VALIDATE_ADMIN_ACCOUNT,
                null);

        Invitation savedInvitation = invitationManager.save(invitation);
        invitationManager.sendInvitation(savedInvitation, tenant);
        return savedAdmin;
    }



    public User createAdmin(UUID tenantId,
                            @NonNull EmailAddress emailAddress,
                            UserId authorId)
            throws UserAlreadyExistException,
            InvalidUserTypeException, UserNotFoundException, TenantNotFoundException {


        Tenant tenant = tenantManager.getTenant(tenantId)
                .orElseThrow(TenantNotFoundException::new);

        if (userService.exists(tenantId, emailAddress))
            throw new UserAlreadyExistException();

        log.info("retrieve author");
        User admin = userService.getByTenantIdAndUserId(tenantId, authorId)
                .filter(User::isSuperAdmin)
                .orElseThrow(UserNotFoundException::new);

        if (!admin.isSuperAdmin())
            throw new InvalidUserTypeException();

        log.info("attempt to create admin with emailAddress {}", emailAddress);

        String encodedPassword = passwordHelper.randomPassword();

        User newAdmin = userFactory.createAdmin(emailAddress,
                authorId,
                Tenant.DEFAULT_TENANT_ID,
                encodedPassword);

        User savedAdmin = userService.save(newAdmin);

        log.info("super admin created successfully");
        log.info("create and send invitation");

        Base64String hexCode = base64StringGenerator.generate();

        Invitation invitation = new Invitation(
                hexCode.getValue(),
                tenantId,
                Instant.now().plus(ADMIN_INVITATION_EXPIRATION_DURATION),
                emailAddress,
                admin,
                savedAdmin,
                InvitationReason.VALIDATE_ADMIN_ACCOUNT,
                null);

        Invitation savedInvitation = invitationManager.save(invitation);
        invitationManager.sendInvitation(savedInvitation, tenant);

        return savedAdmin;
    }


    public User createDeveloper(@NonNull EmailAddress emailAddress,
                                UserId authorId,
                                UUID targetTenantId)
            throws UserAlreadyExistException, UserNotFoundException, TenantNotFoundException {


        Tenant tenant = tenantManager.getTenant(targetTenantId)
                .orElseThrow(TenantNotFoundException::new);

        if (userService.exists(targetTenantId, emailAddress))
            throw new UserAlreadyExistException();

        log.info("retrieve author");
        User admin = userService.getAdmin(targetTenantId, authorId);

        log.info("attempt to create admin with emailAddress {}", emailAddress);

        String encodedPassword = passwordHelper.randomPassword();

        User newAdmin = userFactory.createDeveloper(emailAddress, targetTenantId, authorId, encodedPassword);
        User savedAdmin = userService.save(newAdmin);

        log.info("super admin created successfully");
        log.info("create invitation");

        Base64String hexCode = base64StringGenerator.generate();

        Invitation invitation = new Invitation(
                hexCode.getValue(),
                Tenant.DEFAULT_TENANT_ID,
                Instant.now().plus(DEVELOPER_INVITATION_EXPIRATION_DURATION),
                emailAddress,
                admin,
                savedAdmin,
                InvitationReason.VALIDATE_DEVELOPER_ACCOUNT,
                null);

        Invitation savedInvitation = invitationManager.save(invitation);
        invitationManager.sendInvitation(savedInvitation, tenant);

        return savedAdmin;
    }




    // TODO: 12/01/2024
    public PageResult<User> getTenantUser(UUID targetTenantId, int page){


        return null;

    }


    public User lockUser(@NonNull UserId userId,
                         @NonNull UserId authorId,
                         UUID targetTenantId)
            throws UserNotFoundException, UserAlreadyLockedException {

        log.info("retrieve author");
        User admin = userService.getAdmin(targetTenantId, authorId);

        User user = userService.getByTenantIdAndUserId(targetTenantId, userId)
                .filter(_user -> !_user.isDeleted())
                .orElseThrow(UserNotFoundException::new);

        if (user.isLocked())
            throw new UserAlreadyLockedException();

        if (user.isAdmin() && !admin.isSuperAdmin())
            throw new IllegalArgumentException();

        UserState userState = user.lock(UserState.StateReason.ADMIN_ACTION, authorId);

        userService.save(userState);

        // TODO: 12/01/2024 add pub event

        return userService.save(user);
    }


    public User unlockUser(@NonNull UserId userId,
                           @NonNull UserId authorId,
                           UUID targetTenantId)
            throws UserNotFoundException, UserAlreadyNonLockedException {

        log.info("retrieve author");
        User admin = userService.getAdmin(targetTenantId, authorId);

        User user = userService.getByTenantIdAndUserId(targetTenantId, userId)
                .filter(_user -> !_user.isDeleted())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isLocked())
            throw new UserAlreadyNonLockedException();

        UserState userState = user.activate(UserState.StateReason.ADMIN_ACTION, authorId);

        userService.save(userState);

        return userService.save(user);
    }


    public void deleteUser(@NonNull UserId userId,
                           @NonNull UserId authorId,
                           UUID targetTenantId)
            throws UserNotFoundException {

        log.info("retrieve author");
        User admin = userService.getAdmin(targetTenantId, authorId);

        User user = userService.getByTenantIdAndUserId(targetTenantId, userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.isAdmin() && !admin.isSuperAdmin())
            throw new IllegalArgumentException();

        userService.delete(user);
    }




}
