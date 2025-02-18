package com.varxiodz.akebichanwallpapers.notification;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;


import com.varxiodz.akebichanwallpapers.R;

import java.util.List;

public class NotificationHelper {

    public static final String CHANNEL_ID = "MyNotificationChannel";
    public static final CharSequence CHANNEL_NAME = "My Notification Channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int ALARM_REQUEST_CODE = 1001;
    private static final long INTERVAL_6_HOURS = 6 * 60 * 60 * 1000; // 6 hours interval

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // Set additional properties for the notification channel (optional)
            channel.setDescription("My Notification Channel Description");
            channel.enableVibration(true);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showNotification(Context context, String title, String message, String url, int icon) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(icon)
                .setAutoCancel(true);

        // Create an intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pendingIntent);

        // Set notification sound
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(soundUri);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
    public static void scheduleRepeatingAlarm(Context context, List<NotificationDetails> notificationList) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (int i = 0; i < notificationList.size(); i++) {
            NotificationDetails notificationDetails = notificationList.get(i);
            Intent notificationIntent = new Intent(context, NotificationReceiver.class);
            notificationIntent.putExtra("title", notificationDetails.getTitle());
            notificationIntent.putExtra("message", notificationDetails.getMessage());
            notificationIntent.putExtra("url", notificationDetails.getUrl());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE + i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Set the initial delay (optional)
            long initialDelay = System.currentTimeMillis() + i * INTERVAL_6_HOURS;

            // Set the alarm to repeat every 6 hours (4 times a day)
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, initialDelay, INTERVAL_6_HOURS, pendingIntent);
            }
        }
    }

    // The rest of the NotificationHelper class remains the same

    public static class NotificationReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            String url = intent.getStringExtra("url");
            int icon = intent.getIntExtra("icon", R.drawable.start); // Default icon if not provided

            showNotification(context, title, message, url, icon);
        }
    }
}
