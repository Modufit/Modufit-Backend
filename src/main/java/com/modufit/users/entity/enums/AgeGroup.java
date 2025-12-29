package com.modufit.users.entity.enums;

public enum AgeGroup {
    TEENS_10("10s"),
    TWENTIES_20("20s"),
    THIRTIES_30("30s"),
    FORTIES_40("40s"),
    FIFTIES_50("50s"),
    SIXTIES_PLUS("60s_plus");

    private final String value;

    AgeGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}