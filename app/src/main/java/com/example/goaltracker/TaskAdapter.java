package com.example.goaltracker;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.sqlite.MyDatabaseHelper;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context myContext;

    private List<Task> taskList;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public TaskAdapter(Context context, List<Task> taskList) {
        myContext = context;
        this.taskList = taskList;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cardview, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        dbHelper = new MyDatabaseHelper(myContext);
        db = dbHelper.getWritableDatabase();

        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = myContext.getResources().getDrawable(R.drawable.circular);
        if (task.getTaskPriority().equals("High")) {
            drawable.setColorFilter(Color.parseColor("#EF5350"), PorterDuff.Mode.SRC_IN);
        } else if (task.getTaskPriority().equals("Low")) {
            drawable.setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.SRC_IN);
        } else if (task.getTaskPriority().equals("Medium")) {
            drawable.setColorFilter(Color.parseColor("#66BB6A"), PorterDuff.Mode.SRC_IN);
        }

        holder.imageViewTaskStatus.setBackground(drawable);
        holder.textViewTaskName.setText(task.getTaskName());
        holder.textViewTaskDate.setText(task.getTaskDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   listener.onItemClick(task);

                Toast.makeText(myContext, task.getTaskName(), Toast.LENGTH_LONG).show();

                //Get shared preferences object
                String sharedPrefFile = "com.example.goaltracker";
                SharedPreferences mPreferences = myContext.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

                //Save data to shared pref
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.putString("task_id", String.valueOf(task.getId()));
                preferencesEditor.putString("task_name", String.valueOf(task.getTaskName()));
                preferencesEditor.putString("task_priority", String.valueOf(task.getTaskPriority()));
                preferencesEditor.putString("task_start_data", String.valueOf(task.taskStartData));
                preferencesEditor.putString("task_end_data", String.valueOf(task.taskEndData));
                preferencesEditor.putString("task_description", String.valueOf(task.taskDescription));

                preferencesEditor.apply();
                Intent i = new Intent(myContext, SingleGoalActivity.class);
                myContext.startActivity(i);

            }
        });
    }

    public void removeItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Delete Note");
        builder.setMessage("Are you sure you want to delete this note?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the note
//                myContext.remove(position);
//                notifyItemRemoved(position);
                String id = String.valueOf(taskList.get(position).getId()); // assuming itemList is your list of items
                taskList.remove(position);
                notifyItemRemoved(position);
                dbHelper.deleteTask(Integer.parseInt(id));
                db.close();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {


        ImageView imageViewTaskStatus;
        TextView textViewTaskName;
        TextView textViewTaskDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewTaskStatus = itemView.findViewById(R.id.imageView_task_status);
            textViewTaskName = itemView.findViewById(R.id.textView_task_name);
            textViewTaskDate = itemView.findViewById(R.id.textView_task_date);

        }

    }
}
