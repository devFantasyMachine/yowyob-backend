/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;
import cm.yowyob.letsgo.trip.infrastructure.entities.LockedTripResourcesEntity;

import java.time.Duration;


public class LockedTripResourcesMapper {


    public static LockedTripResources mapToObject(LockedTripResourcesEntity entity) {

        if (entity == null)
            return null;

        return LockedTripResources.builder()
                .id(entity.getLockedId())
                .planId(entity.getPlannedTripId())
                .issueAt(entity.getIssueAt())
                .expireAt(entity.getExpireAt())
                .lockGroup(entity.getGroup())
                .userId(entity.getUserId())
                .challenge(entity.getSaltedTokenHash())
                .cancelledAt(entity.getCancelledAt())
                .unLockReason(entity.getUnLockReason())
                .lockStatus(entity.getLockStatus())
                .repeatCount(entity.getJobRepeatCount())
                .jobStatus(entity.getCancelJobStatus())
                .repeatInterval(entity.getRepeatInterval() == null ? null : Duration.ofSeconds(entity.getRepeatInterval()))
                .nextActivation(entity.getNextActivation())
                .beginAt(entity.getBeginAt())
                .endAt(entity.getEndAt())
                .build();

    }



    public static LockedTripResourcesEntity mapFromObject(LockedTripResources lockedTripResources) {

        if (lockedTripResources == null)
            return null;

        return LockedTripResourcesEntity.builder()
                .lockedId(lockedTripResources.getLockId())
                .group(lockedTripResources.getLockGroup())
                .issueAt(lockedTripResources.getIssueAt())
                .expireAt(lockedTripResources.getExpireAt())
                .cancelledAt(lockedTripResources.getCancelledAt())
                .lockStatus(lockedTripResources.getLockStatus())
                .unLockReason(lockedTripResources.getUnLockReason())
                .confirmedAt(lockedTripResources.getConfirmedAt())
                .userId(lockedTripResources.getUserId())
                .saltedTokenHash(lockedTripResources.getChallenge())
                .plannedTripId(lockedTripResources.getPlanId())
                .cancelJobStatus(lockedTripResources.getJobStatus())
                .jobRepeatCount(lockedTripResources.getRepeatCount())
                .nextActivation(lockedTripResources.getNextActivation())
                .repeatInterval(lockedTripResources.getRepeatInterval() == null ? null : lockedTripResources.getRepeatInterval().toSeconds())
                .beginAt(lockedTripResources.getBeginAt())
                .endAt(lockedTripResources.getEndAt())
                .build();
    }






}


