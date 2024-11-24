package com.mobdeve.harvesters.kuboquest;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TaskList extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection(FireStoreReferences.USER_COLLECTION);
    CollectionReference tasksRef;
    Map<String, Object> taskData = new HashMap<>();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

    boolean showingTask;
    ImageView bookIcon;

    ArrayList<TaskModel> taskModelList = new ArrayList<>();

    int[] playerLevels, plantSprites;
    int playerLevel;

    TaskList_RecyclerViewAdapter adapter1;

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                public void onActivityResult(ActivityResult result) {
                    int duration = Toast.LENGTH_SHORT;

                    if (result.getResultCode() == 1)
                    {
                        String name = result.getData().getStringExtra("task_name");
                        String desc = result.getData().getStringExtra("task_desc");
                        Date start_date = null;
                        try {
                            start_date = formatter.parse(result.getData().getStringExtra("task_start_date"));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        String frequency = result.getData().getStringExtra("task_frequency");
                        String difficulty = result.getData().getStringExtra("task_difficulty");

                        taskModelList.add(new TaskModel(name, desc, start_date, frequency,
                                                        difficulty,
                                                false));

                        adapter1.notifyItemChanged(taskModelList.size() - 1);

                        //save to db
                        currentUser = mAuth.getCurrentUser();

                        if (currentUser != null) {
                            String uid = currentUser.getUid();

                            taskData.put(FireStoreReferences.TASKNAME_FIELD, name);
                            taskData.put(FireStoreReferences.TASKDESC_FIELD, desc);
                            taskData.put(FireStoreReferences.TASKSTARTDATE_FIELD, start_date);
                            taskData.put(FireStoreReferences.TASKFREQUENCY_FIELD, frequency);
                            taskData.put(FireStoreReferences.TASKDIFFICULTY_FIELD, difficulty);

                            usersRef.document(uid).collection(FireStoreReferences.TASK_COLLECTION).add(taskData);
                        }

                        Toast toast = Toast.makeText(TaskList.this, "Task Added!", duration);
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

/*    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loadUserDataFromDB();
        }
    }*/

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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //set all the data of the user
            loadUserDataFromDB();
        }

        ImageView imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirm();
            }
        });

        ProgressBar progressWater = findViewById(R.id.progressWater);
        TextView txtWater = findViewById(R.id.txtWater);
        ProgressBar progressXP = findViewById(R.id.progressXP);
        TextView txtXP = findViewById(R.id.txtXP);

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView levelRecyclerView = findViewById(R.id.levelRecyclerView);

        setupData();

        adapter1 =  new TaskList_RecyclerViewAdapter(this,
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
                Intent intent = new Intent(TaskList.this, PlantEncyclopediaRecycler.class);
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

    private void showLogoutConfirm(){
        Dialog logoutConfirmDialog = new Dialog(this);

        logoutConfirmDialog.setContentView(R.layout.confirm_logout_card);
        logoutConfirmDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView txtConfirmLogout = logoutConfirmDialog.findViewById(R.id.txtConfirmLogout);
        TextView txtConfirmLogoutSub = logoutConfirmDialog.findViewById(R.id.txtConfirmLogourSub);
        Button btnLogout = logoutConfirmDialog.findViewById(R.id.btnLogout);


        txtConfirmLogout.setText("Confirm Logout");
        txtConfirmLogoutSub.setText("Are you sure you want to logout?");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutConfirmDialog.show();
    }

    private void loadUserDataFromDB() {
        if (currentUser != null) {
            String uid = currentUser.getUid();

            usersRef.document(uid).collection(FireStoreReferences.TASK_COLLECTION)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.exists()) {

                                Timestamp timestamp = doc.getTimestamp("taskStartDate");
                                assert timestamp != null;
                                Date taskStartDate = timestamp.toDate();

                                // Map Firestore document to TaskModel object
                                TaskModel task = new TaskModel(
                                        doc.getId(),
                                        doc.getString("taskName"),
                                        doc.getString("taskDescription"),
                                        taskStartDate,
                                        doc.getString("frequency"),
                                        doc.getString("difficulty"),
                                        Boolean.TRUE.equals(doc.getBoolean("isDone"))
                                );

                                // Add task to taskModelList
                                taskModelList.add(task);
                                adapter1.notifyItemChanged(taskModelList.size() - 1);
                            }
                        }
                    });
        }
    }
}