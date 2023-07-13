package com.example.goaltracker.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goaltracker.NavigationDrawerActivity;
import com.example.goaltracker.R;
import com.example.goaltracker.Task;
import com.example.goaltracker.TaskAdapter;
import com.example.goaltracker.sqlite.MyDatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
This class is created by Yatri Patel
*/
public class AddGoalsFragment extends Fragment {

    private Button btnAddGoal;
    private Spinner spinner;

    private EditText taskName, start_date, end_date, description;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private Context mContext;

    private ProgressBar loading;
    private TextView loading_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_goals, container, false);

        initView(view);

        AddTaskEvent();
        //  Set spinner
        spinner = view.findViewById(R.id.priority);

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

    private void initView(View view) {
        taskName = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        start_date = view.findViewById(R.id.start_date);
        end_date = view.findViewById(R.id.end_date);
        btnAddGoal = view.findViewById(R.id.add_goal_button);

        loading = view.findViewById(R.id.loading);
        loading_text = view.findViewById(R.id.loading_text);
    }

    private void AddTaskEvent() {
        // Set an onClickListener for the button
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task1 = new Task(taskName.getText().toString(), spinner.getSelectedItem().toString(), start_date.getText().toString(), end_date.getText().toString(), description.getText().toString());
                dbHelper.insertTask(task1);
                Log.d("TAG", "Item ADDED");


                // get loading elements
                loading.setVisibility(View.VISIBLE);
                loading_text.setVisibility(View.VISIBLE);

                description.setVisibility(View.GONE);
                start_date.setVisibility(View.GONE);
                end_date.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                taskName.setVisibility(View.GONE);
                btnAddGoal.setVisibility(View.GONE);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.GONE);
                        loading_text.setVisibility(View.GONE);

                        description.setText("");
                        start_date.setText("");
                        end_date.setText("");
                        taskName.setText("");

                        Intent intent = new Intent(getActivity().getApplicationContext(), NavigationDrawerActivity.class);
                        startActivity(intent);

                    }
                }, 1000);

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContext != null) {
            dbHelper = new MyDatabaseHelper(mContext);
            db = dbHelper.getWritableDatabase();
        } else {
            // handle the case where mContext is null

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


}