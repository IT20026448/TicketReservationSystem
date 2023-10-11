package com.example.ticketreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ticketreservationsystem.models.Train;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreateReservation extends AppCompatActivity {
    DatabaseReference database, bookingDB;
    TextView totalPriceTextView, selectedDate, trainIDTextView;
    EditText quantityEditText;
    Button increaseQuantityButton, decreaseQuantityButton, cancelBookingBtn, pickDateBtn, reserveButton;
    Train selectedTrain;

    private int quantity = 1; // Default quantity is 1
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        // Retrieve views
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        quantityEditText = findViewById(R.id.quantityEditText);
        increaseQuantityButton = findViewById(R.id.increaseQuantityButton);
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButton);
        cancelBookingBtn = findViewById(R.id.cancelRes);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDate = findViewById(R.id.idTVSelectedDate);
        reserveButton = findViewById(R.id.confirmRes);

        // Set the default quantity to 1
        quantity = 1;
        quantityEditText.setText(String.valueOf(quantity)); // Set the EditText text to 1

        // Retrieve the trainID from the intent
        String trainID = getIntent().getStringExtra("trainID");

        database = FirebaseDatabase.getInstance().getReference("trains");

        // get instance of bookings collection
        bookingDB = FirebaseDatabase.getInstance().getReference("bookings");

        // Query the database for the train with the matching trainID
        Query query = database.orderByChild("trainID").equalTo(trainID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Train train = dataSnapshot.getValue(Train.class);

                        displayTrainDetails(train);

                        // Set selectedTrain and calculate total price
                        selectedTrain = train;
                        calculateTotalPrice();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set click listeners for quantity buttons
        increaseQuantityButton.setOnClickListener(v -> {
            quantity++;
            quantityEditText.setText(String.valueOf(quantity));
            calculateTotalPrice();
        });

        decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityEditText.setText(String.valueOf(quantity));
                calculateTotalPrice();
            }
        });

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    quantity = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    quantity = 1; // Reset to 1 if invalid input
                }
                calculateTotalPrice();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancelBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrainList.class);
                startActivity(intent);
                finish();
            }
        });

//        https://www.geeksforgeeks.org/datepicker-in-android/
        // on below line we are adding click listener for our pick date button
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        CreateReservation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                selectedDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        // when reserve button is clicked navigate to summary page and give option to book
        // save data in bookings collection
        reserveButton.setOnClickListener(v -> {
            String selectedDateVal = selectedDate.getText().toString();
            String trainId = trainID;
            double totPrice = total;
            String noOfTick = quantityEditText.getText().toString();

            // reference to bookings collection based on trainID of trains collection
            DatabaseReference bookingReference = bookingDB.child(trainId);

            // add the reservation date
        });
    }

    private void displayTrainDetails(Train train) {
        TextView trainIDTextView = findViewById(R.id.trainIDTextView);
        TextView deptTimeTextView = findViewById(R.id.deptTimeTextView);
        TextView arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        TextView startLocTextView = findViewById(R.id.startLocTextView);
        TextView destinationTextView = findViewById(R.id.destinationTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);

        trainIDTextView.setText("Train ID: " + train.getTrainID());
        deptTimeTextView.setText("Departure Time: " + train.getDeptTime());
        arrivalTimeTextView.setText("Arrival Time: " + train.getArrivalTime());
        startLocTextView.setText("Start Location: " + train.getStartLoc());
        destinationTextView.setText("Destination: " + train.getDestination());
        priceTextView.setText("Price: " + train.getPrice());
    }

    private void calculateTotalPrice() {
        if (selectedTrain != null) {
            total = quantity * selectedTrain.getPrice();
            totalPriceTextView.setText("Total Price: " + total);
        }
    }
}