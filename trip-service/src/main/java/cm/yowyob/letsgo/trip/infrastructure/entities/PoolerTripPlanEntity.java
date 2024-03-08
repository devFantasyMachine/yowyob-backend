/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.PaymentMethod;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlanStatus;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicyLevel;
import cm.yowyob.letsgo.trip.domain.model.publish.SubscriberType;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ComfortEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.PoolerInfoEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopLocationEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "pooler_trip_plan")
public class PoolerTripPlanEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "plan_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID tripPlanId;


    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1, name = "planner_id")
    private String plannerId;

    @Column(value = "draft_id")
    private UUID draftId;

    @Column(value = "publish_code")
    private String publishCode;

    private String timezone;

    @Column(value = "status")
    private PlanStatus planStatus;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime departure;

    @Column(value = "from_location")
    private StopLocationEntity fromLocation;

    @Column(value = "to_location")
    private StopLocationEntity toLocation;

    private Set<ComfortEntity> comforts;

    @Column(value = "service_type")
    private ServiceType serviceType;

    @Column(value = "trip_type")
    private TripType tripType;

    @Column(value = "planned_at")
    private LocalDateTime plannedAt;

    @Column(value = "trip_prestige")
    private TripPrestige tripPrestige;

    @Column(value = "total_cost")
    private Float totalCost;

    @Column(value = "place_count")
    private Integer placeCount;

    @Column(value = "vehicle")
    private VehicleEntity vehicleInfo;

    @Column(value = "currency")
    private String currencyCode;

    @Column(value = "transport_modes")
    private Set<TransportMode> transportMode;

    @Column(value = "luggage_box_quantity")
    private Float luggageBoxQuantity;

    @Column(value = "reservation_count")
    private Integer reservationCount;

    @Column(value = "payment_methods")
    @CassandraType(type = CassandraType.Name.MAP,
            typeArguments = {CassandraType.Name.VARCHAR, CassandraType.Name.TEXT})
    private Map<PaymentMethod, String> paymentMethods;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String note;

    @Column(value = "publish_at")
    private LocalDateTime publishedAt;

    @Column(value = "publisher_id")
    private String publisherId;

    @Column(value = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(value = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(value = "executed_at")
    private LocalDateTime executedAt;

    @Column(value = "shared_policy_level")
    private SharedPolicyLevel policyLevel;

    @Column(value = "is_bookable")
    private Boolean isBookable;

    @Column(value = "subscriber_types")
    private Set<SubscriberType> subscriberType;

    @Column(value = "pooler")
    private PoolerInfoEntity plannerInformation;


    @Transient
    private List<LegEntity> legs;

}
