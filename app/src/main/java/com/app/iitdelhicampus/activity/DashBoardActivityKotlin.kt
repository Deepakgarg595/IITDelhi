package com.app.iitdelhicampus.activity

//import com.app.orionsecure.database.Patrolling.PatrollingDatabaseHelper
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.app.iitdelhicampus.R
import com.app.iitdelhicampus.activity.ui.home.HomeFragment
import com.app.iitdelhicampus.constants.Constants
import com.app.iitdelhicampus.preference.AppPreferences
import com.app.iitdelhicampus.ui.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.loopj.android.http.AsyncHttpClient
import kotlinx.android.synthetic.main.app_bar_dash_board.*

class DashBoardActivityKotlin : BaseActivity() {
    private var idd: String? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var imageView: ImageView
    private lateinit var imgClose: ImageView
    private lateinit var imgNotification: ImageView
    private lateinit var imgSync: ImageView
    private lateinit var drawerLayout: DrawerLayout

    private val client = AsyncHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_kotlin)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val hView: View = navView.getHeaderView(0)
//        txtName = hView.findViewById<View>(R.id.txtName) as TextView
//        txtEmail = hView.findViewById<View>(R.id.txtEmail) as TextView
//        imageView = hView.findViewById<View>(R.id.imageView) as ImageView
        imgClose = hView.findViewById<View>(R.id.imgClose) as ImageView
        imgNotification = findViewById(R.id.imgNotifiication) as ImageView
        imgSync = findViewById(R.id.imgSync) as ImageView
//        FileUtils.getFullPic(
//            this,
//            AppPreferences.getInstance(this)!!.profilePic!!,
//            imageView!!,
//            R.drawable.app_icon,
//            "111",
//            false, null
//        )
//        txtName!!.text = AppPreferences.getInstance(this)!!.name+" ("+AppPreferences.getInstance(this)!!.employeeId+")"
//        txtEmail!!.text = AppPreferences.getInstance(this)!!.locationName
        navView.itemIconTintList = null
        appBarConfiguration = AppBarConfiguration(
                setOf(), drawerLayout
        )
        navView.setupWithNavController(navController)
        imgMenu!!.setOnClickListener {
            openCloseNavigator()
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
        imgClose!!.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }

//        val txtName: TextView = navView.findViewById(R.id.txtName)
//
//        txtName.text="Mobile: "+preferences.phone
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.nav_order -> {
//                    val intent = Intent(this@DashBoardActivityKotlin, RoutesActivity::class.java)
//                    startActivity(intent)
//                    if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                        drawerLayout.closeDrawer(GravityCompat.START)
//                    } else {
//                        drawerLayout.closeDrawer(GravityCompat.END)
//                    }
//                    true
//                }
//                R.id.nav_tagging -> {
//                    val intent = Intent(this@DashBoardActivityKotlin, QRTaggingActivity::class.java)
//                    startActivity(intent)
//                    if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                        drawerLayout.closeDrawer(GravityCompat.START)
//                    } else {
//                        drawerLayout.closeDrawer(GravityCompat.END)
//                    }
//                    true
//                }
//
//                R.id.nav_Sync -> {
//                    val intent = Intent(this@DashBoardActivityKotlin, OfflineDataActivity::class.java)
//                    startActivity(intent)
//                    if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                        drawerLayout.closeDrawer(GravityCompat.START)
//                    } else {
//                        drawerLayout.closeDrawer(GravityCompat.END)
//                    }
//                    true
//                }
                R.id.nav_report -> {
                    val intent = Intent(this@DashBoardActivityKotlin, ClientReportListActivity::class.java)
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

   public fun openCloseNavigator(){
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
        val dialog = Dialog(this@DashBoardActivityKotlin)
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
//        FileUtils.getFullPic(
//            this,
//            AppPreferences.getInstance(this)!!.profilePic!!,
//            imageView!!,
//            R.drawable.app_icon,
//            "111",
//            false,
//            null
//        )
//        if (PatrollingDatabaseHelper.getInstance(this@DashBoardActivityKotlin).allOfflineData != null && PatrollingDatabaseHelper.getInstance(
//                this@DashBoardActivityKotlin
//            ).allOfflineData.size != 0
//        ) {
//            imgSync.visibility = View.GONE
//        } else {
//            imgSync.visibility = View.GONE
//        }
//        txtName!!.text = AppPreferences.getInstance(this)!!.name+" ("+AppPreferences.getInstance(this)!!.employeeId+")"
//        txtEmail!!.text = AppPreferences.getInstance(this)!!.locationName
    }

    protected fun showUpdateAlert() {
        val alertDialog = AlertDialog.Builder(this@DashBoardActivityKotlin)
        alertDialog.setTitle(getString(R.string.app_name))
        alertDialog.setMessage(getString(R.string.do_u_want_logout))
        alertDialog.setPositiveButton(
                "Yes"
        ) { dialog, which ->
//            idd = AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.documentId
            AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.clear()
//            AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.isClearData = true
//            AppPreferences.getInstance(this@DashBoardActivityKotlin)!!.documentId = idd
            val intent = Intent(this@DashBoardActivityKotlin, LoginWithNumberActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
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
        if(resultCode!= RESULT_OK) return

        if (HomeFragment.getInstance() != null) {
            val qrCode= data!!.getStringExtra(Constants.EXTRA_DATA);
            val empCode= data!!.getStringExtra(Constants.EXTRA_EMP_CODE);

            if(requestCode==Constants.REQUEST_CODE_IN_SELF){
                HomeFragment.getInstance().attInSelf(qrCode)

            }else if(requestCode==Constants.REQUEST_CODE_OUT_SELF){
                HomeFragment.getInstance().attOutSelf(qrCode)


            }else if(requestCode==Constants.REQUEST_CODE_IN_OUT_OTHERS){
                HomeFragment.getInstance().attInOutOthers(qrCode,empCode)

            }


        }
    }


}