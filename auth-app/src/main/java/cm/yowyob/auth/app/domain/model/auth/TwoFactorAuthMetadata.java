package cm.yowyob.auth.app.domain.model.auth;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;


@Data
@Builder
public class TwoFactorAuthMetadata {

    private Map<String, String> data;

    private Action action;

    private UUID deviceId;

    public enum Action {

        TRUST_DEVICE,
        LOCK_DEVICE
    }



}
