package com.app.iitdelhicampus.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.LoginWithNumberActivity2;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.BitmapUtils;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.DocumentUtils;
import com.app.iitdelhicampus.utility.StringUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import static android.R.attr.targetSdkVersion;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_SYNC_CONTACT_AFTER_PERMISSION;


public class BaseActivity extends AppCompatActivity implements OnClickListener {
    public static final long SIXTEEN_MB = 5 * 1024 * 1024 * 100;
    private static final int REQUEST_CODE = 10;
    public static Context context;
    private static int runningActivities = 0;
    public final String TAG = getClass().getSimpleName();
    public boolean shouldCrop = true;
    public File tempFile;
    protected FrameLayout frameLayout;
    protected Toolbar toolbar;
    public GoogleMap map;
    public Gson gson = new Gson();
    protected AppPreferences preferences;
    private Dialog dialog;
    private SparseArray mErrorString;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = findViewById(R.id.mainContent);
        preferences = AppPreferences.getInstance(this);
        mErrorString = new SparseArray();

    }

    public boolean isMarshMallowOrAvobe() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && targetSdkVersion >= Build.VERSION_CODES.M;
    }


    public void requestAppForReminder(Context mContext, String[] requestPermissions, final int requestCode) {
        requestAppPermissions(requestPermissions, 0, requestCode, mContext);
    }

    public void requestAppPermissions(final String[] requestPermissions, final int stringId, final int requestCode, Context mContext) {

        requestAppPermissions(requestPermissions, stringId, requestCode, null, mContext);
    }

    public void requestAppPermissions(final String[] requestPermissions, final int stringId, final int requestCode, View rootView, Context mContext) {
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        boolean showRequestPermission = false;
        for (String permission : requestPermissions) {
            if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION) {
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (permissionCheck != ContextCompat.checkSelfPermission((Activity) this, permission))
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, permission);
                    permissionCheck = permissionCheck + ContextCompat.checkSelfPermission((Activity) this, permission);
                }

            }else {

                if (permissionCheck != ContextCompat.checkSelfPermission((Activity) this, permission))
                    ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, permission);
                permissionCheck = permissionCheck + ContextCompat.checkSelfPermission((Activity) this, permission);
            }
//            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, requestPermissions, requestCode);
//            if (rootView != null) {
//                Snackbar.make(rootView, R.string.required_permissions, Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        intent.setData(Uri.parse("package:" + getPackageName()));
//                        intent.addCategory(Intent.CATEGORY_DEFAULT);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                        startActivity(intent);
//                    }
//                }).show();
//            } else {
//                ActivityCompat.requestPermissions(this, requestPermissions, requestCode);
//            }

        } else {
            this.context = mContext;
            onPermissionGranted(requestCode);
        }
    }

    public void onPermissionGranted(int requestCode) {
//        ChatWindowActivity myActivity = null;
//        IncomingCallActivity incomingCallActivity = null;
//        if (context instanceof ChatWindowActivity) {
//            myActivity = (ChatWindowActivity) context;
//        } else if (context instanceof IncomingCallActivity) {
//            incomingCallActivity = (IncomingCallActivity) context;
//        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_CAMERA:
                startCamera();
                break;
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                startCameraForEvent();
                break;
            case REQUEST_PICK_IMAGE:
                pickFromGallery();
                break;
//            case REQUEST_PICK_VIDEO:
//                ChatWindowUtils chatWindowUtils = new ChatWindowUtils();
//                chatWindowUtils.pickFromGallery(true);
//                break;
            case REQUEST_SYNC_CONTACT_AFTER_PERMISSION:

//                startService(new Intent(this, SyncContactService.class));

                break;
//            case AppConstants.REQUEST_CODE_LOCATION:
//
//                Intent intent = new Intent(this, MapSendLocationActivity.class);
//                startActivityForResult(intent, AppConstants.REQUEST_CODE_LOCATION);
//                break;
//            case AppConstants.REQUEST_PICK_CONTACT:
//                Intent intentContact = new Intent(this, ContactDisplayActivity.class);
//                intentContact.putExtra(AppConstants.EXTRA_IS_CONTACT_VIEW_MODE, false);
//                startActivityForResult(intentContact, AppConstants.REQUEST_PICK_CONTACT);
//                break;
//            case AppConstants.REQUEST_CODE_DOODLE:
//                Intent intentDoodle = new Intent(this, DoodleActivity.class);
//                startActivityForResult(intentDoodle, AppConstants.REQUEST_CODE_DOODLE);
//                break;
            case AppConstants.REQUEST_PICK_FILE:
                Intent intentFiles = new Intent(Intent.ACTION_GET_CONTENT);
                intentFiles.setType("application/pdf/");
                startActivityForResult(intentFiles, AppConstants.REQUEST_PICK_FILE);
                break;
//            case AppConstants.REQUEST_OPEN_CAMERA:
//                if (myActivity != null) {
//                    myActivity.chatWindowUtils.startCamera(false);
////                    myActivity.showPhotoOptionsDialog(true);
//                }
//                break;
//            case AppConstants.REQUEST_MULTIPLE_IMAGE_CHOOSER:
//                Intent additionalImageIntent = new Intent(this, ChatAdditionalImageActivity.class);
//                additionalImageIntent.putExtra(AppConstants.EXTRA_CHAT_IS_SECRET, false);
//                additionalImageIntent.putExtra(AppConstants.EXTRA_DISPLAY_NAME, "WA");
//                startActivityForResult(additionalImageIntent, AppConstants.REQUEST_MULTIPLE_IMAGE_CHOOSER);
//                break;
//            case AppConstants.REQUEST_VOIP_OUTGOING_CALL:
//                if (myActivity != null) {
////                    if (myActivity.getContact().getSelectMode()== SELECT_MODE.GROUP_CALL)
////                    {
////                    myActivity.startIpCall(false);
////                    }
////                    else{
//                    myActivity.actionToggleCallView();
//                }
//                break;
//            case AppConstants.REQUEST_VOIP_INCOMING_CALL:
//                if (incomingCallActivity != null) {
//                    incomingCallActivity.accept();
//                }
//                break;
//            case AppConstants.REQUEST_RECORD_AUDIO:
//                ChatWindowActivity mContext = (ChatWindowActivity) context;
//                mContext.CiaomRecording = 1;
//                mContext.startRecording(true);
//                mContext.initAudio();
//
//
//                if (myActivity != null) {
//                    myActivity.CiaomRecording = 1;
//                }
//                break;
////            case AppConstants.REQUEST_CODE_SCAN_CAMERA:
////                Intent web = new Intent(context, QRCodeScanner.class);
////                startActivity(web);
////                break;
//            case AppConstants.REQUEST_CODE_VIDEO:
////                startVideoRecord();
//                takeVideo();
//            case REQUEST_CODE_LOCAL_REMINDER:
////                setLocalReminder(eventDetails);
//                break;
//            case REQUEST_CAMCORDER:
////                recordVideo();
//                takeVideo();
//                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionGranted(requestCode);
        }
//        else if (requestCode == REQUEST_SYNC_CONTACT_AFTER_PERMISSION) {
//            preferences.setContactSyncState(SyncContactService.CONTACT_SYNC_STATE.NONE);
//            BaseApplication.sendBroadCast(AppConstants.LOCAL_BROADCAST_CONTACT_SYNC_COMPLETE);
//
////            ToastUtils.showToast(BaseActivity.this, getResources().getString(R.string.required_permissions));
//
////            Snackbar.make(vie, mErrorString.get(requestCode).toString(), Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent();
////                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                    intent.setData(Uri.parse("package:" + getPackageName()));
////                    intent.addCategory(Intent.CATEGORY_DEFAULT);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
////                    startActivity(intent);
////                }
////            }).show();
//        }

    }


    public void showAlertDialogWithFinish(final Context context, String title, final String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!message.toLowerCase().contains("already"))
                            ((Activity) context).finish();
                    }
                });

        alertDialog.show();
    }


    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    public void showAlertDialogCustom(boolean imageVisible, String message, int color, int image, String colorName) {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mark_in_out_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        LinearLayout llPopupBG = (LinearLayout) dialog.findViewById(R.id.llPopupBG);
        ImageView ivTick = (ImageView) dialog.findViewById(R.id.ivTick);

        if (colorName.equalsIgnoreCase("green")) {
            llPopupBG.setBackgroundResource(R.drawable.drawable_mark_inout);
            ivTick.setImageResource(R.drawable.tick_green);
        } else {
            llPopupBG.setBackgroundResource(R.drawable.drawable_mark_inout_red);
            ivTick.setImageResource(R.drawable.cross_red);
        }


        if (imageVisible) {
            ivTick.setVisibility(View.VISIBLE);
        } else {
            ivTick.setVisibility(View.GONE);
        }
        final CustomTextView tvContent = (CustomTextView) dialog.findViewById(R.id.tvContent);
        tvContent.setText(message);
        tvContent.setTextColor(ContextCompat.getColor(this, color));
        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setTextColor(ContextCompat.getColor(this, color));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();


    }

    public void showAlertDialogAutoDismiss(boolean imageVisible, String message, int color, int image, String colorName) {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mark_in_out_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        LinearLayout llPopupBG = (LinearLayout) dialog.findViewById(R.id.llPopupBG);
        ImageView ivTick = (ImageView) dialog.findViewById(R.id.ivTick);

        if (colorName.equalsIgnoreCase("green")) {
            llPopupBG.setBackgroundResource(R.drawable.drawable_mark_inout);
            ivTick.setImageResource(R.drawable.tick_green);
        } else {
            llPopupBG.setBackgroundResource(R.drawable.drawable_mark_inout_red);
            ivTick.setImageResource(R.drawable.cross_red);
        }


        if (imageVisible) {
            ivTick.setVisibility(View.VISIBLE);
        } else {
            ivTick.setVisibility(View.GONE);
        }
        final CustomTextView tvContent = (CustomTextView) dialog.findViewById(R.id.tvContent);
        tvContent.setText(message);
        tvContent.setTextColor(ContextCompat.getColor(this, color));
        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setTextColor(ContextCompat.getColor(this, color));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                dialog.dismiss();
            }
        }.start();


    }


    public void showAlertDialogForUpdateLatLong(Context context, String title, String message, double lat, double lon) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        preferences.setStaticLatitude(lat + "");
                        preferences.setStaticLongitude(lon + "");
                    }
                });

        alertDialog.show();
    }

    public void showAlertDialogForNotRegistered(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                });

        alertDialog.show();
    }


    public void showAlertDialogForExitApp(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    public void showAlertForTimeZone(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });

        alertDialog.show();
    }


    public void showAlertDialogForLogoutApp(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void logout() {
        Intent intent = new Intent(BaseActivity.this, LoginWithNumberActivity2.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        preferences.setLoggedIn(false);
        FirebaseAuth.getInstance().signOut();
        finish();
    }

//    public void showProgressDialog() {
//        if (isFinishing()) {
//            return;
//        }
//
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mProgressDialog.setCancelable(false);
//        }
//
////        String load = getString(R.string.load);
////        mProgressDialog.setMessage(load);
//
//        if (!mProgressDialog.isShowing()) mProgressDialog.show();
//    }

//    public void removeProgressDialog() {
//        if (dialog == null || !dialog.isShowing()) return;
//        dialog.hide();
//        dialog.dismiss();
//        dialog = null;
//    }

    public void removeProgressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) return;
        try {
            if (!isFinishing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showProgressDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
            }

            progressDialog.setMessage("Please wait..");
            progressDialog.show();
            progressDialog.setCancelable(false);

            progressDialog.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        progressDialog.setCancelable(true);
                        progressDialog.dismiss();
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void showProgressDialog() {
//        if (dialog == null) {
//            dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
////            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
////            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setContentView(R.layout.dialog_custom_progress);
//            dialog.setCancelable(false);
//            dialog.findViewById(R.id.ivProgressBar).startAnimation(AnimationUtils.loadAnimation(BaseActivity.this, R.anim.rotate_image));
//            dialog.setOnKeyListener(new Dialog.OnKeyListener() {
//
//                @Override
//                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        dialog.dismiss();
//                    }
//                    return true;
//                }
//            });
//        }
//        if (!(dialog.isShowing()) && !isFinishing())
//            dialog.show();
//
//    }


    public void startCameraForEvent() {
        String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis());
        tempFile = FileUtils.getFile(FileUtils.DIR_IMAGE, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUri(tempFile, context));
        startActivityForResult(intent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUri(tempFile, context));
        startActivityForResult(intent, AppConstants.REQUEST_CODE_CAMERA);
    }

    private void pickFromGallery() {
        String fileType = "image/*";
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        photoPickerIntent.setType(fileType);
        startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);

    }


    public void hideSoftKeypad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public void hideSoftKey() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {

            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void hideSoftKeyBoard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void showSoftKeyBoard() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(0, 0);
    }

    public void openKeyBoard(View view) {
        final InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    protected void onResume() {
        super.onResume();
//        currentDate=Utility.getReportDate(System.currentTimeMillis());
        try {
            if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 1) {
                // Enabled
            } else {
//                showAlertForTimeZone(this,"","Your automatic time zone is disabled, Please enable it to get exact date and time.");

                // Disabed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();


    }

    protected void onStart() {
        super.onStart();
//        context = this;
//        if (runningActivities == 0) {
//            XMPPManager.setAvailability(true);
//        }
//        runningActivities++;
//        if (context != null) {
//            if (!(context instanceof SplashActivity || context instanceof UpdateActivity) &&
//                    !StringUtils.isNullOrEmpty(preferences.getLatestAppVersion())
//                    && !preferences.getLatestAppVersion().equalsIgnoreCase(StringUtils.getCurrentAppVersion(context))) {
//                Intent intent = new Intent(this, UpdateActivity.class);
//                intent.putExtra(AppConstants.EXTRA_UPDATE_PTYPE, "3");
//                startActivity(intent);
//                finish();
//            }
//        }
    }

    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
    }


    public File getFileFromGallery(Intent data) {
        try {
            String path = DocumentUtils.getPath(this, data.getData());
            if (path == null) return null;
            return new File(new URI("file://" + path.replace(" ", "%20")));
        } catch (Exception e) {
            Log.e(TAG, "getFilePathFromGallery" + e);
        }

        return null;
    }

    protected void onImageSelected(File file) {
        if (file == null) return;
        if (shouldCrop) {
            startCroping(file);
        } else {
            try {
                Bitmap bitmap = BitmapUtils.getOrientatedScaledBitmap(file, this, true);
                if (bitmap == null) return;
                BitmapUtils.writeBitmapToFile(file, bitmap);
            } catch (IOException e) {
                Toast.makeText(this, getString(R.string.image_not_supported), Toast.LENGTH_LONG).show();
                return;
            }
            onImageSelectionComplete(file);// added extra
        }

//        if (shouldCrop)
//            beginCrop(file);
//        else onImageSelectionComplete(file);
    }


    private void startCroping(File file) {
//        CropImage.activity(Uri.fromFile(file))
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setActivityTitle("Crop Image")
//                .setCropShape(CropImageView.CropShape.RECTANGLE)
//                .setCropMenuCropButtonTitle("Done")
//                .setAutoZoomEnabled(true)
//                .setRequestedSize(720, 720)
////                .setCropMenuCropButtonIcon(R.mipmap.app_icon)
//                .start(this);
        onImageSelectionComplete(file);

    }

    public void onImageSelectionComplete(File file) {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, FileUtils.getUri(file, context)));

//        ProfileFragmentPet.getInstance1().setImage(FileUtils.getUri(file, context),file);
//
//                if (updateCallback != null) {
//                    updateCallback.updateMediaDetails(requestCode, resultCode, data, tempFile);
//                }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
//        String qrCode= data!=null?data.getStringExtra(Constants.EXTRA_DATA):"";
//        HomeFragment.getInstance().startTimer(qrCode);
    }
}