package com.example.goaltracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.goaltracker.R;

/*
This class is created by Yatri Patel
*/
public class ContactUsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        Log.d("ContactUsFragment:","Enter Here ");

        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }
}