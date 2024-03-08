package cm.yowyob.auth.app.domain.model.user;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
public class UserFactory {


    public User createUser(UUID tenantId,
                           String username,
                           String encodedPassword,
                           String email,
                           String phone,
                           UserId creator) {

        if (Objects.isNull(username) && Objects.isNull(email) && Objects.isNull(phone))
            throw new IllegalArgumentException();

        return new User(
                UserId.fromUUID(UUID.randomUUID()),
                tenantId,
                username == null ? (email == null ? phone : cleanEmail(email)) : username,
                encodedPassword,
                email,
                null,
                Role.SIMPLE_USER_ROLES
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                creator);
    }


    public User createDeveloper(@NonNull Contact contact,
                                UUID tenantId,
                                UserId creator,
                                @NonNull String password) {

        if (!contact.isEmail())
            throw new IllegalArgumentException();

        String _email = contact.getValue();

        Set<RequireAction> requireActions =
                Set.of(RequireAction.VALIDATE_REGISTRATION,
                        RequireAction.CONFIGURE_2FA,
                        RequireAction.RESET_PASSWORD);

        User user = new User(
                UserId.fromUUID(UUID.randomUUID()),
                tenantId,
                cleanEmail(_email),
                password,
                _email,
                null,
                Role.DEVELOPER_ROLES
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                creator);

        user.setEmailVerified(true);
        user.addRequireAction(requireActions);

        return user;
    }


    public User createAdmin(@NonNull Contact contact,
                            @NonNull UserId creator,
                            @NonNull UUID targetTenantId,
                            @NonNull String password) {

        if (!contact.isEmail())
            throw new IllegalArgumentException();

        String _email = contact.getValue();

        Set<RequireAction> requireActions =
                Set.of(RequireAction.VALIDATE_REGISTRATION,
                        RequireAction.CONFIGURE_2FA,
                        RequireAction.RESET_PASSWORD);

        User user = new User(
                UserId.fromUUID(UUID.randomUUID()),
                targetTenantId,
                cleanEmail(_email),
                password,
                _email,
                null,
                Role.ADMIN_ROLES
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                creator);

        user.setEmailVerified(true);
        user.addRequireAction(requireActions);

        return user;

    }


    public User createSuperAdmin(@NonNull UUID targetTenantId,
                                 @NonNull Contact contact,
                                 @NonNull String password) {

        if (!contact.isEmail())
            throw new IllegalArgumentException();

        String _email = contact.getValue();

        Set<RequireAction> requireActions =
                Set.of(RequireAction.VALIDATE_REGISTRATION,
                        RequireAction.CONFIGURE_2FA,
                        RequireAction.RESET_PASSWORD);

        User user = new User(
                UserId.fromUUID(UUID.randomUUID()),
                targetTenantId,
                cleanEmail(_email),
                password,
                _email,
                null,
                Role.SUPER_ADMIN_ROLES
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                null);

        user.setEmailVerified(true);
        user.addRequireAction(requireActions);
        return user;
    }


    private static @NonNull String cleanEmail(@NonNull String email) {

        int endIndex = email.lastIndexOf("@");

        if (endIndex == -1)
            throw new IllegalArgumentException();

        return email.substring(0, endIndex);
    }


}
