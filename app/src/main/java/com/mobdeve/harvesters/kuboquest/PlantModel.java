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
    private int seedResource;
    private int sproutResource;
    private int grownResource;
    private int requiredEnergy;

    public static enum Stage {
        SEED("Seed"),
        SPROUT("Sprout"),
        GROWN("Grown"),
        HARVEST("Harvest");

        private String stage;

        Stage(String stage) {
            this.stage = stage;
        }

        public String getString() {
            return stage;
        }
    }

    public PlantModel(String name, String description, String rarity, int sproutXP, int grownXP, int harvestXP,
                      int iconResource, int seedResource, int sproutResource, int grownResource) {
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
        this.seedResource = seedResource;
        this.sproutResource = sproutResource;
        this.grownResource = grownResource;
    }

    public Stage getStage() {
        if (this.currentXP < this.sproutXP)
            return Stage.SEED;
        if (this.currentXP < this.grownXP)
            return Stage.SPROUT;
        if (this.currentXP < this.harvestXP)
            return Stage.GROWN;

        return Stage.HARVEST;
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

    public int getSeedResource() {
        return seedResource;
    }

    public int getSproutResource() {
        return sproutResource;
    }

    public int getGrownResource() {
        return grownResource;
    }
}

