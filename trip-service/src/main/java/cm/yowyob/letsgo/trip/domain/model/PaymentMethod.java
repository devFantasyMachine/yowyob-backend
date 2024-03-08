/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

public enum PaymentMethod {

    MOBILE_MONEY("MOBILE_MONEY"),
    PAYPAL ("PAYPAL"),
    CASH("CASH"),
    VISA("VISA"),
    MASTERCARD("MASTERCARD");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }
}
