package com.example.goaltracker.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.goaltracker.Task;

import java.util.ArrayList;

public class TaskDao {
    private SQLiteDatabase database;

    public TaskDao(SQLiteDatabase database) {
        this.database = database;
    }
    public void addPerson(Task task) {
        ContentValues values = new ContentValues();

        values.put("taskName", task.getTaskName());
        values.put("taskDate", task.getTaskStartData());
        database.insert("mytable", null, values);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasks = new ArrayList<>();
//To retrieve data from the table, use a query
        String[] projection = {"id", "taskStatus", "taskName","taskData"};
        Cursor cursor = database.query("mytable", projection, null, null, null, null, null);

         while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String goalStatus = cursor.getString(cursor.getColumnIndexOrThrow("taskStatus"));
            String goalname = cursor.getString(cursor.getColumnIndexOrThrow("taskName"));
            String goalDate = cursor.getString(cursor.getColumnIndexOrThrow("taskDate"));

            Log.d("TAG$$$", "id: " + id + ", taskStatus: " + goalStatus + ", name: " + goalname + ", taskDate: " + goalDate);
        }

        cursor.close();

        return tasks;
    }
}