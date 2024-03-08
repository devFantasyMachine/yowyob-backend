/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlanStatus;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicyLevel;
import cm.yowyob.letsgo.trip.domain.model.publish.SubscriberType;
import cm.yowyob.letsgo.trip.domain.model.resources.DiscountTicket;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.*;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "planner_trip_plan")
public class PlannerTripPlanEntity {

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

    @Column(value = "seat_count")
    private Integer seatCount;

    @Column(value = "transport_modes")
    private Set<TransportMode> transportMode;

    @Column(value = "luggage_box_quantity")
    private Float luggageBoxQuantity;

    @Column(value = "reservation_count")
    private Integer reservationCount;

    @Column(value = "payment_methods")
    private Set<String> paymentMethods;

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

    @Column(value = "planner")
    private PlannerInfoEntity plannerInformation;

    @Column(value = "trip_presentation_items")
    private Set<@Frozen TripPresentationItemEntity> tripPresentationItems;

    @Column(value = "payment_setting")
    private PaymentSettingEntity paymentSetting;


    //private Set<DiscountTicket> discountTickets;


    @Column(value = "seat_cost")
    private Float seatCost;

    @Column(value = "discount_ticket_count")
    private Integer discountTicketCount;

    @Column(value = "luggage_cost")
    private Float luggageCost;






    @Column(value = "course_policy")
    private CoursePolicyEntity coursePolicy;

    @Column(value = "reservation_policy")
    private ReservationPolicyEntity reservationPolicy;

    @Column(value = "confirmation_policy")
    private ConfirmationPolicyEntity confirmationPolicy;

    @Column(value = "cancellation_policy")
    private CancellationPolicyEntity cancellationPolicy;






    @Transient
    private List<LegEntity> legs;

    @Transient
    private List<TripResourceEntity> tripResourceEntities;


}
