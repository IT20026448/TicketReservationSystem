package com.example.ticketreservationsystem.models;

// User model to save id, username, NIC, email, phone, address, and account status whether active or disabled in the database
public class User {
    // User class private attributes
    private String id;
    private String userName;
    private String nic;
    private String email;
    private String phone;
    private String address;
    private String status;
    private String password;
    private int type;

    public User() {
        // Default constructor
    }

    public User(String id, String userName, String nic, String email, String phone, String address, String status, int type) {
        this.id = id;
        this.userName = userName;
        this.nic = nic;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

