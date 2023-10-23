package com.example.ticketreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// handle reservations made by user
public class Reservation extends AppCompatActivity {
    private TextView reservationInfoTextView;
    private EditText ticketNumberEditText;
    private Button bookTicketButton, cancelButton, modifyButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("reservations");

        reservationInfoTextView = findViewById(R.id.reservationInfoTextView);
        ticketNumberEditText = findViewById(R.id.ticketNumberEditText);
        bookTicketButton = findViewById(R.id.bookTicketButton);
        cancelButton = findViewById(R.id.cancelButton);
        modifyButton = findViewById(R.id.modifyButton);

        if (user != null) {
            final String userId = user.getUid();

            // Load reservation information if available
            databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        ReservationInfo reservationInfo = dataSnapshot.getValue(ReservationInfo.class);
//                        reservationInfoTextView.setText("Ticket Number: " + reservationInfo.getTicketNumber());
//                    } else {
//                        reservationInfoTextView.setText("No reservation available.");
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });

            bookTicketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String ticketNumber = ticketNumberEditText.getText().toString().trim();
//                    ReservationInfo reservationInfo = new ReservationInfo(ticketNumber);
//
//                    // Save the reservation information to Firebase
//                    databaseReference.child(userId).setValue(reservationInfo);
//
//                    Toast.makeText(Reservation.this, "Ticket booked.", Toast.LENGTH_SHORT).show();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    // Remove the reservation from Firebase
//                    databaseReference.child(userId).removeValue();
//
//                    // Clear the ticket number field
//                    ticketNumberEditText.setText("");
//
//                    Toast.makeText(Reservation.this, "Reservation canceled.", Toast.LENGTH_SHORT).show();
                }
            });

            modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String newTicketNumber = ticketNumberEditText.getText().toString().trim();
//
//                    if (!newTicketNumber.isEmpty()) {
//                        // Update the ticket number in Firebase
//                        databaseReference.child(userId).child("ticketNumber").setValue(newTicketNumber);
//
//                        Toast.makeText(Reservation.this, "Reservation modified.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(Reservation.this, "Please enter a valid ticket number.", Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        }
    }
}