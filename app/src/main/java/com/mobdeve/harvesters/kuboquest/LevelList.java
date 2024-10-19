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

public class LevelList extends AppCompatActivity {

    int[] playerLevels, plantSprites;
    int playerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView levelRecyclerView = findViewById(R.id.levelRecyclerView);

        setupData();

        PlayerLevels_RecyclerViewAdapter adapter =  new PlayerLevels_RecyclerViewAdapter(this,
                playerLevels, plantSprites);
        levelRecyclerView.setAdapter(adapter);
        levelRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
}