package com.app.iitdelhicampus.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.EmployeeShiftListActivity;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Map;

import static android.content.ContentValues.TAG;

import androidx.core.app.NotificationCompat;

public class FireBaseMessagingService extends FirebaseMessagingService {
    Gson gson = new Gson();
    private Bitmap bitmap;
    private String title;
    private String content;
    private String imageUrl;
    private String gameUrl;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData()!=null)
            getImage(remoteMessage);
    }

    private void sendNotification(Bitmap bitmap){


        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), EmployeeShiftListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX);


        notificationManager.notify(1, notificationBuilder.build());


    }
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            sendNotification(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private void getImage(final RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        title = data.get("title");
        content = data.get("content");
        imageUrl = data.get("imageUrl");
        gameUrl = data.get("gameUrl");
        //Create thread to fetch image from notification
        if(remoteMessage.getData()!=null){

            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Get image from data Notification

                    Glide.with(getApplicationContext())
                            .load(imageUrl)
                            .into((ImageView) target);
                }
            }) ;
        }
    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        AppPreferences.getInstance(BaseApplication.getInstance()).setDeviceToken(token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "Token= " + token);
    }
}