package com.example.ticketreservationsystem.models;

// User model to save id, username, NIC, email, phone, address, and account status whether active or disabled in the database
public class User {
    // User class private attributes
    private String id;
    private String nic;
    private String email;
    private String username;
    private String password;
    private String type;
    private String createdAt;
    private String updatedAt;
    private String status;
    private String phone;
    private String address;

    public User() {
        // Default constructor
    }

    public User(String id, String nic, String email, String username, String password, String type, String createdAt, String updatedAt, String status, String phone, String address) {
        this.id = id;
        this.nic = nic;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

