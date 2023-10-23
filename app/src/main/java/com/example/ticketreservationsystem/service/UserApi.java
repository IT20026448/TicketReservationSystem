package com.example.ticketreservationsystem.service;

import com.example.ticketreservationsystem.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE
//https://codingsonata.com/retrofit-tutorial-android-request-headers/
//https://www.digitalocean.com/community/tutorials/retrofit-android-example-tutorial
//Create a Retrofit API interface, define endpoints and get the entries from the database
public interface UserApi {
    @POST("/api/User")
    Call<User> createUser(@Body User user);

    @GET("/api/User/{id}")
    Call<User> getUserById(@Path("id") String id);
}
