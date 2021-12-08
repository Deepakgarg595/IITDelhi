package com.app.iitdelhicampus.location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.app.iitdelhicampus.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;


/**
 * @author pankajsoni <pankajsoni@softwarejoint.com>
 */

public class LocationTracker implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "LocationTracker";
    public GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Location mCurrentLocation;
    private Context mContext;
    private LocationChangeListener locationChangeListener;
    private PendingResult<Status> pendingResult;

    public interface LocationChangeListener {
        void onLocationChanged(Location location);
    }

    public LocationTracker(Context context) {
        mContext = context;
        mGoogleApiClient =
                new GoogleApiClient.Builder(mContext)
                        .addApi(LocationServices.API)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 60 sec
        mLocationRequest.setSmallestDisplacement(100); //  500 mtrs
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (!isClientConnected()) mGoogleApiClient.connect();
    }

    public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }

    public Location getLocation() {
        return mCurrentLocation != null ? mCurrentLocation : (mLastLocation != null) ? mLastLocation : null;
    }

    public boolean isClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    public void disconnectClient() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "connection falied!");
        try {
            connectionResult.startResolutionForResult((Activity) mContext, CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "onConnectionFailed", e);
        }
    }

    @Override
    public void onConnected(Bundle args) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) onGotLocation(mLastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int args) {
        Log.d(TAG, "connection Suspended!: " + args);
    }

    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        } else if (pendingResult != null && !pendingResult.isCanceled()) {
            pendingResult.cancel();
            pendingResult = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        onGotLocation(mCurrentLocation);
    }

//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }

    private void onGotLocation(Location location) {
        if (location == null || locationChangeListener == null) return;
        locationChangeListener.onLocationChanged(location);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle(mContext.getString(R.string.GPS_settings));
        alertDialog.setMessage(mContext.getString(R.string.GPS_is_not_enabled_do_you_want_to));
        alertDialog.setPositiveButton(mContext.getString(R.string.settings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton(mContext.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
