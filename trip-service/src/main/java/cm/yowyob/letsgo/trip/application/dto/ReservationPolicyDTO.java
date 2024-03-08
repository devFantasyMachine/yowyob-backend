/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.policies.ReservationPolicy;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;


@Data
@Builder
public class ReservationPolicyDTO {

    @Nonnull
    private Boolean confirmWithReservation;

    /**
     *  duration between posted datetime and last reservation datetime. in minutes
     */
    @Nonnull
    private Long durationBeforeLastReservation;

    @Nonnull
    private Long confirmationDelayAfterReservation;

    private Integer maxPlaceCountPerUser;

    private Float maxLuggageForUser;


    public ReservationPolicy toReservationPolicy(){

        return ReservationPolicy.builder()
                .confirmWithReservation(confirmWithReservation)
                .maxPlaceCountPerUser(maxPlaceCountPerUser)
                .maxLuggageForUser(maxLuggageForUser)
                .durationBeforeLastReservation(Duration.ofSeconds(durationBeforeLastReservation))
                .confirmationDelayAfterReservation(Duration.ofSeconds(confirmationDelayAfterReservation))
                .build();

    }

    public static ReservationPolicyDTO fromReservationPolicy(ReservationPolicy reservationPolicy){

        if (reservationPolicy == null)
            return null;

        return ReservationPolicyDTO.builder()
                .confirmWithReservation(reservationPolicy.confirmWithReservation())
                .durationBeforeLastReservation(reservationPolicy.durationBeforeLastReservation().toSeconds())
                .confirmationDelayAfterReservation(reservationPolicy.confirmationDelayAfterReservation().toSeconds())
                .build();
    }

}


