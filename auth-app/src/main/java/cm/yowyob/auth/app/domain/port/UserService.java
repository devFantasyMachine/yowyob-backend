package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.*;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User save(User user);

    Optional<User> getByTenantIdAndUserId(UUID tenantId, UserId userId);

    Optional<User> getByTenantIdAndEmail(UUID tenantId, String email);

    Optional<User> getByTenantIdAndPhone(UUID tenantId, String phone);

    Optional<User> getByTenantIdAndUsername(UUID tenantId, String username);

    default User getDeveloper(UUID tenantId, UserId userId) throws UserNotFoundException {

        return  getByTenantIdAndUserId(tenantId, userId)
                .filter(User::isDeveloper)
                .orElseThrow(UserNotFoundException::new);
    }

    default User getAdmin(UUID tenantId, UserId userId) throws UserNotFoundException {

        return  getByTenantIdAndUserId(tenantId, userId)
                .filter(User::isAdmin)
                .orElseThrow(UserNotFoundException::new);
    }


    void delete(User user);

    boolean exists(UUID tenantId, Contact contact);

    UserState save(UserState userState);

    void save(ResetPasswordChallenge resetPasswordChallenge);


    Optional<ResetPasswordChallenge> getResetPasswordChallengeById(String verificationId);
}

