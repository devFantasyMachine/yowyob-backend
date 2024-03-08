package cm.yowyob.auth.app.domain.model;


import cm.yowyob.auth.app.domain.api.DeviceInfo;
import cm.yowyob.auth.app.domain.model.device.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class RequestDetails {

    private final String userAgent;
    private final String userIp;
    private final UUID tenantId;
    private final DeviceInfo deviceInfo;
}
