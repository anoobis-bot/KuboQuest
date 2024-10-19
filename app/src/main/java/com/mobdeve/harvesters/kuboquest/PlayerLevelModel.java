package com.mobdeve.harvesters.kuboquest;

public class PlayerLevelModel {
    protected int playerLevel;
    protected int plantSprite;

    public PlayerLevelModel(int playerLevel, int plantSprite) {
        this.playerLevel = playerLevel;
        this.plantSprite = plantSprite;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getPlantSprite() {
        return plantSprite;
    }
}
