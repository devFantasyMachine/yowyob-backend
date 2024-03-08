package cm.yowyob.letsgo.driver.domain.handler;

import cm.yowyob.letsgo.driver.domain.model.Permission;
import cm.yowyob.letsgo.driver.domain.model.PermissionResult;

import java.util.List;
import java.util.Set;


public class DefaultPermissionChecker implements PermissionChecker {

    @Override
    public PermissionResult checkPermission(String ownerId, List<Permission> permission) {
        return new PermissionResult(true, Set.of());
    }

}
