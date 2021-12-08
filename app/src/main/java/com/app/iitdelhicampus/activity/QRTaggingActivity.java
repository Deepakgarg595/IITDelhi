package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ClientListAdapter;
import com.app.iitdelhicampus.adapter.ImageAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.location.LocationTracker;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.service.FetchAddressTask;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.Constants.RequestType.GETALL_CLIENTLIST;
import static com.app.iitdelhicampus.constants.Constants.RequestType.SAVE_QR_TAG;

public class QRTaggingActivity extends BaseActivity implements OnUpdateResponse, OnMapReadyCallback, FetchAddressTask.OnTaskCompleted {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    public GoogleMap map;
    Animation animation;
    ClientListModel.Data listSelected;
    private String QRCode;
    private ImageView imgBack;
    private String cCode;
    private String Phone;
    private CustomTextViewBold btnSubmit;
    private CustomEditText etClientName;
    private CustomEditText etAddress;
    private ImageView ivScanQRCode;
    private CustomTextView tvQrCodeId;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private boolean isMapActivated;
    private CustomEditText etAddressLine1;
    private CustomEditText etAddressLine2;
    private CustomEditText etAddressLine3;
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private ProgressDialog progressDialog;
    private FrameLayout frameMap;
    private ImageView imgRefreshIcon;
    private LinearLayout llBottomView;
    private ImageView ivBounceIcon;
    private ArrayList<ClientListModel.Data> listData;
    private LinearLayout llQRView;
    private LocationManager manager;
    private ImageView ivCamera;
    private final int MY_CAMERA_REQUEST_CODE = 101;
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerViewImage;
    private final ArrayList<MediaDetails> listImage = new ArrayList<>();
    private String imageUrl;
    private ProgressBar progressBar;
    private CustomTextView tvLocationCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_qrcode);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgRefreshIcon = (ImageView) findViewById(R.id.imgRefreshIcon);
        imgRefreshIcon.setOnClickListener(this);
        llBottomView = (LinearLayout) findViewById(R.id.llBottomView);
        llBottomView.setVisibility(View.VISIBLE);
        ivBounceIcon = (ImageView) findViewById(R.id.ivBounceIcon);
        ivBounceIcon.setVisibility(View.GONE);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        tvLocationCapture=(CustomTextView)findViewById(R.id.tvLocationCapture);


        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerViewImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImage.setLayoutManager(mLayoutManager);

        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.smoothScrollToPosition(0);

        llQRView = (LinearLayout) findViewById(R.id.llQRView);
        llQRView.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.bounce);
        ivBounceIcon.setVisibility(View.GONE);
        listData = new ArrayList<>();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);
        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }
        frameMap = (FrameLayout) findViewById(R.id.frameMap);
        frameMap.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Location Detection");
        progressDialog.setIcon(R.mipmap.location_icon);
        progressDialog.setMessage("Please wait..\nWe are trying to detect accurate location..");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSubmit = (CustomTextViewBold) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        etClientName = (CustomEditText) findViewById(R.id.etClientName);
        etClientName.setOnClickListener(this);
        etAddress = (CustomEditText) findViewById(R.id.etAddress);
        ivScanQRCode = (ImageView) findViewById(R.id.ivScanQRCode);
        ivScanQRCode.setOnClickListener(this);
        tvQrCodeId = (CustomTextView) findViewById(R.id.tvQrCodeId);

        etAddressLine1 = (CustomEditText) findViewById(R.id.etAddressLine1);
        etAddressLine2 = (CustomEditText) findViewById(R.id.etAddressLine2);
        etAddressLine3 = (CustomEditText) findViewById(R.id.etAddressLine3);

        // Initialize the location callbacks.
        mLocationCallback = new LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient updates your location.
             * @param locationResult The result containing the device location.
             */
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // If tracking is turned on, reverse geocode into an address
                progressDialog.dismiss();
                if (mTrackingLocation) {
                    if (locationResult != null) {
                        mCurrentLatitude = locationResult.getLastLocation().getLatitude();
                        mCurrentLongitude = locationResult.getLastLocation().getLongitude();
//                    new FetchAddressTask(QRTaggingActivity.this, QRTaggingActivity.this)
//                            .execute(locationResult.getLastLocation());
                        if (map != null) {
                            map.clear();
                            onMapReady(map);
                        }
                    }
                    stopTrackingLocation();
                }
            }
        };
        startTrackingLocation();

        loadMap();
        getClientList();
    }

    private void getClientList() {
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.GETALL_CLIENTLIST, this, GETALL_CLIENTLIST);

    }

    private void loadMap() {
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map_frame, mapFragment).commit();
        mapFragment.getMapAsync(QRTaggingActivity.this);


    }

    @Override
    public void onBackPressed() {
        CreateEventModel.getInstance(false).setQRDescription(QRCode);
        finish();
    }

    private void getDownloadUrl() {
        showProgressDialog();
        File file=null;
        if(listImage.size()>0){
            file=new File(listImage.get(0).getImageUrl());
        }else {
            hitApiSaveQRTaggingDetail();
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("images/"+file.getName());
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
                    imageUrl=downloadUri.toString();
                    if(listImage.size()>0){
                        listImage.remove(0);
                    }
                    getDownloadUrl();


                } else {
                    hitApiSaveQRTaggingDetail();
                    // Handle failures
                    // ...
                }
            }
        });
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        removeProgressDialog();
        try {
            if (responseCode == SAVE_QR_TAG) {
                UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
                if (otpResponseModel != null) {
//                    showAlertDialogWithFinish(this,"",otpResponseModel.getMsg().replace("allready","already"));
//                    showAlertDialog(this,"",otpResponseModel.getMsg());
//                    ToastUtils.showToast(this, otpResponseModel.getMsg());
//                    finish();
                }else {
                    ToastUtils.showToast(this, "Some error occurred.");

                }

            } else if (responseCode == GETALL_CLIENTLIST) {

                if (!StringUtils.isNullOrEmpty(response)) {
                    ClientListModel clientListModel = gson.fromJson(response, ClientListModel.class);
                    listData.addAll(clientListModel.getData());

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showToast(this, "Some error occurred.");

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
                tvLocationCapture.setText(Utility.getFormattedDateAndTime(System.currentTimeMillis())+",\n"+address);
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mCurrentLatitude != 0) {
            frameMap.setVisibility(View.VISIBLE);
            llBottomView.setVisibility(View.VISIBLE);
//            getCurrentLocation(mCurrentLatitude, mCurrentLongitude);
        } else {
            LocationTracker locationTracker = new LocationTracker(this);
            if (locationTracker.getLocation() != null) {
                mCurrentLatitude = locationTracker.getLocation().getLatitude();
                mCurrentLongitude = locationTracker.getLocation().getLongitude();
            }
        }
        this.map = googleMap;
//        map.setOnMapClickListener(this);
        map.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(mCurrentLatitude, mCurrentLongitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

        BitmapDescriptor marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) /*BitmapDescriptorFactory.fromResource(R.mipmap.location_pointer_icon)*/;
        map.addMarker(new MarkerOptions().position(latLng).icon(marker));
        map.setMyLocationEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        Circle circle = map.addCircle(new CircleOptions()
                .center(latLng)
                .radius(10)
                .strokeWidth(2)
                .strokeColor(Color.BLUE)
                .fillColor(Color.parseColor("#00000000"))
                .clickable(true));

        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
//        animation.cancel();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.llQRView:
            case R.id.ivScanQRCode:
                CreateEventModel.getInstance(false).setQRDescription("");
                Intent intent = new Intent(this, ScanQRCodeActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.imgRefreshIcon:
                if (mCurrentLatitude != 0) {
//                    progressDialog.setMessage("Please wait..\nRefreshing location.");
//                    progressDialog.show();
//                    ivBounceIcon.startAnimation(animation);
                    progressBar.setVisibility(View.VISIBLE);
                    startTrackingLocation();
                } else {
                    showAlertDialog(this, "", "Please scan QR code.");
                }
                break;
            case R.id.etClientName:
                intent=new Intent(QRTaggingActivity.this,ClientsListActivity.class);
                intent.putExtra(Constants.EXTRA_DATA,listData);
                startActivityForResult(intent,Constants.REQUEST_CODE);
//                initMultiSelectionDialog();

                break;
            case R.id.btnSubmit:
                if(StringUtils.isNullOrEmpty(tvQrCodeId.getText().toString().trim())){
                    ToastUtils.showToast(this,"Please scan QR code.");
                    return;
                }
                if(StringUtils.isNullOrEmpty(etClientName.getText().toString().trim())){
                    ToastUtils.showToast(this,"Please select client name.");
                    return;
                }
                getDownloadUrl();
//                hitApiSaveToken();
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
    public void onTaskCompleted(String result) {
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map_frame, mapFragment).commit();
        mapFragment.getMapAsync(this);
//        getCurrentLocation(location.getLatitude(), location.getLongitude());

    }


    /**
     * Starts tracking the device. Checks for
     * permissions, and requests them if they aren't present. If they are,
     * requests periodic location updates, sets a loading text and starts the
     * animation.
     */
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

    /**
     * Callback that is invoked when the user responds to the permissions
     * dialog.
     *
     * @param requestCode  Request code representing the permission request
     *                     issued by the app.
     * @param permissions  An array that contains the permissions that were
     *                     requested.
     * @param grantResults An array with the results of the request for each
     *                     permission requested.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
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
            case  MY_CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
                }


        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 101:
                if (!mTrackingLocation && !StringUtils.isNullOrEmpty(CreateEventModel.getInstance(false).getQRDescription())) {
                    progressDialog.show();
                    startTrackingLocation();
                    String qrDescription = CreateEventModel.getInstance(false).getQRDescription();
                    if (!StringUtils.isNullOrEmpty(qrDescription)) {
                        tvQrCodeId.setText("ID: " + qrDescription);
                    }
                }
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
                            listData.clear();
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
            case Constants.REQUEST_CODE:
                if(resultCode!=RESULT_OK) return;
                listSelected=data.getParcelableExtra(Constants.EXTRA_DATA);
                etClientName.setText(listSelected.getUnit_name());
                etAddress.setText(listSelected.getLocation());
                etAddress.setVisibility(View.VISIBLE);
                etAddress.setClickable(false);

                break;
            default:
                break;
        }

    }


    private void initMultiSelectionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_category_selection);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewItems);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final ClientListAdapter singleSelectionAdapter = new ClientListAdapter(this);
        recyclerView.setAdapter(singleSelectionAdapter);
        singleSelectionAdapter.updateListData(listData);
        CustomTextView tvTitle = (CustomTextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Select a Client");
        CustomTextView txtCancel = (CustomTextView) dialog.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        CustomTextView txtDone = (CustomTextView) dialog.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSelected = singleSelectionAdapter.getSelectedItemData();
                etAddress.setText(listSelected.getLocation());

                etClientName.setText(singleSelectionAdapter.getSelectedItemString());
                hideSoftKeypad(view);
                dialog.dismiss();
//                todo perform action

            }
        });


        final ArrayList<ClientListModel.Data> listTemp = new ArrayList<>();
        ImageView ivSearch = (ImageView) dialog.findViewById(R.id.ivSearch);


        final RelativeLayout rlSearchByName = (RelativeLayout) dialog.findViewById(R.id.rlSearchByName);
        rlSearchByName.setVisibility(View.GONE);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rlSearchByName.getVisibility() == View.VISIBLE) {
                    hideSoftKeypad(view);
                } else {
                    rlSearchByName.setVisibility(View.VISIBLE);
                    showSoftKeyBoard();
                }


            }
        });
        final CustomEditText editSearchByName = (CustomEditText) dialog.findViewById(R.id.editSearchByName);
        CustomTextView tvCancel = (CustomTextView) dialog.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearchByName.getText().clear();
                rlSearchByName.setVisibility(View.GONE);
                singleSelectionAdapter.updateListData(listData);

            }
        });


        editSearchByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    listTemp.clear();
                    for (ClientListModel.Data commonDropdownModel : listData) {
                        if (commonDropdownModel.getUnit_name().toLowerCase().startsWith(charSequence.toString().trim())) {
                            listTemp.add(commonDropdownModel);
                        }
                    }
                    singleSelectionAdapter.updateListData(listTemp);
                } else {
                    listTemp.clear();
                    singleSelectionAdapter.updateListData(listData);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editSearchByName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeypad(editSearchByName);
                    return true;
                }
                return false;
            }
        });
        dialog.show();

    }


    public void hitApiSaveQRTaggingDetail() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
//            if (listSelected == null) {
//                Toast.makeText(this, "Please select client name", Toast.LENGTH_SHORT).show();
//                return;
//            }
            RequestParams requestParams = new RequestParams();
            try {
                requestParams.put("lat", mCurrentLatitude);
                requestParams.put("lng", mCurrentLongitude);
                requestParams.put("unitCode", listSelected.getUnit_code());
                requestParams.put("taggingId", CreateEventModel.getInstance(false).getQRDescription());
                requestParams.put("userId", preferences.getUserId());
                requestParams.put("imageUrl", imageUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethod(Constants.SAVE_QR_TAG, requestParams, this, SAVE_QR_TAG);
        } catch (Exception e) {
            e.printStackTrace();
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

                            status.startResolutionForResult(QRTaggingActivity.this, REQUEST_CHECK_SETTINGS);
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

}
