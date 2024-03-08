/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.policies;


import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Objects;


@Getter
@Builder
public final class ConfirmationPolicy {

    public static final ConfirmationPolicy DEFAULT = null;


    private final Boolean confirmByUserWhoHaveReserved;
    private final Boolean requireUserIdentity;
    private final Boolean requireVerifiedUserIdentity;
    private final Boolean requireIdentityForAllPassenger;
    private final Boolean isOneTicketPerPassenger;

    /**
     *  duration between posted datetime and last confirmation datetime
     */
    private final Duration durationBeforeLastConfirmation;



    public ConfirmationPolicy(Boolean confirmByUserWhoHaveReserved, Boolean requireUserIdentity, Boolean requireVerifiedUserIdentity, Boolean requireIdentityForAllPassenger, Boolean isOneTicketPerPassenger, Duration durationBeforeLastConfirmation) {
        this.isOneTicketPerPassenger = Objects.requireNonNullElse(isOneTicketPerPassenger, false);

        if(requireVerifiedUserIdentity && !requireUserIdentity)
            throw new IllegalStateException();

        if(requireIdentityForAllPassenger  && !requireUserIdentity)
            throw new IllegalStateException();

        this.confirmByUserWhoHaveReserved = confirmByUserWhoHaveReserved;
        this.requireUserIdentity = requireUserIdentity;
        this.requireVerifiedUserIdentity = requireVerifiedUserIdentity;
        this.requireIdentityForAllPassenger = requireIdentityForAllPassenger;
        this.durationBeforeLastConfirmation = durationBeforeLastConfirmation;
    }


}
