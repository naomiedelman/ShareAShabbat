package com.example.myapplication;

public enum UserType {
    HOST,
    GUEST;

    private String inverse;

    static {
        HOST.inverse = "host";
        GUEST.inverse = "guest";
    }

    public String getInverseUserType() {
        return inverse;
    }

    public static UserType getUserType(String s) {
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
