/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.application.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public final class DriverLicenceDTO {

    @Nonnull
    private final String licenceUId;
    private final Boolean isVerified;
    @Nonnull
    private final LocalDateTime issueAt;
    @Nonnull
    private final LocalDateTime expireAt;
    @Nonnull
    private final String identityProviderId;
    @Nonnull
    private final Set<String> docs;

}
