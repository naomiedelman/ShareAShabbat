package com.example.myapplication;

import com.google.firebase.firestore.PropertyName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class User {
    private String name;
    private int age;
    private String username;
    private UserType userType;
    private int avatarIndex;
    private final static int maxAvatarIndex = 8;
    private final static int minAvatarIndex = 1;

    public User() {
    }

    public User(String name, int age, String username, UserType userType, int avatarIndex) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.userType = userType;
        if (avatarIndex < 1) {
            Random r = new Random();
            this.avatarIndex = r.nextInt(maxAvatarIndex - minAvatarIndex + 1) + minAvatarIndex;
        } else {
            this.avatarIndex = avatarIndex;
        }

    }

    @PropertyName("name")
    public String getName() {
        return this.name;
    }

    @PropertyName("age")
    public String getAge() {
        return Integer.toString(this.age);
    }

    @PropertyName("avatarIndex")
    public int getAvatarIndex() {
        return this.avatarIndex;
    }

    @PropertyName("age")
    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    @PropertyName("username")
    public String getUsername() {
        return this.username;
    }


    @PropertyName("userType")
    public String getUserType() {
        switch (this.userType) {
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
        obj.put("userType", this.userType);
        obj.put("avatarIndex", this.avatarIndex);
        return obj;
    }

    public static User deserialize(String rawGuest) throws JSONException {
        JSONObject obj = new JSONObject(rawGuest);
        String name = obj.getString("name");
        int age = obj.getInt("age");
        String username = obj.getString("username");
        UserType userType = UserType.getUserType(obj.getString("userType"));
        int avatarIndex = obj.getInt("avatarIndex");
        return new User(name, age, username, userType, avatarIndex);
    }
}
