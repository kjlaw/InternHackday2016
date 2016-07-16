package com.fantasticthree.funapp.entity;

public class UserProfileEntity {

    private String firstName;
    private String headline;
    private String lastName;

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getHeadLine() {
        return headline;
    }
}
