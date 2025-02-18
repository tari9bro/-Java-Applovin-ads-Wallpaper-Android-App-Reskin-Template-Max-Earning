package com.varxiodz.akebichanwallpapers;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.sdk.AppLovinPrivacySettings;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkSettings;
import com.varxiodz.akebichanwallpapers.ads.Ads;
import com.varxiodz.akebichanwallpapers.notification.NotificationDetails;
import com.varxiodz.akebichanwallpapers.notification.NotificationHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Ads ads;
    Settings sttngs;
    ClickListenerHelper clickListenerHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RecyclerFragment())
                    .commit();
        }
        sttngs = new Settings(MainActivity.this,this);
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdkSettings settings = new AppLovinSdkSettings( this );
        settings.setTestDeviceAdvertisingIds(Arrays.asList("65f07fc3-1f8c-4a08-815a-49bc93c63a54","cd683b7d-35e6-4ffb-bd94-10ca1ad1abe1","cc02b347-7a08-47ce-bcfe-10ca1ad1abe1"));
         AppLovinSdk.getInstance( settings, this );
        AppLovinPrivacySettings.setHasUserConsent( true, this );
        AppLovinPrivacySettings.setIsAgeRestrictedUser( false, this );


        AppLovinPrivacySettings.setDoNotSell( true, this );


        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener()
        {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {


                ads = new Ads(MainActivity.this,MainActivity.this);

                // AppLovin SDK is initialized, start loading ads
                ads.loadBanner();
                ads.LoadInterstitialAd();
                ads.loadRewarded();

            }
        } );






        clickListenerHelper = new ClickListenerHelper(MainActivity.this,this);

        findViewById(R.id.share).setOnClickListener(clickListenerHelper);
        findViewById(R.id.apps).setOnClickListener(clickListenerHelper);
        findViewById(R.id.exit).setOnClickListener(clickListenerHelper);
        findViewById(R.id.floatingActionButton).setOnClickListener(clickListenerHelper);







        NotificationHelper.createNotificationChannel(this);

        // Create a list of NotificationDetails objects with your notifications
        List<NotificationDetails> notificationList = new ArrayList<>();
        notificationList.add(new NotificationDetails("you can't win this game", "Rolling Ball Ball Rolling Game: A Thrilling Puzzle Adventure!", "https://play.google.com/store/apps/details?id=com.nezzak.mygame", R.drawable.game1));
        notificationList.add(new NotificationDetails("you can't win this game", "an exciting and immersive basketball game that puts you in the shoes of a skilled dunker.", "https://play.google.com/store/apps/details?id=com.nazzark.slamdunkthefirst", R.drawable.game2));
        notificationList.add(new NotificationDetails("you can't win this game", "Blue Beetle: Gun Game is an exhilarating action-packed video game that immerses players in a high-octane world of crime-fighting and intense gunplay", "https://play.google.com/store/apps/details?id=com.nezzak.bluebeetle", R.drawable.game3));

        // Schedule all notifications in the list
        NotificationHelper.scheduleRepeatingAlarm(this, notificationList);

        sttngs.noInternetDialog(getLifecycle());




    }


}
