package com.mobdeve.harvesters.kuboquest;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskList extends AppCompatActivity {

    ArrayList<TaskModel> taskModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ProgressBar progressWater = findViewById(R.id.progressWater);
        ProgressBar progressXP = findViewById(R.id.progressXP);

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);

        setuptaskModelList();

        TaskList_RecyclerViewAdapter adapter =  new TaskList_RecyclerViewAdapter(this,
                taskModelList);
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        animateProgress(progressWater, progressWater.getProgress());
        animateProgress(progressXP, progressXP.getProgress());
    }

    private void setuptaskModelList() {
        String taskName;
        for(int i = 1; i <= 10; i++) {
            taskModelList.add(new TaskModel("Task " + i, "This is a task", false));
        }

    }

    private void animateProgress(ProgressBar progressBar, int progress) {
        // Create an ObjectAnimator to animate progress from 0 to the desired value
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, progress);
        animation.setDuration(1500); // Set the duration (1.5 seconds)
        animation.start();
    }
}