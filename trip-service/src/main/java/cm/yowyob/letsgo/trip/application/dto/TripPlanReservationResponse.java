/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;


@Data
@Builder
public class TripPlanReservationResponse {

    private String publishTripId;
    private ZonedDateTime publishAt;
    private TripPrestige tripPrestige;
    private TripType tripType;

    private PlannerInformationDTO plannerInformationDTO;
    private ReservationPolicyDTO reservationPolicyDTO;
    private ConfirmationPolicyDTO confirmationPolicyDTO;
    private CancellationPolicyDTO cancellationPolicyDTO;

    private PaymentSettingDTO paymentSettingDTO;
}
