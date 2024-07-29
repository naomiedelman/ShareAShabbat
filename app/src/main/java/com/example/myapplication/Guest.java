package com.example.myapplication;

public class Guest {
    private String name;
    private int age;
    private String username;
    private GuestType guestType;

    public Guest() {
    }

    public Guest(String name, int age, String username, GuestType guestType) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.guestType = guestType;
    }

    public String getName() {
        return this.name;
    }

    public String getAge() {
        return Integer.toString(this.age);
    }

    public int getAgeAsInt() {
        return this.age;
    }

    public String getUsername() {
        return this.username;
    }

    public String getGuestType() {
        switch (this.guestType) {
            case GUEST:
                return "GUEST";
            case HOST:
                return "HOST";
            default:
                return "";
        }
    }
}
