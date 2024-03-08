package cm.yowyob.letsgo.driver.domain.exception;

import java.util.Objects;
import java.util.Set;

public class InvalidPermissionException extends Exception {
    public InvalidPermissionException(Set<String> errors) {
        super(Objects.requireNonNullElse(errors, Set.of()).toString());
    }
}
