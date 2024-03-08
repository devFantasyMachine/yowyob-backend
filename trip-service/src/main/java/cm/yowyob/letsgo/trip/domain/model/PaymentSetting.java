/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;


import lombok.Getter;

import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Booking settings
 */
@Getter
public final class PaymentSetting {

    /**
     *  None means that, the planner doesn't set payment setting yet
     */
    public static final PaymentSetting NONE = new PaymentSetting((String) null, Map.of());


    private final Currency currency;
    private final Map<PaymentMethod, String> methods;

    /**
     */
    public PaymentSetting(Currency currency, Map<PaymentMethod, String> methods) {
        this.currency = currency;
        this.methods = Objects.requireNonNullElse(methods, Map.of());
    }

    public PaymentSetting(String currencyCode, Map<PaymentMethod, String> methods) {
        this(currencyCode == null ? null : Currency.getInstance(currencyCode), methods);
    }

    public static PaymentSetting of(String currencyCode, Map<PaymentMethod, String> paymentMethods) {
        return new PaymentSetting(currencyCode, paymentMethods);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PaymentSetting) obj;
        return Objects.equals(this.currency, that.currency) &&
                Objects.equals(this.methods, that.methods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, methods);
    }

    @Override
    public String toString() {
        return "PaymentSetting[" +
                "getCurrency=" + currency + ", " +
                "getMethods=" + methods + ']';
    }

    public String getCurrencyCode() {

        return currency == null ? null : currency.getCurrencyCode();
    }

}


