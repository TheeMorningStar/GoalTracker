package com.example.goaltracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else if (task.getTaskPriority().equals("Low")) {
            drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        } else if (task.getTaskPriority().equals("Medium")) {
            drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }

        holder.imageViewTaskStatus.setBackground(drawable);
        holder.textViewTaskName.setText(task.getTaskName());
        holder.textViewTaskDate.setText(task.getTaskStartData());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   listener.onItemClick(task);

                Toast.makeText(myContext, task.getTaskName(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void removeItem(int position) {
        String id = String.valueOf(taskList.get(position).getId()); // assuming itemList is your list of items
        taskList.remove(position);
        notifyItemRemoved(position);
        dbHelper.deleteTask(Integer.parseInt(id));
        db.close();

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
