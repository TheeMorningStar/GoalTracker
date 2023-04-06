package com.example.goaltracker.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goaltracker.Task;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskContract.TaskEntry.COLUMN_NAME_NAME + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_NAME_PRIORITY + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_NAME_START_DATE + " TEXT ," +
                    TaskContract.TaskEntry.COLUMN_NAME_END_DATE + " TEXT ," +
                    TaskContract.TaskEntry.COLUMN_NAME_END_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public long insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // values.put(TaskContract.TaskEntry._ID, getTaskId(task.getTaskName()));
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getTaskName());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY, task.getTaskPriority());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START_DATE, task.getTaskStartData());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END_DATE, task.getTaskEndData());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_END_DESCRIPTION, task.getTaskDescription());

        // Insert the new row into the table and get the ID of the new row
        long newRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        //getTaskId(task.getTaskName());
// Check that the new row ID is valid
        if (newRowId != -1) {
            Log.d("TAG", "New row inserted with ID " + newRowId);
            //Log.d("###", "DataBase" + getTaskId(task.getTaskName()));
        } else {
            Log.e("TAG", "Error inserting new row into table");
        }
        //   By using the insert() method, you can ensure that the primary key value is automatically generated and that there are no issues with the raw SQL statement you are using to insert data into the table.


        return newRowId;

    }

   /* public int row_ID(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        return db.execSQL("SELECT" + TaskContract.TaskEntry._ID + "FROM" + task+"WHERE name =" + task.getTaskName());
    }*/

    public int updateTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY, task.getTaskPriority());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getTaskName());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_START_DATE, task.getTaskStartData());
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(task.getTaskStartData())};
        return db.update(TaskContract.TaskEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public int deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TaskContract.TaskEntry.TABLE_NAME, "id=?", new String[]{String.valueOf(id)}); // assuming "myTable" is the name of your table
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                TaskContract.TaskEntry.COLUMN_NAME_ID,
                TaskContract.TaskEntry.COLUMN_NAME_NAME,
                TaskContract.TaskEntry.COLUMN_NAME_PRIORITY,
                TaskContract.TaskEntry.COLUMN_NAME_START_DATE,
                TaskContract.TaskEntry.COLUMN_NAME_END_DATE,
                TaskContract.TaskEntry.COLUMN_NAME_END_DESCRIPTION

        };
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_NAME));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY));
            String start_date = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_START_DATE));
            String end_date = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_END_DATE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_END_DESCRIPTION));
            Task task = new Task(id, name, priority, start_date, end_date, description);
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }


}
