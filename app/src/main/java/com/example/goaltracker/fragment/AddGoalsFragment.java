package com.example.goaltracker.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.goaltracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
This class is created by Yatri Patel
*/
public class AddGoalsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_goals, container, false);

        //  Set spinner
        Spinner spinner = view.findViewById(R.id.priority);

        // Set values in drop down menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.priority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Disable the first item
        View firstItem = spinner.getSelectedView();
        if (firstItem != null) {
            firstItem.setEnabled(false);
        }

        // Get a reference to the start date
        EditText start_date = view.findViewById(R.id.start_date);

        // Set an OnClickListener to show the DatePicker when the user clicks on the button
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        // The user has selected a new date
                        // Retrieve the selected date
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date selectedDate = calendar.getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA.getDefault());
                        start_date.setText(dateFormat.format(selectedDate));

                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        // Get a reference to the end date
        EditText end_date = view.findViewById(R.id.end_date);

        // Set an OnClickListener to show the DatePicker when the user clicks on the button
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        // The user has selected a new date
                        // Retrieve the selected date
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date selectedDate = calendar.getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA.getDefault());
                        end_date.setText(dateFormat.format(selectedDate));

                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                // Get the selected start date and set the minimum date to it
                String startDateString = start_date.getText().toString();

                if (startDateString.isEmpty()) {
                   Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please select start date first", Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER, 0, 0);
                   toast.show();

                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA.getDefault());
                    try {
                        Date startDate = dateFormat.parse(startDateString);
                        long startTimeInMillis = startDate.getTime();
                        datePickerDialog.getDatePicker().setMinDate(startTimeInMillis);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Show the DatePickerDialog
                    datePickerDialog.show();
                }
            }
        });


        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_add_goals, container, false);
        return view;

    }
}