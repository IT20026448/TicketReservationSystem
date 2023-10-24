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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// handle user login
public class Login extends AppCompatActivity {
    // Create variable for logging
    private static final String TAG = "Login";
    TextView navRegBtn;
    TextInputEditText editTextNIC, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    List<User> users;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), Home.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        navRegBtn = findViewById(R.id.nav_register);
        editTextNIC = findViewById(R.id.nic);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);

        // Create a UserService instance
        UserService userService = new UserService(userApi);

        // Call the getUser method to make the get all the users
        Call<List<User>> call = userService.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(response.isSuccessful()){
                   users = response.body();

                   // Loop through the list of users and display them (or perform any other operation)
                   for (User user : users) {
                       Log.d(TAG, "User ID: " + user.getId());
                       Log.d(TAG, "Username: " + user.getUsername());
                       Log.d(TAG, "NIC: " + user.getNic());
                       Log.d(TAG, "Password: " + user.getPassword());

                   }
               }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String nic, password;
                String displayName = "";
                nic = String.valueOf(editTextNIC.getText());
                password = String.valueOf(editTextPassword.getText());

                Log.d("Login", "Login button pressed");

                if (TextUtils.isEmpty(nic)) {
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                boolean found = false;

                for (User user : users) {
                    if (user.getNic().equals(nic)) {
                        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if (result.verified) {
                            // Password is correct
                            found = true;
                            Log.d("Login", "User match ID: " + user.getEmail());
                            displayName = user.getUsername();
                            break;
                        }
                    }
                }

                progressBar.setVisibility(View.GONE);

                if (found) {
                    Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    // Pass the username as an extra to the Home activity
                    intent.putExtra("username", displayName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        navRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the Registration activity
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
                Log.d("Login", "Navigating to Registration page..");
            }
        });
    }
}