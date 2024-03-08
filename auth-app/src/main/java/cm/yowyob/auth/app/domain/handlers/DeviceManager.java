package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.BadDeviceRequestException;
import cm.yowyob.auth.app.domain.exceptions.DeviceLockedException;
import cm.yowyob.auth.app.domain.exceptions.DeviceNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UnTrustedDeviceException;
import cm.yowyob.auth.app.domain.model.notification.Notification;
import cm.yowyob.auth.app.domain.model.notification.NotificationPriority;
import cm.yowyob.auth.app.domain.model.notification.NotificationSeverity;
import cm.yowyob.auth.app.domain.model.notification.NotificationType;
import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.code.FingerPrintGenerator;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.device.DeviceType;
import cm.yowyob.auth.app.domain.model.device.UserAgent;
import cm.yowyob.auth.app.domain.model.device.UserAgentParser;
import cm.yowyob.auth.app.domain.model.token.SaltedTokenHash;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.DeviceService;
import cm.yowyob.auth.app.domain.port.GeoIPHelper;
import cm.yowyob.auth.app.domain.port.NotificationProducer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@AllArgsConstructor
public class DeviceManager {


    private DeviceService deviceService;
    private GeoIPHelper geoIPHelper;
    private UserAgentParser userAgentParser;
    private NotificationProducer notificationProducer;



    private final FingerPrintGenerator fPrintGenerator = new FingerPrintGenerator(20);


    public Set<Device> retrieveDevices(UserId userId) {

        return deviceService.getAllDevicesByUser(userId);
    }


    public SortedSet<Device> retrieveAllUserDevicesSortedByOrder(UserId userId) {

        return new TreeSet<>(retrieveDevices(userId));
    }


    public Device logout(UUID deviceId, UserId userId, FingerPrint targetDeviceFingerPrint)
            throws DeviceNotFoundException, UnTrustedDeviceException {

        Device currentDevice = getDeviceByUserIdAndDeviceId(deviceId, userId);

        if (currentDevice.isUnTrusted()) throw new UnTrustedDeviceException();

        Device targetDevice =
                deviceService.getDeviceByFingerPrintAndUserId(targetDeviceFingerPrint, userId)
                        .filter(Device::isNonDeleted)
                        .filter(Device::isActive)
                        .orElseThrow(DeviceNotFoundException::new);

        if (!targetDevice.isActive()) return targetDevice;

        targetDevice.revokeDevice();
        Device device = deviceService.save(targetDevice);

        Notification notification = Notification.builder()
                .userId(userId)
                .type(NotificationType.TENANT)
                .severity(NotificationSeverity.WARN)
                .priority(NotificationPriority.HIGTHGER)
                .subject("Device Logout")
                .createAt(LocalDateTime.now())
                .build();

        notificationProducer.produce(notification);

        return device;

    }


    public Device getDeviceByUserIdAndDeviceId(UUID deviceId, UserId userId) throws DeviceNotFoundException {
        return deviceService.getDeviceByDeviceIdAndUserId(deviceId, userId)
                .filter(Device::isNonDeleted)
                .filter(Device::isActive)
                .orElseThrow(DeviceNotFoundException::new);
    }


    public Device delete(UUID deviceId, UserId userId, FingerPrint targetDeviceFingerPrint)
            throws BadDeviceRequestException, DeviceNotFoundException, UnTrustedDeviceException {

        Device currentDevice = deviceService.getDeviceByDeviceIdAndUserId(deviceId, userId).filter(Device::isNonDeleted).filter(Device::isActive).orElseThrow(DeviceNotFoundException::new);

        if (currentDevice.isUnTrusted()) throw new UnTrustedDeviceException();

        Device targetDevice = deviceService.getDeviceByFingerPrintAndUserId(targetDeviceFingerPrint, userId).filter(Device::isNonDeleted).orElseThrow(DeviceNotFoundException::new);

        if (targetDevice.isMasterDevice() && !targetDevice.getLocked()) {
            // require two-step
            throw new BadDeviceRequestException("REQUIRE_TWO_STEP");

        } else {

            targetDevice.setDeleted(true);

            // change master device
            if (targetDevice.isMasterDevice()) {

                targetDevice.setDeviceOrder(-1);
                currentDevice.setDeviceOrder(Device.MASTER_DEVICE_ORDER);
                deviceService.save(currentDevice);
            }

            return deviceService.save(targetDevice);
        }


    }


    public Device lock(Duration ttl, @NonNull Device targetDevice) {
        targetDevice.lock(ttl);
        return deviceService.save(targetDevice);
    }


    public Device lockByUser(UUID deviceId, UserId userId, FingerPrint targetDeviceFingerPrint)
            throws BadDeviceRequestException, DeviceNotFoundException, UnTrustedDeviceException {

        Device currentDevice = deviceService.getDeviceByDeviceIdAndUserId(deviceId, userId).filter(Device::isNonDeleted).filter(Device::isActive).orElseThrow(DeviceNotFoundException::new);

        if (currentDevice.isUnTrusted()) throw new UnTrustedDeviceException();

        Device targetDevice = deviceService.getDeviceByFingerPrintAndUserId(targetDeviceFingerPrint, userId).filter(Device::isNonDeleted).filter(Device::isActive).orElseThrow(DeviceNotFoundException::new);

        if (targetDevice.getLocked()) return targetDevice;

        if (targetDevice.isMasterDevice()) {
            // require two-step
            throw new BadDeviceRequestException("REQUIRE_TWO_STEP");

        } else {

            return lock(null, targetDevice);
        }

    }


    public Device addDevice(UserId userId,
                            UUID tenantId,
                            String userAgentString,
                            String ip,
                            DeviceType deviceType,
                            String deviceName,
                            String deviceModel,
                            String deviceManufacturer)
            throws DeviceLockedException {

        boolean isMaster = false;

        SortedSet<Device> devices = retrieveAllUserDevicesSortedByOrder(userId);

        if (devices.isEmpty()) isMaster = true;

        UserAgent userAgent = userAgentParser.parse(userAgentString);

        Optional<Device> optionalDevice = devices.stream()
                .filter(device -> device.isSame(userAgent,
                        deviceType,
                        deviceName,
                        deviceModel,
                        deviceManufacturer)
                )
                .findFirst();

        if (optionalDevice.isPresent() && optionalDevice.get().getLocked())
            throw new DeviceLockedException();

        Address address = geoIPHelper.findLocation(ip).orElse(Address.UNKNOWN);

        Device device;
        if (optionalDevice.isEmpty()) {

            FingerPrint fingerPrint = fPrintGenerator.generate();

            device = Device.builder()
                    .deviceOrder(isMaster ? Device.MASTER_DEVICE_ORDER : devices.last()
                            .getDeviceOrder() + 1)
                    .userId(userId)
                    .deviceId(UUID.randomUUID())
                    .tenantId(tenantId)
                    .deviceAuthToken(SaltedTokenHash.NONE)
                    .deviceFingerPrint(fingerPrint)
                    .createdAt(Instant.now())
                    .lastSeen(Instant.now())
                    .userAgent(userAgent)
                    .isTrusted(isMaster)
                    .location(address)
                    .deviceType(deviceType)
                    .deviceModel(deviceModel)
                    .deviceManufacturer(deviceManufacturer)
                    .deviceName(deviceName)
                    .deleted(false)
                    .locked(false)
                    .enabled(true)
                    .lastIp(ip)
                    .build();

            log.info("new device {}", device);

            // FIXME: 25/01/2024 null pointer on immutable map
/*
            Notification notification = Notification.builder()
                    .userId(userId)
                    .type(NotificationType.TENANT)
                    .severity(NotificationSeverity.INFO)
                    .priority(NotificationPriority.HIGTHGER)
                    .subject(isMaster ? "Welcome" : "New Device")
                    .createAt(LocalDateTime.now())
                    .data(Map.of("location", address.getFormatted(),
                            "type", deviceType.getDeviceName(),
                            "ip", ip,
                            "name", deviceName,
                            "model", deviceModel)
                    )
                    .build();

            notificationProducer.produce(notification);*/

        }
        else {

            device = optionalDevice.get();
            device.setLocation(address);

            log.info("old device {}", device);
        }

        return deviceService.save(device);
    }


    public Device refresh(UserId userId,
                          UUID driverId,
                          String token,
                          Instant expireAt,
                          String ip)
            throws DeviceNotFoundException, DeviceLockedException {

        Device device = deviceService.getDeviceByDeviceIdAndUserId(driverId, userId)
                .filter(Device::isNonDeleted)
                .orElseThrow(DeviceNotFoundException::new);

        return refresh(device, token, expireAt, ip);
    }


    public Device refresh(@NonNull Device device, String token, Instant expireAt, String ip)
            throws DeviceLockedException {

        if (device.getLocked()) throw new DeviceLockedException();

        Address address = geoIPHelper.findLocation(ip).orElse(Address.UNKNOWN);

        if (Objects.nonNull(token)) {

            SaltedTokenHash tokenHash = SaltedTokenHash.generateFor(token, expireAt);
            device.setDeviceAuthToken(tokenHash);
            device.setEnabled(true);
        }

        device.setLocation(address == Address.UNKNOWN ? device.getLocation() : address);
        device.setLastIp(ip);
        return refresh(device, token, expireAt);

    }


    public Device refresh(@NonNull Device device, String token, Instant expireAt)
            throws DeviceLockedException {

        if (device.getLocked()) throw new DeviceLockedException();

        if (Objects.nonNull(token)) {

            SaltedTokenHash tokenHash = SaltedTokenHash.generateFor(token, expireAt);
            device.setDeviceAuthToken(tokenHash);
            device.setEnabled(true);
        }

        device.setLastSeen(Instant.now());
        return deviceService.save(device);

    }










/*
    public String lockDeviceFirstStep(UserId userId,
                                      UUID deviceId,
                                      FingerPrint targetDeviceFingerPrint)
            throws DeviceNotFoundException, BadDeviceRequestException {

        Device targetDevice =
                deviceService.getDeviceByFingerPrintAndUserId(targetDeviceFingerPrint, userId)
                        .filter(Device::isNonDeleted)
                        .filter(Device::isActive)
                        .orElseThrow(DeviceNotFoundException::new);

        if (targetDevice.getLocked()) throw new BadDeviceRequestException("DEVICE_ALREADY_LOCKED");

        TwoFactorAuthMetadata metadata = TwoFactorAuthMetadata.builder()
                .deviceId(deviceId)
                .action(TwoFactorAuthMetadata.Action.LOCK_DEVICE)
                .data(Map.of("target", targetDevice.getDeviceId().toString()))
                .build();

        return twoFactorAuthenticator.createAttempt(userId, metadata);

    }



    public void lockDeviceSecondStep(String twoFactorId, String code) {


    }


    public void trustDeviceFirstStep(UUID tenantId, UUID deviceId, UserId userId, String methodId) throws DeviceNotFoundException, DeviceAlreadyTrustedException {

        Device currentDevice = deviceService.getDeviceByDeviceIdAndUserId(deviceId, userId).filter(Device::isNonDeleted).filter(Device::isActive).orElseThrow(DeviceNotFoundException::new);

        if (!currentDevice.isUnTrusted()) throw new DeviceAlreadyTrustedException();

        // get user data
        User user = userService.getByTenantIdAndUserId(tenantId, userId).filter(User::isActive).orElseThrow(IllegalArgumentException::new);


        UserTwoFactorConfiguration twoFactorConfiguration = user.getTwoFactorConfiguration();

        if (twoFactorConfiguration.getIsEnabled()) {

            TwoFactorAuthChallenge method;
            if (Objects.isNull(methodId)) {

                method = twoFactorConfiguration.getLastUsedMethod();
            } else {

                method = twoFactorConfiguration.getMethodById(methodId);
            }

        } else {


        }


    }


    public void trustDeviceSecondStep() {


    }
*/



}
