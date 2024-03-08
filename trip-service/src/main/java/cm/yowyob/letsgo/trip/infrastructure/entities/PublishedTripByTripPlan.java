/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;


@Data
@Builder
@Table(value = "published_trip_by_trip_plan")
public class PublishedTripByTripPlan {

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "plan_id")
    private UUID planId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED,  name = "published_id")
    private String publishedId;
}
