package com.example.ticketreservationsystem.service;

import com.example.ticketreservationsystem.RetrofitClient;
import com.example.ticketreservationsystem.models.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TicketService {
    private TicketApi ticketApi;

    public TicketService() {
        ticketApi = RetrofitClient.getRetrofitInstance().create(TicketApi.class);
    }

    public Call<Ticket> getTicketById(String id) {
        return ticketApi.getTicketById(id);
    }
}
