/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.device;


import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.token.SaltedTokenHash;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode()
public class Device implements Comparable<Device> {

    public static Integer MASTER_DEVICE_ORDER = 1;

    @NonNull
    private Integer deviceOrder;
    @NonNull
    private final UserId userId;
    @NonNull
    private final UUID deviceId;
    @NonNull
    private final UUID tenantId;
    @NonNull
    private final FingerPrint deviceFingerPrint;

    private String lastIp;
    private Boolean isTrusted;
    private Boolean locked;
    private Boolean enabled;
    private Instant createdAt;
    private Instant lastSeen;
    private Instant lastLoggedIn;
    private Boolean deleted;

    private final DeviceType deviceType;
    private final String deviceName;
    private final String deviceModel;
    private final String deviceManufacturer;
    private Address location;

    @NonNull
    private final UserAgent userAgent;

    private Instant sessionEndAt;
    private Duration lockedTtl;

    private SaltedTokenHash deviceAuthToken; // full token = hash(salt + access_token) with S256



    public void revokeDevice(){
        this.deviceAuthToken = deviceAuthToken.disable();
        this.enabled = false;
        this.sessionEndAt = Instant.now();
    }

    public void lock(Duration ttl){

        if (this.enabled)
            revokeDevice();

        this.locked = true;
        if (Objects.nonNull(ttl))
            this.lockedTtl = ttl;
    }

    public Boolean isNonDeleted(){return !deleted;}

    public Boolean isActive(){return enabled;}


    @Override
    public int compareTo(Device o) {
        return deviceOrder.compareTo(o.deviceOrder);
    }


    public Boolean isMasterDevice() {
        return Objects.equals(deviceOrder, Device.MASTER_DEVICE_ORDER);
    }





/*
    public boolean isSame(UUID deviceId) {
        return Objects.equals(deviceId, this.deviceId);
    }

    public boolean isSame(UserAgent userAgent) {
        return Objects.equals(userAgent, this.userAgent);
    }

    public boolean isSame(FingerPrint fingerPrint) {
        return Objects.equals(fingerPrint, this.deviceFingerPrint);
    }
*/



    public Boolean isUnTrusted() {

        if (isTrusted == null)
            return false;

        return !isTrusted;
    }



    public Boolean isSame(UserAgent userAgent,
                          DeviceType deviceType,
                          String deviceName,
                          String deviceModel,
                          String deviceManufacturer) {

        if (userAgent == null && deviceType == null
                && deviceName == null && deviceModel == null
                && deviceManufacturer == null)
            return false;

        return  Objects.equals(userAgent, this.userAgent) &&
                Objects.equals(deviceType, this.deviceType) &&
                Objects.equals(deviceName, this.deviceName) &&
                Objects.equals(deviceModel, this.deviceModel) &&
                Objects.equals(deviceManufacturer, this.deviceManufacturer);
    }




}
