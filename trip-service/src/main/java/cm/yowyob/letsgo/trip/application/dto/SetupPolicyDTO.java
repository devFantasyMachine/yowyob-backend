/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.policies.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
public class SetupPolicyDTO {

    @NonNull @NotBlank @NotEmpty
    private String tripPlanId;
    private ReservationPolicyDTO reservationPolicy;
    private ConfirmationPolicyDTO confirmPolicy;
    private CoursePolicyDTO coursePolicy;
    private PricingPolicyDTO pricingPolicy;
    private CancellationPolicyDTO cancellationPolicy;


    public PricingPolicy getPricingPolicy() {
        return pricingPolicy == null ? null : null;
    }

    public ReservationPolicy getReservationPolicy() {

        if (reservationPolicy == null)
            return null;

        return reservationPolicy.toReservationPolicy();
    }

    public CancellationPolicy getCancellationPolicy() {
        if (cancellationPolicy == null)
            return null;
        return cancellationPolicy.toCancellationPolicy();
    }

    public ConfirmationPolicy getConfirmPolicy() {
        return confirmPolicy == null ? null : confirmPolicy.toConfirmPolicy();
    }

    public CoursePolicy getCoursePolicy() {
        return coursePolicy == null ? null : coursePolicy.toCoursePolicy();
    }
}
