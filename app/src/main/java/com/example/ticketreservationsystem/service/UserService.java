package com.example.ticketreservationsystem.service;

import com.example.ticketreservationsystem.RetrofitClient;
import com.example.ticketreservationsystem.models.User;

import java.util.List;

import retrofit2.Call;

public class UserService {
    private UserApi userApi;

    public UserService(UserApi userApi) {
        this.userApi = userApi;
    }

    // Method to create a user
    public Call<User> createUser(User user) {
        return userApi.createUser(user);
    }

    // Method to get a user by ID
    public Call<User> getUserById(String id) {
        return userApi.getUserById(id);
    }

    public Call<List<User>> getUsers(){
        return userApi.getUsers();
    }
}
