package com.mobdeve.harvesters.kuboquest;

public class PlayerModel {
    protected PlantModel activePlant;
    protected int energy;
    protected int soilWater;

    private static PlayerModel sharedInstance = null;

    public static void initialize(PlantModel activePlant) {
        if (sharedInstance == null)
        {
            sharedInstance = new PlayerModel(activePlant);
        }
    }

    public static PlayerModel getInstance()
    {
        return sharedInstance;
    }

    private PlayerModel(PlantModel activePlant) {
        this.activePlant = activePlant;
        this.energy = 8;
        this.soilWater = 100;
    }

    public void setActivePlant(PlantModel activePlant) {
        this.activePlant = activePlant;
    }

    public void setSoilWater(int soilWater) {
        this.soilWater = soilWater;
    }

    public int getSoilWater() {
        return soilWater;
    }

    public int getEnergy() {
        return energy;
    }

    public PlantModel getActivePlant () {
        return this.activePlant;
    }

    public void incrementWater(int value) {
        if (this.soilWater + value >= 100) {
            this.soilWater = 100;
        }

        else {
            this.soilWater = this.soilWater +  value;
        }
    }
}
