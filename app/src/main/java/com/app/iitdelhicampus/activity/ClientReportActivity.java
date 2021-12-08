package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ImageAdapter;
import com.app.iitdelhicampus.adapter.SecurityEquipmentAdapter;
import com.app.iitdelhicampus.adapter.SecurityHazardAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.model.ClientReportModel;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;


/**
 * Created by pawan on 25/10/2020.
 */

public class ClientReportActivity extends BaseActivity {
    private final int MY_CAMERA_REQUEST_CODE = 101;
    private final ArrayList<MediaDetails> listImage = new ArrayList<>();
    private ProgressBar progressBar;
    private CustomEditText etClientName;
    private CustomEditText etRemark;
    private ClientReportModel siteVisitModel = new ClientReportModel();
    private CustomTextViewBold tvSubmit;
    private boolean isEditable;
    private String clientLocation;
    private String clientCode;
    private SecurityHazardAdapter securityHazardAdapter;
    private SecurityEquipmentAdapter securityEquipmentAdapter;
    private ArrayList<String> jsonArrayImage = new ArrayList<>();
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private CustomEditText etDesignation;
    private CustomTextView tvLocationCapture;
    private LocationManager manager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean mTrackingLocation;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private String address;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_report);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSubmit = (CustomTextViewBold) findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        tvLocationCapture=(CustomTextView)findViewById(R.id.tvLocationCapture);

        CreateEventModel.getInstance(false).setQRDescription("");

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);


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
//                    new FetchAddressTask(QRTaggingActivity.this, QRTaggingActivity.this)
//                            .execute(locationResult.getLastLocation());
                    }
                    stopTrackingLocation();
                }
            }
        };
        startTrackingLocation();

        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerViewImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImage.setLayoutManager(mLayoutManager);
        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.smoothScrollToPosition(0);

        ImageView ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);


        etDesignation = (CustomEditText) findViewById(R.id.etDesignation);
        etDesignation.setOnClickListener(this);

        etClientName = (CustomEditText) findViewById(R.id.etClientName);

        etRemark = (CustomEditText) findViewById(R.id.etRemark);
//        setData();
    }


    public void onClickPopupMenu(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.designation_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                etDesignation.setText(item.getTitle());
                return true;
            }
        });

        popup.show();//showing popup menu
    }
    private void setDataToModel() {
        siteVisitModel.setCreatedDate(Utility.getReportDate(System.currentTimeMillis()));
        siteVisitModel.setVisitDate(Utility.getFormattedDateAndTime(System.currentTimeMillis()));
        siteVisitModel.setLastVisitDate(Utility.getDateTransaction(System.currentTimeMillis() / 1000));
        siteVisitModel.setMobile(preferences.getPhone());
        siteVisitModel.setCountryCode(preferences.getCountryCode());


        if (StringUtils.isNullOrEmpty(etClientName.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Client Name.");
            return;
        }

        if (StringUtils.isNullOrEmpty(etDesignation.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Designation.");
            return;
        }
        if (StringUtils.isNullOrEmpty(etRemark.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Remarks.");
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        siteVisitModel.setClientName(etClientName.getText().toString().trim());
        siteVisitModel.setRemark(etRemark.getText().toString().trim());
        siteVisitModel.setCountryCode(preferences.getCountry().countryCode);
        siteVisitModel.setMobile(preferences.getPhone());
        siteVisitModel.setName(preferences.getName());
        siteVisitModel.setEmpCode(preferences.getEmpCode());
        siteVisitModel.setUserId(preferences.getUserId());
        siteVisitModel.setClientLocation(address);
        siteVisitModel.setMetWhom(etDesignation.getText().toString().trim());

//        saveDataToFirebase();
        getDownloadUrl();
    }

    private void saveDataToFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("client_report")
                .add(siteVisitModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String taskId = documentReference.getId();
                        progressBar.setVisibility(View.GONE);
                        showAlertDialogWithFinish(ClientReportActivity.this, "", "Data Submitted Successfully.");
//                        ToastUtils.showToast(SiteObservationActivity.this, "Data Submitted Successfully.");
                        finish();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvSubmit:
                setDataToModel();

                break;

            case R.id.etDesignation:
                onClickPopupMenu(view);
                break;
            case R.id.etDeploymentLocation:
                Intent intent_location = new Intent(this, LocationFinderActivity.class);
                startActivityForResult(intent_location, Constants.Location.LOCATION);
                break;
            case R.id.llQRView:
            case R.id.ivScanQRCode:
                Intent intent = new Intent(this, ScanQRCodeActivity.class);
                startActivity(intent);
                break;

            case R.id.etClientName:
                intent = new Intent(ClientReportActivity.this, ClientsListActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.ivCamera:
                checkPermission();
                break;


        }
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


    private void getDownloadUrl() {
        File file = null;
        if (listImage.size() > 0) {
            file = new File(listImage.get(0).getImageUrl());
        } else {
            saveDataToFirebase();
            return;
        }
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
                    jsonArrayImage.add(downloadUri.toString());
                    siteVisitModel.setImages(jsonArrayImage);
                    if (listImage.size() > 0) {
                        listImage.remove(0);
                    }
                    getDownloadUrl();


                } else {
                    saveDataToFirebase();
                    // Handle failures
                    // ...
                }
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_LOCATION_PERMISSION:

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
                break;


        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        String qrResponse = CreateEventModel.getInstance(false).getQRDescription();
//        if (!StringUtils.isNullOrEmpty(qrResponse)) {
//            try {
//                JSONObject jsonObject = new JSONObject(qrResponse);
//                String id = jsonObject.optString("id");
//                String address = jsonObject.optString("address");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.Location.LOCATION:
//                String location = data.getStringExtra(Constants.Params.NAME_LOCATION);
                CreateEventModel createEventModel = CreateEventModel.getInstance(false);
//                if (!StringUtils.isNullOrEmpty(createEventModel.getLocation()))
//                    etDeploymentLocation.setText(createEventModel.getLocation());

                break;

            case Constants.REQUEST_CODE:
                if (resultCode != RESULT_OK) return;
                ClientListModel.Data listSelected = data.getParcelableExtra(Constants.EXTRA_DATA);
                etClientName.setText(listSelected.getUnit_name());
                clientLocation = listSelected.getLocation();
//                etLocation.setText(listSelected.getLocation());
//                etLocation.setVisibility(View.VISIBLE);
//                etLocation.setClickable(false);
//                frameClientName.setVisibility(View.VISIBLE);
                clientCode = listSelected.getUnit_code();
//                etSiteCode.setText(clientCode);

                break;
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis() + ".png");
                            if (data == null) return;
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            File imageUrl = FileUtils.saveToInternalStorage(imageBitmap, fileName);


                            MediaDetails eventMediaModel = new MediaDetails();
                            eventMediaModel.setImageUrl(imageUrl + "");
                            eventMediaModel.setVideo(false);
                            eventMediaModel.setType(Constants.MediaType.PIC);
                            eventMediaModel.setSelected(true);
                            listImage.add(eventMediaModel);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageAdapter.updateListData(listImage);
                                    getCurrentLocation(mCurrentLatitude,mCurrentLongitude);
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


    /**
     * Stops tracking the device. Removes the location
     * updates, stops the animation, and resets the UI.
     */
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
    protected void onPause() {
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
        super.onPause();
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

                            status.startResolutionForResult(ClientReportActivity.this, REQUEST_CHECK_SETTINGS);
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

                 address = address_short + " " + city;
                String address_short_new = knownName + " " + city;
                String city_new = state + " " + country + " " + postalCode;

                tvLocationCapture.setText(Utility.getFormattedDateAndTime(System.currentTimeMillis())+",\n"+address);
//                etAddress.setText(address);
//
//
//                etAddressLine1.setText(city);
//                etAddressLine2.setText(state);
//                etAddressLine3.setText(country + "-" + postalCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


