/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.reservation;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalCancelReservationException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalConfirmReservationException;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus;
import cm.yowyob.letsgo.trip.domain.model.schedule.ScheduledObject;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;



@Setter
@Getter
public class LockedTripResources extends ScheduledObject {


    private final UUID lockId;
    private final UUID planId;
    private final String userId;
    private final UUID requestId;
    private final String lockGroup;
    /**
     * For mutex
     */
    private final SaltedTokenHash challenge;
    private final LocalDateTime issueAt;
    private LocalDateTime expireAt;
    private LockStatus lockStatus;
    private UnLockReason unLockReason;
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;



    @NonNull
    private final Set<ItineraryProduct> lockedResources;



    public LockedTripResources(LocalDateTime beginAt, LocalDateTime endAt, LocalDateTime nextActivation, Duration repeatInterval, Long repeatCount, JobStatus jobStatus, UUID lockId, UUID planId, String userId, UUID requestId, String lockGroup, SaltedTokenHash challenge, LocalDateTime issueAt, LocalDateTime expireAt, LockStatus lockStatus, UnLockReason unLockReason, LocalDateTime confirmedAt, LocalDateTime cancelledAt, @NonNull Set<ItineraryProduct> lockedResources) {
        super(beginAt,
                endAt,
                nextActivation,
                repeatInterval,
                false,
                null,
                repeatCount,
                3L,
                jobStatus);
        this.lockId = Objects.requireNonNullElse(lockId, UUID.randomUUID());
        this.planId = planId;
        this.userId = userId;
        this.requestId = requestId;
        this.lockGroup = Objects.requireNonNullElse(lockGroup, this.lockId.toString());
        this.challenge = challenge;
        this.issueAt = issueAt;
        this.expireAt = expireAt;
        this.lockStatus = lockStatus;
        this.unLockReason = unLockReason;
        this.confirmedAt = confirmedAt;
        this.cancelledAt = cancelledAt;
        this.lockedResources = lockedResources;
    }


    public static LockedTripResourcesBuilder builder() {
        return new LockedTripResourcesBuilder();
    }


    public boolean isNonAuthorized(@NonNull final String plain) {

        return !challenge.verify(plain);
    }

    public void doCancel(@NonNull UnLockReason unLockReason)
            throws IllegalCancelReservationException {

        if (lockStatus.isNonConfirmable())
            throw new IllegalCancelReservationException();

        setUnLockReason(unLockReason);
        setLockStatus(unLockReason == UnLockReason.TIMEOUT ?
                LockStatus.ABORTED : LockStatus.CANCELED);

        setCancelledAt(LocalDateTime.now());
    }

    public void doConfirm() throws IllegalConfirmReservationException {

        if (lockStatus != LockStatus.LOCKED)
            throw new IllegalConfirmReservationException();

        setLockStatus(LockStatus.CONFIRMED);
        setConfirmedAt(LocalDateTime.now());
    }


    public static final String CANCELER_GROUP = "reservation.canceller";

    public String getId() {
        return lockId.toString();
    }


    @Override
    public String groupJob() {
        return CANCELER_GROUP;
    }

    public static class LockedTripResourcesBuilder {

        private UUID id;
        private UUID planId;
        private String userId;
        private UUID requestId;
        private String lockGroup;
        private SaltedTokenHash challenge;
        private LocalDateTime issueAt;
        private LocalDateTime expireAt;
        private LockStatus lockStatus;
        private UnLockReason unLockReason;
        private LocalDateTime confirmedAt;
        private LocalDateTime cancelledAt;
        private Set<ItineraryProduct> lockedResources;
        private LocalDateTime beginAt;
        private LocalDateTime endAt;
        private LocalDateTime nextActivation;
        private Duration repeatInterval;
        private Long repeatCount;
        private JobStatus jobStatus;

        LockedTripResourcesBuilder() {
        }

        public LockedTripResourcesBuilder repeatCount(Long repeatCount) {
            this.repeatCount = repeatCount;
            return this;
        }

        public LockedTripResourcesBuilder jobStatus(JobStatus jobStatus) {
            this.jobStatus = jobStatus;
            return this;
        }


        public LockedTripResourcesBuilder repeatInterval(Duration repeatInterval) {
            this.repeatInterval = repeatInterval;
            return this;
        }

        public LockedTripResourcesBuilder nextActivation(LocalDateTime nextActivation) {
            this.nextActivation = nextActivation;
            return this;
        }

        public LockedTripResourcesBuilder endAt(LocalDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public LockedTripResourcesBuilder beginAt(LocalDateTime beginAt) {
            this.beginAt = beginAt;
            return this;
        }

        public LockedTripResourcesBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LockedTripResourcesBuilder planId(UUID planId) {
            this.planId = planId;
            return this;
        }

        public LockedTripResourcesBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public LockedTripResourcesBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public LockedTripResourcesBuilder lockGroup(String lockGroup) {
            this.lockGroup = lockGroup;
            return this;
        }

        public LockedTripResourcesBuilder challenge(SaltedTokenHash challenge) {
            this.challenge = challenge;
            return this;
        }

        public LockedTripResourcesBuilder issueAt(LocalDateTime issueAt) {
            this.issueAt = issueAt;
            return this;
        }

        public LockedTripResourcesBuilder expireAt(LocalDateTime expireAt) {
            this.expireAt = expireAt;
            return this;
        }

        public LockedTripResourcesBuilder lockStatus(LockStatus lockStatus) {
            this.lockStatus = lockStatus;
            return this;
        }

        public LockedTripResourcesBuilder unLockReason(UnLockReason unLockReason) {
            this.unLockReason = unLockReason;
            return this;
        }

        public LockedTripResourcesBuilder confirmedAt(LocalDateTime confirmedAt) {
            this.confirmedAt = confirmedAt;
            return this;
        }

        public LockedTripResourcesBuilder cancelledAt(LocalDateTime cancelledAt) {
            this.cancelledAt = cancelledAt;
            return this;
        }

        public LockedTripResourcesBuilder lockedResources(@NonNull Set<ItineraryProduct> lockedResources) {
            this.lockedResources = lockedResources;
            return this;
        }

        public LockedTripResources build() {
            return new LockedTripResources(beginAt, endAt, nextActivation, repeatInterval, repeatCount, jobStatus, this.id, this.planId, this.userId, this.requestId, lockGroup, challenge, this.issueAt, expireAt, this.lockStatus, this.unLockReason, this.confirmedAt, this.cancelledAt, this.lockedResources);
        }

        public String toString() {
            return "LockedTripResources.LockedTripResourcesBuilder(id=" + this.id + ", planId=" + this.planId + ", userId=" + this.userId + ", requestId=" + this.requestId + ", lockGroup=" + this.lockGroup + ", challenge=" + this.challenge + ", issueAt=" + this.issueAt + ", expireAt=" + this.expireAt + ", lockStatus=" + this.lockStatus + ", unLockReason=" + this.unLockReason + ", confirmedAt=" + this.confirmedAt + ", cancelledAt=" + this.cancelledAt + ", lockedResources=" + this.lockedResources + ")";
        }
    }
}
