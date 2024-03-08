/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.reservation.UnLockReason;
import cm.yowyob.letsgo.trip.domain.model.reservation.LockStatus;
import cm.yowyob.letsgo.trip.domain.model.reservation.SaltedTokenHash;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@Table(value = "locked_trip_resources")
public class LockedTripResourcesEntity {
    
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id")
    private UUID lockedId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "group")
    private final String group;

    @Column(value = "user_id")
    private final String userId;

    @Column(value = "planned_trip_id")
    private final UUID plannedTripId;

    @Column(value = "reserved_at")
    private final LocalDateTime issueAt;

    @Column(value = "reserved_at")
    private final LocalDateTime expireAt;

    @Column(value = "confirmed_at")
    private final LocalDateTime confirmedAt;

    @Column(value = "cancelled_at")
    private final LocalDateTime cancelledAt;

    @Column(value = "reservation_status")
    private final LockStatus lockStatus;
    
    @Column(value = "cancel_reason")
    private final UnLockReason unLockReason;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY, prefix = "token_")
    private final SaltedTokenHash saltedTokenHash;

    @Column(value = "job_status")
    private JobStatus cancelJobStatus;

    @Column(value = "repeat_count")
    private Long jobRepeatCount;

    @Column(value = "repeat_interval")
    private Long repeatInterval;

    @Column(value = "next_activation")
    private LocalDateTime nextActivation;

    private LocalDateTime endAt;

    private LocalDateTime beginAt;


    @Frozen
    @Column(value = "reserved_resources")
    private final Set<ReservedResourceEntity> reservedResources;
}

