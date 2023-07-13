package com.example.goaltracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.goaltracker.NavigationDrawerActivity;
import com.example.goaltracker.R;
import com.example.goaltracker.Task;

/*
This class is created by Yatri Patel
*/
public class ContactUsFragment extends Fragment {

    EditText nameET, emailET, phoneET, msgET;
    Button submitBTN;

    ProgressBar loadingPB;
    TextView loading_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        nameET = view.findViewById(R.id.etName);
        emailET = view.findViewById(R.id.etEmail);
        phoneET = view.findViewById(R.id.etPhone);
        msgET = view.findViewById(R.id.etMsg);
        submitBTN = view.findViewById(R.id.btnSubmit);
        loadingPB = view.findViewById(R.id.loading);
        loading_message = view.findViewById(R.id.loading_text);
        submitContactUsFormEvent();
    }

    private void submitContactUsFormEvent() {
        // Set an onClickListener for the button
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String phone = phoneET.getText().toString();
                String msg = msgET.getText().toString();

                if (name.isEmpty()) {
                    nameET.setError("Please enter your name");
                    nameET.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    emailET.setError("Please enter your email");
                    emailET.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Please enter valid email");
                    emailET.requestFocus();
                    return;
                } else if (phone.isEmpty()) {
                    phoneET.setError("Please enter your contact number");
                    phoneET.requestFocus();
                    return;
                } else if (!Patterns.PHONE.matcher(phone).matches()) {
                    phoneET.setError("Please enter valid contact number");
                    phoneET.requestFocus();
                    return;
                } else if (msg.isEmpty()) {
                    msgET.setError("Please enter your message");
                    msgET.requestFocus();
                    return;
                } else {

                    nameET.setVisibility(View.GONE);
                    phoneET.setVisibility(View.GONE);
                    emailET.setVisibility(View.GONE);
                    msgET.setVisibility(View.GONE);
                    submitBTN.setVisibility(View.GONE);

                    loadingPB.setVisibility(View.VISIBLE);
                    loading_message.setVisibility(View.VISIBLE);


//                    Toast.makeText(getActivity().getApplicationContext(), "Thank you for contacting us!", Toast.LENGTH_LONG).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingPB.setVisibility(View.GONE);
                            loading_message.setVisibility(View.GONE);

                            nameET.setText("");
                            phoneET.setText("");
                            emailET.setText("");
                            msgET.setText("");

                            nameET.setVisibility(View.VISIBLE);
                            phoneET.setVisibility(View.VISIBLE);
                            emailET.setVisibility(View.VISIBLE);
                            msgET.setVisibility(View.VISIBLE);
                            submitBTN.setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                }
            }
        });
    }
}