package com.example.ticketreservationsystem.service;

import com.example.ticketreservationsystem.models.Ticket;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE
//https://codingsonata.com/retrofit-tutorial-android-request-headers/
//Create a Retrofit API interface, define endpoints and get the entries from the database
public interface TicketApi {
    @GET("api/ticket/{id}")
    Call<Ticket> getTicketById(@Path("id") String id);
}
