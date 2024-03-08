package cm.yowyob.letsgo.driver.domain.model;


import java.util.Set;


public record PermissionResult(Boolean isAccepted, Set<String> errors) {

}
