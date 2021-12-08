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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.fragment.HomeFragment;
import com.app.iitdelhicampus.fragment.NotificationFragment;
import com.app.iitdelhicampus.fragment.ProfileFragment;
import com.app.iitdelhicampus.model.MetaDataDetailModel;
import com.app.iitdelhicampus.model.MetaDataResponseModel;
import com.app.iitdelhicampus.model.VisitTimingModel;
import com.app.iitdelhicampus.model.WeeklyOffModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
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

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_VIDEO;


public class DashBoardActivity extends BaseActivity implements View.OnClickListener, UpdateMediaCallback,OnUpdateResponse {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Handler handler = new Handler();
    String notificationFragment;
    Gson gson;
    AppPreferences preferences;

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


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dash_board);
        gson = new Gson();
        updateCallback = this;
        ivReportIncident=(ImageView)findViewById(R.id.ivReportIncident);
        ivReportIncident.setOnClickListener(this);

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
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DashBoardActivity.this, new OnSuccessListener<InstanceIdResult>() {
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

    }


    public void homeView() {
        fragment = new HomeFragment();
        loadFragment(fragment, getString(R.string.Home));

        tvHome.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tvProfile.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvNotifications.setTextColor(ContextCompat.getColor(this, R.color.grey));

        HomeIcon.setImageResource(R.mipmap.home_active);
        ProfileIcon.setImageResource(R.mipmap.profile_inactive);
        NotificationsIcon.setImageResource(R.mipmap.notification_inactive);


//        if (fragment instanceof HomeTabFragment && ((HomeTabFragment) fragment).view != null) {
//            TextView tvNotification = (TextView) ((HomeTabFragment) fragment).view.findViewById(R.id.txtNotificationCount);
//            if (tvNotification == null) return;
//            tvNotification.setText(String.valueOf(preferences.getNotificationCount()));
//            tvNotification.setVisibility(preferences.getNotificationCount() > 0 ? View.VISIBLE : View.GONE);
//        } else {
//            txtNotificationCount.setVisibility(View.GONE);
//            txtNotificationCount.setText(preferences.getNotificationCount() + "");
//        }


    }

    public void notificationView() {
        fragment = new NotificationFragment();
        loadFragment(fragment, getString(R.string.Home));

        tvHome.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvProfile.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvNotifications.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

        HomeIcon.setImageResource(R.mipmap.home_inactive);
        ProfileIcon.setImageResource(R.mipmap.profile_inactive);
        NotificationsIcon.setImageResource(R.mipmap.notification_active);

    }


    public void profileView() {
        fragment = new ProfileFragment();
        loadFragment(fragment, getString(R.string.Home));

        tvHome.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvNotifications.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvProfile.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));


        HomeIcon.setImageResource(R.mipmap.home_inactive);
        NotificationsIcon.setImageResource(R.mipmap.notification_inactive);
        ProfileIcon.setImageResource(R.mipmap.profile_active);


    }


    @Override
    public void onBackPressed() {

    }


    @Override
    public void onClick(View v) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "No Internet Connection!!");
        }
        switch (v.getId()) {

            case R.id.ivReportIncident:
                Intent intent=new Intent(DashBoardActivity.this, ReportIncidentActivity.class);
                startActivity(intent);
                break;

            case R.id.llhome:
            case R.id.HomeIcon:
                try {
                    homeView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.llProfileView:
                try {
                    profileView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.llNotificationView:
                try {
                    notificationView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        switch (requestCode) {
            case 11111:

                if (updateCallback != null) {
                    updateCallback.updateMediaDetails(requestCode, resultCode, data, tempFile);
                }
                break;
//
            case Constants.Location.LOCATION:
                ProfileFragment.getInstance1().setAddress(data.getStringExtra(Constants.Params.NAME_LOCATION));
                break;

            case 1201:
                ProfileFragment.getInstance1().setGender(data.getStringExtra(AppConstants.EXTRA_MULTIPLE_IMAGES));

                break;

            case 1202:
                ProfileFragment.getInstance1().setWeekOff(data.<WeeklyOffModel>getParcelableArrayListExtra("list"));

                break;

            case 1203:
                ProfileFragment.getInstance1().setVisitTiming(data.<VisitTimingModel>getParcelableArrayListExtra("list"));

                break;

            case 1204:
                ProfileFragment.getInstance1().setExperience(data.getStringExtra(AppConstants.EXTRA_MULTIPLE_IMAGES));

                break;

            case 1205:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"),data.getStringExtra("type"));

                break;

            case 1206:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"),data.getStringExtra("type"));

                break;

            case 1207:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"),data.getStringExtra("type"));

                break;

            case 1208:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"),data.getStringExtra("type"));

                break;

            case 1010:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"),data.getStringExtra("type"));

                break;

//            case 8080:
//                HomeFragmentPet.getInstance1().setPetDetails(data.getStringExtra("name"), data.getStringExtra("age"), data.getStringExtra("image"), data.getStringExtra("petid"));
//
//                break;
            case REQUEST_PICK_IMAGE:

                ProfileFragment.getInstance1().setImage(tempFile);
//
                if (updateCallback != null) {
                    updateCallback.updateMediaDetails(requestCode, resultCode, data, tempFile);
                }
                break;
//
            case Constants.REQUEST_MULTIPLE_IMAGE_CHOOSER:
            case Constants.REQUEST_IMAGE_CAMERA:
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                if (updateCallback != null) {
                    updateCallback.updateMediaDetails(requestCode, resultCode, data, tempFile);
                }
                ProfileFragment.getInstance1().setImage(tempFile);

                break;
//            case REQUEST_CODE_CROP_VIDEO:
//                onImageSelectionComplete(new File(data.getStringExtra(EXTRA_FILE_PATH)));
//                break;
//
//            case REQUEST_PICK_VIDEO:
//                if (data == null) return;
//                Uri selectedVideo = data.getData();
//                if (isNewGooglePhotosUri(selectedVideo)) {
//                    String pathUri = selectedVideo.getPath();
//                    int length = 0;
//                    if (pathUri.contains("/ORIGINAL")) {
//                        length = pathUri.lastIndexOf("/ORIGINAL");
//                    } else if (pathUri.contains("/ACTUAL")) {
//                        length = pathUri.lastIndexOf("/ACTUAL");
//                    }
//                    String newUri = pathUri.substring(pathUri.indexOf("content"), length);
//                    String newFilePath = getDataColumn(context, Uri.parse(newUri), null, null);
//                    File path = new File(newFilePath);
//                    onImageSelectionComplete(path);
//
////                    startTrimActivity(newFilePath);
//                } else {
//                    File galleryFile = getFileFromGallery(data);
//                    onImageSelectionComplete(galleryFile);
//
////                    startTrimActivity(selectedVideo.getPath());
//                }
//                break;
//            case AppConstants.REQUEST_CAMCORDER:
////                if (data == null) return;
////                Uri selectedVideo = data.getData();
//                if (tempFile == null) return;
//                onImageSelectionComplete(tempFile);
//
////                startTrimActivity(tempFile.toString());
//                break;
            default:
                if (updateCallback != null) {
                    updateCallback.updateMediaDetails(requestCode, resultCode, data, tempFile);
                }
                break;
        }

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
        if (StringUtils.isNullOrEmpty(response)){
            return;
        }
        if (isSuccess) {
            switch (responseCode){
                case Constants.RequestType.GET_META_DATA:
                    MetaDataResponseModel metaDataResponseModel= gson.fromJson(response,MetaDataResponseModel.class);
                    preferences.setMetaDataResponse(response);
                    break;
            }
        }

    }
}
