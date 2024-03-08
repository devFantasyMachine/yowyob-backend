package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("pricing")
public class DriverPricingEntity {

    private String currencyCode;
    private Boolean canNegotiated;
    private Float pricePerDay;
    private Float pricePerHour;
    private Float pricePerKilometer;
}
