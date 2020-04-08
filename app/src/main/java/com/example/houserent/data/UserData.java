package com.example.houserent.data;

import java.util.ArrayList;

public class UserData {
    private String name = null;
    private String userName = null;
    private String email = null;
    private String phoneNumber = null;
    private String gender = null;
    private String password = null;
    private ArrayList<String> favouriteCars = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFavouriteCars() {
        return favouriteCars;
    }

    public void setFavouriteCars(ArrayList<String> favouriteCars) {
        this.favouriteCars = favouriteCars;
    }
}