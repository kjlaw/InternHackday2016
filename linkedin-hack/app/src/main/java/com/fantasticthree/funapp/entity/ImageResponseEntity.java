package com.fantasticthree.funapp.entity;

public class ImageResponseEntity {
    String userId;
    String name;
    String company;
    String email;
    boolean found;

    public String getUserId() {
        return userId;
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
