package com.example.myapplication;

public enum GuestType {
    HOST,
    GUEST;

    private String inverse;

    static {
        HOST.inverse = "host";
        GUEST.inverse = "guest";
    }

    public String getInverseGuestType() {
        return inverse;
    }

    public static GuestType getGuestType(String s) {
        switch (s) {
            case "host":
                return HOST;
            case "guest":
                return GUEST;
            default:
                return GUEST;
        }
    }
}
