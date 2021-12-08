package com.app.iitdelhicampus.activity

//import com.app.orionsecure.database.Patrolling.PatrollingDatabaseHelper

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationManager
import android.content.*
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.app.iitdelhicampus.R
import com.app.iitdelhicampus.activity.ui.home.HomeFragmentMap
import com.app.iitdelhicampus.constants.Constants
import com.app.iitdelhicampus.fragment.IncidentFragment
import com.app.iitdelhicampus.fragment.ProfileFragmentGate
import com.app.iitdelhicampus.model.EmployeeModel
import com.app.iitdelhicampus.network.LoopJRequestHandler
import com.app.iitdelhicampus.network.OnUpdateResponse
import com.app.iitdelhicampus.preference.AppPreferences
import com.app.iitdelhicampus.service.MyBackgroundLocationService
import com.app.iitdelhicampus.ui.BaseActivity
import com.app.iitdelhicampus.utility.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_dash_board.*
import org.json.JSONObject
import java.util.*


class DashBoardActivityKotlin2 : BaseActivity(), View.OnClickListener, OnUpdateResponse {
    private var frag: Fragment?=null
    private  var tite: String="DashBoard"
    private var idd: String? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var txtName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var imageView: ImageView

    //    private lateinit var imgClose: ImageView
    private lateinit var imgNotification: ImageView
    private lateinit var imgSync: ImageView
    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var tabLayout: SpaceTabLayout
//    private lateinit var viewPager: ViewPager
//    lateinit var navView: NavigationView

    private lateinit var tvDashboard: CustomTextViewBold

    private lateinit var imgUnselected1: ImageView

    private lateinit var txtIncident: TextView
    private lateinit var txtAccount: TextView
    private lateinit var imgHome: ImageView
    private lateinit var imgIncident: ImageView
    private lateinit var imgAccount: ImageView
    private lateinit var imgUnselected2: ImageView
    private lateinit var imgUnselected3: ImageView
    private lateinit var llIncident: LinearLayout
    private lateinit var llAccount: LinearLayout
    private lateinit var selectedTab1: RelativeLayout
    private lateinit var selectedTab2: RelativeLayout
    private lateinit var selectedTab3: RelativeLayout
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null
    public var mTrackingLocation = false
    public var mCurrentLatitude = 0.0
    private var mCurrentLongitude = 0.0
    private var manager: LocationManager? = null
    private var needToShowNotification: Boolean = false

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_kotlin2)
        if (!preferences.isLoggedIn) {
            val intent = Intent(this@DashBoardActivityKotlin2, LoginWithNumberActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
//        if (!preferences.data) {
//            AutoStartHelper.getInstance().getAutoStartPermission(this)
//        }
        drawerLayout = findViewById(R.id.drawer_layout)
        preferences.isAppRestarted = true;
//        if (preferences.userType.contentEquals("TL"))
//            navView = findViewById(R.id.nav_view_tl)

//        stopAnim();
        manager = getSystemService(LOCATION_SERVICE) as LocationManager
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this)
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    CheckListActivity.TRACKING_LOCATION_KEY)
        }

        requestAppPermissions(arrayOf(/*Manifest.permission.ACCESS_BACKGROUND_LOCATION*/ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0, 0, this)
        mLocationCallback = object : LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient updates your location.
             * @param locationResult The result containing the device location.
             */
            override fun onLocationResult(locationResult: LocationResult) {
                // If tracking is turned on, reverse geocode into an address
                if (!preferences.isLoggedIn) return
                if (!ConnectivityUtils.isNetworkEnabled(this@DashBoardActivityKotlin2)) {
                    if (needToShowNotification == false) {
//                        Utility.showNotification(this@DashBoardActivityKotlin2, "Wach (IIT)", "Please check your internet connection..", intent)
                        needToShowNotification = true
                    }
                    return
                } else {
                    needToShowNotification = false

                }
                if (mTrackingLocation) {

                    if (locationResult != null) {
                        mCurrentLatitude = locationResult.lastLocation.latitude
                        mCurrentLongitude = locationResult.lastLocation.longitude
                        preferences.setCurrentLatitude(mCurrentLatitude.toString())
                        preferences.setCurrentLongitude(mCurrentLongitude.toString())

                        if (HomeFragmentMap.getInstance() != null) {
                            HomeFragmentMap.getInstance().updateCurrentTime();

                        }
//                        updateLocation();
                        Datastatic.getInstance().updateLocation(this@DashBoardActivityKotlin2)

                        removeProgressDialog()
                    }
                }
            }
        }
        startTrackingLocation()




        hitApiFetchEmployeeList()
        if (!StringUtils.isNullOrEmpty(preferences.metaDataResponse)) {
            val updateAttendanceModel = gson.fromJson(preferences.metaDataResponse, EmployeeModel::class.java)
            if (updateAttendanceModel != null) {
                Datastatic.getInstance().loadEmpData(updateAttendanceModel.success)
                val employeeData = Datastatic.getInstance().getEmpDataList(preferences.empCode)
                if (employeeData != null) {
                    preferences.setRadiusInMeter(employeeData.radius)
                }

            }
        }



        tvDashboard = findViewById(R.id.tvDashboard) as CustomTextViewBold
        tvDashboard!!.setOnClickListener {
//            if (count < 5) {
//                count = count + 1
//                return@setOnClickListener
//            }
//            if (mCurrentLatitude == 0.0 || mCurrentLongitude == 0.0) {
//                showAlertDialogRefresh(this, "Refresh Location", "Unable to fetch exact location, Please refresh once.")
//                return@setOnClickListener
//            }
//
//            showAlertDialogForUpdateLatLong(this, "BIND LOCATION", "Please bind following geo code to specific location\nLatitude: " + mCurrentLatitude + "\nLongitude: " + mCurrentLongitude, mCurrentLatitude, mCurrentLongitude)
        }


        val navView: NavigationView = findViewById(R.id.nav_view)

//        val nav_header: View = LayoutInflater.from(this).inflate(R.layout.nav_header_dash_board_tl, null)
//
//        (nav_header.findViewById<View>(R.id.tvName) as TextView).text = "PK"
//        navView.addHeaderView(nav_header)


        val navController = findNavController(R.id.nav_host_fragment)


        val hView: View = navView.getHeaderView(0)
        txtName = hView.findViewById<View>(R.id.tvName) as TextView
        txtName.text = if (preferences.name.isEmpty()) {
            preferences.phone
        } else preferences.name

        tvLocation = hView.findViewById<View>(R.id.tvLocation) as TextView
        tvLocation.text = preferences.phone
//        imageView = hView.findViewById<View>(R.id.imageView) as ImageView
//        imgClose = hView.findViewById<View>(R.id.imgClose) as ImageView
        imgNotification = findViewById(R.id.imgNotifiication) as ImageView
        imgSync = findViewById(R.id.imgSync) as ImageView



        imgUnselected1 = findViewById(R.id.imgUnselected1) as ImageView
        imgUnselected1.setOnClickListener(this)
        imgUnselected1.visibility = View.VISIBLE
        imgUnselected2 = findViewById(R.id.imgUnselected2) as ImageView
        imgUnselected2.setOnClickListener(this)
        imgUnselected2.visibility = View.GONE
        imgUnselected3 = findViewById(R.id.imgUnselected3) as ImageView
        imgUnselected3.setOnClickListener(this)
        imgUnselected3.visibility = View.VISIBLE
        txtIncident = findViewById(R.id.txtIncident) as TextView
        imgHome = findViewById(R.id.imgHome) as ImageView
        txtAccount = findViewById(R.id.txtAccount) as TextView
        imgIncident = findViewById(R.id.imgIncident) as ImageView
        imgAccount = findViewById(R.id.imgAccount) as ImageView
        llIncident = findViewById(R.id.llIncident) as LinearLayout
        llAccount = findViewById(R.id.llAccount) as LinearLayout
        llIncident.setOnClickListener(this)
        llAccount.setOnClickListener(this)
        imgHome.setOnClickListener(this)

        selectedTab1 = findViewById(R.id.selectedTab1) as RelativeLayout
        selectedTab1.setOnClickListener(this)
        selectedTab1.visibility = View.GONE
        selectedTab2 = findViewById(R.id.selectedTab2) as RelativeLayout
        selectedTab2.setOnClickListener(this)
        selectedTab2.visibility = View.VISIBLE
        selectedTab3 = findViewById(R.id.selectedTab3) as RelativeLayout
        selectedTab3.setOnClickListener(this)
        selectedTab3.visibility = View.GONE


        val homeFrag2 = HomeFragmentMap()
        tvDashboard.text = "DashBoard"
        loadFragment(homeFrag2, "HomeFrag")

        navView.itemIconTintList = null
        var submenu: Menu = navView.menu
        submenu.clear()
//            submenu.add(0, Menu.FIRST, Menu.FIRST, "Report Incident")
//                    .setIcon(R.drawable.report_incident_icon)
        submenu.add(1, Menu.FIRST + 1, Menu.FIRST, "SOS")
        submenu.add(2, Menu.FIRST + 1, Menu.FIRST, "Incident")
        submenu.add(3, Menu.FIRST + 1, Menu.FIRST, "Logout")


//        navView.menu=
        appBarConfiguration = AppBarConfiguration(
                setOf(), drawerLayout
        )
        navView.setupWithNavController(navController)
        imgMenu!!.setOnClickListener {
            openCloseNavigator()
//            tvDashboard.text="Dashboard-"+Datastatic.getInstance().distance(mCurrentLatitude, mCurrentLongitude)
        }
        imgSync!!.setOnClickListener {
//            if (ConnectivityUtils.isNetworkEnabled(this@DashBoardActivityKotlin)) {
//                val `object` =
//                    JSONObject(PatrollingDatabaseHelper.getInstance(this@DashBoardActivityKotlin).allOfflineData[0].response)
//                if (`object` != null) {
//                    hitApi(`object`);
//                }
//                else
//                {
//                    imgSync.visibility=View.GONE
//                }
//            }
//            val intent = Intent(context, OfflineDataActivity::class.java)
//            context.startActivity(intent)
        }
        imgNotification!!.setOnClickListener {
//            val intent = Intent(context, Notication::class.java)
//            intent.putExtra("header", "Notifications")
//            context.startActivity(intent)
        }
//        imgClose!!.setOnClickListener {
//            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                drawerLayout.closeDrawer(GravityCompat.START)
//            } else {
//                drawerLayout.closeDrawer(GravityCompat.END)
//            }
//        }

//        val txtName: TextView = navView.findViewById(R.id.txtName)
//
//        txtName.text="Mobile: "+preferences.phone
        navView.setNavigationItemSelectedListener {
            val position: Int = it.order
            if (preferences.userType.equals("Tl", ignoreCase = true)) {
                when (it.title) {
                    "Dashboard" -> true
                    "Incident Review" -> showAlertDialog(this, "", "This feature is under process.")

                    "Report Incident" -> {
                        val intent = Intent(this@DashBoardActivityKotlin2, ReviewIncidentActivity::class.java)
                        startActivity(intent)
                    }

                    "Site Observation" -> {
                        val intent = Intent(this@DashBoardActivityKotlin2, SiteObservationListActivity::class.java)
                        startActivity(intent)
                    }
                    "Attendance" -> showAlertDialog(this, "", "This feature is under process.")
                    "Leave" -> showAlertDialog(this, "", "This feature is under process.")
                    "Emp List" -> showAlertDialog(this, "", "This feature is under process.")
                    "SOS" -> {
                        hitApiSOS()
                    }
                    "Incident" -> {
                        hitApiIncident()
                    }
                    "Logout" -> {
                        showUpdateAlert()
                    }

//                    else -> { // Note the block
//                        print("x is neither 1 nor 2")
//                    }
                }
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
                true
            } else if (preferences.userType.equals("PO", ignoreCase = true)) {
                when (it.title) {
                    "Report Incident" -> {
                        val intent = Intent(this@DashBoardActivityKotlin2, ReportAnIssue::class.java)
                        startActivity(intent)
                    }
                    "Logout" -> {
                        showUpdateAlert()
                    }

                }
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
                true
            } else {
                when (it.itemId) {
                    R.id.maintenence -> {
                        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        } else {
                            drawerLayout.closeDrawer(GravityCompat.END)
                        }
                        true
                    }
//
                    R.id.nav_report -> {
                        val intent = Intent(this@DashBoardActivityKotlin2, ReportAnIssue::class.java)
                        startActivity(intent)
                        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        } else {
                            drawerLayout.closeDrawer(GravityCompat.END)
                        }
                        true
                    }
                    R.id.nav_logout -> {
                        showUpdateAlert()
                        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        } else {
                            drawerLayout.closeDrawer(GravityCompat.END)
                        }
                        true
                    }

                    else -> false
                }

            }

        }
        hitApiUpdateAttendance()
        hitApiUpdateDeviceDetail()
    }

    private fun hitApiIncident() {
        txtIncident.setTextColor(resources.getColor(R.color.colorAccent))
        imgIncident.setImageDrawable(resources.getDrawable(R.drawable.warning_sign_r))
        if (preferences.inOut.equals("I")) {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.semer))
        }
        else
        {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
        }
        txtAccount.setTextColor(resources.getColor(R.color.white))
        imgAccount.setImageDrawable(resources.getDrawable(R.drawable.accountin))

        val homeFrag2 = IncidentFragment()
        tvDashboard.text = "Incident"
        loadFragment(homeFrag2, "IncidentFrag")
    }

    private fun hitApiSOS() {
        txtIncident.setTextColor(resources.getColor(R.color.white))
        imgIncident.setImageDrawable(resources.getDrawable(R.drawable.warning_sign))
        if (preferences.inOut.equals("I")) {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss2))
        }
        else
        {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
        }
        txtAccount.setTextColor(resources.getColor(R.color.white))
        imgAccount.setImageDrawable(resources.getDrawable(R.drawable.accountin))
         frag = HomeFragmentMap()
        tvDashboard.text = "DashBoard"
        loadFragment(frag as HomeFragmentMap, "HomeFrag")
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//
//        }
//        return true
//    }

    private fun startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    CheckListActivity.REQUEST_LOCATION_PERMISSION)
        } else {
            mTrackingLocation = true
            mFusedLocationClient!!.requestLocationUpdates(getLocationRequest(),
                    mLocationCallback,
                    null /* Looper */)
        }
    }



    /**
     * Stops tracking the device. Removes the location
     * updates, stops the animation, and resets the UI.
     */
    private fun stopTrackingLocation() {
        if (mTrackingLocation) {
//            if (mFusedLocationClient != null) {
//                mFusedLocationClient!!.removeLocationUpdates(mLocationCallback);
//            }

            mTrackingLocation = false
            //            mLocationButton.setText(R.string.start_tracking_location);
//            mLocationTextView.setText(R.string.textview_hint);
//            mRotateAnim.end();
        }
    }


    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private fun getLocationRequest(): LocationRequest? {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }


    /**
     * Saves the last location on configuration change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(CheckListActivity.TRACKING_LOCATION_KEY, mTrackingLocation)
        super.onSaveInstanceState(outState)
    }

    public fun openCloseNavigator() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START)
        } else {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    override fun onBackPressed() {
        exit()
    }

    fun exit() {
        val dialog = Dialog(this@DashBoardActivityKotlin2)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_logout)
        val txtNumber = dialog.findViewById<View>(R.id.txtNumber) as TextView
        txtNumber.text = "Do you want to Exit?"
        val btnNo = dialog.findViewById<View>(R.id.btnNo) as Button
        btnNo.setOnClickListener { dialog.dismiss() }
        val btnYes = dialog.findViewById<View>(R.id.btnYes) as Button
        btnYes.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onResume() {
        super.onResume()
//        if (preferences.inOut.equals("I")) {
//
//        } else {
//            imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
//        }
        if (mTrackingLocation) {
            startTrackingLocation()
        }
        if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            displayLocationSettingsRequest()
        }
        if (ConnectivityUtils.isNetworkEnabled(context)) {
            Datastatic.getInstance().UpdateOffLineData(context)
        }
        if (preferences.roleName.equals("Guards")) {
            val intent = Intent(this@DashBoardActivityKotlin2, MyBackgroundLocationService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            stopService(intent)
        } else {
            stopService(intent)
        }
        }


//        val intent = Intent(this@DashBoardActivityKotlin2, MyService::class.java)
//        intent.action = MyService.STOP_SERVICE
//        startService(intent)
//
//        if (mBound) {
//            unbindService(connection)
//            mBound = false
//        }
//

    }

//    override fun onStop() {
//        super.onStop()
//        if (mTrackingLocation) {
//            stopTrackingLocation()
//            mTrackingLocation = true
//        }
//    }

    override fun onPause() {
        super.onPause()
        if (mTrackingLocation) {
            stopTrackingLocation()
            mTrackingLocation = true

        }
        if (!preferences.isLoggedIn) return
        if (preferences.roleName.equals("Guards")) {
            val intent =
                Intent(this@DashBoardActivityKotlin2, MyBackgroundLocationService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
//        if (!preferences.isLoggedIn) return
//
//        val intent = Intent(this@DashBoardActivityKotlin2, MyService::class.java)
//        intent.action = MyService.START_SERVICE
//        startService(intent)
//        bindWithService()
    }


    fun displayLocationSettingsRequest() {
        val googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
            private val REQUEST_CHECK_SETTINGS = 0x1
            override fun onResult(result: LocationSettingsResult) {
                val status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                         //   Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(this@DashBoardActivityKotlin2, REQUEST_CHECK_SETTINGS)
                        } catch (e: SendIntentException) {
                            e.printStackTrace()
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        })
    }

    protected fun showUpdateAlert() {
        val alertDialog = AlertDialog.Builder(this@DashBoardActivityKotlin2)
        alertDialog.setTitle(getString(R.string.app_name))
        alertDialog.setMessage(getString(R.string.do_u_want_logout))
        alertDialog.setPositiveButton(
                "Yes"
        ) { dialog, which ->
//            idd = AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.documentId
//            AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.isClearData = true
//            AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.documentId = idd

            hitApiUpdateAttendanceLogout(this@DashBoardActivityKotlin2, "O")

            //            Intent intent1=new Intent(context,MyBackgroundLocationService.class);
//            context.stopService(intent1);

        }
        alertDialog.setNegativeButton(
                "No"
        ) { dialog, which -> dialog.cancel() }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        if (HomeFragmentMap.getInstance() != null) {
            val qrCode = data!!.getStringExtra(Constants.EXTRA_DATA);
            val empCode = data!!.getStringExtra(Constants.EXTRA_EMP_CODE);

            if (requestCode == Constants.REQUEST_CODE_IN_SELF) {
                HomeFragmentMap.getInstance().attInSelf(qrCode)

            } else if (requestCode == Constants.REQUEST_CODE_OUT_SELF) {
                HomeFragmentMap.getInstance().attOutSelf(qrCode, "")

            } else if (requestCode == Constants.REQUEST_CODE_IN_OUT_OTHERS) {
                HomeFragmentMap.getInstance().attInOutOthers(qrCode, empCode)

            }


        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        imgUnselected1.visibility = View.GONE
        imgUnselected2.visibility = View.GONE
        imgUnselected3.visibility = View.GONE

        selectedTab1.visibility = View.GONE
        selectedTab2.visibility = View.GONE
        selectedTab3.visibility = View.GONE
        if (view != null) {
            when (view.id) {
                R.id.imgHome -> {
                    txtIncident.setTextColor(resources.getColor(R.color.white))
                    imgIncident.setImageDrawable(resources.getDrawable(R.drawable.warning_sign))
                    if (preferences.inOut.equals("I")) {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss2))
                    }
                    else
                    {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
                    }
                    txtAccount.setTextColor(resources.getColor(R.color.white))
                    imgAccount.setImageDrawable(resources.getDrawable(R.drawable.accountin))
                     frag = HomeFragmentMap()
                    tvDashboard.text = "DashBoard"
                    loadFragment(frag as HomeFragmentMap, "HomeFrag")

                }
                R.id.llIncident -> {
                    txtIncident.setTextColor(resources.getColor(R.color.colorAccent))
                    imgIncident.setImageDrawable(resources.getDrawable(R.drawable.warning_sign_r))
                    if (preferences.inOut.equals("I")) {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.semer))
                    }
                    else
                    {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
                    }

                    txtAccount.setTextColor(resources.getColor(R.color.white))
                    imgAccount.setImageDrawable(resources.getDrawable(R.drawable.accountin))

                    frag = IncidentFragment()
                    tvDashboard.text = "Incident"
                    loadFragment(frag as IncidentFragment, "IncidentFrag")
                }
                R.id.llAccount -> {
                    txtIncident.setTextColor(resources.getColor(R.color.white))
                    imgIncident.setImageDrawable(resources.getDrawable(R.drawable.warning_sign))

                    if (preferences.inOut.equals("I")) {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.semer))
                    }
                    else
                    {
                        imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
                    }
                    txtAccount.setTextColor(resources.getColor(R.color.colorAccent))
                    imgAccount.setImageDrawable(resources.getDrawable(R.drawable.accountactive))

                    frag = ProfileFragmentGate()
                    tite="Account"
                    tvDashboard.text = "Account"
                    loadFragment(frag as ProfileFragmentGate, "ProfileFrag")
                }
                R.id.imgUnselected1 -> {
                    selectedTab1.visibility = View.VISIBLE
                    imgUnselected2.visibility = View.VISIBLE
                    imgUnselected3.visibility = View.VISIBLE
                    val homeFrag2 = IncidentFragment()
                    tvDashboard.text = "Incident"
                    tite="Incident"

                    loadFragment(homeFrag2, "IncidentFrag")

                }
                R.id.imgUnselected2 -> {
                    selectedTab2.visibility = View.VISIBLE
                    imgUnselected1.visibility = View.VISIBLE
                    imgUnselected3.visibility = View.VISIBLE
                    val homeFrag2 = HomeFragmentMap()
                    tvDashboard.text = "DashBoard"
                    tite="DashBoard"

                    loadFragment(homeFrag2, "HomeFrag")
                }
                R.id.imgUnselected3 -> {
                    selectedTab3.visibility = View.VISIBLE
                    imgUnselected1.visibility = View.VISIBLE
                    imgUnselected2.visibility = View.VISIBLE
                    val homeFrag2 = ProfileFragmentGate()
                    tvDashboard.text = "Profile"
                    loadFragment(homeFrag2, "ProfileFrag")
                }
                R.id.selectedTab1 -> {
                    selectedTab1.visibility = View.VISIBLE
                    imgUnselected2.visibility = View.VISIBLE
                    imgUnselected3.visibility = View.VISIBLE
                }
                R.id.selectedTab2 -> {
                    selectedTab2.visibility = View.VISIBLE
                    imgUnselected1.visibility = View.VISIBLE
                    imgUnselected3.visibility = View.VISIBLE
                }

                R.id.selectedTab3 -> {
                    selectedTab3.visibility = View.VISIBLE
                    imgUnselected1.visibility = View.VISIBLE
                    imgUnselected2.visibility = View.VISIBLE
                }


            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment, title)
        transaction.addToBackStack(title)
        transaction.commitAllowingStateLoss()
    }

    fun showAlertDialogRefresh(context: Context?, title: String?, message: String?) {
        val alertDialog = AlertDialog.Builder(context).create()
        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.setTitle(title)
        } else {
            alertDialog.setTitle(title)
        }
        alertDialog.setMessage(message)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "REFRESH"
        ) { dialog, which ->
            dialog.dismiss()
            startTrackingLocation()
            showProgressDialog()
        }
        alertDialog.show()
    }

    fun updateHome() {
        if (tite.equals("DashBoard")) {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss2))
        }
        else
        {
            imgHome.setImageDrawable(resources.getDrawable(R.drawable.semer))
        }
    }

    fun updateHome_() {
        imgHome.setImageDrawable(resources.getDrawable(R.drawable.ss_))
    }

    fun hitApiFetchEmployeeList() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return
            }
            val requestParams = JSONObject()
            try {
                requestParams.put("PunchDate", Utility.getDateFromSec(System.currentTimeMillis()))
                requestParams.put("PunchTime", Utility.getEventTime(System.currentTimeMillis()))
                requestParams.put("UserId", preferences.userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(Constants.FETCH_EMPLOYEE, requestParams, this, Constants.RequestType.FETCH_EMPLOYEE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hitApiUpdateDeviceDetail() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return
            }
            val requestParams = JSONObject()
            try {
                requestParams.put("status_datetime", Utility.getDateTimeUpdate(System.currentTimeMillis()))
                requestParams.put("employee_code", preferences.empCode_)
                requestParams.put("battery_percentage", Datastatic.getInstance().getBatteryPercentage(this@DashBoardActivityKotlin2))
                requestParams.put("gps_status", manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER))
                requestParams.put("network_status", ConnectivityUtils.isNetworkEnabled(this))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(Constants.UPDATE_DEVICE_DETAIL, requestParams, this, Constants.RequestType.UPDATE_DEVICE_DETAIL)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hitApiUpdateAttendance() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return
            }
            //            strInOut = inOut;
//            strEmpCode = empCode;
            val requestParams = JSONObject()
            try {
                requestParams.put("punch_date", Utility.getDate(System.currentTimeMillis()))
                requestParams.put("punch_time", Utility.getTime24Hours(System.currentTimeMillis()))
                requestParams.put("in_out", "I")
                requestParams.put("employee_card", preferences.empCode)
                requestParams.put("branch_id", preferences.branchId)
                requestParams.put("latitude", preferences.currentLatitude)
                requestParams.put("longitude", preferences.currentLongitude)
                requestParams.put("image_url", preferences.empProfileURL)
                requestParams.put("created_by", preferences.empCode)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            if (preferences.roleName.equals("Guards")) {
                LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(Constants.UPDATE_ATTENDANCE, requestParams, this, Constants.RequestType.UPDATE_AUTO_OUT_IN12HOURS)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun stopRunnigService() {
        if (preferences.roleName.equals("Guards")) {
            val intent1 =
                Intent(this@DashBoardActivityKotlin2, MyBackgroundLocationService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                stopService(intent1)
            } else {
                stopService(intent1)
            }
        }
    }

    override fun onResultSuccess(isSuccess: Boolean, response: String?, responseCode: Int) {
//        TODO("Not yet implemented")

        if (responseCode == Constants.RequestType.UPDATE_ATTENDANCE_LOGOUT) {
            AppPreferences.getInstance(this@DashBoardActivityKotlin2)!!.clear()
            AppPreferences.getInstance(this@DashBoardActivityKotlin2)!!.isAppRestarted = false
            stopRunnigService()

            val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll();

            val intent = Intent(this@DashBoardActivityKotlin2, LoginWithNumberActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        if (responseCode == Constants.RequestType.UPDATE_DEVICE_DETAIL) {
            Log.e("Dashboard", "Devide detail updated successfully.")
        } else
            if (responseCode == Constants.RequestType.FETCH_EMPLOYEE) {
                preferences.metaDataResponse = response;
                val updateAttendanceModel = gson.fromJson(response, EmployeeModel::class.java)
                if (updateAttendanceModel != null) {
                    Datastatic.getInstance().loadEmpData(updateAttendanceModel.success)
////               mActivity.showAlertDialog(mActivity,"",updateAttendanceModel.getMessage());
                    for (employeeModel in updateAttendanceModel.success) {
                        if (preferences.empCode.equals(employeeModel.employeeCode, ignoreCase = true)) {
//                        preferences.setStaticLatitude(employeeModel.latitude)
//                        preferences.setStaticLongitude(employeeModel.longitude)
                            preferences.setRadiusInMeter(employeeModel.radius)
                        }
                    }
                }
            }
    }


//    private fun bindWithService() {
//        val intent = Intent(this, MyService::class.java)
//        bindService(intent, connection, BIND_IMPORTANT)
//    }

//    var mService: MyService? = null
//    var mBound = false
//    val TAG = "MainActivity"
//    private val connection: ServiceConnection = object : ServiceConnection {
//        override fun onServiceConnected(
//                className: ComponentName,
//                service: IBinder
//        ) {
//            // We've bound to MyService, cast the IBinder and get MyService instance
//            val binder: MyService.LocalBinder = service as MyService.LocalBinder
//            mService = binder.getService()
//            mBound = true
//            bringServiceToForeground()
//        }
//
//        override fun onServiceDisconnected(arg0: ComponentName) {
//            mBound = false
//        }
//    }

//    private fun bringServiceToForeground() {
//        mService?.let {
//            if (!it.isForeGroundService) {
//                val intent = Intent(this, MyService::class.java)
//                intent.action = MyService.FOREGROUND_SERVICE
//                startForegroundService(this, intent)
//                mService!!.doForegroundThings()
//            } else {
//                Log.d(TAG, "Service is already in foreground")
//            }
//        } ?: Log.d(TAG, "Service is null")
//
//    }


    fun hitApiUpdateAttendanceLogout(context: Context, inOut: String) {
        val preferences = AppPreferences.getInstance(context)
        try {
            val requestParams = JSONObject()
            if (ConnectivityUtils.isNetworkEnabled(context)) {
                try {
                    requestParams.put("PunchDate", Utility.getDate(System.currentTimeMillis()))
                    requestParams.put(
                            "PunchTime",
                            Utility.getTime24Hours(System.currentTimeMillis())
                    )
                    requestParams.put("InOut", inOut)
                    requestParams.put("EmployeeCode", preferences.empCode)
                    requestParams.put("WarehouseId", preferences.warehouseCode)
                    requestParams.put("Latitude", preferences.currentLatitude)
                    requestParams.put("Longitude", preferences.currentLongitude)
                    requestParams.put("ImageURL", preferences.empProfileURL)
                    requestParams.put("UserId", preferences.empCode)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(
                        Constants.UPDATE_ATTENDANCE,
                        requestParams,
                        this,
                        Constants.RequestType.UPDATE_ATTENDANCE_LOGOUT
                )
            } else {
                stopRunnigService()
                val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancelAll()
                val intent = Intent(this@DashBoardActivityKotlin2, LoginWithNumberActivity2::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}