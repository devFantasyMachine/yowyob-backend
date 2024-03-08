/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.policies.ConfirmationPolicy;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;


@Data
@Builder
public class ConfirmationPolicyDTO {

    @Nonnull
    private Boolean confirmByUserWhoHaveReserved;
    @Nonnull
    private Boolean requireUserIdentity;
    private Boolean requireVerifiedUserIdentity;
    private Boolean requireIdentityForAllPassenger;

    @Nonnull
    private Long durationBeforeLastConfirmation;
    private Boolean isOneTicketPerPassenger;

    public static ConfirmationPolicyDTO fromConfirmationPolicy(ConfirmationPolicy confirmationPolicy) {

        if (confirmationPolicy == null)
            return null;

        return ConfirmationPolicyDTO.builder()
                .confirmByUserWhoHaveReserved(confirmationPolicy.getConfirmByUserWhoHaveReserved())
                .durationBeforeLastConfirmation(confirmationPolicy.getDurationBeforeLastConfirmation().toSeconds())
                .isOneTicketPerPassenger(confirmationPolicy.getIsOneTicketPerPassenger())
                .requireUserIdentity(confirmationPolicy.getRequireUserIdentity())
                .requireIdentityForAllPassenger(confirmationPolicy.getRequireIdentityForAllPassenger())
                .requireVerifiedUserIdentity(confirmationPolicy.getRequireVerifiedUserIdentity())
                .build();
    }


    public ConfirmationPolicy toConfirmPolicy(){

        return new ConfirmationPolicy(confirmByUserWhoHaveReserved,
                requireUserIdentity,
                requireVerifiedUserIdentity,requireIdentityForAllPassenger, isOneTicketPerPassenger, Duration.ofMinutes(durationBeforeLastConfirmation));

    }

}


