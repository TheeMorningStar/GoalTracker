package com.example.goaltracker;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goaltracker.sqlite.LoginContract;
import com.example.goaltracker.sqlite.MyDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView forgot_password, sign_up;
    Button login_button;
    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        // Login
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.login);

        // Forgot password
        forgot_password = findViewById(R.id.forget_password_text);

        // Sign up
        sign_up = findViewById(R.id.signup_text);

        // Event for login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputEmail.isEmpty()) {
                    email.setError("Please enter email");
                    email.requestFocus();
                } else if (inputPassword.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else {
                    Cursor loginDetailsCursor = databaseHelper.checkSignInDetails(inputEmail, inputPassword);
                    if (loginDetailsCursor != null && loginDetailsCursor.moveToFirst()) {
                        // Login successful, retrieve user details from the cursor
                        String name = loginDetailsCursor.getString(loginDetailsCursor.getColumnIndexOrThrow(LoginContract.LoginEntry.COLUMN_NAME_NAME));
                        String email = loginDetailsCursor.getString(loginDetailsCursor.getColumnIndexOrThrow(LoginContract.LoginEntry.COLUMN_NAME_EMAIL));
                        String password = loginDetailsCursor.getString(loginDetailsCursor.getColumnIndexOrThrow(LoginContract.LoginEntry.COLUMN_NAME_PASSWORD));

                        // Save login credentials in SharedPreferences
                        saveLoginCredentials(name, email, password);

                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                    if (loginDetailsCursor != null) {
                        loginDetailsCursor.close();
                    }
                }
            }
        });

        // Forgot password
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Sign up
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveLoginCredentials(String name, String inputEmail, String inputPassword) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", inputEmail);
        editor.putString("password", inputPassword);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

}