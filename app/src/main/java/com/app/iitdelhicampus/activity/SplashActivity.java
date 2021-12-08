package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.preference.AppPreferences;

/**
 * Created by pawan on 30/3/17.
 */

public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;
    private AppPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = AppPreferences.getInstance(this);
//        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.act_splash);
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class clz = null;
                if (preferences.isLoggedIn()) {
                    clz = DashBoardActivityKotlin2.class;

//                    if (preferences.getUserType().equalsIgnoreCase("client")) {
//                        clz = DashBoardActivityKotlin2.class;
//                    }
                } else {
                    clz = LoginWithNumberActivity2.class;
                }
                Intent i = new Intent(SplashActivity.this, clz);
                startActivity(i);
                finish();


            }
        }, 2000);
    }

}


