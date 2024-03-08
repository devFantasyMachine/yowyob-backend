package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.model.user.Profile;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.UserService;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
public class UserManager {


    private UserService userService;
    private PasswordHelper passwordHelper;

    public Optional<User> getByTenantIdAndUserId(UUID tenantId, UserId userId) {

        return userService.getByTenantIdAndUserId(tenantId, userId);
    }

    public Profile updateUserProfile(UUID tenantId, UserId userId, Profile profile)
            throws UserNotFoundException {

        User user = getByTenantIdAndUserId(tenantId, userId)
                .orElseThrow(UserNotFoundException::new);

        Profile oldProfile = user.getProfile();
        Profile newProfile = oldProfile.merge(profile);

        user.setProfile(newProfile);

        return userService.save(user)
                .getProfile();
    }

    public User save(User user) {
        return userService.save(user);
    }

    public void save(ResetPasswordChallenge resetPasswordChallenge) {
        userService.save(resetPasswordChallenge);
    }



    public Optional<User> getByTenantIdAndEmail(UUID tenantId, String email){

        return userService.getByTenantIdAndEmail(tenantId, email);
    }

    public Optional<User> getByTenantIdAndPhone(UUID tenantId, String phone){

        return userService.getByTenantIdAndPhone(tenantId, phone);
    }

    public Optional<User> getByTenantIdAndUsername(UUID tenantId, String username){

        return userService.getByTenantIdAndUsername(tenantId, username);
    }


    public Optional<ResetPasswordChallenge> getResetPasswordById(String verificationId) {
        return userService.getResetPasswordChallengeById(verificationId);
    }

    public User resetPassword(User user, String password) {

        user.setPassword(passwordHelper.encode(password));
        return userService.save(user);
    }


    // TODO: 14/01/2024 update account, delete,
}
