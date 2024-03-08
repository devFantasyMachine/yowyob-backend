/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SplitLegDTO {

    @Nonnull Integer legIndex;
    @Nonnull StopDTO intermediateStop;
}
