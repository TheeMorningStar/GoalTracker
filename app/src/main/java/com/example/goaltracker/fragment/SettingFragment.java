package com.example.goaltracker.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.goaltracker.R;
import com.example.goaltracker.activity.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class SettingFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);

        // Find the "SignOut" TextView and set a click listener
        View signOutButton = view.findViewById(R.id.log_out_text);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the saved login credentials
                clearLoginCredentials();

                // Show a snack bar indicating successful logout
                Snackbar.make(v, "Logged out successfully", Snackbar.LENGTH_LONG).show();

                // Redirect to the login screen using an explicit Intent
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }

    private void clearLoginCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
