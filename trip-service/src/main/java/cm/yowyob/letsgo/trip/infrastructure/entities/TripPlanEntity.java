/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.PaymentMethod;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopLocationEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Builder
@Table(value = "trip_plan")
public class TripPlanEntity {


    @Column(value = "trip_plan_id")
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID tripPlanId;

    @Column(value = "is_published")
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private boolean isPublished;

    //@Indexed
    @Column(value = "planner_id")
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String plannerId;

    @Column(value = "route_id")
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String routeId;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private ZonedDateTime departure;


    @Column(value = "from_location")
    @CassandraType(userTypeName = "stop_location", type = CassandraType.Name.UDT)
    private StopLocationEntity fromLocation;

    @Column(value = "to_location")
    @CassandraType(userTypeName = "stop_location", type = CassandraType.Name.UDT)
    private StopLocationEntity toLocation;

    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.VARCHAR, CassandraType.Name.TEXT})
    private Map<String, String> comfortsDesc;

    @Column(value = "trip_type")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private TripType tripType;

    @Column(value = "planned_at")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private ZonedDateTime plannedAt;

    @Column(value = "trip_prestige")
    private TripPrestige tripPrestige;

    @Column(isStatic = true, value = "payment_methods")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.VARCHAR, CassandraType.Name.VARCHAR})
    private Map<PaymentMethod, String> paymentMethods;

    @Column(isStatic = true, value = "currency_code")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private String currencyCode;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String note;

    @Frozen
    @Column(value = "trip_presentation_items")
    private Set<TripPresentationItemEntity> tripPresentationItems;





}
