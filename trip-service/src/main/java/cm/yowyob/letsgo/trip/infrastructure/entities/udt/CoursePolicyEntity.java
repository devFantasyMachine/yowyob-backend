package cm.yowyob.letsgo.trip.infrastructure.entities.udt;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("course_policy")
public class CoursePolicyEntity {

    @Column(value = "max_waiting_duration_on_stop")
    private Long maxWaitingDurationOnStop;

    @Column(value = "max_departure_waiting_duration")
    private Long maxDepartureWaitingDuration;


}
