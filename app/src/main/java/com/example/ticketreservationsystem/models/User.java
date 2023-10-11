package com.example.ticketreservationsystem.models;

public class User {
    private String nic;
    private String name;
    private String phone;
    private String email;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String nic, String name, String phone, String email) {
        this.nic = nic;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Add getters and setters for the fields

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

