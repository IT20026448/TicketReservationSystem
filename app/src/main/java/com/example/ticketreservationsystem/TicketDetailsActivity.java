package com.example.ticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketreservationsystem.models.Ticket;
import com.example.ticketreservationsystem.service.TicketApi;
import com.example.ticketreservationsystem.service.TicketService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE

public class TicketDetailsActivity extends AppCompatActivity {

    // Get references to the TextView elements
    TextView ticketIdTextView;
    TextView trainNameTextView;
    TextView timeTextView;
    TextView startLocationTextView;
    TextView departureLocationTextView;
    TextView createdTimeTextView;
    TextView lastUpdatedTimeTextView;
    TextView statusTextView;
    TextView numberOfReservationsTextView;
    TextView assigneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        ticketIdTextView = findViewById(R.id.ticketIdTextView);
        trainNameTextView = findViewById(R.id.trainNameTextView);
        timeTextView = findViewById(R.id.timeTextView);
        startLocationTextView = findViewById(R.id.startLocationTextView);
        departureLocationTextView = findViewById(R.id.departureLocationTextView);
        createdTimeTextView = findViewById(R.id.createdTimeTextView);
        lastUpdatedTimeTextView = findViewById(R.id.lastUpdatedTimeTextView);
        statusTextView = findViewById(R.id.statusTextView);
        numberOfReservationsTextView = findViewById(R.id.numberOfReservationsTextView);
        assigneTextView = findViewById(R.id.assigneTextView);

        // Retrieve ticket details by ID
        String ticketId = "a4eed4c73290a204a8614595"; // Replace with your actual ticket ID
        retrieveTicketDetails("a4eed4c73290a204a8614595");
    }

    private void retrieveTicketDetails(String ticketId) {
        // Create a Retrofit instance using the RetrofitClient
        TicketApi ticketApi = RetrofitClient.getRetrofitInstance().create(TicketApi.class);

        // Call the getTicketById method to retrieve the ticket details
        Call<Ticket> call = ticketApi.getTicketById(ticketId);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                int responseCode = response.code();
                if (response.isSuccessful()) {
                    Ticket ticket = response.body();
                    displayTicketDetails(ticket);
                } else {
                    // Handle the case where the API request was successful but the ticket couldn't be found
                    Toast.makeText(TicketDetailsActivity.this, "Ticket not found", Toast.LENGTH_SHORT).show();
                    Log.e("TicketDetailsActivity", "Ticket not found");
                    // Handle the case where the server returned an error
                    Log.e("TicketDetailsActivity", "API Error: " + responseCode + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                // Handle the case where there was a network error or the API request failed
                Toast.makeText(TicketDetailsActivity.this, "Failed to retrieve ticket details", Toast.LENGTH_SHORT).show();
                Log.e("TicketDetailsActivity", "Failed to retrieve ticket details", t);
            }
        });
    }

    private void displayTicketDetails(Ticket ticket) {
        // Display the ticket details in TextView elements
        if (ticket != null) {
            ticketIdTextView.setText("Ticket ID: " + ticket.getId());
            trainNameTextView.setText("Train Name: " + ticket.getTrainName());
            timeTextView.setText("Time: " + ticket.getTime());
            startLocationTextView.setText("Start Location: " + ticket.getStartLocation());
            departureLocationTextView.setText("Departure Location: " + ticket.getDepartureLocation());
            createdTimeTextView.setText("Created Time: " + ticket.getCreatedTime());
            lastUpdatedTimeTextView.setText("Last Updated Time: " + ticket.getLastUpdatedTime());
            statusTextView.setText("Status: " + ticket.getStatus());
            numberOfReservationsTextView.setText("Number of Reservations: " + ticket.getNumberOfReservations());
            assigneTextView.setText("Assignee: " + ticket.getAssigne());

            Log.d("TicketDetailsActivity", "Ticket ID: " + ticket.getId());
            Log.d("TicketDetailsActivity", "Train Name: " + ticket.getTrainName());
            Log.d("TicketDetailsActivity", "Time: " + ticket.getTime());
            Log.d("TicketDetailsActivity", "Start Location: " + ticket.getStartLocation());
            Log.d("TicketDetailsActivity", "Departure Location: " + ticket.getDepartureLocation());
            Log.d("TicketDetailsActivity", "Created Time: " + ticket.getCreatedTime());
            Log.d("TicketDetailsActivity", "Last Updated Time: " + ticket.getLastUpdatedTime());
            Log.d("TicketDetailsActivity", "Status: " + ticket.getStatus());
            Log.d("TicketDetailsActivity", "Number of Reservations: " + ticket.getNumberOfReservations());
            Log.d("TicketDetailsActivity", "Assignee: " + ticket.getAssigne());
        }
    }
}