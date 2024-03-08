package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.domain.model.application.Scope;

import java.util.*;

public interface ApplicationService {
    Application save(Application app);

    Scope save(Scope role);

    ApplicationRole save(ApplicationRole role);

    Set<ApplicationRole> saveAll(Collection<ApplicationRole> applicationRoles);

    Optional<Application> getById(UUID appId);

    List<Application> getAllByIds(List<UUID> apps);
}
