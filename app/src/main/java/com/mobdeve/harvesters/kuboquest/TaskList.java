package com.mobdeve.harvesters.kuboquest;

import android.os.Bundle;

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

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);

        setuptaskModelList();

        TaskList_RecyclerViewAdapter adapter =  new TaskList_RecyclerViewAdapter(this,
                taskModelList);
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setuptaskModelList() {
        String taskName;
        for(int i = 1; i <= 10; i++) {
            taskModelList.add(new TaskModel("Task " + i, "This is a task", false));
        }

    }
}