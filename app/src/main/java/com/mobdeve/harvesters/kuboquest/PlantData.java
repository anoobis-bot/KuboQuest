package com.mobdeve.harvesters.kuboquest;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlantData {
    private static PlantData sharedInstance = null;

    public static final List<PlantModel> plantData = new ArrayList<>();

    public static void initialize(Context context) {
        if (sharedInstance == null) {
            sharedInstance = new PlantData(context);
        }
    }

    private PlantData(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.plants);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                String rarity = jsonObject.getString("rarity");
                int sproutXP = Integer.parseInt(jsonObject.getString("sproutXP"));
                int grownXP = Integer.parseInt(jsonObject.getString("grownXP"));
                int harvestXP = Integer.parseInt(jsonObject.getString("harvestXP"));
                int iconResource = context.getResources().getIdentifier(jsonObject.getString("icon"),
                        "drawable", context.getPackageName());
                int seedResource = context.getResources().getIdentifier(jsonObject.getString("level_seed"),
                        "drawable", context.getPackageName());
                int sproutResource = context.getResources().getIdentifier(jsonObject.getString("level_sprout"),
                        "drawable", context.getPackageName());
                int grownResource = context.getResources().getIdentifier(jsonObject.getString("level_grown"),
                        "drawable", context.getPackageName());
                plantData.add(new PlantModel(name, description, rarity, sproutXP, grownXP, harvestXP,
                        iconResource, seedResource, sproutResource, grownResource));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public List<PlantModel> getPlantData() {
        return plantData;
    }

    public static PlantModel findPlantByName(String name) {
        for (PlantModel plant : plantData) {
            if (plant.getName().equalsIgnoreCase(name)) {
                return plant; // Found the matching plant
            }
        }
        return null;
    }
}
