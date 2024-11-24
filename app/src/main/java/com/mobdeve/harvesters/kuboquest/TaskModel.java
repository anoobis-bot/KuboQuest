package com.mobdeve.harvesters.kuboquest;

import java.util.Date;

public class TaskModel {
    protected String taskID;
    protected String taskName;
    protected String taskDescription;
    protected Date createdDate;
    protected String frequency;
    protected String difficulty;
    protected boolean isDone;

    public TaskModel() {

    }

    public TaskModel(String taskName, String taskDescription, boolean isDone) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    public TaskModel(String taskName, String taskDescription, Date createdDate, String frequency, String difficulty, boolean isDone) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.createdDate = createdDate;
        this.frequency = frequency;
        this.difficulty = difficulty;
        this.isDone = isDone;
    }

    public TaskModel(String taskID, String taskName, String taskDescription, Date createdDate, String frequency, String difficulty, boolean isDone) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.createdDate = createdDate;
        this.frequency = frequency;
        this.difficulty = difficulty;
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
