package com.example.myapplication;
public class Guest {
    private String GuestImage;
    private String Name;
    private int Age;
    private String Username;
    private String Password;
    private String Sex;

    // No-argument constructor required for Firebase deserialization
    public Guest() {
    }

    // Argument constructor for creating new instances
    public Guest(String guestImage, String name, int age, String username, String password, String sex) {
        this.GuestImage = guestImage;
        this.Name = name;
        this.Age = age;
        this.Username = username;
        this.Password = password;
        this.Sex = sex;
    }

    // Getters and setters
    public String getGuestImage() {
        return GuestImage;
    }

    public void setGuestImage(String guestImage) {
        GuestImage = guestImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}
