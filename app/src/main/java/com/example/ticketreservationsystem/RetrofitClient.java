package com.example.ticketreservationsystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.8.101:7193/";

    public static Retrofit getRetrofitInstance() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .connectTimeout(30, TimeUnit.SECONDS) // Adjust the timeout as needed
                .readTimeout(30, TimeUnit.SECONDS) // Adjust the timeout as needed
                .writeTimeout(30, TimeUnit.SECONDS) // Adjust the timeout as needed
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
