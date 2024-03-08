package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@AllArgsConstructor
@UserDefinedType("stop_arrival")
public class StopArrivalEntity {

    private String name;
    private String city;
    private String timezone;
    private Double lon;
    private Double lat;
    private Integer pos;
    private Long delay;
}
