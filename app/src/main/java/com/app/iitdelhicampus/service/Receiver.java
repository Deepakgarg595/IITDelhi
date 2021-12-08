package com.app.iitdelhicampus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.iitdelhicampus.preference.AppPreferences;

public class Receiver extends BroadcastReceiver {
    AppPreferences preferences;

@Override
public void onReceive(Context context, Intent intent)
    {
        preferences=AppPreferences.getInstance(context);

    String whichAction = intent.getAction();


    switch (whichAction)
        {

        case "quit_action":
            Intent intent1=new Intent(context,MyBackgroundLocationService.class);
//            context.stopService(intent1);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.stopService(intent1);
            } else {
                context.stopService(intent1);

            }


        }


    }






}