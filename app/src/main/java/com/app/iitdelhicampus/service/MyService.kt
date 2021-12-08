package com.app.iitdelhicampus.service

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.iitdelhicampus.R
import com.app.iitdelhicampus.activity.DashBoardActivityKotlin2
import com.app.iitdelhicampus.application.BaseApplication
import com.app.iitdelhicampus.preference.AppPreferences
import com.app.iitdelhicampus.utility.Datastatic
import com.google.android.gms.location.*


class MyService : Service() {
    companion object {
        val START_SERVICE = "start"
        val STOP_SERVICE = "stop"
        val FOREGROUND_SERVICE = "foreground"
        const val TAG = "MyService"
    }

    var mLocationCallBack: LocationCallback? = null
    var preferences: AppPreferences? = null
    private var mLocationClient: FusedLocationProviderClient? = null


    var isForeGroundService = false

    val CHANNEL_ID: String = "channelId"

    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onCreate() {
        super.onCreate()
        isForeGroundService = false
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intentAction = intent?.action
        when (intentAction) {
            START_SERVICE -> {
                showToast("Service started")
            }
            STOP_SERVICE -> stopService()
            FOREGROUND_SERVICE -> {
                doForegroundThings()
                initLocation()
                getLocationUpdates()
            }
            else -> {
                showToast(intentAction ?: "Empty action intent")
            }
        }
        return START_STICKY /*super.onStartCommand(intent, flags, startId)*/
    }

    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun doForegroundThings() {
        showToast("Going foreground")
        createNotificationChannel()
        val notificationIntent = Intent(this, DashBoardActivityKotlin2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        isForeGroundService = true
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle("Wach (IIT)")
                .setContentText("Service In Progress...")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .addAction(R.drawable.cross_red, "Stop service", makePendingIntent("quit_action"))

        val notification = builder.build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(4, notification)
        }


// Notification ID cannot be 0.

        startForeground(4, notification)
    }


    fun initLocation() {
        preferences = AppPreferences.getInstance(BaseApplication.getInstance())
        mLocationClient = LocationServices.getFusedLocationProviderClient(this@MyService)
        mLocationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
//                    Log.d(MyBackgroundLocationService.TAG, "onLocationResult: is null")
                    return
                }
                preferences!!.setCurrentLatitude(locationResult.locations[0].latitude.toString() + "")
                preferences!!.setCurrentLongitude(locationResult.locations[0].longitude.toString() + "")
                Datastatic.getInstance().updateLocation(this@MyService)
                //                Log.d(TAG, "onLocationResult: " + locationResult.getLocations().get(0).getLatitude() + "/" + locationResult.getLocations().get(0).getLongitude());
            }
        }
    }

    private fun getLocationUpdates() {
        try {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 10000
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    stopSelf()
                }
            }
            mLocationClient!!.requestLocationUpdates(locationRequest, mLocationCallBack, Looper.myLooper())
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = resources.getString(R.string.app_name)
            val descriptionText = getString(R.string.channel)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun stopService() {
        showToast("Service stopping")
        try {
            stopForeground(true)
            isForeGroundService = false
            stopSelf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun makePendingIntent(name: String?): PendingIntent? {
        val intent = Intent(this, Receiver::class.java)
        intent.action = name
        return PendingIntent.getBroadcast(this, 0, intent, 0)
    }
}