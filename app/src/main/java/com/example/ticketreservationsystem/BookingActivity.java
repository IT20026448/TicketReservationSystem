package com.example.ticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookingActivity extends AppCompatActivity {
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    }
}