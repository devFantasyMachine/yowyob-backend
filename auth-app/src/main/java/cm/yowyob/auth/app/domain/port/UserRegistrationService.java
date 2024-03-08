package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserRegistration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRegistrationService {

    UserRegistration save(UserRegistration userRegistration);

    Optional<UserRegistration> getByApplicationIdAndUserId(UUID appId, UserId userId);

    List<UserRegistration> getAllByAppId(UUID appId);
}
