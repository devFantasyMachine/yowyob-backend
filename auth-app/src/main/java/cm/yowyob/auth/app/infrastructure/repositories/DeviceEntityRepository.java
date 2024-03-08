package cm.yowyob.auth.app.infrastructure.repositories;


import cm.yowyob.auth.app.infrastructure.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, DeviceEntity.DeviceKey> {

    List<DeviceEntity> findByUserId(String userId);

    Optional<DeviceEntity> findByDeviceIdAndUserId(UUID deviceId, String userId);

    Optional<DeviceEntity> findByFingerPrintAndUserId(String fingerPrint, String userId);
}