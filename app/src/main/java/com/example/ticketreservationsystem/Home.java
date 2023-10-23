package com.example.ticketreservationsystem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private static final String TAG = "Login";
    TextView textView, usernameText;
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogout;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        buttonLogout = findViewById(R.id.logout);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Set the ActionBarDrawerToggle as the DrawerListener for the DrawerLayout
//        drawerLayout.addDrawerListener(drawerToggle);

        // Enable the hamburger icon
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        // Firebase setup
        // Replace "your-firebase-project-id" with your actual Firebase project ID
        String databaseUrl = "https://ticket-reservation-syste-8fe02-default-rtdb.firebaseio.com";
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseUrl).child("trains");
        // Your LinearLayout container

        usernameText = findViewById(R.id.usernameTextView);

        user = auth.getCurrentUser();

        if(user != null){
            String email = user.getEmail();

            usernameText.setText(email);
        }
        usernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String trainID = snapshot.child("trainID").getValue(String.class);
                    String deptTime = snapshot.child("deptTime").getValue(String.class);
                    String arrivalTime = snapshot.child("arrivalTime").getValue(String.class);
                    String startLoc = snapshot.child("startLoc").getValue(String.class);
                    String destination = snapshot.child("destination").getValue(String.class);

                    // Log the data to the terminal (Logcat)
                    Log.d(TAG, "Train ID: " + trainID);
                    Log.d(TAG, "Departure Time: " + deptTime);
                    Log.d(TAG, "Arrival Time: " + arrivalTime);
                    Log.d(TAG, "Start Location: " + startLoc);
                    Log.d(TAG, "Destination: " + destination);

                    // Create a new CardView for each train
                    View cardView = getLayoutInflater().inflate(R.layout.activity_home, null);
                    TextView trainIDTextView = cardView.findViewById(R.id.trainIDTextView);
                    TextView deptTimeTextView = cardView.findViewById(R.id.deptTimeTextView);
                    TextView arrivalTimeTextView = cardView.findViewById(R.id.arrivalTimeTextView);
                    TextView startLocTextView = cardView.findViewById(R.id.startLocTextView);
                    TextView destinationTextView = cardView.findViewById(R.id.destinationTextView);

                    // Set the data to TextViews
                    trainIDTextView.setText("Train ID: " + trainID);
                    deptTimeTextView.setText("Departure Time: " + deptTime);
                    arrivalTimeTextView.setText("Arrival Time: " + arrivalTime);
                    startLocTextView.setText("Start Location: " + startLoc);
                    destinationTextView.setText("Destination: " + destination);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        });
    }
}


//
////        deactivateAccountBtn = findViewById(R.id.deactivate_account);
//        textView = findViewById(R.id.user_details);
//        user = auth.getCurrentUser();

//        if(user != null){
//            Intent intent = new Intent(getApplicationContext(), Login.class);
//            startActivity(intent);
//            finish();
//        } else {
//            //textView.setText(user.getEmail());
//        }


//        deactivateAccountBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
////                        .setDisplayName("disabled")
////                        .build();
////
////                user.updateProfile(profileUpdates)
////                        .addOnCompleteListener(new OnCompleteListener<Void>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Void> task) {
////                                if (task.isSuccessful()) {
////                                    // Sign out the user
////                                    FirebaseAuth.getInstance().signOut();
////                                    Intent intent = new Intent(getApplicationContext(), Login.class);
////                                    startActivity(intent);
////                                    finish();
////                                } else {
////                                    // Handle error
////                                    Toast.makeText(Home.this, "Failed to deactivate account", Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        });
//            }
//        });
    //}
//}