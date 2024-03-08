package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.driver.DriverPricing;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class DriverPricingDTO {

    @Nullable
    @Pattern(regexp = "^([A-Za-z])*$")
    private String currency;
    private Boolean canNegotiate;

    @Nullable
    @Positive
    private Float pricePerDay;

    @Nullable
    @Positive
    private Float pricePerHour;

    @Nullable
    @Positive
    private Float pricePerKilometer;

    public static DriverPricingDTO fromDomainObject(DriverPricing driverPricing1) {

        return driverPricing1 == null ? null : DriverPricingDTO.builder()
                .canNegotiate(driverPricing1.getCanNegotiated())
                .currency(driverPricing1.getCurrency() == null ? null : driverPricing1.getCurrency().getCurrencyCode())
                .pricePerHour(driverPricing1.getPricePerHour())
                .pricePerDay(driverPricing1.getPricePerHour())
                .pricePerKilometer(driverPricing1.getPricePerHour())
                .build();
    }

}
