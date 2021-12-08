package com.app.iitdelhicampus.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.iitdelhicampus.BuildConfig;
import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.fragment.HomeFragment;
import com.app.iitdelhicampus.fragment.NotificationFragment;
import com.app.iitdelhicampus.fragment.ProfileFragment;
import com.app.iitdelhicampus.model.FcmDataModel;
import com.app.iitdelhicampus.model.MetaDataDetailModel;
import com.app.iitdelhicampus.model.VisitTimingModel;
import com.app.iitdelhicampus.model.WeeklyOffModel;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_VIDEO;


public class TLProfileActivity extends BaseActivity implements View.OnClickListener, UpdateMediaCallback, OnUpdateResponse {

    String notificationFragment;
    Gson gson;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Handler handler = new Handler();
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
    private CircleImageView ivProfile;
    private ImageView ivProfileBlurr;
    private CustomTextView tvLoginTime;
    private ImageView ivSiteVisit;
    private ImageView ivEmpList;
    private ImageView ivLeave;
    private ImageView ivAttendance;
    private LinearLayout llEmpList;
    private LinearLayout llReportIncident;
    private LinearLayout llSiteObservation;
    private LinearLayout llAttendance;
    private LinearLayout llLeave;
    private FirebaseAuth mAuth;
    private ImageView ivIncedentReview;
    private LinearLayout llTLView;
    private LinearLayout llPOView;
    private LinearLayout llWholeViewClick;
    private CustomTextViewBold txtHeader;
    private CustomTextView tvUserId;
    private CustomTextView tvMobile;
    private LinearLayout llQRView;


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tl_profile);
        gson = new Gson();
        mAuth = FirebaseAuth.getInstance();
        updateCallback = this;
        llTLView = (LinearLayout) findViewById(R.id.llTLView);
        llPOView = (LinearLayout) findViewById(R.id.llPOView);
        llTLView.setVisibility(View.GONE);
        llPOView.setVisibility(View.GONE);
        llQRView=(LinearLayout)findViewById(R.id.llQRView);
        llQRView.setOnClickListener(this);

//        llQRView.setVisibility(View.GONE);
//        if(preferences.getUserType().equalsIgnoreCase("QR")){
//            llQRView.setVisibility(View.VISIBLE);
//        }

        llWholeViewClick = (LinearLayout) findViewById(R.id.llWholeViewClick);
        llWholeViewClick.setOnClickListener(this);

        txtHeader = (CustomTextViewBold) findViewById(R.id.txtHeader);
        tvUserId = (CustomTextView) findViewById(R.id.tvUserId);
        tvUserId.setText("Emp Code: " + preferences.getEmpCode());


        CustomTextView tvVersion = (CustomTextView) findViewById(R.id.tvVersion);
        tvVersion.setText("Version: " + BuildConfig.VERSION_NAME + "");


        tvMobile = (CustomTextView) findViewById(R.id.tvMobile);
        tvMobile.setText("Mobile: " + preferences.getPhone());


        if (preferences.getUserType().equalsIgnoreCase("TL")) {
            llTLView.setVisibility(View.VISIBLE);
            txtHeader.setText("TL Profile");
        } else {
            llPOView.setVisibility(View.VISIBLE);
            txtHeader.setText("PO Profile");
        }


        ImageView ivReportIncidentPO = (ImageView) findViewById(R.id.ivReportIncidentPO);
        ivReportIncidentPO.setOnClickListener(this);


        ImageView ivDashboard = (ImageView) findViewById(R.id.ivDashboard);
        ivDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TLProfileActivity.this,DashBoardActivityKotlin.class);
                startActivity(intent);
            }
        });
        ImageView ivLogout = (ImageView) findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(this);
        ivReportIncident = (ImageView) findViewById(R.id.ivReportIncident);

        ivReportIncident.setOnClickListener(this);
        ivIncedentReview = (ImageView) findViewById(R.id.ivIncedentReview);
        ivIncedentReview.setOnClickListener(this);

        ivSiteVisit = (ImageView) findViewById(R.id.ivSiteVisit);
        ivSiteVisit.setOnClickListener(this);
        ivEmpList = (ImageView) findViewById(R.id.ivEmpList);
        ivEmpList.setOnClickListener(this);

        ivLeave = (ImageView) findViewById(R.id.ivLeave);
        ivLeave.setOnClickListener(this);

        ivAttendance = (ImageView) findViewById(R.id.ivAttendance);
        ivAttendance.setOnClickListener(this);

        CustomTextViewBold tvName = (CustomTextViewBold) findViewById(R.id.tvName);
        tvName.setText(preferences.getName());


        ivProfile = (CircleImageView) findViewById(R.id.ivProfile);
        ivProfileBlurr = (ImageView) findViewById(R.id.ivProfileBlurr);


        tvLoginTime = (CustomTextView) findViewById(R.id.tvLoginTime);
        tvLoginTime.setText("Login Time: " + Utility.getDateForPayment(System.currentTimeMillis()));
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


        llReportIncident = (LinearLayout) findViewById(R.id.llReportIncident);
        llReportIncident.setOnClickListener(this);

        LinearLayout llReviewIncident = (LinearLayout) findViewById(R.id.llReviewIncident);
        llReviewIncident.setOnClickListener(this);


        llSiteObservation = (LinearLayout) findViewById(R.id.llSiteObservation);
        llSiteObservation.setOnClickListener(this);
        llAttendance = (LinearLayout) findViewById(R.id.llAttendance);
        llAttendance.setOnClickListener(this);
        llLeave = (LinearLayout) findViewById(R.id.llLeave);
        llLeave.setOnClickListener(this);
        llEmpList = (LinearLayout) findViewById(R.id.llEmpList);
        llEmpList.setOnClickListener(this);


        if (StringUtils.isNullOrEmpty(preferences.getDeviceToken())) {
//            showDeviceTokenAlertDialog(this, "", "Device token is not updated, Please refresh token.");
            try {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(TLProfileActivity.this, new OnSuccessListener<InstanceIdResult>() {
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

        llNotificationView.setOnClickListener(this);
        llProfileView.setOnClickListener(this);
        llhome.setOnClickListener(this);
        HomeIcon.setOnClickListener(this);
        hitApiSaveToken();
//        getApiServer();

//        hitMetaData();
        updatePofileView();
    }


    public void updatePofileView() {
        FileUtils.getBlurredImage(this, preferences.getProfileImage(), ivProfileBlurr, R.drawable.profile_header_bg, preferences.getAvatarHash(), false);
        FileUtils.getProfilePic(this, preferences.getProfileImage(), ivProfile, R.mipmap.default_user_image, preferences.getAvatarHash(), true);
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
        showAlertDialogForExitApp(this, "", "Do you want to close the app?");

    }


    @Override
    public void onClick(View v) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "No Internet Connection!!");
        }
        switch (v.getId()) {
            case R.id.ivLogout:
                showAlertDialogForLogoutApp(this, "", "Do you want to Logout?");
                break;

            case R.id.llReviewIncident:
            case R.id.ivIncedentReview:
                showAlertDialog(this,"","This feature is under process.");

//                Intent intent = new Intent(TLProfileActivity.this, QRTaggingActivity.class);
//                startActivity(intent);
                break;

            case R.id.llQRView:
                Intent intent = new Intent(TLProfileActivity.this, QRTaggingActivity.class);
                startActivity(intent);
                break;
            case R.id.llReportIncident:
            case R.id.ivReportIncident:
                intent = new Intent(TLProfileActivity.this, ReviewIncidentActivity.class);
                startActivity(intent);
                break;
            case R.id.llSiteObservation:
            case R.id.ivSiteVisit:
                intent = new Intent(TLProfileActivity.this, SiteObservationListActivity.class);
                startActivity(intent);
                break;
            case R.id.llEmpList:
            case R.id.ivEmpList:
//                intent = new Intent(TLProfileActivity.this, EmployeeListActivity.class);
//                startActivity(intent);
                showAlertDialog(this, "", "This feature is under process.");

                break;
            case R.id.llLeave:
            case R.id.ivLeave:
//                intent = new Intent(TLProfileActivity.this, LeaveListActivity.class);
//                startActivity(intent);
                showAlertDialog(this, "", "This feature is under process.");

                break;
            case R.id.llAttendance:
            case R.id.ivAttendance:
//                intent = new Intent(TLProfileActivity.this, AttendanceListActivity.class);
//                startActivity(intent);
                showAlertDialog(this, "", "This feature is under process.");

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
            case R.id.llWholeViewClick:
            case R.id.ivReportIncidentPO:
                intent = new Intent(this, POReportIncidentActivity.class);
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
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"), data.getStringExtra("type"));

                break;

            case 1206:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"), data.getStringExtra("type"));

                break;

            case 1207:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"), data.getStringExtra("type"));

                break;

            case 1208:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"), data.getStringExtra("type"));

                break;

            case 1010:
                ProfileFragment.getInstance1().setQualification(data.<MetaDataDetailModel>getParcelableArrayListExtra("list"), data.getStringExtra("type"));

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

    private void getApiServer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("api_server").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    FcmDataModel taskModel = document.toObject(FcmDataModel.class);
                                    preferences.setBaseApi(taskModel.getApi());
                                    preferences.setPort(taskModel.getPort());
                                    preferences.setProfileImage(taskModel.getProfileImage());
                                    String appVersion=BuildConfig.VERSION_NAME.replace(".","");
                                    String remoteVersion=taskModel.getAppVersion().replace(".","");
                                    if(Integer.parseInt(remoteVersion)>Integer.parseInt(appVersion)){
                                       showAlertDialogForceUpdate(TLProfileActivity.this,"","A new version "+"(v"+taskModel.getAppVersion()+ ") is available, Please update the app.");
                                    }

                                        //Newer version is avail

                                }
                            }

                        }
                    }
                });

    }
    public void showAlertDialogForceUpdate(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.orionsecure"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
alertDialog.setCancelable(false);
        alertDialog.show();
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


    public void hitApiSaveToken() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestParams requestParams = new RequestParams();
            try {
                requestParams.put(Constants.Params.MOBILE, preferences.getPhone());
                requestParams.put(Constants.Params.TOKEN, preferences.getDeviceToken());


            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethod(Constants.SAVE_TOKEN, requestParams, this, Constants.RequestType.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApiServer();
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (StringUtils.isNullOrEmpty(response)) {
            return;
        }
        if (isSuccess) {
            switch (responseCode) {
                case Constants.RequestType.GET_META_DATA:
//                    MetaDataResponseModel metaDataResponseModel = gson.fromJson(response, MetaDataResponseModel.class);
//                    preferences.setMetaDataResponse(response);
                    break;
            }
        }

    }
}
