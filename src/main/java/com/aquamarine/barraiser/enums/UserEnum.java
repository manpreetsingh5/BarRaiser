package com.aquamarine.barraiser.enums;

public enum UserEnum {
    TRAINEE("Trainee"),
    BARTENDER("Bartender");

    private final String label;

    UserEnum(String label) {
        this.label = label;
    }
}
