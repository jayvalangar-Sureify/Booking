package com.example.booking;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseProductionSingletonClass {

    private static FirebaseProductionSingletonClass instance;
    private static FirebaseApp INSTANCE;

    public static FirebaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getSecondProject(context);
        }
        return INSTANCE;
    }

    private static FirebaseApp getSecondProject(Context context) {
        FirebaseOptions options1 = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyApEBnnCE7JeYaYfZaCjZAo7fqregUKl58")
                .setApplicationId("com.example.booking")
                .setProjectId("booking-production-57cff")
                // setDatabaseURL(...)      // in case you need firebase Realtime database
                // setStorageBucket(...)    // in case you need firebase storage MySecondaryProject
                .build();

        FirebaseApp.initializeApp(context, options1, "admin");
        return FirebaseApp.getInstance("admin");
    }
}