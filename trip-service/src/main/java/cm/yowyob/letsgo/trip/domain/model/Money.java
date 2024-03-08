/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @param currency The getCurrency of the money.
 * @param amount   The actual getCurrency value in the minor unit, so 1 Euro is represented as 100.
 */
public record Money(Currency currency, float amount) implements Comparable<Money> {

    public static final Money NONE = new Money(null, 0);

    public static Money unknownCurrency(float cents) {
        return new Money(null, cents);
    }

    public static Money euros(float cents) {
        return new Money(Currency.getInstance("EUR"), cents);
    }

    public static Money xaf(float cents) {
        return new Money(Currency.getInstance("XAF"), cents);
    }

    public static Money usDollars(float cents) {
        return new Money(Currency.getInstance("USD"), cents);
    }

    public static Money amount(Float newCost) {
        return unknownCurrency(newCost);
    }

    public Money plus(@NonNull Money money){

        if (currency != money.currency)
            throw new RuntimeException("Can't compare " + money.currency + " to " + currency);

        return new Money(currency, amount + money.amount);
    }

    public Money withSameCurrency(float cents){

        return new Money(currency, cents);
    }

    public Money withSameCurrency(Money money){

        return new Money(money.currency, amount);
    }

    @JsonIgnore
    public boolean isPositive(){

        return amount > 0;
    }

    @Override
    public int compareTo(Money m) {
        if (m.currency != currency) {
            throw new RuntimeException("Can't compare " + m.currency + " to " + currency);
        }
        return Float.compare(amount, m.amount);
    }

    public String localize(Locale loc) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(loc);
        nf.setCurrency(currency);
        nf.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        return nf.format(amount / (Math.pow(10, currency.getDefaultFractionDigits())));
    }

    @Override
    public String toString() {
        return localize(Locale.FRENCH);
    }

    public Money plus(float overflowCost) {
        return plus(new Money(currency, overflowCost));
    }

    public Money minus(float overflowCost) {
        return plus(new Money(currency, -overflowCost));
    }
}
