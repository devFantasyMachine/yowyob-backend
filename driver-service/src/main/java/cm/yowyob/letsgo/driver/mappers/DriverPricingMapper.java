package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.DriverPricing;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DriverPricingEntity;

import java.util.Currency;

public class DriverPricingMapper extends Mapper<DriverPricing, DriverPricingEntity> {

    @Override
    public DriverPricing toObject(DriverPricingEntity entity) {

        if (entity == null)
            return null;

        return DriverPricing.builder()
                .canNegotiated(entity.getCanNegotiated())
                .pricePerDay(entity.getPricePerDay())
                .pricePerHour(entity.getPricePerHour())
                .pricePerKilometer(entity.getPricePerKilometer())
                .currency(entity.getCurrencyCode() == null ? null : Currency.getInstance(entity.getCurrencyCode()))
                .build();
    }

    @Override
    public DriverPricingEntity toEntity(DriverPricing object) {

        if (object == null)
            return null;

        return DriverPricingEntity.builder()
                .canNegotiated(object.getCanNegotiated())
                .pricePerDay(object.getPricePerDay())
                .pricePerHour(object.getPricePerHour())
                .pricePerKilometer(object.getPricePerKilometer())
                .currencyCode(object.getCurrency() == null ? null : object.getCurrency().getCurrencyCode())
                .build();
    }

}
