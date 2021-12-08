package com.app.iitdelhicampus.service;

import static com.app.iitdelhicampus.constants.Constants.SOS;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.DashBoardActivityKotlin2;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.SOSModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ScreenReceiver extends BroadcastReceiver {
    public static boolean wasScreenOn = true;
    private AppPreferences preferences;
    private Context context;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e("LOB","onReceive");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;

        } else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            preferences= AppPreferences.getInstance(context);

//            Intent i = new Intent(context, DashBoardActivityKotlin2.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
        }
    }
}
