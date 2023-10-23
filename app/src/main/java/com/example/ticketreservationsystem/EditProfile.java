package com.example.ticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketreservationsystem.models.Ticket;
import com.example.ticketreservationsystem.models.User;
import com.example.ticketreservationsystem.service.TicketApi;
import com.example.ticketreservationsystem.service.UserApi;
import com.example.ticketreservationsystem.service.UserService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    // Create variable for logging
    private static final String TAG = "EditProfile";

    // Create variables store references to user interface elements in the Android layout
    // create variable for text input to take the user input data
    TextInputEditText editTextUserName, editTextNIC, editTextEmail, editTextPassword, editTextPhone, editTextAddress;
    TextView userId;
    // create variable to reference registration button, Button UI element in the Android layout

    FirebaseAuth mAuth;
    String nic = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            email = currentUser.getEmail();

            // Split the email address using the "@" symbol
            String[] parts = email.split("@");

            // Check if the split resulted in two parts
            if (parts.length == 2) {
                // The part before "@" is the "NIC"
                nic = parts[0];
            }
        }

        editTextUserName = findViewById(R.id.user_name);
        editTextNIC = findViewById(R.id.NIC);
        editTextEmail = findViewById(R.id.email_address);
        editTextPhone = findViewById(R.id.phone);
        editTextAddress = findViewById(R.id.address);
        editTextPassword = findViewById(R.id.password);
        userId = findViewById(R.id.Id);

        retrieveUserDetailsById(email);
    }

    public void retrieveUserDetailsById(String id){
        // Create a Retrofit instance using the RetrofitClient
        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);

        // Create a UserService instance
        UserService userService = new UserService(userApi);

        // Call the getTicketById method to retrieve the user details
        Call<User> call = userService.getUserById(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int responseCode = response.code();
                if (response.isSuccessful()) {
                    User user = response.body();
                    displayUserDetails(user);
                } else {
                    // Handle the case where the API request was successful but the ticket couldn't be found
                    Toast.makeText(EditProfile.this, "User not found", Toast.LENGTH_SHORT).show();
                    Log.e("EditProfile", "User not found");
                    // Handle the case where the server returned an error
                    Log.e("EditProfile", "API Error: " + responseCode + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle the case where there was a network error or the API request failed
                Toast.makeText(EditProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                Log.e("EditProfile", "Failed to retrieve user details", t);
            }
        });
    }

    private void displayUserDetails(User user) {
        // Display the ticket details in TextView elements
        if (user != null) {
            editTextUserName.setText(user.getUsername());
            editTextNIC.setText(user.getNic());
            editTextEmail.setText(user.getEmail());
            editTextPhone.setText(user.getPhone());
            editTextAddress.setText(user.getAddress());
            editTextPassword.setText(user.getPassword());
            userId.setText(user.getId());

            Log.d("EditProfile", "User ID: " + user.getId());
            Log.d("EditProfile", "Username: " + user.getUsername());
            Log.d("EditProfile", "NIC: " + user.getNic());
            Log.d("EditProfile", "Email: " + user.getEmail());
            Log.d("EditProfile", "Phone: " + user.getPhone());
            Log.d("EditProfile", "Address: " + user.getAddress());
        }
    }
}