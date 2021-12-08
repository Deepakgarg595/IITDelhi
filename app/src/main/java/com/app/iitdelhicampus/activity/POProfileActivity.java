package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.location.LocationTracker;
import com.app.iitdelhicampus.model.MetaDataResponseModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.UpdateMediaCallback;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_VIDEO;


public class POProfileActivity extends BaseActivity implements View.OnClickListener, UpdateMediaCallback, OnUpdateResponse {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Handler handler = new Handler();
    String notificationFragment;
    Gson gson;

    private LinearLayout llNotificationView;
    private TextView tvNotifications;
    private ImageView NotificationsIcon;

    private LinearLayout llhome;
    private TextView tvHome;
    private ImageView HomeIcon;

    private LinearLayout llProfileView;
    private TextView tvProfile;
    private ImageView ProfileIcon;

    private UpdateMediaCallback updateCallback;
    private ImageView ivReportIncident;
    private ImageView ivProfile, ivProfileBlurr;
    private CustomTextView tvLoginTime;
    private ImageView ivSiteVisit;
    private ImageView ivEmpList;
    private ImageView ivLeave;
    private ImageView ivAttendance;
    private LinearLayout llWholeViewClick;


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_profile);
        gson = new Gson();
        updateCallback = this;
        ImageView ivLogout=(ImageView)findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(this);
        llWholeViewClick=(LinearLayout)findViewById(R.id.llWholeViewClick);
        llWholeViewClick.setOnClickListener(this);
        ivReportIncident = (ImageView) findViewById(R.id.ivReportIncident);
        ivReportIncident.setOnClickListener(this);
        ivSiteVisit = (ImageView) findViewById(R.id.ivSiteVisit);
//        ivSiteVisit.setOnClickListener(this);
        ivEmpList=(ImageView)findViewById(R.id.ivEmpList);
        ivEmpList.setOnClickListener(this);

        ivLeave=(ImageView)findViewById(R.id.ivLeave);
        ivLeave.setOnClickListener(this);

        ivAttendance=(ImageView)findViewById(R.id.ivAttendance);
        ivAttendance.setOnClickListener(this);

        CustomTextViewBold tvName = (CustomTextViewBold) findViewById(R.id.tvName);
        tvName.setText(preferences.getName());
try {
    LocationTracker locationTracker = new LocationTracker(this);
    locationTracker.getLocation();
}catch (Exception e){
    e.printStackTrace();
}


        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        ivProfileBlurr = (ImageView) findViewById(R.id.ivProfileBlurr);


        tvLoginTime=(CustomTextView)findViewById(R.id.tvLoginTime);
        tvLoginTime.setText("Login Time: "+Utility.getDateForPayment(System.currentTimeMillis()));
        preferences = AppPreferences.getInstance(this);
        fragmentManager = getSupportFragmentManager();

        llhome = (LinearLayout) findViewById(R.id.llhome);
        llNotificationView = (LinearLayout) findViewById(R.id.llNotificationView);
        llProfileView = (LinearLayout) findViewById(R.id.llProfileView);

        NotificationsIcon = (ImageView) findViewById(R.id.NotificationsIcon);
        HomeIcon = (ImageView) findViewById(R.id.HomeIcon);
        ProfileIcon = (ImageView) findViewById(R.id.ProfileIcon);

        tvHome = (TextView) findViewById(R.id.tvHome);
        tvNotifications = (TextView) findViewById(R.id.tvNotifications);
        tvProfile = (TextView) findViewById(R.id.tvProfile);


        if (StringUtils.isNullOrEmpty(preferences.getDeviceToken())) {
//            showDeviceTokenAlertDialog(this, "", "Device token is not updated, Please refresh token.");
            try {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(POProfileActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("newToken", newToken);
                        preferences.setDeviceToken(null);
                        preferences.setDeviceToken(newToken);
                        AppPreferences.getInstance(BaseApplication.getInstance()).setDeviceToken(newToken);
//                        DataStatic.getInstance().updateFCMToken();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            DataStatic.getInstance().updateFCMToken();
        }

//        notificationFragment = getIntent().getStringExtra(Constants.Params.NOTIFICATION_FRAGMENT);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && targetSdkVersion >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION, REQUEST_PERMISSION, this);
//            }
//        }


//        if (!StringUtils.isNullOrEmpty(notificationFragment) && notificationFragment.equalsIgnoreCase("notificationFragment")) {
//            notificationView();
//        } else if (StringUtils.isNullOrEmpty(notificationFragment)) {
//        homeView();


        llNotificationView.setOnClickListener(this);
        llProfileView.setOnClickListener(this);
        llhome.setOnClickListener(this);
        HomeIcon.setOnClickListener(this);

//        hitMetaData();
        updatePofileView();
    }


    public void updatePofileView() {
        FileUtils.getBlurredImage(this, "https://astrostar.s3-eu-west-1.amazonaws.com/profile/16014fnxg01.png", ivProfileBlurr, R.drawable.profile_header_bg, preferences.getAvatarHash(), false);
        FileUtils.getProfilePic(this, "https://astrostar.s3-eu-west-1.amazonaws.com/profile/16014fnxg01.png", ivProfile, R.mipmap.default_user_image, preferences.getAvatarHash(), true);
    }


    @Override
    public void onBackPressed() {
        showAlertDialogForExitApp(this,"Alert!","Do you want to close the app?");
    }


    @Override
    public void onClick(View v) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "No Internet Connection!!");
        }
        switch (v.getId()) {

            case R.id.ivLogout:
                showAlertDialogForLogoutApp(this,"Alert","Do you want to Logout?");
                break;


            case R.id.llWholeViewClick:
            case R.id.ivReportIncident:
                Intent intent = new Intent(POProfileActivity.this, POReportIncidentActivity.class);
                startActivity(intent);
                break;
//            case R.id.ivSiteVisit:
//                intent = new Intent(POProfileActivity.this, SiteObservationActivity.class);
//                startActivity(intent);
//                break;
            case R.id.ivEmpList:
                intent = new Intent(POProfileActivity.this, EmployeeListActivity.class);
                startActivity(intent);

                break;

            case R.id.ivLeave:
                intent = new Intent(POProfileActivity.this, LeaveListActivity.class);
                startActivity(intent);

                break;
            case R.id.ivAttendance:
                intent = new Intent(POProfileActivity.this, AttendanceListActivity.class);
                startActivity(intent);

                break;

        }
    }

    private void loadFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, title);
        transaction.addToBackStack(title);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

    }

    @Override
    protected void onImageSelected(File file) {
        super.onImageSelected(file);
        if (updateCallback != null) {
            tempFile = file;
            updateCallback.updateMediaDetails(REQUEST_PICK_VIDEO, RESULT_OK, null, file);
        }
    }

    @Override
    public void onImageSelectionComplete(File file) {
        super.onImageSelectionComplete(file);
        if (updateCallback != null) {
            tempFile = file;
            updateCallback.updateMediaDetails(REQUEST_PICK_VIDEO, RESULT_OK, null, file);
        }


    }


    @Override
    public void updateMediaDetails(int requestCode, int resultCode, Intent data, File filePath) {
//        if (preferences.getSelectedTab().equalsIgnoreCase(Constants.SelectedTab.HOME_PARENT))
//            (HomeFragment.getInstance()).updateMediaDetails(requestCode, resultCode, data, tempFile);
//        if (preferences.getSelectedTab().equalsIgnoreCase(Constants.SelectedTab.HOME_CHILD))
//            (SocialFragment.getInstance()).updateMediaDetails(requestCode, resultCode, data, tempFile);
//        if (preferences.getSelectedTab().equalsIgnoreCase(Constants.SelectedTab.HOME_DOCUMENT))
//            (DocumentsFragment.getInstance()).updateMediaDetails(requestCode, resultCode, data, tempFile);
//        if (preferences.getSelectedTab().equalsIgnoreCase(Constants.SelectedTab.PROFILE))
//            (ProfileFragmentPet.getInstance1()).updateMediaDetails(requestCode, resultCode, data, tempFile);

    }


    public void hitMetaData() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.GET_META_DATA);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
                requestParams.put("categoryId", preferences.getCategoryId());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.GET_META_DATA, requestParams, this, Constants.RequestType.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (StringUtils.isNullOrEmpty(response)) {
            return;
        }
        if (isSuccess) {
            switch (responseCode) {
                case Constants.RequestType.GET_META_DATA:
                    MetaDataResponseModel metaDataResponseModel = gson.fromJson(response, MetaDataResponseModel.class);
                    preferences.setMetaDataResponse(response);
                    break;
            }
        }

    }
}
