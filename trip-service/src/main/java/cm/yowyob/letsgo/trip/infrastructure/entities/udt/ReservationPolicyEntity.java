package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("reservation_policy")
public class ReservationPolicyEntity {

    @Column(value = "confirm_with_reservation")
    private Boolean confirmWithReservation;

    @Column(value = "duration_before_last_reservation")
    private Long durationBeforeLastReservation;

    @Column(value = "confirmation_delay_after_reservation")
    private Long confirmationDelayAfterReservation;

    @Column(value = "max_seat_count_per_user")
    private Integer maxPlaceCountPerUser;

    @Column(value = "max_luggage_per_user")
    private Float maxLuggagePerUser;

    @Column(value = "max_free_luggage_weight_per_user")
    private Float maxFreeLuggageWeightPerUser;

    @Column(value = "require_planner_consent")
    private Boolean requirePlannerConsent;



}
