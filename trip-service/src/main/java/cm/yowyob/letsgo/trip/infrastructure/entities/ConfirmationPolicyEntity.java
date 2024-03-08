/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;


import cm.yowyob.letsgo.trip.domain.model.policies.ConfirmationPolicy;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.Duration;

@Data
@Builder
@UserDefinedType(value = "confirmation_policy")
public class ConfirmationPolicyEntity   {

    @Column(value = "confirm_by_booker")
    private Boolean confirmByUserWhoHaveReserved;

    @Column(value = "require_pooler_identity")
    private Boolean requireUserIdentity;

    @Column(value = "require_verified_pooler_identity")
    private Boolean requireVerifiedUserIdentity;

    @Column(value = "require_passenger_identity")
    private Boolean requireIdentityForAllPassenger;

    @Column(value = "one_ticket_per_passenger")
    private Boolean isOneTicketPerPassenger;

    @Column(value = "duration_before_confirmation")
    private Long durationBeforeLastConfirmation;


    public static ConfirmationPolicyEntity fromConfirmationPolicy(ConfirmationPolicy confirmationPolicy) {

        if (confirmationPolicy == null)
            return null;

        return ConfirmationPolicyEntity.builder()
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
                requireVerifiedUserIdentity,
                requireIdentityForAllPassenger,
                isOneTicketPerPassenger,
                durationBeforeLastConfirmation != null ?
                Duration.ofSeconds(durationBeforeLastConfirmation) : null);

    }
}
