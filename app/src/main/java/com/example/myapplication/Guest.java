package com.example.myapplication;

import com.google.firebase.firestore.PropertyName;

import org.json.JSONException;
import org.json.JSONObject;

public class Guest {
    private String name;
    private int age;
    private String username;
    private GuestType guestType;
    private String location;

    public Guest() {
    }

    public Guest(String name, int age, String username, GuestType guestType) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.guestType = guestType;
    }

    @PropertyName("name")
    public String getName() {
        return this.name;
    }

    @PropertyName("age")
    public String getAge() {
        return Integer.toString(this.age);
    }

    @PropertyName("age")
    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    @PropertyName("username")
    public String getUsername() {
        return this.username;
    }


    @PropertyName("guestType")
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

    public JSONObject serialize() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("age", this.age);
        obj.put("username", this.username);
        obj.put("guestType", this.guestType);
        return obj;
    }

    public static Guest deserialize(String rawGuest) throws JSONException {
        JSONObject obj = new JSONObject(rawGuest);
        String name = obj.getString("name");
        int age = obj.getInt("age");
        String username = obj.getString("username");
        GuestType guestType = GuestType.getGuestType(obj.getString("guestType"));
        return new Guest(name, age, username, guestType);
    }
}
