package com.varxiodz.akebichanwallpapers.ads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.varxiodz.akebichanwallpapers.R;

import java.util.concurrent.TimeUnit;

public class Ads implements MaxRewardedAdListener {

    public static MaxInterstitialAd interstitialAd;
    public int retry = 0;

    preferencesHelper pref;
    public Ads(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        pref = new preferencesHelper(activity);
        //AppLovinSdk.getInstance(context).setMediationProvider();
    }
    public static MaxRewardedAd rewardedAd;
    private int           retryAttempt;
    public MaxAdView adView;
    private final Activity activity;
    private final Context context;



    public void loadBanner() {
        // Create an ad request.

        adView = new MaxAdView(activity.getResources().getString(R.string.bannerAd), activity);

        // adView.setExtraParameter( "15", "120" );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.v("banner", "banner load it");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.v("banner", "banner ad  displayed");
            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.v("banner", "banner load failed");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.v("banner", String.valueOf(error));
            }
        });

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);
        adView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.BOTTOM));
        adView.setBackgroundColor(Color.WHITE);

        LinearLayout layout = activity.findViewById(R.id.adLayout);
        layout.addView(adView);
        adView.loadAd();
     // Where adView is an instance of MaxAdView
       adView.setExtraParameter( "10", "10");
    }
    public  void LoadInterstitialAd( ) {
        interstitialAd = new MaxInterstitialAd(activity.getResources().getString(R.string.Interstitial_id), activity);
        MaxAdListener adListener = new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                retry++;
                long delay = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retry)));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interstitialAd.loadAd();
                    }
                }, delay);

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        };
        interstitialAd.setListener(adListener);
        interstitialAd.loadAd();
    }

    public  void playInterstitialAd() {
        if (interstitialAd != null){
        if (interstitialAd.isReady()) {
            interstitialAd.showAd();
        }
        }else{
            Log.v("Tag", "interstitialAd is null");        }
    }



    public void playRewarded(){
        if ( rewardedAd.isReady() )
        {
            rewardedAd.showAd();
        }else {
            Toast.makeText(context,"ad is not ready yet!", Toast.LENGTH_LONG).show();
        }
    }
    public void loadRewarded() {
        rewardedAd = MaxRewardedAd.getInstance( activity.getString(R.string.Video_id), activity );
        rewardedAd.setListener( this );

        rewardedAd.loadAd();
    }

    // MAX Ad Listener
    @Override
    public void onAdLoaded(final MaxAd maxAd) {
        // Rewarded ad is ready to be shown. rewardedAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0;
    }

    @Override
    public void onAdLoadFailed(final String adUnitId, final MaxError error) {
        // Rewarded ad failed to load
        // We recommend retrying with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

        new Handler().postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                rewardedAd.loadAd();
            }
        }, delayMillis );
    }

    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
        // Rewarded ad failed to display. We recommend loading the next ad
        rewardedAd.loadAd();
    }

    @Override
    public void onAdDisplayed(final MaxAd maxAd) {}

    @Override
    public void onAdClicked(final MaxAd maxAd) {}

    @Override
    public void onAdHidden(final MaxAd maxAd) {
        // rewarded ad is hidden. Pre-load the next ad
        rewardedAd.loadAd();
    }

    @Override
    public void onRewardedVideoStarted(final MaxAd maxAd) {} // deprecated

    @Override
    public void onRewardedVideoCompleted(final MaxAd maxAd) {

    } // deprecated


    @Override
    public void onUserRewarded(final MaxAd ad, final MaxReward reward)
    {

     pref.SaveBool("Rewarded",true);
       // reward.getAmount()
   // reward.getLabel() ;
    }
}
