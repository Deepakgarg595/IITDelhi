package com.app.iitdelhicampus.utility;

import android.util.Log;

import com.app.iitdelhicampus.BuildConfig;


/**
 * Created by pawan on 3/4/17.
 */

public class Logger {
    public static void printLog(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg);

    }
}
