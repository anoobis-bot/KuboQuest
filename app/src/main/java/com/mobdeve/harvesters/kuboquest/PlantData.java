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
    static final List<PlantModel> plantData = new ArrayList<>();

    PlantData(Context context) {
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
                plantData.add(new PlantModel(name, description, rarity, sproutXP, grownXP, harvestXP,
                        0));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public List<PlantModel> getPlantData() {
        return plantData;
    }
}
