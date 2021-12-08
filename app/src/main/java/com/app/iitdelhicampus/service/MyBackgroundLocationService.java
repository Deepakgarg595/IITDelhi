package com.app.iitdelhicampus.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.DashBoardActivityKotlin2;
import com.app.iitdelhicampus.activity.LoginWithNumberActivity2;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.Datastatic;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MyBackgroundLocationService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String TAG = MyBackgroundLocationService.class.getSimpleName();
    LocationCallback mLocationCallBack;
    AppPreferences preferences;
    private FusedLocationProviderClient mLocationClient;


    public MyBackgroundLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initData() {
        preferences = AppPreferences.getInstance(BaseApplication.getInstance());
        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mLocationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d(TAG, "onLocationResult: is null");
                    //Toast.makeText(MyBackgroundLocationService.this, "location--", //Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(MyBackgroundLocationService.this, "Service Running..", //Toast.LENGTH_SHORT).show();

                preferences.setCurrentLatitude(locationResult.getLocations().get(0).getLatitude() + "");
                preferences.setCurrentLongitude(locationResult.getLocations().get(0).getLongitude() + "");
                Datastatic.getInstance().updateLocation(MyBackgroundLocationService.this);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        initData();
        getLocationUpdates();
        //Toast.makeText(MyBackgroundLocationService.this, "Service started", //Toast.LENGTH_SHORT).show();
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, preferences.isLoggedIn() ? DashBoardActivityKotlin2.class : LoginWithNumberActivity2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Wach (IIT Delhi Campus)")
                .setContentText("Location Service is in progress..")
                .setSmallIcon(R.mipmap.app_icon)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
//                .addAction(R.drawable.cross_red,  "Stop service", makePendingIntent("quit_action"))

                .build();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForeground(1, notification);
        }

        return START_STICKY;
    }

    private void getLocationUpdates() {
        final LocationRequest locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(MyBackgroundLocationService.this, "Service Stopped", //Toast.LENGTH_SHORT).show();

                stopSelf();
            }
        }
        mLocationClient.requestLocationUpdates(locationRequest, mLocationCallBack, Looper.myLooper());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(MyBackgroundLocationService.this, "Service Stopped", //Toast.LENGTH_SHORT).show();

        Log.d(TAG, "onDestroyCommand: ");
        // mLocationClient.removeLocationUpdates(mLocationCallBack);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            serviceChannel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public PendingIntent makePendingIntent(String name) {
        Intent intent = new Intent(this, Receiver.class);
        intent.setAction(name);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        return pendingIntent;
    }

}