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
import java.util.List;

public class PlantEncyclopediaRecycler extends AppCompatActivity {

    List<PlantModel> plantList = PlantData.plantData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_encyclopedia_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView plantEncyclopediaRecyclerView = findViewById(R.id.plantEncyclopediaRecyclerView);

//        setupPlantList();

        PlantEncyclopedia_RecyclerViewAdapter adapter = new PlantEncyclopedia_RecyclerViewAdapter(this, plantList);
        plantEncyclopediaRecyclerView.setAdapter(adapter);
        plantEncyclopediaRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setupPlantList() {
//        plantList.add(new PlantModel("Tomato", "Tomatoes are a great source of vitamins A, C, and K. They are easy to grow and require full sunlight for optimal growth.",
//                false, R.drawable.fruit_tomato, 10, 70));
//        plantList.add(new PlantModel("Sesame Seed", "Lorem Ipsum",
//                true, R.drawable.fruit_tomato, 10, 70));
//        plantList.add(new PlantModel("Winter Melon", "Lorem Ipsum",
//                true, R.drawable.fruit_tomato, 10, 70));
//        plantList.add(new PlantModel("Bottle Gourd", "Lorem Ipsum",
//                true, R.drawable.fruit_tomato, 10, 70));
//        plantList.add(new PlantModel("Sponge Gourd", "Lorem Ipsum",
//                true, R.drawable.fruit_tomato, 10, 70));
    }
}