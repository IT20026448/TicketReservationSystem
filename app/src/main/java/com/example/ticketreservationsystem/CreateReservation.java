package com.example.ticketreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ticketreservationsystem.models.Train;
import com.example.ticketreservationsystem.models.Reservation;
import com.example.ticketreservationsystem.service.TrainService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// this class is linked to the activity_create_reservation.xml page.
// display train details when user clicks reserve on the previous page
// user can create the reservation here.
public class CreateReservation extends AppCompatActivity {
    // instance of Random to create hex 24 strings
    private static Random random = new Random();
    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    // Instance of firebase database.
    // https://firebase.google.com/docs/database/android/read-and-write
    DatabaseReference database, bookingDB;
    // Define elements from activity_create_reservation.xml
    TextView totalPriceTextView, selectedDate, trainIDTextView;
    EditText quantityEditText;
    Button increaseQuantityButton, decreaseQuantityButton, cancelBookingBtn, pickDateBtn, reserveButton;
    // create instance of Train model class to get the train details.
    Train selectedTrain;
    // initiate quantity, number of tickets to be booked by the user.
    private int quantity = 1; // Default quantity is 1
    // declare variable to calculate the total price
    private double total;

    //https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE
    // create static reference to TicketApiManager
    //public static TicketApiManager ticketApiManager;

    // onCreate method performs actions when the page loads.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        // https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE
        // singleton instance of the TicketApiManager
        //ticketApiManager = TicketApiManager.getInstance();

        //https://codingsonata.com/retrofit-tutorial-android-request-headers/
        // https://www.youtube.com/watch?v=H-m1FK-PV7Q&list=PLirRGafa75rQPdUwDCSt-HjHHpDJcXwnu&index=2
        // Create a retrofit instance to connect the mobile application
        // to the MongoDB and access the collections using the C# web service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.1.5:7193/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a TrainService instance
        TrainService trainService = retrofit.create(TrainService.class);

        // Make the API GET request to get the reservation details
        Call<List<Train>> call = trainService.getTrains("a4eed4c73290a204a8614595");

        call.enqueue(new Callback<List<Train>>() {
            @Override
            public void onResponse(Call<List<Train>> call, Response<List<Train>> response) {
                List<Train> trains = response.body();

                // use a loop to access and display train details
                for(int i = 0; i < trains.size(); i++){
                    Log.d("API response", "Train ID: " + trains.get(i).getTrainID());
                    Log.d("API response", "Start Location: " + trains.get(i).getStartLoc());
                }

            }

            @Override
            public void onFailure(Call<List<Train>> call, Throwable t) {
                Log.e("CreateReservation", "API Call Failed", t);
            }
        });

        // Retrieve views
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        quantityEditText = findViewById(R.id.quantityEditText);
        increaseQuantityButton = findViewById(R.id.increaseQuantityButton);
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButton);
        cancelBookingBtn = findViewById(R.id.cancelRes);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDate = findViewById(R.id.idTVSelectedDate);
        reserveButton = findViewById(R.id.confirmRes);

        // Set the EditText text to 1
        quantityEditText.setText(String.valueOf(quantity));

        // Retrieve the trainID from the intent
        String trainID = getIntent().getStringExtra("trainID");

        // https://firebase.google.com/docs/database/android/read-and-write
        // database reference to trains collection
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
            // https://firebase.google.com/docs/database/android/read-and-write
            String selectedDateVal = selectedDate.getText().toString();
            String trainId = trainIDTextView.getText().toString();
            String totPrice = totalPriceTextView.getText().toString();
            String noOfTickets = quantityEditText.getText().toString();
            // referenceNo is a 24 digit hex string
            String referenceNo = generateHexString();

            // Create a Reservation object to store the reservation details
            //Reservation reservation = new Reservation(referenceNo, trainId, noOfTickets, totPrice, selectedDateVal);

            // https://firebase.google.com/docs/database/android/read-and-write
            // Get a reference to the "reservations" collection
            DatabaseReference reservationsDatabase = FirebaseDatabase.getInstance().getReference("reservations");

            // Push the reservation to the "reservations" collection
            //reservationsDatabase.child(referenceNo).setValue(reservation);

            // navigate to summary page
            Intent intent = new Intent(getApplicationContext(), ReservationSummary.class);
            startActivity(intent);
            finish();
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

    // http://www.java2s.com/example/android/java.util/generate-random-hex-string-of-wanted-size.html
    public String generateHexString(){
        StringBuffer sb = new StringBuffer();
        random.nextInt(10);
        for (int i = 0; i < 24; i++) {
            sb.append(hexDigits[random(hexDigits.length)]);
        }
        return sb.toString();
    }

    public static int random(int maxValue) {
        return random.nextInt(maxValue);
    }
}