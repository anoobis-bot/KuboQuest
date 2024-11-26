package com.mobdeve.harvesters.kuboquest;

import static com.mobdeve.harvesters.kuboquest.PlantData.plantData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GachaSystem {
    public static Map<String, Double> rarityProbabilities = new HashMap<>();

    public GachaSystem() {
        rarityProbabilities.put("Common", 0.52);
        rarityProbabilities.put("Uncommon", 0.30);
        rarityProbabilities.put("Rare", 0.13);
        rarityProbabilities.put("Legendary", 0.05);
    }

    public static PlantModel gachaPull() {
        String rarity = getRarityByProbability();

        List<PlantModel> filteredItems = new ArrayList<>();
        for (PlantModel plant : plantData) {
            if (plant.getRarity().equalsIgnoreCase(rarity)) {
                filteredItems.add(plant);
            }
        }

        Random random = new Random();
        return filteredItems.get(random.nextInt(filteredItems.size()));
    }

    public static String getRarityByProbability() {
        Random random = new Random();
        double rand = random.nextDouble(); // Random value between 0.0 and 1.0

        double cumulativeProbability = 0.0;
        for (Map.Entry<String, Double> entry : rarityProbabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (rand <= cumulativeProbability) {
                return entry.getKey();
            }
        }

        // Fallback (shouldn't occur if probabilities sum to 1.0)
        return "Common";
    }
}
