package cm.yowyob.auth.app.infrastructure.entities;

import cm.yowyob.auth.app.domain.model.device.DeviceType;
import cm.yowyob.auth.app.domain.model.device.UserAgent;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.infrastructure.entities.users.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(value = DeviceEntity.DeviceKey.class)
public class DeviceEntity {

    @Id
    private String userId;

    @Id
    private UUID deviceId;

    @Id
    private UUID tenantId;

    @Column(nullable = false)
    private Integer deviceOrder;

    private String deviceName;
    private String deviceManufacturer;
    private String deviceModel;
    private String userAgent;
    private String lastIp;

    @UpdateTimestamp
    private Instant lastSeen;
    private Instant lastLoggedIn;

    private String salt;
    private String authToken;

    private Instant expiresAt;

    @Column(nullable = false, updatable = false, unique = true)
    private String fingerPrint;

    private String family;
    private String devicePlatform;
    private String deviceOs;
    private String osVersion;

    @CreationTimestamp
    private Instant createdAt;


    @Embedded
    private AddressEntity location;

    // todo replace to @IdClass

    private Boolean isTrusted;
    private Boolean locked;
    private Boolean enabled;
    private Boolean deleted;

    private DeviceType deviceType;

    private Instant sessionEndAt;
    private Long lockedTtl;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceKey implements Serializable {

        private String userId;
        private UUID deviceId;
        private UUID tenantId;
    }



}