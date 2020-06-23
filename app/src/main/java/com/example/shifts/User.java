package com.example.shifts;

public class User {
    private String fullName;
    private String email;
    private Float hourlWage;

    public User(String fullName, String email, Float hourlWage) {
        this.fullName = fullName;
        this.hourlWage = hourlWage;
        this.email = email;
    }

    public User(){}

    public void setHourlyAge(Float hourlyAge) {
        this.hourlWage = hourlyAge;
    }

    public Float getHourlyAge() {
        return hourlWage;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
