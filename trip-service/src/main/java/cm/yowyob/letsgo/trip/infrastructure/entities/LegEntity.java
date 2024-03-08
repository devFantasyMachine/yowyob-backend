/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.DriverEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopArrivalEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopPointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@Table(value = "legs")
@NoArgsConstructor
@AllArgsConstructor
public class LegEntity {


    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "plan_id")
    private UUID planId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1, ordering = Ordering.ASCENDING, name = "leg_index")
    private Integer legIndex;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2, name = "planner_id")
    private String plannerId;

    @Column(value = "from_location")
    private StopPointEntity fromLocation;

    @Column(value = "to_location")
    private StopPointEntity toLocation;


    @Frozen
    @Column(value = "intermediate_stop")
    private List<StopArrivalEntity> intermediateStops;

    @Column(value = "transit_duration")
    private Long transitDuration;

    @Column(value = "total_duration")
    private Long totalDuration;

    @Column(value = "distance")
    private Double distanceMeters;

    @Column(value = "direct_distance")
    private Double directDistanceMeters;

    @Column(value = "mode")
    private TransportMode transportMode;

    private String timezone;

    private LocalDateTime departure;

    @Column(value = "vehicle")
    private VehicleEntity vehicleInfo;


    @Column(value = "driver")
    private DriverEntity driverInfo;


}

