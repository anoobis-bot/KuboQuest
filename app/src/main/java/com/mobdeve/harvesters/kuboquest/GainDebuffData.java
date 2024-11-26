package com.mobdeve.harvesters.kuboquest;

import java.util.HashMap;
import java.util.Map;

public class GainDebuffData {
    // ------------- For XP
    // Nested Map to store XP and debuff values
    private static final Map<String, Map<String, XPConfig>> xpTable = new HashMap<>();

    static {
        // Initialize the table for each frequency and difficulty combination

        // DAILY
        xpTable.put("Daily", Map.of(
                "Easy", new XPConfig(10, -2),
                "Medium", new XPConfig(20, -5),
                "Hard", new XPConfig(30, -10)
        ));

        // WEEKLY
        xpTable.put("Weekly", Map.of(
                "Easy", new XPConfig(20, -5),
                "Medium", new XPConfig(40, -10),
                "Hard", new XPConfig(80, -20)
        ));

        // MONTHLY
        xpTable.put("Monthly", Map.of(
                "Easy", new XPConfig(50, -10),
                "Medium", new XPConfig(100, -20),
                "Hard", new XPConfig(200, -50)
        ));
    }

    // Method to get XPConfig for a given frequency and difficulty
    public static int getXPGain(String frequency, String difficulty) {
        // Validate input
        if (!xpTable.containsKey(frequency)) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
        Map<String, XPConfig> difficultyMap = xpTable.get(frequency);
        if (!difficultyMap.containsKey(difficulty)) {
            throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
        return difficultyMap.get(difficulty).getXpGain();
    }
    public static int getXPDebuff(String frequency, String difficulty) {
        // Validate input
        if (!xpTable.containsKey(frequency)) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
        Map<String, XPConfig> difficultyMap = xpTable.get(frequency);
        if (!difficultyMap.containsKey(difficulty)) {
            throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
        return difficultyMap.get(difficulty).getXpDebuff();
    }

    private static class XPConfig {
        private final int xpGain;
        private final int xpDebuff;

        public XPConfig(int xpGain, int xpDebuff) {
            this.xpGain = xpGain;
            this.xpDebuff = xpDebuff;
        }

        public int getXpGain() {
            return xpGain;
        }

        public int getXpDebuff() {
            return xpDebuff;
        }
    }


    // ------------- For WATER
    private static final Map<String, Map<String, WaterConfig>> waterTable = new HashMap<>();

    static {
        // Initialize the table for each frequency and difficulty combination

        // DAILY
        waterTable.put("Daily", Map.of(
                "Easy", new WaterConfig(1, -1),
                "Medium", new WaterConfig(2, -1),
                "Hard", new WaterConfig(3, -2)
        ));

        // WEEKLY
        waterTable.put("Weekly", Map.of(
                "Easy", new WaterConfig(3, -1),
                "Medium", new WaterConfig(5, -2),
                "Hard", new WaterConfig(8, -4)
        ));

        // MONTHLY
        waterTable.put("Monthly", Map.of(
                "Easy", new WaterConfig(5, -2),
                "Medium", new WaterConfig(10, -4),
                "Hard", new WaterConfig(15, -8)
        ));
    }

    // Method to get WaterConfig for a given frequency and difficulty
    public static int getWaterGain(String frequency, String difficulty) {
        // Validate input
        if (!waterTable.containsKey(frequency)) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
        Map<String, WaterConfig> difficultyMap = waterTable.get(frequency);
        if (!difficultyMap.containsKey(difficulty)) {
            throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
        return difficultyMap.get(difficulty).getGain();
    }
    public static int getWaterDebuff(String frequency, String difficulty) {
        // Validate input
        if (!waterTable.containsKey(frequency)) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
        Map<String, WaterConfig> difficultyMap = waterTable.get(frequency);
        if (!difficultyMap.containsKey(difficulty)) {
            throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
        return difficultyMap.get(difficulty).getDebuff();
    }

    // Nested class to store Gain and Debuff values
    private static class WaterConfig {
        private final int gain;
        private final int debuff;

        public WaterConfig(int gain, int debuff) {
            this.gain = gain;
            this.debuff = debuff;
        }

        public int getGain() {
            return gain;
        }

        public int getDebuff() {
            return debuff;
        }
    }
}
