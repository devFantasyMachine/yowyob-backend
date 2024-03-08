package com.example.demo.entities;

public enum SenderMethod {
    EMAIL("EMAIL"),
    SMS("SMS"),
    WHATSAPP("WHATSAPP");

    private final String methodName;

    private SenderMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
