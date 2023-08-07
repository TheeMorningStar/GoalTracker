package com.example.goaltracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goaltracker.R;
import com.example.goaltracker.sqlite.MyDatabaseHelper;

public class UpdateTaskActivity extends AppCompatActivity {
    private String id, taskName, taskPriority, taskStartData, taskEndData, taskDescription;
    private Button btnAddGoal;
    private Spinner spinner;
    private EditText task_Name, start_date, end_date, description;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_goals);
        initView();


    }

    private void initView() {

        task_Name = findViewById(R.id.name);
        spinner = findViewById(R.id.priority);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        description = findViewById(R.id.description);
        btnAddGoal = findViewById(R.id.add_goal_button);

        dbHelper = new MyDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // get shared preferences object
        String sharedPrefFile = "com.example.goaltracker";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // retrieve saved data from shared preferences
        id = mPreferences.getString("task_id", "");
        taskName = mPreferences.getString("task_name", "");
        taskPriority = mPreferences.getString("task_priority", "");
        taskStartData = mPreferences.getString("task_start_data", "");
        taskEndData = mPreferences.getString("task_end_data", "");
        taskDescription = mPreferences.getString("task_description", "");

        task_Name.setText(taskName);
        start_date.setText(taskStartData);
        end_date.setText(taskEndData);
        description.setText(taskDescription);

        // Set values in drop down menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Disable the first item
        View firstItem = spinner.getSelectedView();
        if (firstItem != null) {
            firstItem.setEnabled(false);
        }
        if (taskPriority != null) {
            int spinnerPosition = adapter.getPosition(taskPriority);
            spinner.setSelection(spinnerPosition);
        }
        UpdateTaskEvent();

    }

    private void UpdateTaskEvent() {
        // Set an onClickListener for the button
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateTask(db, Integer.parseInt(id), task_Name.getText().toString(), spinner.getSelectedItem().toString(), start_date.getText().toString(), end_date.getText().toString(), description.getText().toString());
                Log.d("TAG", "Item ADDED");
                Toast.makeText(UpdateTaskActivity.this, "Item Updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateTaskActivity.this, NavigationDrawerActivity.class));
            }
        });
    }
}
