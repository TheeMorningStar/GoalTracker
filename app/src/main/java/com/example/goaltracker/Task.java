package com.example.goaltracker;

public class Task extends Throwable {
    String id;
    String taskName;
    String taskPriority;
    String taskStartData;

    String taskEndData;

    String taskDescription;


    public Task(String taskName, String taskPriority, String taskStartData, String taskEndData, String taskDescription) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskStartData = taskStartData;
        this.taskEndData = taskEndData;
        this.taskDescription = taskDescription;
    }

    public Task() {

    }

    public Task(String id, String taskName, String taskPriority, String taskStartData, String taskEndData, String taskDescription) {
        this.id = id;
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskStartData = taskStartData;
        this.taskEndData = taskEndData;
        this.taskDescription = taskDescription;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStartData() {
        return taskStartData;
    }

    public void setTaskStartData(String taskStartData) {
        this.taskStartData = taskStartData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskEndData() {
        return taskEndData;
    }

    public void setTaskEndData(String taskEndData) {
        this.taskEndData = taskEndData;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
