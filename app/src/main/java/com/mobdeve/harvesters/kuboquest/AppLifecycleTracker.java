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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppLifecycleTracker implements LifecycleObserver {
    private final Context appContext; // Store the application context
    private static boolean isAppInForeground = false;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    CollectionReference usersRef;

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
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection(FireStoreReferences.USER_COLLECTION);

        if (currentUser != null) {

            usersRef.document(currentUser.getUid())
                    .collection(FireStoreReferences.TASK_COLLECTION)
                    .whereEqualTo(FireStoreReferences.TASKISDONE_FIELD, false)
                    .get()
                    .addOnSuccessListener( queryDocumentSnapshots -> {
                        int dailyCount = 0;
                        int weeklyCount = 0;
                        int monthlyCount = 0;
                        int total = queryDocumentSnapshots.size();

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String frequency = document.getString("taskFrequency");
                            if (frequency != null) {
                                switch (frequency) {
                                    case "Daily":
                                        dailyCount++;
                                        break;
                                    case "Weekly":
                                        weeklyCount++;
                                        break;
                                    case "Monthly":
                                        monthlyCount++;
                                        break;
                                }
                            }
                        }
                        String message;

                        if (total == 0) {
                            message = "You have no task left at the moment. Try to add some tasks";
                        }
                        else {
                            message = "You have " + total + " tasks left! " + dailyCount + " daily task, " + weeklyCount + " weekly task, and " + monthlyCount + " monthly task left!";
                        }

                        scheduleRepeatingAlarm(this.appContext, message);
                    });
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

    public void scheduleRepeatingAlarm(Context context, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("NOTIFICATION_MESSAGE", message);
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
