package com.varxiodz.akebichanwallpapers.ads;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class preferencesHelper {
    public preferencesHelper(Activity activity) {
        this.activity = activity;
    }

    Activity activity;
    public void SaveString(String key, String value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String LoadString(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getString(key, activity.getPackageName());

    }
    public void SaveInt(String key, int value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int LoadInt(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    public boolean LoadBool(String key) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }
    public void SaveBool(String key, Boolean value) {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}

