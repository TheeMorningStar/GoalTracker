package com.example.goaltracker.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goaltracker.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView, userPasswordTextView, userNameTitleTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        userNameTextView = findViewById(R.id.user_name_txt);
        userEmailTextView = findViewById(R.id.user_email_txt);
        userPasswordTextView = findViewById(R.id.user_password_txt);
        userNameTitleTextView = findViewById(R.id.user_name_title_txt);


        // Retrieve saved user details from SharedPreferences and update UI
        fetchAndDisplayUserDetails();
    }

    private void fetchAndDisplayUserDetails() {
        String userName = sharedPreferences.getString("name", "");
        String userEmail = sharedPreferences.getString("email", "");
        String userPassword = sharedPreferences.getString("password", "");


        // Update UI elements
        userNameTitleTextView.setText(userName);
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);
        userPasswordTextView.setText(userPassword);

    }
}
