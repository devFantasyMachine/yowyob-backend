package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.device.UserAgent;
import cm.yowyob.auth.app.domain.model.token.SaltedTokenHash;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.infrastructure.entities.DeviceEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DeviceMapper extends Mapper<Device, DeviceEntity> {


    private final AddressMapper addressMapper = AddressMapper.getInstance();

    @Override
    public Device toObject(DeviceEntity entity) {

        if (entity == null) return null;

        SaltedTokenHash deviceAuthToken = entity.getAuthToken() == null ? null :
                new SaltedTokenHash(entity.getAuthToken(),
                        entity.getSalt(),
                        entity.getEnabled(),
                        entity.getExpiresAt());

        UserAgent userAgent = new UserAgent(entity.getFamily(),
                entity.getDevicePlatform(),
                entity.getDeviceOs(),
                entity.getOsVersion(),
                entity.getUserAgent());

        return Device.builder()
                .deviceOrder(entity.getDeviceOrder())
                .userId(UserId.of(entity.getUserId()))
                .deviceId(entity.getDeviceId())
                .tenantId(entity.getTenantId())
                .deviceName(entity.getDeviceName())
                .deviceManufacturer(entity.getDeviceManufacturer())
                .deviceModel(entity.getDeviceModel())
                .locked(entity.getLocked())
                .lockedTtl(entity.getLockedTtl() == null ? null : Duration.ofSeconds(entity.getLockedTtl()))
                .enabled(entity.getEnabled())
                .deleted(entity.getDeleted())
                .createdAt(entity.getCreatedAt())
                .lastSeen(entity.getLastSeen())
                .lastLoggedIn(entity.getLastLoggedIn())
                .deviceAuthToken(deviceAuthToken)
                .userAgent(userAgent)
                .isTrusted(entity.getIsTrusted())
                .deleted(entity.getDeleted())
                .location(addressMapper.toObject(entity.getLocation()))
                .deviceType(entity.getDeviceType())
                .deviceFingerPrint(FingerPrint.of(entity.getFingerPrint()))
                .lastIp(entity.getLastIp())
                .build();
    }



    @Override
    public DeviceEntity toEntity(Device object) {

        if (object == null) return null;

        return DeviceEntity.builder()
                .deviceOrder(object.getDeviceOrder())
                .tenantId(object.getTenantId())
                .deviceId(object.getDeviceId())
                .userId(object.getUserId().getId())
                .deviceName(object.getDeviceName())
                .deviceManufacturer(object.getDeviceManufacturer())
                .deviceModel(object.getDeviceModel())
                .createdAt(object.getCreatedAt())
                .lastSeen(object.getLastSeen())
                .lastLoggedIn(object.getLastLoggedIn())
                .authToken(object.getDeviceAuthToken() == null ? null : object.getDeviceAuthToken().hash())
                .salt(object.getDeviceAuthToken() == null ? null : object.getDeviceAuthToken().salt())
                .expiresAt(object.getDeviceAuthToken() == null ? null : object.getDeviceAuthToken().expireAt())
                .locked(object.getLocked())
                .enabled(object.getEnabled())
                .deleted(object.getDeleted())
                .lastIp(object.getLastIp())
                .userAgent(object.getUserAgent().value())
                .location(addressMapper.toEntity(object.getLocation()))
                .devicePlatform(object.getUserAgent().devicePlatform())
                .deviceOs(object.getUserAgent().deviceOs())
                .family(object.getUserAgent().family())
                .osVersion(object.getUserAgent().osVersion())
                .lockedTtl(object.getLockedTtl() == null ? null: object.getLockedTtl().getSeconds())
                .deviceType(object.getDeviceType())
                .fingerPrint(object.getDeviceFingerPrint().getValue())
                .isTrusted(object.getIsTrusted())
                .build();
    }

}
