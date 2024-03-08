/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@AllArgsConstructor
@UserDefinedType(value = "reserved_resource")
public class ReservedResourceEntity {

    @Column("resource_type")
    private ResourceType resourceType;

    @Column("resource_id")
    private String resourceId;

    private Float quantity;
}
