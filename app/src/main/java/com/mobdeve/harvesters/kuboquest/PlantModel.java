package com.mobdeve.harvesters.kuboquest;

public class PlantModel {
    private final String name;
    private final String description;
    private final String rarity;
    private final int sproutXP;
    private final int grownXP;
    private final int harvestXP;

    private int currentXP;
    private boolean isLocked;
    private int iconResource;
    private int requiredEnergy;

    public PlantModel(String name, String description, String rarity, int sproutXP, int grownXP, int harvestXP,
                      int iconResource) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.sproutXP = sproutXP;
        this.grownXP = grownXP;
        this.harvestXP = harvestXP;

        // dummy value
        this.currentXP = 100;
        this.isLocked = true;
        this.requiredEnergy = 10;

        this.iconResource = iconResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSproutXP() {
        return sproutXP;
    }

    public int getGrownXP() {
        return grownXP;
    }

    public int getHarvestXP() {
        return harvestXP;
    }

    public String getRarity() {
        return rarity;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getIconResource() {
        return iconResource;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void incrementXP(int value) {
        this.currentXP = this.currentXP + value;
    }
}

