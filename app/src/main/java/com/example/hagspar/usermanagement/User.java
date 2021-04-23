package com.example.hagspar.usermanagement;


import com.example.hagspar.forecast.data.Forecast;

import java.util.ArrayList;
import java.util.List;

public class  User {
/*
    User.java
    Object used to store users.
    JPA:
        - One-to-Many relationship with Forecast

    List of methods
        - Basic Getters and Setters
*/
    public long id;

    public String username;
    public String userPassword;
    private String Name;
    private String Email;
    public Boolean isEnabled;
    public Boolean isAdmin;

    private List<Forecast> forecasts = new ArrayList<Forecast>();

    // Constructor is empty since HomeController handles construction
    public User() { }

    // Getters and setters

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<Forecast> getForecasts() { return forecasts; }

    public void setForecasts(List<Forecast> forecasts) { this.forecasts = forecasts; }
}
