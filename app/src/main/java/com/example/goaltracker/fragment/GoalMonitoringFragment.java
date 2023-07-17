package com.example.goaltracker.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.goaltracker.R;
import com.example.goaltracker.Task;
import com.example.goaltracker.sqlite.MyDatabaseHelper;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;


/*
This class is created by Yatri Patel
*/

public class GoalMonitoringFragment extends Fragment {
    private Context mContext;
    // Create the object of TextView
    // and PieChart class
    TextView txtHigh, txtMedium, txtLow;
    PieChart pieChart;
    MyDatabaseHelper dbHelper;

    SQLiteDatabase db;
    int highPriorityCount = 0, mediumPriorityCount = 0, lowPriorityCount = 0;
    double highPriorityPercentage, mediumPriorityPercentage, lowPriorityPercentage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_monitoring, container, false);
        // Link those objects with their
        // respective id's that
        // we have given in .XML file
        txtHigh = view.findViewById(R.id.txt_high);
        txtMedium = view.findViewById(R.id.txt_medium);
        txtLow = view.findViewById(R.id.txt_low);
        pieChart = view.findViewById(R.id.piechart);

        // Creating a method setData()
        // to set the text in text view and pie chart

        return view;

    }

    private void getData() {
        ArrayList<Task> tasks = dbHelper.getAllTasks();
        for (Task task : tasks) {
            String taskPriority = task.getTaskPriority();
            // Count tasks by priority level
            if (task.getTaskPriority().equals("High")) {
                highPriorityCount++;
            } else if (task.getTaskPriority().equals("Medium")) {
                mediumPriorityCount++;
            } else if (task.getTaskPriority().equals("Low")) {
                lowPriorityCount++;
            }
            int totalCount = tasks.size();
            highPriorityPercentage = (double) highPriorityCount / totalCount * 100;
            mediumPriorityPercentage = (double) mediumPriorityCount / totalCount * 100;
            lowPriorityPercentage = (double) lowPriorityCount / totalCount * 100;

        }

        // Print the total count for each priority level
        Log.d("TAG", "High Priority Count: " + highPriorityPercentage);
        Log.d("TAG", "Medium Priority Count: " + mediumPriorityPercentage);
        Log.d("TAG", "Low Priority Count: " + lowPriorityPercentage);
    }

    private void setData() {
        txtHigh.setText(String.format("%.0f", highPriorityPercentage)+"%");
        txtMedium.setText(String.format("%.0f", mediumPriorityPercentage)+"%");
        txtLow.setText(String.format("%.0f", lowPriorityPercentage)+"%");

        pieChart.addPieSlice(new PieModel("High", (float) highPriorityPercentage, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Medium", (float) mediumPriorityPercentage, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Low", (float) lowPriorityPercentage, Color.parseColor("#FDD835")));

        pieChart.startAnimation();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContext != null) {
            dbHelper = new MyDatabaseHelper(mContext);
            db = dbHelper.getWritableDatabase();
            getData();
            setData();

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