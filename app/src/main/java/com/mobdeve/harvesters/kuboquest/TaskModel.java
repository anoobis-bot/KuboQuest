package com.mobdeve.harvesters.kuboquest;

public class TaskModel {
    protected String taskName;
    protected String taskDescription;
    protected boolean isDone;

    public TaskModel(String taskName, String taskDescription, boolean isDone) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void invertIsDone() {
        this.isDone = !this.isDone;
    }
}
