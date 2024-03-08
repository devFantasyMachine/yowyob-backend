package cm.yowyob.letsgo.driver.domain.handler;

import cm.yowyob.letsgo.driver.domain.model.Permission;
import cm.yowyob.letsgo.driver.domain.model.PermissionResult;

import java.util.List;

public interface PermissionChecker {

    PermissionResult checkPermission(String ownerId, List<Permission> permission);
}
