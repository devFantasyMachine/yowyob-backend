package cm.yowyob.auth.app.domain.port;


import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DeviceService {

    Device save(Device device);

    void save(List<Device> devices);

    Optional<Device> getDeviceByDeviceIdAndUserId(UUID deviceId, UserId userId);

    Set<Device> getAllDevicesByUser(UserId userId);

    Optional<Device> getDeviceByFingerPrintAndUserId(FingerPrint fingerPrint, UserId userId);
}
