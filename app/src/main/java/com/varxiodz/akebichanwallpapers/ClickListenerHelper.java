package com.varxiodz.akebichanwallpapers;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class ClickListenerHelper implements View.OnClickListener {

    private final Activity activity;
    private final Settings settings;


    public ClickListenerHelper(Activity activity, Context context) {
        this.activity = activity;
        settings = new Settings(activity,context);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // Handle onClick event for all TextViews here
        if(view.getId()==R.id.share){
            settings.sharTheApp();
            showConstraintLayout();
        }
        if(view.getId()==R.id.apps){
            settings.moreApps();
            showConstraintLayout();
        }
        if(view.getId()==R.id.exit){
            settings.exitTheApp();
            showConstraintLayout();
        }

        if(view.getId()==R.id.floatingActionButton){
            showConstraintLayout();


        }

    }

    private void showConstraintLayout() {
        if (!loadBool()) {
            // Show ConstraintLayout
            activity.findViewById(R.id.fabLayout).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.floatingActionButton).animate().rotation(45f);
            saveBool(true);
        } else {
            // Hide ConstraintLayout
           // inter.playInterstitialAd();
            activity.findViewById(R.id.fabLayout).setVisibility(View.GONE);
            activity.findViewById(R.id.floatingActionButton).animate().rotation(0f);
            saveBool(false);
        }
    }

    private void saveBool(boolean isConstraintLayoutVisible) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isConstraintLayoutVisible", isConstraintLayoutVisible);
        editor.apply();
    }
    private boolean loadBool() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isConstraintLayoutVisible", false);
    }
}
