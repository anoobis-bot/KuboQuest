package com.mobdeve.harvesters.kuboquest;

public class PlantModel {
    protected String plantName;
    protected String plantDesc;
    protected boolean isLocked;
    protected int imageResource;
    protected int requiredEnergy;

    public PlantModel(String name, String plantDesc, boolean isLocked, int imageResource, int requiredEnergy) {
        this.plantName = name;
        this.plantDesc = plantDesc;
        this.isLocked = isLocked;
        this.imageResource = imageResource;
        this.requiredEnergy = requiredEnergy;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getPlantDesc() {
        return plantDesc;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }
}

