package com.example.goaltracker.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.SwipeToDeleteCallback;
import com.example.goaltracker.sqlite.MyDatabaseHelper;
import com.example.goaltracker.R;
import com.example.goaltracker.Task;
import com.example.goaltracker.TaskAdapter;

import java.util.ArrayList;

/*
This class is created by Yatri Patel
*/
public class HomeFragment extends Fragment {
    private Context mContext;
    private TaskAdapter taskAdapter;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContext != null) {
            dbHelper = new MyDatabaseHelper(mContext);
            db = dbHelper.getWritableDatabase();

            RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            taskAdapter = new TaskAdapter(getContext(), dbHelper.getAllTasks());
            recyclerView.setAdapter(taskAdapter);
            SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(taskAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } else {
            // handle the case where mContext is null
            Log.d("HomeFragment", "onViewCreated  null");
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