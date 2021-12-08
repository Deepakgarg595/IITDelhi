package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.EmployeeModel;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.Datastatic;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.Result;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.app.iitdelhicampus.activity.CheckListActivity.REQUEST_LOCATION_PERMISSION;
import static com.app.iitdelhicampus.activity.CheckListActivity.TRACKING_LOCATION_KEY;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;

public class ScanQRCodeForAttendanceActivity extends BaseActivity implements ZXingScannerView.ResultHandler, OnUpdateResponse, View.OnClickListener {
    private static final int REQUEST_CAMERA = 1;
    private final int MY_CAMERA_REQUEST_CODE = 101;
    CustomTextViewBold tvInOut;
    private ZXingScannerView mScannerView;
    private String QRCode;
    private ImageView imgBack;
    private String cCode;
    private String Phone;
    private TextInputLayout tvInputEmpCode;
    private CustomEditText etEmpCode;
    private CustomTextViewBold tvQRCode;
    private LinearLayout llQRView;
    private boolean isOtherEmp;
    private CustomTextView tvEmpName;
    private CustomTextView tvPunchingDate;
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private LocationManager manager;
    private ImageView ivCamera;
    private CustomTextView tvLocationCapture;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode_attendance);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }


        mLocationCallback = new LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient updates your location.
             * @param locationResult The result containing the device location.
             */
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // If tracking is turned on, reverse geocode into an address
                if (mTrackingLocation) {
                    if (locationResult != null) {
                        mCurrentLatitude = locationResult.getLastLocation().getLatitude();
                        mCurrentLongitude = locationResult.getLastLocation().getLongitude();
                        preferences.setCurrentLatitude(mCurrentLatitude + "");
                        preferences.setCurrentLongitude(mCurrentLongitude + "");

                        removeProgressDialog();
//                    new FetchAddressTask(QRTaggingActivity.this, QRTaggingActivity.this)
//                            .execute(locationResult.getLastLocation());
                    }
//                    stopTrackingLocation();
                }
            }
        };
        startTrackingLocation();
        tvLocationCapture = (CustomTextView) findViewById(R.id.tvLocationCapture);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        mScannerView = (ZXingScannerView) findViewById(R.id.scannerView);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvInputEmpCode = (TextInputLayout) findViewById(R.id.tvInputEmpCode);
        tvInputEmpCode.setVisibility(View.GONE);
        ImageView ivQRCode = (ImageView) findViewById(R.id.ivQRCode);
        ivQRCode.setOnClickListener(this);
        etEmpCode = (CustomEditText) findViewById(R.id.etEmpCode);
        tvQRCode = (CustomTextViewBold) findViewById(R.id.tvQRCode);
        tvQRCode.setOnClickListener(this);
        llQRView = (LinearLayout) findViewById(R.id.llQRView);
        tvInOut = (CustomTextViewBold) findViewById(R.id.tvInOut);
        tvInOut.setOnClickListener(this);
        tvEmpName = (CustomTextView) findViewById(R.id.tvEmpName);
        tvPunchingDate = (CustomTextView) findViewById(R.id.tvPunchingDate);
        tvPunchingDate.setVisibility(View.GONE);
//        tvPunchingDate.setOnClickListener(this);

        tvInOut.setText(preferences.getOtherEmpShift());
        if (preferences.getOtherEmpShift().equalsIgnoreCase("OUT")) {
            tvInOut.setBackgroundResource(R.drawable.rounded_attendance_out);
            tvPunchingDate.setText(getResources().getString(R.string.punching_date));
            tvPunchingDate.setEnabled(true);
//            tvPunchingDate.setVisibility(View.VISIBLE);
        } else {
            tvInOut.setBackgroundResource(R.drawable.rounded_attendance_in);
            tvPunchingDate.setEnabled(false);
            tvPunchingDate.setText(Utility.getReportDate(System.currentTimeMillis()));
            preferences.setPunchingDate(Utility.getReportDate(System.currentTimeMillis()));
            tvPunchingDate.setVisibility(View.GONE);
        }
        RelativeLayout rlOtherView = (RelativeLayout) findViewById(R.id.rlOtherView);
        rlOtherView.setVisibility(View.GONE);
        isOtherEmp = getIntent().getBooleanExtra(Constants.EXTRA_DATA, false);
        if (isOtherEmp) {
            tvInputEmpCode.setVisibility(View.VISIBLE);
            rlOtherView.setVisibility(View.VISIBLE);
            if (preferences.getOtherEmpShift().equalsIgnoreCase("OUT")) {
//                tvPunchingDate.setVisibility(View.VISIBLE);
            }
        } else {
            tvPunchingDate.setVisibility(View.GONE);
        }

        etEmpCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EmployeeModel.EmpList employeeData = Datastatic.getInstance().getEmpDataList(etEmpCode.getText().toString().trim());
                if (employeeData != null) {
                    tvEmpName.setText("Emp Name: " + employeeData.getEmployeeName());
                } else {
                    tvEmpName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            } else {
                dispatchTakePictureIntent();
            }
        } else {
            dispatchTakePictureIntent();
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }


    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
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
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    /**
     * Saves the last location on configuration change
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        CreateEventModel.getInstance(false).setQRDescription(QRCode);
//        if(mScannerView!=null){
//            mScannerView.stopCamera();
//            mScannerView.setVisibility(View.VISIBLE);
//            return;
//        }
        finish();
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openScanner();
            return true;
        } else if (ScanQRCodeForAttendanceActivity.this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openScanner();
            return true;
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        return false;
    }

    void openScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openScanner();
            }
        } else if (requestCode == REQUEST_LOCATION_PERMISSION) {

            // If the permission is granted, get the location, otherwise,
            // show a Toast
            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                startTrackingLocation();
            } else {
                Toast.makeText(this,
                        "Location permissions denied,\n" +
                                "Please enable the permissions to use this app.",
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onPause() {
        mScannerView.stopCamera();
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTrackingLocation) {
            startTrackingLocation();
        }


        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            displayLocationSettingsRequest();
        }
    }

    public void displayLocationSettingsRequest() {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            private static final int REQUEST_CHECK_SETTINGS = 0x1;

            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // Log.i(TAG, "All location settings are satisfied.");
//                        openMapView();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //   Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().

                            status.startResolutionForResult(ScanQRCodeForAttendanceActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //  Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);

        }
    }


    @Override
    public void handleResult(Result rawResult) {
        try {
            QRCode = rawResult.getText();
            mScannerView.resumeCameraPreview(this);
            mScannerView.stopCamera();
            CreateEventModel.getInstance(false).setQRDescription(QRCode);
            ToastUtils.showToast(this, "QR Code Scanned Successfully.");
            if (ConnectivityUtils.isNetworkEnabled(this)) {
//                hitLoginWithQRCode();
            }
            handleIntentData(QRCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void handleIntentData(String warehouseCode) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_EMP_CODE, etEmpCode.getText().toString().trim());
        intent.putExtra(Constants.EXTRA_DATA, warehouseCode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        removeProgressDialog();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivCamera:
                if(!checkRadius()) return;
                checkPermission();
                break;

            case R.id.tvQRCode:
                if(!checkRadius()) return;
                if(StringUtils.isNullOrEmpty(imageUrl)){
                    showAlertDialogCustom(true,"Please take self Image.",R.color.red,0,"red");
                    return ;
                }
                handleIntentData(preferences.getWarehouseCode());//direct submit data
                break;
            case R.id.ivQRCode:
                hideSoftKey();
                hideSoftKeyBoard();
                hideSoftKeypad(etEmpCode);

                if(!checkRadius()) return;
                if(StringUtils.isNullOrEmpty(imageUrl)){
                    showAlertDialogCustom(true,"Please take self Image.",R.color.red,0,"red");
                    return ;
                }




                if (isOtherEmp) {
                    if (StringUtils.isNullOrEmptyOrZero(etEmpCode.getText().toString().trim())) {
//                        ToastUtils.showToast(this, "Please enter Employee code.");
                        showAlertDialogCustom(true,"Please enter Employee code.",R.color.red,R.drawable.cross_red,"red");

//                        showAlertDialog(this, "", "Please enter Employee code.");

                        return;
                    }
//                    if (Datastatic.getInstance().getEmpData(etEmpCode.getText().toString().trim()) == null) {
//                        showAlertDialog(this, "", "Please enter valid Employee code.");
////                        ToastUtils.showToast(this, "Please enter valid Employee code.");
//                        return;
//                    }
//                    if (tvPunchingDate.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.punching_date))) {
////                        ToastUtils.showToast(this, "Please select punching date.");
//                        showAlertDialog(this, "Alert!", "Please select the punching date to mark out the attendance.");
//                        return;
//                    }

                }

                llQRView.setVisibility(View.GONE);
//                mScannerView.setVisibility(View.VISIBLE);
                mayRequestCamera();
                break;
            case R.id.tvInOut:
                if (tvInOut.getText().toString().trim().equalsIgnoreCase("in")) {
                    tvInOut.setText("OUT");
                    preferences.setOtherEmpShift("OUT");
                    tvInOut.setBackgroundResource(R.drawable.rounded_attendance_out);
                    tvPunchingDate.setText(getResources().getString(R.string.punching_date));
                    tvPunchingDate.setEnabled(true);
//                    tvPunchingDate.setVisibility(View.VISIBLE);
                } else {
                    tvInOut.setText("IN");
                    preferences.setOtherEmpShift("IN");
                    tvInOut.setBackgroundResource(R.drawable.rounded_attendance_in);
                    tvPunchingDate.setEnabled(false);
                    tvPunchingDate.setText(Utility.getReportDate(System.currentTimeMillis()));
                    preferences.setPunchingDate(Utility.getReportDate(System.currentTimeMillis()));
                    tvPunchingDate.setVisibility(View.GONE);
                }
                break;
            case R.id.tvPunchingDate:
                datePicker();
                break;
        }
    }

    public void datePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        c.set(year, monthOfYear, dayOfMonth);
                        String date = Utility.getReportDate(c.getTimeInMillis());
                        tvPunchingDate.setText(date);
                        preferences.setPunchingDate(date);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private boolean checkRadius(){
        boolean status=false;
//        if (Datastatic.getInstance().distance(preferences.getCurrentLatitude(), preferences.getCurrentLongitude()) >= preferences.getRadiusInMeter()) {
//            showAlertOutOfRage("Your attendance can not be marked..!\nYou are "+Datastatic.getInstance().distance(preferences.getCurrentLatitude(), preferences.getCurrentLongitude()) + " meter away from work location\nWork Location:(" + preferences.getStaticLatitude() + ", " + preferences.getStaticLongitude() + ")\nYour Location:(" + preferences.getCurrentLatitude() + ", " + preferences.getCurrentLongitude()+")");
//            return false;
//        }
        return true;
    }

    public void showAlertDialogRefresh(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startTrackingLocation();
                        showProgressDialog();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                try {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis() + ".png");
                            if (data == null) return;
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            final File imagePath = FileUtils.saveToInternalStorage(imageBitmap, fileName);


//                            MediaDetails eventMediaModel = new MediaDetails();
//                            eventMediaModel.setImageUrl(imageUrl + "");
//                            eventMediaModel.setVideo(false);
//                            eventMediaModel.setType(Constants.MediaType.PIC);
//                            eventMediaModel.setSelected(true);
//                            listData.clear();
//                            listImage.add(eventMediaModel);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    imageAdapter.updateListData(listImage);
                                    FileUtils.getProfilePic(context, imagePath.toString(), ivCamera, R.drawable.place_holder_default, "1111", false);
                                    getDownloadUrl(imagePath);
                                    getCurrentLocation(mCurrentLatitude, mCurrentLongitude);
                                }
                            });

                        }
                    }).start();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public void getCurrentLocation(double lat, double lon) {

        try {

//            String lattitude = String.valueOf(lat);
//            String longitude = String.valueOf(lon);

            Geocoder geocoder;
            List<Address> addresses = null;


            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lon, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (!addresses.isEmpty()) {
                String address_short = addresses.get(0).getAddressLine(0);

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                String address = address_short + " " + city;
                String address_short_new = knownName + " " + city;
                String city_new = state + " " + country + " " + postalCode;
                tvLocationCapture.setText(Utility.getFormattedDateAndTime(System.currentTimeMillis()) + ",\n" + address);
//                etAddress.setText(address);
//
//                etAddressLine1.setText(city);
//                etAddressLine2.setText(state);
//                etAddressLine3.setText(country + "-" + postalCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDownloadUrl(File file) {
        showProgressDialog();
//        File file=null;
//        if(listImage.size()>0){
//            file=new File(listImage.get(0).getImageUrl());
//        }else {
//            hitApiSaveQRTaggingDetail();
//            return;
//        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("images/" + file.getName());
        UploadTask uploadTask = ref.putFile(Uri.fromFile(file));

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();
                    preferences.setEmpProfileURL(imageUrl);
                    removeProgressDialog();
//                    CreateEventModel.getInstance(false).setPaymentUrl(imageUrl);
//                    if(listImage.size()>0){
//                        listImage.remove(0);
//                    }
//                    getDownloadUrl();


                } else {
//                    CreateEventModel.getInstance(false).setPaymentUrl(imageUrl);
                    // Handle failures
                    // ...
                }
            }
        });
    }


    public void showAlertOutOfRage(String message) {


        final Dialog dialog = new  Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_out_of_range);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        final CustomTextView tvContent = (CustomTextView) dialog.findViewById(R.id.tvContent);
        tvContent.setText(message);
        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startTrackingLocation();
                showProgressDialog();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


}
