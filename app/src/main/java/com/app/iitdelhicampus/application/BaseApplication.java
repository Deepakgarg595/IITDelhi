package com.app.iitdelhicampus.application;


import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;
import java.util.Timer;
import java.util.TimerTask;


public class BaseApplication extends MultiDexApplication {

    private static final String TAG = "BaseApplication";

    private static final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;
    private static BaseApplication mInstance;

    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    private boolean mIsInBackground = true;
    private boolean mIsCallingInBackground = false;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static void sendBroadCast(String action) {
        Intent intent = new Intent(action);
        sendBroadCast(intent);
    }

    public static void sendBroadCast(Intent intent) {
        LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(mInstance);

    }


}
