package com.example.myapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMessagingService";
    public static final String PREFS_NAME = "MyPrefs";
    public static final String PARKIRISCE_KEY = "parkirisce";
    public static final String PRIHOD_KEY = "prihod";
    public static final String ODHOD_KEY = "odhod";
    private static final String CHANNEL_ID = "default_channel_id";


        @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        createNotificationChannel();

            Map<String, String> data = remoteMessage.getData();

            // Extract data from the notification
            String parkirisce = data.get("parkirisce");
            String prihod = data.get("prihod");
            String odhod = data.get("odhod");
            String title = data.get("title");
            String body = data.get("body");
            String sound = data.get("sound");

            // Store the data in SharedPreferences
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("wasInBackground", !isAppInForeground(this));
            editor.apply();

            // Create a notification intent
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Create a notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default_channel_id")
                    .setSmallIcon(R.drawable.ic_baseline_local_parking_24)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            if ("default".equals(sound)) {
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            }


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());

            // Send a broadcast when a notification is received
            Intent intent1 = new Intent("com.example.myapplication.NOTIFICATION_RECEIVED");
            sendBroadcast(intent1);




        }

    public boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {



            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                //se vzpostavi povezava z bazo če je bila aplikacija prižgana;
                Domov.vozilo = Baza.OsveziDB();
                return true;
            }
        }
        return false;
    }




    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Default Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("TAG", "Refreshed token: " + token);

        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        // You can save the token to your backend server here
    }

    public void deleteToken(){
        FirebaseMessaging.getInstance().deleteToken().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Log.d(TAG, "onComplete: Token not deleted");
                        }
                        Log.d(TAG, "onComplete: Token deleted");
                    }
                });
    }






}


