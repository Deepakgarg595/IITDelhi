package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ImageAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.CheckListModel;
import com.app.iitdelhicampus.model.LoginModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PERMISSION;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;
import static com.app.iitdelhicampus.constants.Constants.RequestType.SAVE_INCIDENT_REPORT;


public class CheckListActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {

    public static final String TRACKING_LOCATION_KEY = "tracking_location";
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    private final ArrayList<MediaDetails> listImage = new ArrayList<>();
    private final int MY_CAMERA_REQUEST_CODE = 101;
    Gson gson;
    CheckListModel checkListModel = new CheckListModel();
    boolean perimeterRound, isSecurityGuard, acOutdoor;
    private ImageView iv_back;
    private TextView btnVerifyNumber;
    private ProgressBar pbar;
    private CustomEditText etAnyOtherObservation;
    private ImageView ivCamera;
    private ImageView ivScanQRCode;
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private boolean branchLight;
    private boolean branchShutter;
    private boolean signatureWorking;
    private boolean atmWorking;
    private boolean checkedAtmSkimming;
    private boolean atmAcWorking;
    private boolean atmRoom;
    private boolean fireExtinguisher;
    private boolean atmBackRoom;
    private boolean dGSetBattery;
    private boolean policePatrol;
    private boolean policeStationVisit;
    private String teamLead;
    private String clientName;
    private ArrayList<String> jsonArrayImage = new ArrayList<>();
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private LocationManager manager;
    private String clientCode;
    private String location;
    private CustomTextViewBold txtHeader;
    private CustomTextView tvLocationCapture;

    @Override
    protected void onStart() {
        super.onStart();
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.CHECK_USER + preferences.getPhone(), this, Constants.RequestType.SEND_OTP);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        txtHeader=(CustomTextViewBold)findViewById(R.id.txtHeader);
        tvLocationCapture=(CustomTextView)findViewById(R.id.tvLocationCapture);

//        stopAnim();
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gson = new Gson();
        teamLead = getIntent().getStringExtra("TEAM_LEAD");
        clientName = getIntent().getStringExtra("CLIENT_NAME");
        clientCode = getIntent().getStringExtra("CLIENT_CODE");
        location = getIntent().getStringExtra("LOCATION");
        if(!StringUtils.isNullOrEmpty(clientName)) {
            txtHeader.setText(clientName+" Check List");
        }else {
            txtHeader.setText("Check List");
        }


        btnVerifyNumber = (TextView) findViewById(R.id.btnVerifyNumber);
        btnVerifyNumber.setOnClickListener(this);
        hideKeyboard();
        preferences = AppPreferences.getInstance(this);
        iv_back.setOnClickListener(this);
        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerViewImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImage.setLayoutManager(mLayoutManager);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);
        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.smoothScrollToPosition(0);

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
//                    new FetchAddressTask(QRTaggingActivity.this, QRTaggingActivity.this)
//                            .execute(locationResult.getLastLocation());
                    }
                    stopTrackingLocation();
                }
            }
        };
        startTrackingLocation();

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);
        ivScanQRCode = (ImageView) findViewById(R.id.ivScanQRCode);
        ivScanQRCode.setOnClickListener(this);


        etAnyOtherObservation = (CustomEditText) findViewById(R.id.etAnyOtherObservation);

        CustomTextView txtDateNTime = (CustomTextView) findViewById(R.id.txtDateNTime);
        txtDateNTime.setText("Date & Time: " + Utility.getDateForPayment(System.currentTimeMillis()));
        CustomTextView txtLastVisitDate = (CustomTextView) findViewById(R.id.txtLastVisitDate);
        txtLastVisitDate.setText("Last Visit Date: " + Utility.getDateTransaction(System.currentTimeMillis() / 1000));


        final RadioGroup radioGroupPerimeterRound = (RadioGroup) findViewById(R.id.radioGroupPerimeterRound);
        radioGroupPerimeterRound.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupPerimeterRound.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                perimeterRound = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setPerimeterRoundTaken(true);
                } else {
                    checkListModel.setPerimeterRoundTaken(false);
                }
            }
        });

        final RadioGroup radioGroupIsSecurityGuard = (RadioGroup) findViewById(R.id.radioGroupIsSecurityGuard);
        radioGroupIsSecurityGuard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupIsSecurityGuard.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                isSecurityGuard = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setSecurityGuardAvailable(true);
                } else {
                    checkListModel.setSecurityGuardAvailable(false);
                }
            }
        });

        final RadioGroup radioGroupAcOutdoor = (RadioGroup) findViewById(R.id.radioGroupAcOutdoor);
        radioGroupAcOutdoor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupAcOutdoor.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                acOutdoor = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setAcoutdoorUnitOK(true);
                } else {
                    checkListModel.setAcoutdoorUnitOK(false);
                }
            }
        });

        final RadioGroup radioGroupBranchLight = (RadioGroup) findViewById(R.id.radioGroupBranchLight);
        radioGroupBranchLight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupBranchLight.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                branchLight = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setBranchLightandACOff(true);
                } else {
                    checkListModel.setBranchLightandACOff(false);
                }
            }
        });
        final RadioGroup radioGroupBranchShutter = (RadioGroup) findViewById(R.id.radioGroupBranchShutter);
        radioGroupBranchShutter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupBranchShutter.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                branchShutter = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setBranchShutterLockTestedChecked(true);
                } else {
                    checkListModel.setBranchShutterLockTestedChecked(false);
                }
            }
        });

        final RadioGroup radioGroupSignatureWorking = (RadioGroup) findViewById(R.id.radioGroupSignatureWorking);
        radioGroupSignatureWorking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupSignatureWorking.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                signatureWorking = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setSignatureWorking(true);
                } else {
                    checkListModel.setSignatureWorking(false);
                }
            }
        });
        final RadioGroup radioGroupAtmWorking = (RadioGroup) findViewById(R.id.radioGroupAtmWorking);
        radioGroupAtmWorking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupAtmWorking.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                atmWorking = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setAtmWorking(true);
                } else {
                    checkListModel.setAtmWorking(false);
                }
            }
        });
        final RadioGroup radioGroupCheckedAtmSkimming = (RadioGroup) findViewById(R.id.radioGroupCheckedAtmSkimming);
        radioGroupCheckedAtmSkimming.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupCheckedAtmSkimming.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                checkedAtmSkimming = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setCheckedATMSkimmingDeviceFound(true);
                } else {
                    checkListModel.setCheckedATMSkimmingDeviceFound(false);
                }
            }
        });
        final RadioGroup radioGroupAtmAcWorking = (RadioGroup) findViewById(R.id.radioGroupAtmAcWorking);
        radioGroupAtmAcWorking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupAtmAcWorking.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                atmAcWorking = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setAtmACWorking(true);
                } else {
                    checkListModel.setAtmACWorking(false);
                }
            }
        });
        final RadioGroup radioGroupAtmRoom = (RadioGroup) findViewById(R.id.radioGroupAtmRoom);
        radioGroupAtmRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupAtmRoom.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                atmRoom = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setAtmroomClean(true);
                } else {
                    checkListModel.setAtmroomClean(false);
                }
            }
        });
        final RadioGroup radioGroupFireExtinguisher = (RadioGroup) findViewById(R.id.radioGroupFireExtinguisher);
        radioGroupFireExtinguisher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupFireExtinguisher.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                fireExtinguisher = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setFireExtinguisherAvailable(true);
                } else {
                    checkListModel.setFireExtinguisherAvailable(false);
                }
            }
        });
        final RadioGroup radioGroupAtmBackRoom = (RadioGroup) findViewById(R.id.radioGroupAtmBackRoom);
        radioGroupAtmBackRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupAtmBackRoom.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                atmBackRoom = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setAtmbackRoomLocked(true);
                } else {
                    checkListModel.setAtmbackRoomLocked(false);
                }
            }
        });
        final RadioGroup radioGroupDGSetBattery = (RadioGroup) findViewById(R.id.radioGroupDGSetBattery);
        radioGroupDGSetBattery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupDGSetBattery.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                dGSetBattery = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setDgsetBatteryChecked(true);
                } else {
                    checkListModel.setDgsetBatteryChecked(false);
                }
            }
        });

        final RadioGroup radioGroupPolicePatrol = (RadioGroup) findViewById(R.id.radioGroupPolicePatrol);
        radioGroupPolicePatrol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupPolicePatrol.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                policePatrol = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setPolicePatrolMet(true);
                } else {
                    checkListModel.setPolicePatrolMet(false);
                }
            }
        });

        final RadioGroup radioGroupPoliceStationVisit = (RadioGroup) findViewById(R.id.radioGroupPoliceStationVisit);
        radioGroupPoliceStationVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupPoliceStationVisit.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                policeStationVisit = true;
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    checkListModel.setPoliceStationVisitChecked(true);
                } else {
                    checkListModel.setPoliceStationVisitChecked(false);
                }
            }
        });


    }

    private boolean isFieldChecked(){
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!perimeterRound) {
            Toast.makeText(this, "Please select Perimeter Round Taken.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isSecurityGuard) {
            Toast.makeText(this, "Please select Security Guard Availability.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!acOutdoor) {
            Toast.makeText(this, "Please select AC Outdoor Unit.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!branchLight) {
            Toast.makeText(this, "Please select Branch Light AC.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!branchShutter) {
            Toast.makeText(this, "Please select Branch Shutter Lock.", Toast.LENGTH_LONG).show();
            return false;
        }




        if (!atmWorking) {
            Toast.makeText(this, "Please select ATM Working.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!checkedAtmSkimming) {
            Toast.makeText(this, "Please select ATM Skimming Device.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!atmAcWorking) {
            Toast.makeText(this, "Please select ATM AC Working.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!atmRoom) {
            Toast.makeText(this, "Please select ATM Room Clean.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!fireExtinguisher) {
            Toast.makeText(this, "Please select Fire Extinguisher.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!atmBackRoom) {
            Toast.makeText(this, "Please select ATM Back Room Locked.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!dGSetBattery) {
            Toast.makeText(this, "Please select DG Set Battery.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!policePatrol) {
            Toast.makeText(this, "Please select Police Patrol Met.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!policeStationVisit) {
            Toast.makeText(this, "Please select Police Station Visit.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!signatureWorking) {
            Toast.makeText(this, "Please select Signage Working.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setDataToModel() {

        if(!isFieldChecked()){
            btnVerifyNumber.setEnabled(true);
            return;
        }
        try {
            pbar.setVisibility(View.VISIBLE);
//        startAnim();
            checkListModel.setTeamLeadName(teamLead);
            checkListModel.setClientName(clientName);
            checkListModel.setDate(Utility.getDateForPayment(System.currentTimeMillis()));
//            checkListModel.setLastDate(Utility.getDateForPayment(System.currentTimeMillis()));
            checkListModel.setAnyOtherObservation(etAnyOtherObservation.getText().toString().trim());
//        checkListModel.setDate(Utility.getReportDate(System.currentTimeMillis()));
            checkListModel.setCreatedDate(Utility.getReportDate(System.currentTimeMillis()));
            checkListModel.setCountryCode(preferences.getCountry().countryCode);
            checkListModel.setMobile(preferences.getPhone());
            checkListModel.setName(preferences.getName());
            checkListModel.setEmpCode(preferences.getEmpCode());
            checkListModel.setUserId(preferences.getUserId());
            checkListModel.setTeam_lead_id(preferences.getTeamLeadID());
            checkListModel.setTeamlead(preferences.getTeamLead());
            checkListModel.setDepartment(preferences.getDepartment());
            checkListModel.setClientCode(clientCode);
            checkListModel.setLocation(location);
            checkListModel.setDesignation(preferences.getUserType());
            checkListModel.setBranchId(preferences.getBranchId());
            checkListModel.setBranchName(preferences.getBranchName());


            try {
                checkListModel.setLatitude(mCurrentLatitude + "");
                checkListModel.setLongitude(mCurrentLongitude + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            getDownloadUrl();
//        saveDataToFirebase();
        }catch (Exception e){
            e.printStackTrace();
            btnVerifyNumber.setEnabled(true);
        }

    }

    private void saveDataToFirebase() {

        hitApiSaveIncidentReport();

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("po_incident_report")
//                .add(checkListModel)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        String taskId = documentReference.getId();
//                        pbar.setVisibility(View.GONE);
//                        showAlertDialogWithFinish(CheckListActivity.this, "", "Check List Submitted Successfully.");
//                        if (POReportIncidentActivity.poContext != null) {
//                            POReportIncidentActivity.poContext.finish();
//                        }
//
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                hideSoftKeyBoard();
                onBackPressed();
                break;
            case R.id.btnVerifyNumber:
                btnVerifyNumber.setEnabled(false);
                setDataToModel();

                break;


            case R.id.ivCamera:
                checkPermission();
                break;
            case R.id.ivScanQRCode:
                Intent intent = new Intent(this, ScanQRCodeActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void selectImage() {

        final CharSequence[] options = {"Photo from Camera", "Photo from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].toString().equalsIgnoreCase(options[0].toString())) {
                    if (isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(CheckListActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestAppPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT, CheckListActivity.this);
                    } else {
                        startCameraForEvent();
                    }
                } else if (options[item].toString().equalsIgnoreCase(options[1].toString())) {
                    if (isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(CheckListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION, REQUEST_PERMISSION, CheckListActivity.this);
                    } else {
                        pickFromGallery();
//                        takeImageFromGallery();
                    }
                } else if (options[item].toString().equalsIgnoreCase(options[2].toString())) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void pickFromGallery() {
        String fileType = "image/*";
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        photoPickerIntent.setType(fileType);
        startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
    }

    public void startCameraForEvent() {
        String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis());
        tempFile = FileUtils.getFile(FileUtils.DIR_IMAGE, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUri(tempFile, this));
        startActivityForResult(intent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);

    }

    private void dispatchTakePictureIntent() {
        if(listImage!=null && listImage.size()>=4){
            ToastUtils.showToast(this,"You can not upload more than 4 images.");
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }


//    public void numberVerificationDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.confirmation_dialog);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.txtConrifrm);
//        final CustomTextView txtNumber = (CustomTextView) dialog.findViewById(R.id.txtNumber);
////        preferences.setPhoneWCC(phoneNumber.getText().toString().trim());
//        txtNumber.setText(txvCountryCode.getText().toString() + Utility.replaceFirstZero(phoneNumber.getText().toString()));
//        text.setText("Is your phone number above correct ?");
//        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
//        btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                preferences.setCountryCode(txvCountryCode.getText().toString());
//                preferences.setPhone(Utility.replaceFirstZero(phoneNumber.getText().toString().trim()));
//                dialog.dismiss();
//                Intent intent = new Intent(ReportIncidentActivity.this, VerificationActivity.class);
//                startActivity(intent);
////                hitSendotp(preferences.getPhone());/ uncomment when api integrated
//            }
//        });
//        dialog.show();
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis() + ".png");
                            if (data == null) return;
                            try {
                                Bundle extras = data.getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                File imageUrl = FileUtils.saveToInternalStorage(imageBitmap, fileName);


                                MediaDetails eventMediaModel = new MediaDetails();
                                eventMediaModel.setImageUrl(imageUrl + "");
                                eventMediaModel.setVideo(false);
                                eventMediaModel.setType(Constants.MediaType.PIC);
                                eventMediaModel.setSelected(true);
                                listImage.add(eventMediaModel);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
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


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    public void hitSendotp(String phoneNumber) {
//        try {
//            if (!ConnectivityUtils.isNetworkEnabled(this)) {
//                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
////                progressDialog.dismiss();
//                pbar.setVisibility(View.GONE);
//                return;
//            }
//            pbar.setVisibility(View.VISIBLE);
//
////            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
////            AppPreferences.getInstance(this).setDeviceId(deviceId);
//            JSONObject requestParams = new JSONObject();
//            try {
//                requestParams.put(Constants.Params.PHONE, phoneNumber);
//                requestParams.put(Constants.Params.REQUEST_ID, String.valueOf(Constants.RequestType.SEND_OTP));
//                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
//                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
//                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
//                requestParams.put(Constants.Params.COUNTRY_CODE, txvCountryCode.getText().toString().trim());
//                requestParams.put(Constants.Params.TYPE,"1");
//
//                if (preferences.getLoginType().equalsIgnoreCase(Constants.LoginType.FB)) {
//                    requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
//                    requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getLoginType());
//                    requestParams.put(Constants.Params.SOCIAL_ID, getIntent().getStringExtra(Constants.Params.SOCIAL_ID));
//
//                } else {
//                    requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getLoginType());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.SEND_OTP, requestParams, this, Constants.RequestType.SEND_OTP);
//        } catch (Exception e) {
//        }
//    }


    public void hitApiSaveIncidentReport() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestParams requestParams = new RequestParams();
            try {
                String jsonObject = gson.toJson(checkListModel);
                requestParams.put("data", new JSONObject(jsonObject));

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethod(Constants.SAVE_INCIDENT_REPORT, requestParams, this, SAVE_INCIDENT_REPORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
//        progressDialog.dismiss();
        btnVerifyNumber.setEnabled(true);
        pbar.setVisibility(View.GONE);
        try {
//        stopAnim();
            if (isSuccess && responseCode == Constants.RequestType.SEND_OTP /*&& otpResponseModel!=null*/) {
                UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
                btnVerifyNumber.setEnabled(true);
                if (otpResponseModel != null) {
//                    if (otpResponseModel.isStatus()) {
//                        if (otpResponseModel.getData() != null && otpResponseModel.getData().size() > 0) {
//                            preferences.setName(otpResponseModel.getData().get(0).getUserName());
//                            preferences.setUserType(otpResponseModel.getData().get(0).getDesignation());
//                            preferences.setEmail(otpResponseModel.getData().get(0).getEmail());
//                            preferences.setEmpCode(otpResponseModel.getData().get(0).getEmpCode());
//                            preferences.setUserId(otpResponseModel.getData().get(0).getUserId());
//                            preferences.setTeamLead(otpResponseModel.getData().get(0).getTeamlead());
//                            preferences.setTeamLeadID(otpResponseModel.getData().get(0).getTeam_lead_id());
//                            preferences.setDepartment(otpResponseModel.getData().get(0).getDepartment());
//                            preferences.setBranchId(otpResponseModel.getData().get(0).getBranchId());
//                            preferences.setBranchName(otpResponseModel.getData().get(0).getBranchName());
//
//                            pbar.setVisibility(View.GONE);
//                        } else {
//                            showAlertDialog(this, "", otpResponseModel.getMsg());
//                        }
//                    } else {
//                        showAlertDialogForNotRegistered(this, "", otpResponseModel.getMsg());
//                    }
                }
            } else if (responseCode == SAVE_INCIDENT_REPORT) {
                UserLoginModel loginModel = gson.fromJson(response, UserLoginModel.class);
//                showAlertDialogWithFinish(this, "", loginModel.getMsg());
            }else if (responseCode == Constants.RequestType.SEND_OTP) {
                UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
                if (otpResponseModel != null) {
                    /*if (otpResponseModel.isStatus()) {
                        if (otpResponseModel.getData() != null && otpResponseModel.getData().size() > 0) {
                            preferences.setName(otpResponseModel.getData().get(0).getUserName());
                            preferences.setUserType(otpResponseModel.getData().get(0).getDesignation());
                            preferences.setEmail(otpResponseModel.getData().get(0).getEmail());
                            preferences.setEmpCode(otpResponseModel.getData().get(0).getEmpCode());
                            preferences.setUserId(otpResponseModel.getData().get(0).getUserId());
                            preferences.setTeamLead(otpResponseModel.getData().get(0).getTeamlead());
                            preferences.setTeamLeadID(otpResponseModel.getData().get(0).getTeam_lead_id());
                            preferences.setDepartment(otpResponseModel.getData().get(0).getDepartment());
                            preferences.setBranchId(otpResponseModel.getData().get(0).getBranchId());
                            preferences.setBranchName(otpResponseModel.getData().get(0).getBranchName());

                        } else {
                            showAlertDialog(this, "", otpResponseModel.getMsg());
                        }
//                    }*/ }else {
//                        showAlertDialogForNotRegistered(this, "", otpResponseModel.getMsg());
//                    }
                }


            }else {
                LoginModel otpResponseModel = gson.fromJson(response, LoginModel.class);
                Toast.makeText(this, "Some error occurred.", Toast.LENGTH_LONG).show();
                showAlertDialog(this, getString(R.string.app_name), otpResponseModel.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
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
                    checkListModel.setImages(jsonArrayImage);
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
            case MY_CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
                }
                break;


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

                            status.startResolutionForResult(CheckListActivity.this, REQUEST_CHECK_SETTINGS);
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

                String address = address_short + " " + city;
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