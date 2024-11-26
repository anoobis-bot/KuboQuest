package com.mobdeve.harvesters.kuboquest;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppLifecycleTracker implements LifecycleObserver {
    private final Context appContext; // Store the application context
    private static boolean isAppInForeground = false;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public AppLifecycleTracker(Application application) {
        // Register the observer to track app lifecycle
        this.appContext = application.getApplicationContext();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        isAppInForeground = true; // App is in the foreground

        cancelAlarm(this.appContext);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        isAppInForeground = false; // App is in the background

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            scheduleRepeatingAlarm(this.appContext);
        }
    }

    public static boolean isAppInForeground() {
        return isAppInForeground;
    }

    private void cancelAlarm(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void scheduleRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long interval = 10 * 60 * 100; // 60 secs in millisecond
        long triggerAtMillis = System.currentTimeMillis() + interval;

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP to wake up the device for the alarm
                triggerAtMillis,
                interval,
                pendingIntent
        );
    }
}
