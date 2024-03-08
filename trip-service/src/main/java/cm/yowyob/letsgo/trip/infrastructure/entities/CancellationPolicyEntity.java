/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.policies.CancellationPolicy;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@Builder
@UserDefinedType(value = "cancellation_policy")
public class CancellationPolicyEntity  {

    private String note;

    @Column(value = "items")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.BIGINT, CassandraType.Name.FLOAT})
    private Map<Long, Float> cancelPolicyItems;



    public static CancellationPolicyEntity fromCancellationPolicy(CancellationPolicy cancellationPolicy) {

        if (cancellationPolicy == null)
            return null;

        Map<Long, Float> cancelPolicyItems = new HashMap<>();

        for (CancellationPolicy.CancelPolicyItem cancelPolicyItem : cancellationPolicy.getCancelPolicyItems()) {
            cancelPolicyItems.put(cancelPolicyItem.getRepaymentMaxDurationAfterConfirmation().toSeconds(), cancelPolicyItem.getRepaymentPercent());

        }

        return CancellationPolicyEntity.builder()
                .note(cancellationPolicy.getNote())
                .cancelPolicyItems(cancelPolicyItems)
                .build();
    }

    public CancellationPolicy toCancellationPolicy() {

        CancellationPolicy.CancellationPolicyBuilder note = CancellationPolicy.builder()
                .note(this.note);

        if (cancelPolicyItems != null && !cancelPolicyItems.isEmpty())
            note.cancelPolicyItems(new TreeSet<>(cancelPolicyItems.keySet().stream().map(l -> new  CancellationPolicy.CancelPolicyItem(Duration.ofSeconds(l), cancelPolicyItems.get(l))).collect(Collectors.toSet())));

        return note.build();
    }


}
