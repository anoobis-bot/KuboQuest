package com.mobdeve.harvesters.kuboquest;

public class Plant {
    private String name;
    private boolean isLocked;
    private int imageResource;

    public Plant(String name, boolean isLocked, int imageResource) {
        this.name = name;
        this.isLocked = isLocked;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getImageResource() {
        return imageResource;
    }
}

