/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.user;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@Getter
@Builder
@AllArgsConstructor
public class User implements Serializable {

    @NonNull
    private final UUID tenantId;
    @NonNull
    private final UserId userId;
    @NonNull
    private final String username;

    private String password;

    @NonNull
    @Setter
    private Profile profile;

    @NonNull
    private Set<String> roles;

    private String phone;
    private Boolean phoneVerified;
    private String email;
    private Boolean emailVerified;

    private LocalDateTime lastLoginInstant;
    private LocalDateTime updatedAt;
    private LocalDateTime expiry;
    private final LocalDateTime createdAt;

    private String parentEmail;
    private final UserId creator;


    private UserTwoFactorConfiguration twoFactorConfiguration;
    private UserRecoveryConfiguration userRecoveryConfiguration;

    private List<UserRegistration> registrations = new ArrayList<>();


    private Set<RequireAction> requireActions;

    @NonNull
    private UserState.State userState;


    protected User(UserId userId,
                   UUID tenantId,
                   String username,
                   String password,
                   String email,
                   String phone,
                   Set<String> roles,
                   UserId creator) {

        this.userId = Objects.requireNonNull(userId);
        this.tenantId = Objects.requireNonNull(tenantId);
        this.username = Objects.requireNonNull(username);
        this.password = password;
        this.creator = creator;

        this.requireActions = new HashSet<>();
        this.profile = Profile.EMPTY;

        this.email = email;
        this.phone = phone;

        this.emailVerified = false;
        this.phoneVerified = false;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.roles = Collections.unmodifiableSet(roles);

        this.userRecoveryConfiguration = UserRecoveryConfiguration.DEFAULT;
        this.twoFactorConfiguration = UserTwoFactorConfiguration.DEFAULT;
        this.userState = UserState.State.PENDING;
    }



    public UserState activate(UserState.StateReason stateReason, UserId userId) {

        if (this.isActive() || this.isDeleted())
            throw new IllegalArgumentException();

        this.userState = UserState.State.ACTIVE;
        return new UserState(UUID.randomUUID(),
                UserState.State.ACTIVE,
                stateReason,
                LocalDateTime.now(),
                userId);
    }

    public UserState lock(UserState.StateReason stateReason, UserId authorId) {

        if (this.isLocked() || this.isDeleted())
            throw new IllegalArgumentException();

        this.userState = UserState.State.LOCKED;
        return new UserState(UUID.randomUUID(), UserState.State.LOCKED, stateReason, LocalDateTime.now(), authorId);

    }

    public UserState delete(UserState.StateReason stateReason, UserId authorId) {

        if (this.isDeleted())
            throw new IllegalArgumentException();

        if (this.isLocked()) {

            this.userState = UserState.State.LOCKED_DELETED;
            return new UserState(UUID.randomUUID(), UserState.State.LOCKED_DELETED, stateReason, LocalDateTime.now(), authorId);
        }

        this.userState = UserState.State.DELETED;
        return  new UserState(UUID.randomUUID(), UserState.State.DELETED, stateReason, LocalDateTime.now(), authorId);

    }


    public boolean isActive() {

        return userState == UserState.State.ACTIVE;
    }

    public boolean isLocked() {
        return userState == UserState.State.LOCKED || userState == UserState.State.LOCKED_DELETED;
    }

    public boolean isDeleted() {
        return userState == UserState.State.DELETED || userState == UserState.State.LOCKED_DELETED;
    }

    public boolean isDeveloper() {
        return roles.contains(Role.DEVELOPER_ROLE_NAME);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN_ROLE_NAME);
    }

    public boolean isSuperAdmin() {
        return roles.contains(Role.SUPER_ADMIN_ROLE_NAME);
    }


    public void removeAction(RequireAction requireAction) {
        this.requireActions.remove(requireAction);
    }


    public void addRequireAction(Set<RequireAction> requireActions) {
        this.requireActions = new HashSet<>(this.requireActions);
        this.requireActions.addAll(requireActions);
    }


    public void withProfile(Profile profile) {
        this.profile = profile;
    }

    public void setEmailVerified(boolean b) {
        this.emailVerified = b;
    }


    public void setPhoneVerified(boolean b) {
        this.phoneVerified = b;
    }

    public void addRoles(List<String> roles) {

        this.roles = new HashSet<>(this.roles);
        this.roles.addAll(roles);

    }


    public boolean hasParentEmail() {

        return parentEmail != null;
    }


    public void setPassword(String encodedPassword) {
        this.password = Objects.requireNonNull(encodedPassword);
    }


}



