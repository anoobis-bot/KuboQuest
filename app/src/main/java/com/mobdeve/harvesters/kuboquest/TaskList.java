package com.mobdeve.harvesters.kuboquest;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskList extends AppCompatActivity {

    boolean showingTask;
    ImageView bookIcon;

    ArrayList<TaskModel> taskModelList = new ArrayList<>();

    int[] playerLevels, plantSprites;
    int playerLevel;

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                public void onActivityResult(ActivityResult result) {
                    int duration = Toast.LENGTH_SHORT;

                    if (result.getResultCode() == 1)
                    {
                        Toast toast = Toast.makeText(TaskList.this, "Added!", duration);
                        toast.show();
                    }

                    else if (result.getResultCode() == 2)
                    {
                        Toast toast = Toast.makeText(TaskList.this, "Edited!", duration);
                        toast.show();
                    }

                    else if (result.getResultCode() == 3)
                    {
                        Toast toast = Toast.makeText(TaskList.this, "Deleted!", duration);
                        toast.show();

                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED)
                    {
                        Toast toast = Toast.makeText(TaskList.this, "Canceled!", duration);
                        toast.show();
                    }
                }
            });

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
        TextView txtWater = findViewById(R.id.txtWater);
        ProgressBar progressXP = findViewById(R.id.progressXP);
        TextView txtXP = findViewById(R.id.txtXP);

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView levelRecyclerView = findViewById(R.id.levelRecyclerView);

        setuptaskModelList();
        setupData();

        TaskList_RecyclerViewAdapter adapter1 =  new TaskList_RecyclerViewAdapter(this,
                taskModelList);
        taskRecyclerView.setAdapter(adapter1);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlayerLevels_RecyclerViewAdapter adapter2 =  new PlayerLevels_RecyclerViewAdapter(this,
                playerLevels, plantSprites);
        levelRecyclerView.setAdapter(adapter2);
        levelRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        animateProgress(progressWater, progressWater.getProgress(), txtWater, "", "/100", 1);
        animateProgress(progressXP, progressXP.getProgress(), txtXP, "", "XP", 10);

        RadioGroup sortByButtons = findViewById(R.id.sortByButtons);

        showingTask = true;
        ImageView imgPlant = findViewById(R.id.imgPlant);
        imgPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showingTask)
                {
                    sortByButtons.setVisibility(View.VISIBLE);
                    taskRecyclerView.setVisibility(View.VISIBLE);
                    levelRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    sortByButtons.setVisibility(View.GONE);
                    taskRecyclerView.setVisibility(View.GONE);
                    levelRecyclerView.setVisibility(View.VISIBLE);
                }

                showingTask = !showingTask;
            }
        });

        ImageView bookIcon = findViewById(R.id.imageView8);
        bookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, PlantEncyclopedia.class);
                startActivity(intent);
            }
        });

        ImageView addTaskImage = findViewById(R.id.addTaskImage);
        addTaskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskList.this, AddTaskActivity.class);
                myActivityResultLauncher.launch(i);
            }
        });


    }



    private void setuptaskModelList() {
        String taskName;
        for(int i = 1; i <= 10; i++) {
            taskModelList.add(new TaskModel("Task " + i, "This is a task", false));
        }

    }

    private void setupData() {
        playerLevel = 8;

        ArrayList<Integer> lvls = new ArrayList<>();

        for (int i = 0; i <= playerLevel; i = i + 5) {
            lvls.add(i);
        }
        lvls.add(lvls.get(lvls.size() - 1) + 5);


        playerLevels = lvls.stream().mapToInt(i -> i).toArray();
        plantSprites = new int[]{R.drawable.sprite_plant_lvl0, R.drawable.sprite_plant_lvl5};
    }

    private void animateProgress(ProgressBar progressBar, int progress,
                                 TextView progressText, String pre, String post, int multiplier) {
        // Create an ObjectAnimator to animate progress from 0 to the desired value
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, progress);
        animation.setDuration(1500); // Set the duration (1.5 seconds)
        animation.start();

        // update the text dynamically during the animation
        animation.addUpdateListener(animator -> {
            int animatedValue = (int) animator.getAnimatedValue();
            animatedValue = animatedValue * multiplier;
            progressText.setText(pre + animatedValue + post);
        });
    }
}