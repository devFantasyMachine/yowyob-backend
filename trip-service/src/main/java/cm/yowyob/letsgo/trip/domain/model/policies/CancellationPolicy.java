/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.policies;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.util.Objects;
import java.util.SortedSet;


@Getter
@Builder
public final class CancellationPolicy {

    private static final Duration MAX_REPAYMENT_DURATION_AFTER_CONFIRMATION = Duration.ofDays(2);
    private static final Duration MIN_REPAYMENT_DURATION_AFTER_CONFIRMATION = Duration.ofMinutes(5);

    private String note;
    private final SortedSet<CancelPolicyItem> cancelPolicyItems;

    public CancellationPolicy(String note, @NonNull SortedSet<CancelPolicyItem> cancelPolicyItems) {
        this.note = note;
        this.cancelPolicyItems = cancelPolicyItems;
    }

    @Data
    @Builder
    public static class CancelPolicyItem implements Comparable<CancelPolicyItem>{

        private final Duration repaymentMaxDurationAfterConfirmation;
        private Float repaymentPercent;

        public CancelPolicyItem(@NonNull Duration repaymentMaxDurationAfterConfirmation, Float repaymentPercent) {

            if(repaymentMaxDurationAfterConfirmation.compareTo(MIN_REPAYMENT_DURATION_AFTER_CONFIRMATION) < 0
                    || repaymentMaxDurationAfterConfirmation.compareTo(MAX_REPAYMENT_DURATION_AFTER_CONFIRMATION) > 0)
                throw new IllegalStateException();

            this.repaymentMaxDurationAfterConfirmation = repaymentMaxDurationAfterConfirmation;
            this.repaymentPercent = repaymentPercent;
        }

        @Override
        public int compareTo(@NonNull CancelPolicyItem o) {
            return Objects.compare(this.repaymentMaxDurationAfterConfirmation, o.repaymentMaxDurationAfterConfirmation, Duration::compareTo);
        }
    }


}
