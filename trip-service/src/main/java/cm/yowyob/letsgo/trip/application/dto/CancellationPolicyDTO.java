/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.policies.CancellationPolicy;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class CancellationPolicyDTO {


    private String note;
    private SortedSet<CancelPolicyItemDTO> cancelPolicyItems;

    public static CancellationPolicyDTO fromCancellationPolicy(CancellationPolicy cancellationPolicy) {

        if (cancellationPolicy == null)
            return null;

        return CancellationPolicyDTO.builder()
                .note(cancellationPolicy.getNote())
                .cancelPolicyItems(new TreeSet<>(cancellationPolicy.getCancelPolicyItems()
                        .stream()
                        .map(CancelPolicyItemDTO::fromCancelPolicyItem)
                        .collect(Collectors.toSet())))
                .build();
    }

    public CancellationPolicy toCancellationPolicy() {

        return CancellationPolicy.builder()
                .note(note)
                .cancelPolicyItems(new TreeSet<>(cancelPolicyItems.stream().map(CancelPolicyItemDTO::toCancelPolicyItem).collect(Collectors.toSet())))
                .build();
    }


    @Builder
    @AllArgsConstructor
    public static class CancelPolicyItemDTO  {

        @Nonnull
        private final Long repaymentMaxDurationAfterConfirmation;
        @Nonnull
        private final Float repaymentPercent;


        public CancellationPolicy.CancelPolicyItem toCancelPolicyItem() {

            return CancellationPolicy.CancelPolicyItem.builder()
                    .repaymentPercent(repaymentPercent)
                    .repaymentMaxDurationAfterConfirmation(Duration.ofSeconds(repaymentMaxDurationAfterConfirmation))
                    .build();

        }

        public static CancelPolicyItemDTO fromCancelPolicyItem(CancellationPolicy.CancelPolicyItem cancelPolicyItem) {

            if (cancelPolicyItem == null)
                return null;


            return CancelPolicyItemDTO.builder()
                    .repaymentMaxDurationAfterConfirmation(cancelPolicyItem.getRepaymentMaxDurationAfterConfirmation().toSeconds())
                    .repaymentPercent(cancelPolicyItem.getRepaymentPercent())
                    .build();
        }
    }

}
