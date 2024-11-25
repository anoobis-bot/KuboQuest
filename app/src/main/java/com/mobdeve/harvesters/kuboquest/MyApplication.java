package com.mobdeve.harvesters.kuboquest;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the app lifecycle tracker
        new AppLifecycleTracker(this);
    }
}
