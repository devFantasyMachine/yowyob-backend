package cm.yowyob.letsgo.trip.infrastructure.entities.udt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("address")
public class AddressEntity {
    private final String continent;
    private final String country; // pays
    private final String region; // continent
    private final String state; // etat, province
    private final String town; // city
    private final String timezone;
    private String street;
    private String postalCode;
    private Boolean isCurrent;
    private Double lat;
    private Double lon;
    private final LocalDateTime addAt;
}
