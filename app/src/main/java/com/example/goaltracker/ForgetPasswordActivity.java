package com.example.goaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText emailInput, passwordInput1, passwordInput2;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailInput = findViewById(R.id.email_input);
        passwordInput1 = findViewById(R.id.password_input1);
        passwordInput2 = findViewById(R.id.password_input2);
        resetButton = findViewById(R.id.go_back_to_login);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password1 = passwordInput1.getText().toString().trim();
                String password2 = passwordInput2.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Email is required!");
                    emailInput.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Please enter a valid email address!");
                    emailInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    passwordInput1.setError("Password is required!");
                    passwordInput1.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password2)) {
                    passwordInput2.setError("Please confirm your password!");
                    passwordInput2.requestFocus();
                    return;
                }

                if (!password1.equals(password2)) {
                    passwordInput2.setError("Passwords do not match!");
                    passwordInput2.requestFocus();
                    return;
                }

                // Password validation can be further customized as per requirements
                Toast.makeText(getApplicationContext(), "Password reset successful!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}