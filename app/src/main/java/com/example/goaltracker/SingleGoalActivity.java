package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.goaltracker.sqlite.MyDatabaseHelper;

public class SingleGoalActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView goalNameTextView, goalPriorityTextView, goalStartDateTextView, goalEndDateTextView, goalDescriptionTextView;

    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private String id, taskName, taskPriority, taskStartData, taskEndData, taskDescription;
    private Button btnUpdateGoal;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_goal);

        // Find the views in the layout
        progressBar = findViewById(R.id.goal_progress_bar);
        goalNameTextView = findViewById(R.id.goal_name);
        goalPriorityTextView = findViewById(R.id.goal_priority);
        goalStartDateTextView = findViewById(R.id.goal_start_date);
        goalEndDateTextView = findViewById(R.id.goal_end_date);
        goalDescriptionTextView = findViewById(R.id.goal_description);
        btnUpdateGoal= findViewById(R.id.want_to_update_goal_button);


        // Set up the progress bar
        progressBar.setMax(100);
        progressBar.setProgress(50);

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

        // Set up the text views
        goalNameTextView.setText(taskName);
        goalPriorityTextView.setText(taskPriority);
        goalStartDateTextView.setText(taskStartData);
        goalEndDateTextView.setText(taskEndData);
        goalDescriptionTextView.setText(taskDescription);
        wantToUpdateGoal();

    }
    private void wantToUpdateGoal() {
        // Set an onClickListener for the button
        btnUpdateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleGoalActivity.this, UpdateTaskActivity.class));
            }
        });
    }
}
