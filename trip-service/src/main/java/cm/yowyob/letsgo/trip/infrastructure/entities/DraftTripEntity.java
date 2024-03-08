/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ComfortEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopLocationEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopPointEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@Table(value = "drafts")
public class DraftTripEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "draft_id")
    private UUID draftId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1, name = "owner_id")
    private String ownerId;

    @Column(value = "trip_type")
    private TripType tripType;

    @Column(value = "trip_prestige")
    private TripPrestige tripPrestige;

    @Column(value = "service_type")
    private ServiceType serviceType;

    @Column(value = "from_location")
    private StopLocationEntity fromLocation;

    @Column(value = "to_location")
    private StopLocationEntity toLocation;

    @Column(value = "intermediate_stops")
    private List<StopPointEntity> intermediateStops;

    private Set<ComfortEntity> comforts;

    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    private LocalDateTime updatedAt;

    @Column(value = "usage_count")
    private Integer usageCount;

}
