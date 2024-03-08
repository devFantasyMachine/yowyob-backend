package cm.yowyob.letsgo.driver.domain.model.driver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;


@Data
@Builder
@AllArgsConstructor
public final class DriverPricing {

    public static DriverPricing NONE = DriverPricing.builder()
            .canNegotiated(true)
            .build();

    private Currency currency;
    private Boolean canNegotiated;
    private Float pricePerDay;
    private Float pricePerHour;
    private Float pricePerKilometer;

}
