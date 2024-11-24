package com.mobdeve.harvesters.kuboquest;

import java.util.Date;

public class TaskModel {
    protected String taskID;
    protected String taskName;
    protected String taskDesc;
    protected Date createdDate;
    protected String taskFrequency;
    protected String taskDifficulty;
    protected boolean isDone;

//    public TaskModel(String taskName, String taskDescription, String taskStartDate,
//            String taskFrequency, String taskDifficulty, boolean isDone) {
//        this.taskName = taskName;
//        this.taskDescription = taskDescription;
//        this.taskStartDate = taskStartDate;
//        this.taskFrequency = taskFrequency;
//        this.taskDifficulty = taskDifficulty;
//        this.isDone = isDone;
//    }

    public TaskModel(String taskName, String taskDescription, Date createdDate, String frequency, String difficulty, boolean isDone) {
        this.taskName = taskName;
        this.taskDesc = taskDescription;
        this.createdDate = createdDate;
        this.taskFrequency = frequency;
        this.taskDifficulty = difficulty;
        this.isDone = isDone;
    }

    public TaskModel(String taskID, String taskName, String taskDescription, Date createdDate, String frequency, String difficulty, boolean isDone) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDesc = taskDescription;
        this.createdDate = createdDate;
        this.taskFrequency = frequency;
        this.taskDifficulty = difficulty;
        this.isDone = isDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDesc;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void invertIsDone() {
        this.isDone = !this.isDone;
    }

    public String getTaskFrequency() {
        return taskFrequency;
    }
}
