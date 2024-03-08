package cm.yowyob.auth.app.domain.api;

import cm.yowyob.auth.app.domain.model.device.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {

    private UUID deviceId;
    private DeviceType deviceType;
    private String deviceName;
    private String deviceModel;
    private String deviceManufacturer;

}
