package com.example.ticketreservationsystem.service;

// https://codingsonata.com/retrofit-tutorial-android-request-headers/

import com.example.ticketreservationsystem.models.Train;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// https://www.youtube.com/watch?v=H-m1FK-PV7Q&list=PLirRGafa75rQPdUwDCSt-HjHHpDJcXwnu&index=2
public interface TrainService {
    @GET("api/Train/{id}")
    Call<List<Train>> getTrains(@Path("id") String id);
}
