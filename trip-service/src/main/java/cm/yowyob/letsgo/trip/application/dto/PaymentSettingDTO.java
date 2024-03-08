/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.PaymentMethod;
import cm.yowyob.letsgo.trip.domain.model.PaymentSetting;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettingDTO {

    private @Nonnull String currency;
    private Map<PaymentMethod, String> methods;

    public static PaymentSettingDTO fromPayment(PaymentSetting paymentSetting) {
        return new PaymentSettingDTO(paymentSetting.getCurrencyCode(), paymentSetting.getMethods());
    }

    public PaymentSetting toPaymentSetting() {
        return new PaymentSetting(Currency.getInstance(currency), methods);
    }
}
