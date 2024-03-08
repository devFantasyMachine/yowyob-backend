package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.registration.Registration;

import java.util.Optional;

public interface RegistrationService {
    Optional<Registration> getById(String registrationId);

    Registration save(Registration registration);
}
