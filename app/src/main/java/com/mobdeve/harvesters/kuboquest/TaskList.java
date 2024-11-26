package com.mobdeve.harvesters.kuboquest;

import static com.mobdeve.harvesters.kuboquest.PlantData.plantData;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TaskList extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection(FireStoreReferences.USER_COLLECTION);
    CollectionReference tasksRef;
    Map<String, Object> taskData = new HashMap<>();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

    // UI DATA
    PlayerModel player;
    TextView txtWater;
    ProgressBar progressWater;
    TextView goalXP;
    ProgressBar progressXP;
    TextView txtXP;
    TextView txtPlantName;
    TextView txtLevel;
    ImageView imgPlant;
    ImageView imgFruit;

    // DB variables
    PlantModel currentPlant;
    int currentPlantXP;
    String currentPlantName;
    String currentPlantID;
    int currentWaterLevel;
    int currentPlantLevel;

    boolean showingTask;
    ImageView bookIcon;

    ArrayList<TaskModel> taskModelList = new ArrayList<>();

    int[] playerLevels, plantSprites;
    int playerLevel;

    TaskList_RecyclerViewAdapter adapter1;
    PlayerLevels_RecyclerViewAdapter adapter2;

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

                        //save to db
                        currentUser = mAuth.getCurrentUser();

                        if (currentUser != null) {
                            String uid = currentUser.getUid();

                            taskData.put(FireStoreReferences.TASKNAME_FIELD, name);
                            taskData.put(FireStoreReferences.TASKDESC_FIELD, desc);
                            taskData.put(FireStoreReferences.TASKSTARTDATE_FIELD, start_date);
                            taskData.put(FireStoreReferences.TASKFREQUENCY_FIELD, frequency);
                            taskData.put(FireStoreReferences.TASKDIFFICULTY_FIELD, difficulty);

                            Date finalStart_date = start_date;
                            usersRef.document(uid).collection(FireStoreReferences.TASK_COLLECTION)
                                    .add(taskData)
                                    .addOnSuccessListener(documentReference -> {
                                        // Retrieve the document ID of thw Task
                                        String documentId = documentReference.getId();

                                        // save to taskModelList
//                                        taskModelList.add(new TaskModel(documentId, name, desc, finalStart_date, frequency, difficulty, false));
//
//                                        adapter1.notifyItemChanged(taskModelList.size() - 1);

                                        adapter1.addTask(new TaskModel(documentId, name, desc, finalStart_date, frequency, difficulty, false));
                                    });
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
                        String taskID = result.getData().getStringExtra("task_id");

                        adapter1.deleteTask(taskID);

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

        progressWater = findViewById(R.id.progressWater);
        txtWater = findViewById(R.id.txtWater);
        progressXP = findViewById(R.id.progressXP);
        txtXP = findViewById(R.id.txtXP);
        goalXP = findViewById(R.id.txtGoal);
        txtPlantName = findViewById(R.id.txtPlantName);
        txtLevel = findViewById(R.id.txtLevel);
        imgPlant = findViewById(R.id.imgPlant);
        imgFruit = findViewById(R.id.imgFruit);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        PlantData.initialize(this);

        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //set all the data of the user
            setupData();

            adapter2 =  new PlayerLevels_RecyclerViewAdapter(this,
                    playerLevels, plantSprites);

            adapter1 =  new TaskList_RecyclerViewAdapter(this,
                    taskModelList, "Daily", progressXP, txtXP, progressWater, txtWater,
                    txtLevel, imgPlant, adapter2, myActivityResultLauncher);

            loadUserDataFromDB();
        }


        ImageView imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirm();
            }
        });

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView levelRecyclerView = findViewById(R.id.levelRecyclerView);

        taskRecyclerView.setAdapter(adapter1);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        levelRecyclerView.setAdapter(adapter2);
        levelRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        RadioGroup sortByButtons = findViewById(R.id.sortByButtons);
        sortByButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // checkedId is the ID of the selected RadioButton
                RadioButton selectedRadioButton = findViewById(i);

                // Get the text of the selected RadioButton
                String selectedText = selectedRadioButton.getText().toString();

                adapter1.changeFilter(selectedText);
                adapter1.notifyDataSetChanged();
            }
        });

        showingTask = true;
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
        if (currentUser != null) {
            String uid = currentUser.getUid();

            usersRef.document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            currentWaterLevel = Objects.requireNonNull(documentSnapshot.getLong("waterLevel")).intValue();
                            currentPlantID = documentSnapshot.getString("activePlant");



                            usersRef.document(uid)
                                    .collection(FireStoreReferences.PLANT_COLLECTION)
                                    .document(currentPlantID)
                                    .get()
                                    .addOnSuccessListener(documentSnapshot1 -> {
                                        if (documentSnapshot1.exists()) {
                                            currentPlantXP = Objects.requireNonNull(documentSnapshot1.getLong("currentXP")).intValue();
                                            currentPlantName = documentSnapshot1.getString("plantName");

                                            // initialize all data in the UI
                                            PlayerModel.initialize(PlantData.findPlantByName(currentPlantName));
                                            player = PlayerModel.getInstance();
                                            player.setSoilWater(currentWaterLevel);
                                            player.getActivePlant().setCurrentXP(currentPlantXP);
                                            progressXP.setProgress(player.getActivePlant().getCurrentXP());
                                            txtPlantName.setText(player.getActivePlant().getName());
                                            updatePlantImgTxt(txtLevel, imgPlant);
                                            imgFruit.setImageResource(player.getActivePlant().getIconResource());


                                            goalXP.setText("Goal: " + player.getActivePlant().getHarvestXP() + "XP");

                                            // animate after data is received from db
                                            animateProgress(progressWater, player.getSoilWater(), 100, txtWater, "", "/100", 1);
                                            animateProgress(progressXP, progressXP.getProgress(), player.getActivePlant().getHarvestXP(), txtXP, "", "XP", 1);
                                        }
                                    });

                            usersRef.document(uid)
                                    .collection(FireStoreReferences.PLANT_COLLECTION)
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                            String plantName = doc.getString("plantName");
                                            int plantCurrentXP =  Objects.requireNonNull(doc.getLong("currentXP")).intValue();
                                            boolean plantIsLocked = Boolean.TRUE.equals(doc.getBoolean("isLocked"));

                                            // Update the corresponding object in the list
                                            for (PlantModel plant : plantData) {
                                                if (plant.getName().equals(plantName)) {
                                                    plant.setCurrentXP(plantCurrentXP);
                                                    plant.setLocked(plantIsLocked);
                                                    break;
                                                }
                                            }
                                        }
                                    });
                        }
                    });

        }

        ArrayList<Integer> lvls = new ArrayList<>();

        for (int i = 0; i <= playerLevel; i = i + 5) {
            lvls.add(i);
        }
        lvls.add(lvls.get(lvls.size() - 1) + 5);


        playerLevels = lvls.stream().mapToInt(i -> i).toArray();
        plantSprites = new int[]{R.drawable.sprite_plant_lvl0, R.drawable.sprite_plant_lvl5};
    }

    private void animateProgress(ProgressBar progressBar, int currentProgress, int maxProgress,
                                 TextView progressText, String pre, String post, int multiplier) {
//        // Create an ObjectAnimator to animate progress from 0 to the desired value
//        int progressNorm = (int)((float)currentProgress/maxProgress * 100);
//        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, progressNorm);
//        animation.setDuration(1500); // Set the duration (1.5 seconds)
//        animation.start();
//
//        // update the text dynamically during the animation
//        animation.addUpdateListener(animator -> {
//            int animatedValue = (int) animator.getAnimatedValue();
//            animatedValue = (int)((float)animatedValue / progressBar.getMax() * maxProgress);
//            progressText.setText(pre + animatedValue + post);
//        });

        // Calculate progressNorm as a float
        float progressNorm = (float) currentProgress / maxProgress * 100;

        // Create a ValueAnimator to animate between 0 and progressNorm
        ValueAnimator animation = ValueAnimator.ofFloat(0f, progressNorm);
        animation.setDuration(1500); // Set the duration (1.5 seconds)

        // Add an update listener to update the progress and text dynamically
        animation.addUpdateListener(animator -> {
            float animatedValue = (float) animator.getAnimatedValue(); // Get the current animated value

            // Update the ProgressBar's progress (convert to int)
            progressBar.setProgress(Math.round(animatedValue));

            // Calculate the corresponding progress relative to maxProgress
            int calculatedProgress = Math.round(animatedValue / 100 * maxProgress);

            // Update the progress text
            progressText.setText(pre + calculatedProgress + post);
        });

// Start the animation
        animation.start();


    }

    public static void updateProgressBar(ProgressBar progressXP, TextView txtXP, int maxProgress, int increment, String postText) {
//        // Create an ObjectAnimator to animate progress from 0 to the desired value
//        int currentProgress = progressXP.getProgress();
//        int maxProgress = PlayerModel.getInstance().getActivePlant().getHarvestXP();
//        float val = ((float)increment / maxProgress) * 100;
//        int addedProgress = (int)(((float)increment / maxProgress) * 100);
//
//        ObjectAnimator animation = ObjectAnimator.ofInt(progressXP, "progress", currentProgress, currentProgress + addedProgress);
//        animation.setDuration(500); // Set the duration (.5 seconds)
//        animation.start();
//
//        // update the text dynamically during the animation
//        animation.addUpdateListener(animator -> {
//            int animatedValue = (int) animator.getAnimatedValue();
//            animatedValue = (int)((float)animatedValue / progressXP.getMax() * maxProgress);
//            txtXP.setText(animatedValue + "XP");
//        });

        int currentProgress = progressXP.getProgress(); // Current progress of the ProgressBar
//        int maxProgress = PlayerModel.getInstance().getActivePlant().getHarvestXP(); // Maximum progress value
        float val = ((float) increment / maxProgress) * 100; // Calculate normalized increment
        float targetProgress = currentProgress + val; // Target progress as a float

        // Create a ValueAnimator to animate between the current progress and target progress
        ValueAnimator animation = ValueAnimator.ofFloat(currentProgress, targetProgress);
        animation.setDuration(500); // Set the duration (0.5 seconds)

        // Add an update listener to dynamically update the progress and text
        animation.addUpdateListener(animator -> {
            float animatedValue = (float) animator.getAnimatedValue(); // Get the animated value

            // Update the ProgressBar progress (convert to int for ProgressBar)
            progressXP.setProgress(Math.round(animatedValue));

            // Calculate the XP value corresponding to the current progress
            int calculatedXP = Math.round(animatedValue / 100 * maxProgress);

            // Update the TextView to display the current XP
            txtXP.setText(calculatedXP + postText);
        });

        // Start the animation
        animation.start();
    }

    public static void updatePlantImgTxt (TextView txtLevel, ImageView imgPlant) {
        PlayerModel player = PlayerModel.getInstance();
        switch (player.getActivePlant().getStage()) {
            case SEED:
                txtLevel.setText("Seed");
                imgPlant.setImageResource(player.getActivePlant().getSeedResource());
                break;
            case SPROUT:
                txtLevel.setText("Sprout");
                imgPlant.setImageResource(player.getActivePlant().getSproutResource());
                break;
            case GROWN:
                txtLevel.setText("Grown");
                imgPlant.setImageResource(player.getActivePlant().getGrownResource());
                break;
            case HARVEST:
                txtLevel.setText("Ready to Harvest");
                imgPlant.setImageResource(player.getActivePlant().getGrownResource());
                break;
        }
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
                                        doc.getString("taskDesc"),
                                        taskStartDate,
                                        doc.getString("taskFrequency"),
                                        doc.getString("taskDifficulty"),
                                        Boolean.TRUE.equals(doc.getBoolean("isDone"))
                                );

                                // Add task to taskModelList
//                                taskModelList.add(task);
//                                adapter1.notifyItemChanged(taskModelList.size() - 1);
                                adapter1.addTask(task);
                            }
                        }
                    });
        }
    }
}