package com.mobdeve.harvesters.kuboquest;

public class PlayerModel {
    protected PlantModel activePlant;
    protected int energy;
    protected int soilWater;

    public PlayerModel(PlantModel activePlant) {
        this.activePlant = activePlant;
        this.energy = 8;
        this.soilWater = 70;
    }

    public void setActivePlant(PlantModel activePlant) {
        this.activePlant = activePlant;
    }

    public PlantModel getActivePlant () {
        return this.activePlant;
    }
}
