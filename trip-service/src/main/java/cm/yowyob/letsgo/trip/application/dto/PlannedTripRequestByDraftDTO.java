/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class PlannedTripRequestByDraftDTO {

    @Nonnull private UUID draftId;
    @Nonnull private String plannerId;
    @Nonnull private LocalDateTime startDatetime;
}
