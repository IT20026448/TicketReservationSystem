package com.example.ticketreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketreservationsystem.models.User;
import com.example.ticketreservationsystem.service.UserApi;
import com.example.ticketreservationsystem.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Register class to handle user registration.
// Saves user credentials NIC and password in firebase to be used for authentication
// saves name, email address, etc. in database

// https://www.codeproject.com/Articles/5308542/A-Complete-Tutorial-to-Connect-Android-with-ASP-NE

public class Register extends AppCompatActivity {
    // Create variable for logging
    private static final String TAG = "Registration";

    // Create variables store references to user interface elements in the Android layout
    // create variable for text input to take the user input data
    TextInputEditText editTextUserName, editTextNIC, editTextEmail, editTextPassword, editTextPhone, editTextAddress, editTextName;
    // create variable to reference registration button, Button UI element in the Android layout
    Button buttonReg;
    // create variable to reference text displayed, TextView UI element in the Android layout
    TextView navLogin, travelerID;
    // Create instance of FirebaseAuth
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    String randomNo = generateRandomNumber();

    // https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // If the user has not signed in correctly navigate to the Sign up page.
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        }

        // for ID generate a 24 digit hex string value
        travelerID = findViewById(R.id.Id);
        // show the ID when the page loads
        travelerID.setText(randomNo);
    }

    // method that defines what happens when the page loads.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // navigate to the registration page
        setContentView(R.layout.activity_registration);

        // Extract the user input from keyboard.
        editTextUserName = findViewById(R.id.user_name);
        editTextNIC = findViewById(R.id.NIC);
        editTextEmail = findViewById(R.id.email_address);
        editTextPhone = findViewById(R.id.phone);
        editTextAddress = findViewById(R.id.address);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        navLogin = findViewById(R.id.nav_login);

        Log.d("Register", "User ID: " + randomNo);

        // perform actions when register button is clicked
        buttonReg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             progressBar.setVisibility(View.VISIBLE);
             Log.d(TAG, "Register button clicked");
             String id, username, NIC, emailAdd, phone, address, status, password;
             int type;
             // when the register button is clicked take the user input data and save in variables
             id = randomNo;
             username = String.valueOf(editTextUserName.getText());
             NIC = String.valueOf(editTextNIC.getText());
             emailAdd = String.valueOf(editTextEmail.getText());
             address = String.valueOf(editTextAddress.getText());
             // until the user deactivates account, status will be active
             status = "active";
             password = String.valueOf(editTextPassword.getText());
             phone = String.valueOf(editTextPhone.getText());
             type = 1;

             // show a message if the user clicks on the register button without entering the NIC
             if (TextUtils.isEmpty(NIC)) {
                 Toast.makeText(Register.this, "Enter NIC", Toast.LENGTH_SHORT).show();
                 return;
             }
             // show a message if the user clicks on the register button without entering the password
             if (TextUtils.isEmpty(password)) {
                 Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                 return;
             }

             // take the user NIC and password and create the account
             // using password authentication in firebase which requires an email and has no option
             // to create accounts with NIC. So, we take user input as NIC put send to the database
             // in the email format
             mAuth.createUserWithEmailAndPassword(NIC + "@travelreserve.com", password)
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             progressBar.setVisibility(View.GONE);
                             // if the account is created successfully
                             if (task.isSuccessful()) {

//                                    // create a reference of the current user
//                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    // variable to store current user's ID
//                                    String uid = currentUser.getUid();
//                                    // variable to store current user's email
//                                    String email = currentUser.getEmail();

//                                    // Get a reference to the Firebase Database, user's collection.
//                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                    DatabaseReference userRef = database.getReference("users").child(uid);


                                 // Create a User object id, username, NIC, email, phone, address, status, password, name
                                 User user = new User(id, username, NIC, emailAdd, phone, address, status, type);

    //                                    // Set the user object in the Firebase Database
    //                                    userRef.setValue(user);

                                 // Create a Retrofit instance using the RetrofitClient
                                 UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);

                                 // Create a UserService instance
                                 UserService userService = new UserService(userApi);

                                 // Call the createUser method to make the POST request
                                 Call<User> call = userService.createUser(user);

                                 call.enqueue(new Callback<User>() {
                                     @Override
                                     public void onResponse(Call<User> call, Response<User> response) {
                                         if (response.isSuccessful()) {
                                             // User created successfully
                                             User createdUser = response.body();
                                             Log.d("Register", "User ID: " + createdUser.getId());
                                             Log.d("Register", "NIC: " + createdUser.getNic());
                                             FirebaseAuth.getInstance().signOut();
                                             Intent intent = new Intent(getApplicationContext(), Login.class);
                                             startActivity(intent);
                                             finish();
                                             // Handle the created user as needed
                                         } else {
                                             // Handle the case where the user creation request was not successful
                                             // You can access the error response with response.errorBody()
                                             try {
                                                 String errorResponse = response.errorBody().string();
                                                 Log.e("Register", "Error Response: " + errorResponse);
                                             } catch (IOException e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<User> call, Throwable t) {
                                         // Handle the case where there was a network error or the API request failed
                                         Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                         Log.e("Register", "Failed to retrieve user details", t);
                                     }
                                 });

                                 // If sign in successful, display a message to the user.
                                 Toast.makeText(Register.this, "Account created.",
                                         Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(), Login.class);
                                 startActivity(intent);
                                 finish();
                             } else {
                                 Exception e = task.getException();
                                 if (e != null) {
                                     e.printStackTrace();
                                     if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                         // Handle invalid credentials exception
                                         Log.e(TAG, "Registration failed: " + e.getMessage());
                                     } else if (e instanceof FirebaseAuthUserCollisionException) {
                                         // Handle user collision (email already in use) exception
                                         Log.e(TAG, "Registration failed: " + e.getMessage());
                                     } else {
                                         // Handle other exceptions
                                         // If sign-up fails, display a message to the user.
                                         Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                         Log.e(TAG, "Registration failed: " + e.getMessage());
                                     }
                                 } else {
                                     // The exception is null, handle it accordingly
                                 }
                             }
                         }
                     });
         }
     });


        // navigate to login page when button pressed
        navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // method to generate random number hex 24 string
    //https://www.geeksforgeeks.org/generate-n-random-hexadecimal-numbers/
    // method to generate random number hex 24 string
    public String generateRandomNumber(){
        int maxSize = 24;
        StringBuilder randomNo = new StringBuilder(maxSize);
        char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        for (int i = 0; i < maxSize; i++) {
            // Print a randomly selected character
            randomNo.append(hexChar[(int)(Math.random() * 16)]);
        }

        return randomNo.toString();
    }
}