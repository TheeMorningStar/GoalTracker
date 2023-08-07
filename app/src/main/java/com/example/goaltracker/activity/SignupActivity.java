package com.example.goaltracker.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goaltracker.R;
import com.example.goaltracker.sqlite.MyDatabaseHelper;
import com.example.goaltracker.sqlite.LoginContract;

public class SignupActivity extends AppCompatActivity {

    // Get all the elements
    EditText et_name, et_email, et_password, et_confirm_password;
    Button sign_up;
    TextView login;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize database helper
        databaseHelper = new MyDatabaseHelper(this);

        // Connect elements with form
        et_name = findViewById(R.id.name);
        et_email = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        et_confirm_password = findViewById(R.id.confirm_password);
        sign_up = findViewById(R.id.signup);
        login = findViewById(R.id.login_text);

        // Set on click event of sign up button
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirm_password = et_confirm_password.getText().toString();

                // Validations
                if (name.isEmpty()) {
                    et_name.setError("Enter name");
                    et_name.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    et_email.setError("Enter email");
                    et_email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    et_password.setError("Enter password");
                    et_password.requestFocus();
                    return;
                }
                if (confirm_password.isEmpty()) {
                    et_confirm_password.setError("Enter confirm password");
                    et_confirm_password.requestFocus();
                    return;
                }
                if (!password.equals(confirm_password)) {
                    et_confirm_password.setError("Password and confirm password must be same");
                    et_confirm_password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.setError("Please enter a valid email address");
                    et_email.requestFocus();
                    return;
                }

                // If validation success then register the user and display Login screen
                boolean isSignupSuccessful = saveSignupData(name, email, password);

                if (isSignupSuccessful) {
                    Toast.makeText(SignupActivity.this, "Signup success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click event of login text
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean saveSignupData(String name, String email, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LoginContract.LoginEntry.COLUMN_NAME_NAME, name);
        values.put(LoginContract.LoginEntry.COLUMN_NAME_EMAIL, email);
        values.put(LoginContract.LoginEntry.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(LoginContract.LoginEntry.TABLE_NAME, null, values);

        return newRowId != -1;
    }
}
