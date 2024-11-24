package com.mobdeve.harvesters.kuboquest;

import java.util.Date;

public class TaskModel {
    protected String taskName;
    protected String taskDescription;
    protected String taskStartDate;
    protected String taskFrequency;
    protected String taskDifficulty;
    protected boolean isDone;

    public TaskModel(String taskName, String taskDescription, String taskStartDate,
            String taskFrequency, String taskDifficulty, boolean isDone) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskFrequency = taskFrequency;
        this.taskDifficulty = taskDifficulty;
        this.isDone = isDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public String getTaskFrequency() {
        return taskFrequency;
    }

    public String getTaskDifficulty() {
        return taskDifficulty;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void invertIsDone() {
        this.isDone = !this.isDone;
    }
}
