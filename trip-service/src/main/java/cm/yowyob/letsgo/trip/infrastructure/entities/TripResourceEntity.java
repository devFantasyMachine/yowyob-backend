/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;


@Data
@Builder
@Table(value = "trip_resource")
public class TripResourceEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "resource_id")
    private UUID resourceId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "trip_id")
    private UUID tripId;

    //@PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "planner_id")
    private String plannerId;

    private String label;
    private Float defaultUnitCost;
    private Float totalQuantity;
    private Float unitQuantity;
    private String unitTag;

    //private PricingPolicy pricingPolicy;

    private Integer reservationCount;

    @Column(value = "resource_type")
    private ResourceType type;

}
