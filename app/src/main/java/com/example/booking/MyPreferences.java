package com.example.booking;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static String getStringValue(String key, String defaultValue, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setStringValue(String key, String value, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getIntValue(String key, int defaultValue, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void setIntValue(String key, int value, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static boolean getBooleanValue(String key, boolean defaultValue, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBooleanValue(String key, boolean value, Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // you can add other methods for other types such as float, long, etc.
}
