package com.fantasticthree.funapp.entity;

public class ImageResponseEntity {
    String uuid;
    String name;
    String company;
    String email;
    boolean found;

    public String getUserId() {
        return uuid;
    }

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSuccessful(){
        return found;
    }
}
