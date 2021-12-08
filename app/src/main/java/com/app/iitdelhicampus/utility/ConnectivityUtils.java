package com.app.iitdelhicampus.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;

public class ConnectivityUtils {

	private static final String LOG_TAG = "ConnectivityUtils";

	public static boolean isNetworkEnabled(Context context) {
		NetworkInfo activeNetwork = getActiveNetwork(context);
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	public static boolean isLocationEnabled(Context context) {
	    int locationMode = 0;
	    String locationProviders;

	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
	        try {
	            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
	        } catch (SettingNotFoundException e) {
				Log.e(LOG_TAG, "isLocationEnabled", e);
			}

	        return locationMode != Settings.Secure.LOCATION_MODE_OFF;

	    }else{
	        locationProviders =
					Settings.Secure
							.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        return !TextUtils.isEmpty(locationProviders);
	    }
	} 

	public static NetworkInfo getActiveNetwork(Context pContext) {
		if(pContext == null) return null;
		ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
	}
}