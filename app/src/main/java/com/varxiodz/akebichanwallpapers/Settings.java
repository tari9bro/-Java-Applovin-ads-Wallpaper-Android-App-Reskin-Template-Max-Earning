package com.varxiodz.akebichanwallpapers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

public class Settings {
    private final Activity activity;
    private final Context context;

    public Settings(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public  void exitTheApp() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(R.string.exit_dialog_title);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setMessage(R.string.exit_dialog_msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> activity.finish());
        dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> Toast.makeText(context,activity.getString(R.string.cancel_exit), Toast.LENGTH_SHORT).show()) ;
        dialog.show();

    }
    public void noInternetDialog( Lifecycle lifecycle ){
        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                activity, lifecycle
        );
//getLifecycle()
        DialogPropertiesPendulum properties = builder.getDialogProperties();

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });

        properties.setCancelable(false); // Optional
        properties.setNoInternetConnectionTitle(activity.getString(R.string.n1)); // Optional
        properties.setNoInternetConnectionMessage(activity.getString(R.string.n2)); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText(activity.getString(R.string.n3)); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle(activity.getString(R.string.n1)); // Optional
        properties.setOnAirplaneModeMessage(activity.getString(R.string.n4)); // Optional
        properties.setPleaseTurnOffText(activity.getString(R.string.n5)); // Optional
        properties.setAirplaneModeOffButtonText(activity.getString(R.string.n6)); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();
    }
    public void moreApps() {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.developer_search))));
        } catch (android.content.ActivityNotFoundException exeption) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.developer_id))));
        }
    }

    public void sharTheApp() {
        String url = activity.getString(R.string.app_link);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plane");
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.share_dialog_msg));
        intent.putExtra(Intent.EXTRA_TEXT, url);
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_dialog_title)));
    }









}
