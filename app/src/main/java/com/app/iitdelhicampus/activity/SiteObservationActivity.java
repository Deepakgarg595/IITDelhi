package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ImageAdapter;
import com.app.iitdelhicampus.adapter.SecurityEquipmentAdapter;
import com.app.iitdelhicampus.adapter.SecurityHazardAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.ClientDataForPOModel;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.model.SiteVisitMode;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.Constants.RequestType.GET_CLIENT_BY_QR_CODE;
import static com.app.iitdelhicampus.constants.Constants.RequestType.SAVE_SITE_OBSERVATION;


/**
 * Created by pawan on 25/10/2020.
 */

public class SiteObservationActivity extends BaseActivity implements OnUpdateResponse {
    private final int MY_CAMERA_REQUEST_CODE = 101;
    private final ArrayList<MediaDetails> listImage = new ArrayList<>();
    boolean isSOPClicked;
    boolean isUniformAccessoryClicked;
    boolean isGuardGroomingClicked;
    boolean isGuardUniformClicked;
    boolean isLastNightCheckClicked;
    boolean isLastNightTrainingClicked;
    boolean isSecurityHazardNoticedClicked;
    boolean isAnyIncidentTookPlaceClicked;
    boolean isSecurityEquipmentProvidedClicked;
    private ProgressBar progressBar;
    private CustomEditText etSiteCode;
    private CustomEditText etClientName;
    private CustomEditText etDeploymentLocation;
    private CustomEditText etServiceType;
    private CustomEditText etNoOfManpower;
    private CustomEditText etShortage;
    private CustomEditText etShortageReason;
    private CustomEditText etReasonForNonIssuanceOfSOPs;
    //    private CustomEditText etUniformNAccessories;
//    private CustomEditText etGuardGrooming;
//    private CustomEditText etGuardUniform;
    private CustomEditText etGuardAwarenessOfDuty;
    private CustomEditText etLastInvoiceSubmitted;
    private CustomEditText etPaymentReceivedTillWhichMonth;
    private CustomEditText etMetWhom;
    private CustomEditText etAnyClientConcern;
    //    private CustomEditText etAnySecurityHazardNoticed;
    private CustomEditText etRemark;
    private TextInputLayout tvInputLastNightCheckReason;
    private TextInputLayout tvInputLastNightCheckDate;
    private TextInputLayout tvInputLastTrainingConductedDate;
    private TextInputLayout tvInputLastTrainingConductedReason;
    private TextInputLayout tvInputAnyincIdentInPlaceSinceLastWeek;
    private TextInputLayout tvInputAnyincIdentInPlaceSinceLastWeekReason;
    private TextInputLayout tvInputSecurityEquipmentStatusProvidedByUsDate;
    private TextInputLayout tvInputSecurityEquipmentStatusProvidedByUsReason;
    private TextInputLayout tvInputNonIssuanceOfSOPsReason;
    private SiteVisitMode siteVisitModel = new SiteVisitMode();
    private CustomTextViewBold tvSubmit;
    private CustomTextView txtDateNTime, txtLastVisitDate;
    private CustomEditText etServiceTypeOther;
    private TextInputLayout tvInputOtherService;
    private CustomEditText txtLastNightCheckDate;
    private CustomEditText etLastNightCheckReason;
    private CustomEditText etLastTrainingConductedDate;
    private CustomEditText etLastTrainingConductedReason;
    private CustomEditText etAnyincIdentInPlaceSinceLastWeedDate;
    private CustomEditText etAnyincIdentInPlaceSinceLastWeekReason;
    private CustomEditText etSecurityEquipmentStatusProvidedByUsDate;
    private CustomEditText etSecurityEquipmentStatusProvidedByUsReason;
    private CustomTextView tvQRResponse;
    private boolean isEditable;
    private RadioButton radioButton1IsSitePostStanding;
    private RadioButton radioButton2IsSitePostStanding;
    private RadioButton radioButton1LastNightCheck;
    private RadioButton radioButton2LastNightCheck;
    private RadioButton radioButton1LastTrainingConducted;
    private RadioButton radioButton2LastTrainingConducted;
    private RadioButton radioButton1AnyincIdentInPlaceSinceLastWeek;
    private RadioButton radioButton2AnyincIdentInPlaceSinceLastWeek;
    private RadioGroup radioGroupLastNightCheck;
    private RadioGroup radioGroupLastTrainingConducted;
    private RadioGroup radioGroupAnyincIdentInPlaceSinceLastWeek;
    private RadioGroup radioGroupSecurityEquipmentStatusProvidedByUs;
    private ImageView ivScanQRCode;
    private RadioButton radioButton1SecurityEquipmentStatusProvidedByUs;
    private RadioButton radioButton2SecurityEquipmentStatusProvidedByUs;
    private LinearLayout llQRView;
    private String clientLocation;
    private String clientCode;
    private SecurityHazardAdapter securityHazardAdapter;
    private SecurityEquipmentAdapter securityEquipmentAdapter;
    private ArrayList<String> jsonArrayImage = new ArrayList<>();
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private CustomTextView tvLocationCapture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_observation);
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

        llQRView = (LinearLayout) findViewById(R.id.llQRView);
        llQRView.setOnClickListener(this);


        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerViewImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImage.setLayoutManager(mLayoutManager);
        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.smoothScrollToPosition(0);

        ImageView ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);


//        CustomEditText txtClientName=(CustomEditText)findViewById(R.id.txtClientName);
//        txtClientName.setOnClickListener(this);

        ivScanQRCode = (ImageView) findViewById(R.id.ivScanQRCode);
        ivScanQRCode.setOnClickListener(this);
        tvQRResponse = (CustomTextView) findViewById(R.id.tvQRResponse);

        txtDateNTime = (CustomTextView) findViewById(R.id.txtDateNTime);
        txtDateNTime.setText("Date & Time: " + Utility.getDateForPayment(System.currentTimeMillis()));
        txtLastVisitDate = (CustomTextView) findViewById(R.id.txtLastVisitDate);
        txtLastVisitDate.setText("Last Visit Date: " + Utility.getDateTransaction(System.currentTimeMillis() / 1000));


        etSiteCode = (CustomEditText) findViewById(R.id.etSiteCode);
        etClientName = (CustomEditText) findViewById(R.id.etClientName);
        etClientName.setOnClickListener(this);
        etDeploymentLocation = (CustomEditText) findViewById(R.id.etDeploymentLocation);
        etDeploymentLocation.setOnClickListener(this);
        etServiceType = (CustomEditText) findViewById(R.id.etServiceType);
        etServiceType.setOnClickListener(this);
        etNoOfManpower = (CustomEditText) findViewById(R.id.etNoOfManpower);
        etShortage = (CustomEditText) findViewById(R.id.etShortage);
        etShortageReason = (CustomEditText) findViewById(R.id.etShortageReason);
        etServiceTypeOther = (CustomEditText) findViewById(R.id.etServiceTypeOther);
        txtLastNightCheckDate = (CustomEditText) findViewById(R.id.txtLastNightCheckDate);
        txtLastNightCheckDate.setOnClickListener(this);
        etLastNightCheckReason = (CustomEditText) findViewById(R.id.etLastNightCheckReason);

        etLastTrainingConductedDate = (CustomEditText) findViewById(R.id.etLastTrainingConductedDate);
        etLastTrainingConductedDate.setOnClickListener(this);
        etLastTrainingConductedReason = (CustomEditText) findViewById(R.id.etLastTrainingConductedReason);


        etAnyincIdentInPlaceSinceLastWeedDate = (CustomEditText) findViewById(R.id.etAnyincIdentInPlaceSinceLastWeedDate);
        etAnyincIdentInPlaceSinceLastWeedDate.setOnClickListener(this);
        etAnyincIdentInPlaceSinceLastWeekReason = (CustomEditText) findViewById(R.id.etAnyincIdentInPlaceSinceLastWeekReason);

        etSecurityEquipmentStatusProvidedByUsDate = (CustomEditText) findViewById(R.id.etSecurityEquipmentStatusProvidedByUsDate);
        etSecurityEquipmentStatusProvidedByUsDate.setOnClickListener(this);
        etSecurityEquipmentStatusProvidedByUsReason = (CustomEditText) findViewById(R.id.etSecurityEquipmentStatusProvidedByUsReason);


        tvInputLastNightCheckReason = (TextInputLayout) findViewById(R.id.tvInputLastNightCheckReason);
        tvInputLastNightCheckReason.setVisibility(View.GONE);
        tvInputLastNightCheckDate = (TextInputLayout) findViewById(R.id.tvInputLastNightCheckDate);
        tvInputLastNightCheckDate.setVisibility(View.GONE);

        tvInputLastTrainingConductedDate = (TextInputLayout) findViewById(R.id.tvInputLastTrainingConductedDate);
        tvInputLastTrainingConductedDate.setVisibility(View.GONE);
        tvInputLastTrainingConductedReason = (TextInputLayout) findViewById(R.id.tvInputLastTrainingConductedReason);
        tvInputLastTrainingConductedReason.setVisibility(View.GONE);

        tvInputAnyincIdentInPlaceSinceLastWeek = (TextInputLayout) findViewById(R.id.tvInputAnyincIdentInPlaceSinceLastWeek);
        tvInputAnyincIdentInPlaceSinceLastWeek.setVisibility(View.GONE);
        tvInputAnyincIdentInPlaceSinceLastWeekReason = (TextInputLayout) findViewById(R.id.tvInputAnyincIdentInPlaceSinceLastWeekReason);
        tvInputAnyincIdentInPlaceSinceLastWeekReason.setVisibility(View.GONE);

        tvInputSecurityEquipmentStatusProvidedByUsDate = (TextInputLayout) findViewById(R.id.tvInputSecurityEquipmentStatusProvidedByUsDate);
        tvInputSecurityEquipmentStatusProvidedByUsDate.setVisibility(View.GONE);
        tvInputSecurityEquipmentStatusProvidedByUsReason = (TextInputLayout) findViewById(R.id.tvInputSecurityEquipmentStatusProvidedByUsReason);
        tvInputSecurityEquipmentStatusProvidedByUsReason.setVisibility(View.GONE);

        tvInputOtherService = (TextInputLayout) findViewById(R.id.tvInputOtherService);
        tvInputOtherService.setVisibility(View.GONE);


        tvInputNonIssuanceOfSOPsReason = (TextInputLayout) findViewById(R.id.tvInputNonIssuanceOfSOPsReason);
        tvInputNonIssuanceOfSOPsReason.setVisibility(View.GONE);


        radioButton1IsSitePostStanding = (RadioButton) findViewById(R.id.radioButton1IsSitePostStanding);
        radioButton2IsSitePostStanding = (RadioButton) findViewById(R.id.radioButton2IsSitePostStanding);


        radioButton1LastNightCheck = (RadioButton) findViewById(R.id.radioButton1LastNightCheck);
        radioButton2LastNightCheck = (RadioButton) findViewById(R.id.radioButton2LastNightCheck);


        radioButton1LastTrainingConducted = (RadioButton) findViewById(R.id.radioButton1LastTrainingConducted);
        radioButton2LastTrainingConducted = (RadioButton) findViewById(R.id.radioButton2LastTrainingConducted);


        radioButton1AnyincIdentInPlaceSinceLastWeek = (RadioButton) findViewById(R.id.radioButton1AnyincIdentInPlaceSinceLastWeek);
        radioButton2AnyincIdentInPlaceSinceLastWeek = (RadioButton) findViewById(R.id.radioButton2AnyincIdentInPlaceSinceLastWeek);

        radioButton1SecurityEquipmentStatusProvidedByUs = (RadioButton) findViewById(R.id.radioButton1SecurityEquipmentStatusProvidedByUs);
        radioButton2SecurityEquipmentStatusProvidedByUs = (RadioButton) findViewById(R.id.radioButton2SecurityEquipmentStatusProvidedByUs);


        final RadioGroup radioGroupIsSitePostStanding = (RadioGroup) findViewById(R.id.radioGroupIsSitePostStanding);
        radioGroupIsSitePostStanding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isSOPClicked = true;
                int selectedId = radioGroupIsSitePostStanding.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    tvInputNonIssuanceOfSOPsReason.setVisibility(View.GONE);
                    siteVisitModel.setSOPIssued(true);

                } else {
                    siteVisitModel.setSOPIssued(false);
                    tvInputNonIssuanceOfSOPsReason.setVisibility(View.VISIBLE);
                }
            }
        });
        etReasonForNonIssuanceOfSOPs = (CustomEditText) findViewById(R.id.etReasonForNonIssuanceOfSOPs);


        radioGroupLastNightCheck = (RadioGroup) findViewById(R.id.radioGroupLastNightCheck);
        radioGroupLastNightCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isLastNightCheckClicked = true;
                int selectedId = radioGroupLastNightCheck.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    tvInputLastNightCheckDate.setVisibility(View.VISIBLE);
                    tvInputLastNightCheckReason.setVisibility(View.VISIBLE);
                    siteVisitModel.setLastNightCheck(true);
                } else {
                    siteVisitModel.setLastNightCheck(false);
                    tvInputLastNightCheckDate.setVisibility(View.GONE);
                    tvInputLastNightCheckReason.setVisibility(View.GONE);
                }
            }
        });

        radioGroupLastTrainingConducted = (RadioGroup) findViewById(R.id.radioGroupLastTrainingConducted);
        radioGroupLastTrainingConducted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isLastNightTrainingClicked = true;
                int selectedId = radioGroupLastTrainingConducted.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    tvInputLastTrainingConductedDate.setVisibility(View.VISIBLE);
                    tvInputLastTrainingConductedReason.setVisibility(View.VISIBLE);
                    siteVisitModel.setLastTrainingConducted(true);
                } else {
                    siteVisitModel.setLastTrainingConducted(false);
                    tvInputLastTrainingConductedDate.setVisibility(View.GONE);
                    tvInputLastTrainingConductedReason.setVisibility(View.GONE);
                }
            }
        });

        radioGroupAnyincIdentInPlaceSinceLastWeek = (RadioGroup) findViewById(R.id.radioGroupAnyincIdentInPlaceSinceLastWeek);
        radioGroupAnyincIdentInPlaceSinceLastWeek.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isAnyIncidentTookPlaceClicked = true;
                int selectedId = radioGroupAnyincIdentInPlaceSinceLastWeek.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    tvInputAnyincIdentInPlaceSinceLastWeekReason.setVisibility(View.VISIBLE);
                    tvInputAnyincIdentInPlaceSinceLastWeek.setVisibility(View.GONE);
                    siteVisitModel.setAnyIncidentInLastWeek(true);

                } else {
                    siteVisitModel.setAnyIncidentInLastWeek(false);
                    tvInputAnyincIdentInPlaceSinceLastWeekReason.setVisibility(View.GONE);
                    tvInputAnyincIdentInPlaceSinceLastWeek.setVisibility(View.GONE);

                }
            }
        });

        radioGroupSecurityEquipmentStatusProvidedByUs = (RadioGroup) findViewById(R.id.radioGroupSecurityEquipmentStatusProvidedByUs);
        radioGroupSecurityEquipmentStatusProvidedByUs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isSecurityEquipmentProvidedClicked = true;
                int selectedId = radioGroupSecurityEquipmentStatusProvidedByUs.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    tvInputSecurityEquipmentStatusProvidedByUsReason.setVisibility(View.GONE);
                    tvInputSecurityEquipmentStatusProvidedByUsDate.setVisibility(View.GONE);
                    siteVisitModel.setSecurityEquipmentProvided(true);
                    initSecurityEquipmentProvidedByUs(true, true);
                } else {
                    siteVisitModel.setSecurityEquipmentProvided(false);
                    tvInputSecurityEquipmentStatusProvidedByUsReason.setVisibility(View.GONE);
                    tvInputSecurityEquipmentStatusProvidedByUsDate.setVisibility(View.GONE);
                    initSecurityEquipmentProvidedByUs(false, true);

                }
            }
        });


        final RadioGroup radioGroupGuardGrooming = (RadioGroup) findViewById(R.id.radioGroupGuardGrooming);
        radioGroupGuardGrooming.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isGuardGroomingClicked = true;
                int selectedId = radioGroupGuardGrooming.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.radioButton1GuardGrooming:
                        siteVisitModel.setGuardGrooming(radioButton.getText().toString());
                        break;
                    case R.id.radioButton2GuardGrooming:
                        siteVisitModel.setGuardGrooming(radioButton.getText().toString());
                        break;
                    case R.id.radioButton3GuardGrooming:
                        siteVisitModel.setGuardGrooming(radioButton.getText().toString());
                        break;
                }
            }
        });


        final RadioGroup radioGroupUniformAccessories = (RadioGroup) findViewById(R.id.radioGroupUniformAccessories);
        radioGroupUniformAccessories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isUniformAccessoryClicked = true;
                int selectedId = radioGroupUniformAccessories.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.radioButton1UniformAccessories:
                        siteVisitModel.setUniformAccessory(radioButton.getText().toString());
                        break;
                    case R.id.radioButton2UniformAccessories:
                        siteVisitModel.setUniformAccessory(radioButton.getText().toString());
                        break;
                    case R.id.radioButton3UniformAccessories:
                        siteVisitModel.setUniformAccessory(radioButton.getText().toString());
                        break;
                }
            }
        });


        final RadioGroup radioGroupGuardUniform = (RadioGroup) findViewById(R.id.radioGroupGuardUniform);
        radioGroupGuardUniform.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isGuardUniformClicked = true;
                int selectedId = radioGroupGuardUniform.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.radioButton1GuardUniform:
                        siteVisitModel.setGuardUniform(radioButton.getText().toString());
                        break;
                    case R.id.radioButton2GuardUniform:
                        siteVisitModel.setGuardUniform(radioButton.getText().toString());
                        break;
                    case R.id.radioButton3GuardUniform:
                        siteVisitModel.setGuardUniform(radioButton.getText().toString());
                        break;
                }
            }
        });


        final RadioGroup radioGroupAnySecurityHazardNoticed = (RadioGroup) findViewById(R.id.radioGroupAnySecurityHazardNoticed);
        radioGroupAnySecurityHazardNoticed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isSecurityHazardNoticedClicked = true;
                int selectedId = radioGroupAnySecurityHazardNoticed.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.radioButton1AnySecurityHazardNoticed:
                        siteVisitModel.setSecurityHazardStatus(true);
                        initSecurityHazard(true, true);
                        break;
                    case R.id.radioButton2AnySecurityHazardNoticed:
                        siteVisitModel.setSecurityHazardStatus(false);
                        initSecurityHazard(false, true);
                        break;
                }
            }
        });


//        etUniformNAccessories = (CustomEditText) findViewById(R.id.etUniformNAccessories);
//        etGuardGrooming = (CustomEditText) findViewById(R.id.etGuardGrooming);

//        etGuardUniform = (CustomEditText) findViewById(R.id.etGuardUniform);
        etGuardAwarenessOfDuty = (CustomEditText) findViewById(R.id.etGuardAwarenessOfDuty);

        etLastInvoiceSubmitted = (CustomEditText) findViewById(R.id.etLastInvoiceSubmitted);
        etPaymentReceivedTillWhichMonth = (CustomEditText) findViewById(R.id.etPaymentReceivedTillWhichMonth);
        etPaymentReceivedTillWhichMonth.setOnClickListener(this);

        etMetWhom = (CustomEditText) findViewById(R.id.etMetWhom);
        etAnyClientConcern = (CustomEditText) findViewById(R.id.etAnyClientConcern);

//        etAnySecurityHazardNoticed = (CustomEditText) findViewById(R.id.etAnySecurityHazardNoticed);
        etRemark = (CustomEditText) findViewById(R.id.etRemark);

        isEditable = getIntent().getBooleanExtra(Constants.NEED_TO_EDIT, false);
        try {
            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.CHECK_USER + preferences.getPhone(), this, Constants.RequestType.SEND_OTP);

    }

    private void initSecurityHazard(boolean visibility, boolean textEnabled) {
        RecyclerView recyclerViewSecurityHazard = (RecyclerView) findViewById(R.id.recyclerViewSecurityHazard);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSecurityHazard.setLayoutManager(mLayoutManager);
        securityHazardAdapter = new SecurityHazardAdapter(this, textEnabled);
        recyclerViewSecurityHazard.setAdapter(securityHazardAdapter);
        securityHazardAdapter.updateListData(siteVisitModel.getSecurityHazardList());
        recyclerViewSecurityHazard.setVisibility(View.GONE);
        if (visibility) {
            recyclerViewSecurityHazard.setVisibility(View.VISIBLE);
        }
    }


    private void initSecurityEquipmentProvidedByUs(boolean visibility, boolean textEnabled) {
        RecyclerView recyclerViewSecurityEquipmentProvidedByUs = (RecyclerView) findViewById(R.id.recyclerViewSecurityEquipmentProvidedByUs);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSecurityEquipmentProvidedByUs.setLayoutManager(mLayoutManager);
        securityEquipmentAdapter = new SecurityEquipmentAdapter(this, textEnabled);
        recyclerViewSecurityEquipmentProvidedByUs.setAdapter(securityEquipmentAdapter);
        securityEquipmentAdapter.updateListData(siteVisitModel.getSecurityEquipmentProvidedList());
        recyclerViewSecurityEquipmentProvidedByUs.setVisibility(View.GONE);
        if (visibility) {
            recyclerViewSecurityEquipmentProvidedByUs.setVisibility(View.VISIBLE);
        }
    }

    private void setData() {
        if (isEditable) {
            tvSubmit.setVisibility(View.GONE);
            siteVisitModel = getIntent().getParcelableExtra(Constants.EXTRA_DATA);
            if (siteVisitModel != null) {
                llQRView.setVisibility(View.GONE);
                ivScanQRCode.setEnabled(false);
                radioGroupLastNightCheck.setEnabled(false);
                radioGroupLastTrainingConducted.setEnabled(false);

                radioGroupAnyincIdentInPlaceSinceLastWeek.setEnabled(false);
                radioGroupSecurityEquipmentStatusProvidedByUs.setEnabled(false);

                radioButton1IsSitePostStanding.setEnabled(false);
                radioButton2IsSitePostStanding.setEnabled(false);//ok
                ((LinearLayout) findViewById(R.id.llQRParentView)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.llPhotoView)).setVisibility(View.GONE);


                radioButton1SecurityEquipmentStatusProvidedByUs.setEnabled(false);
                radioButton2SecurityEquipmentStatusProvidedByUs.setEnabled(false);


                if (siteVisitModel.getSecurityEquipmentProvidedList() != null && siteVisitModel.getSecurityEquipmentProvidedList().size() > 0) {
                    ((RadioButton) (findViewById(R.id.radioButton1SecurityEquipmentStatusProvidedByUs))).setChecked(true);
                    initSecurityEquipmentProvidedByUs(true, false);
                } else {
                    ((RadioButton) (findViewById(R.id.radioButton2SecurityEquipmentStatusProvidedByUs))).setChecked(true);

                }


                ((RadioButton) (findViewById(R.id.radioButton1AnySecurityHazardNoticed))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton2AnySecurityHazardNoticed))).setEnabled(false);


                if (siteVisitModel.getSecurityHazardList() != null && siteVisitModel.getSecurityHazardList().size() > 0) {
                    ((RadioButton) (findViewById(R.id.radioButton1AnySecurityHazardNoticed))).setChecked(true);
                    initSecurityHazard(true, false);

                } else {
                    ((RadioButton) (findViewById(R.id.radioButton2AnySecurityHazardNoticed))).setChecked(true);
                }

                tvInputLastTrainingConductedReason.setVisibility(View.GONE);
                if (!StringUtils.isNullOrEmpty(siteVisitModel.getLastTrainingSubjectCovered())) {
                    etLastTrainingConductedReason.setText(siteVisitModel.getLastTrainingSubjectCovered());
                    tvInputLastTrainingConductedReason.setVisibility(View.VISIBLE);
                }


                tvInputLastNightCheckReason.setVisibility(View.GONE);
                if (!StringUtils.isNullOrEmpty(siteVisitModel.getLastNightChecObservation())) {
                    etLastNightCheckReason.setText(siteVisitModel.getLastNightChecObservation());
                    tvInputLastNightCheckReason.setVisibility(View.VISIBLE);
                }


                ((RadioButton) (findViewById(R.id.radioButton1UniformAccessories))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton2UniformAccessories))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton3UniformAccessories))).setEnabled(false);
                if (!StringUtils.isNullOrEmpty(siteVisitModel.getUniformAccessory())) {
                    ((RadioButton) (findViewById(R.id.radioButton1UniformAccessories))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton2UniformAccessories))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton3UniformAccessories))).setChecked(false);
                    switch (siteVisitModel.getUniformAccessory()) {
                        case "Good":
                            ((RadioButton) (findViewById(R.id.radioButton1UniformAccessories))).setChecked(true);
                            break;
                        case "Average":
                            ((RadioButton) (findViewById(R.id.radioButton2UniformAccessories))).setChecked(true);
                            break;
                        case "Bad":
                            ((RadioButton) (findViewById(R.id.radioButton3UniformAccessories))).setChecked(true);
                            break;
                        default:
                            break;
                    }
                }

                ((RadioButton) (findViewById(R.id.radioButton1GuardGrooming))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton2GuardGrooming))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton3GuardGrooming))).setEnabled(false);
                if (!StringUtils.isNullOrEmpty(siteVisitModel.getGuardGrooming())) {
                    ((RadioButton) (findViewById(R.id.radioButton1GuardGrooming))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton2GuardGrooming))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton3GuardGrooming))).setChecked(false);
                    switch (siteVisitModel.getGuardGrooming()) {
                        case "Good":
                            ((RadioButton) (findViewById(R.id.radioButton1GuardGrooming))).setChecked(true);
                            break;
                        case "Average":
                            ((RadioButton) (findViewById(R.id.radioButton2GuardGrooming))).setChecked(true);
                            break;
                        case "Bad":
                            ((RadioButton) (findViewById(R.id.radioButton3GuardGrooming))).setChecked(true);
                            break;
                        default:
                            break;
                    }
                }

                ((RadioButton) (findViewById(R.id.radioButton1GuardUniform))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton2GuardUniform))).setEnabled(false);
                ((RadioButton) (findViewById(R.id.radioButton3GuardUniform))).setEnabled(false);
                if (!StringUtils.isNullOrEmpty(siteVisitModel.getGuardUniform())) {
                    ((RadioButton) (findViewById(R.id.radioButton1GuardUniform))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton2GuardUniform))).setChecked(false);
                    ((RadioButton) (findViewById(R.id.radioButton3GuardUniform))).setChecked(false);
                    switch (siteVisitModel.getGuardUniform()) {
                        case "Good":
                            ((RadioButton) (findViewById(R.id.radioButton1GuardUniform))).setChecked(true);
                            break;
                        case "Average":
                            ((RadioButton) (findViewById(R.id.radioButton2GuardUniform))).setChecked(true);
                            break;
                        case "Bad":
                            ((RadioButton) (findViewById(R.id.radioButton3GuardUniform))).setChecked(true);
                            break;
                        default:
                            break;
                    }
                }

                tvInputNonIssuanceOfSOPsReason.setVisibility(View.VISIBLE);
                if (siteVisitModel.isSOPIssued()) {
                    radioButton1IsSitePostStanding.setChecked(true);
                    tvInputNonIssuanceOfSOPsReason.setVisibility(View.GONE);
                } else {
                    radioButton2IsSitePostStanding.setChecked(true);
                }


                tvInputLastNightCheckDate.setVisibility(View.GONE);
                tvInputLastNightCheckReason.setVisibility(View.GONE);

                radioButton1LastNightCheck.setEnabled(false);
                radioButton2LastNightCheck.setEnabled(false);//ok

                if (siteVisitModel.isLastNightCheck()) {
                    radioButton1LastNightCheck.setChecked(true);
                    tvInputLastNightCheckDate.setVisibility(View.VISIBLE);
                    tvInputLastNightCheckReason.setVisibility(View.VISIBLE);
                } else {
                    radioButton2LastNightCheck.setChecked(true);
                }


                radioButton1LastTrainingConducted.setEnabled(false);
                radioButton2LastTrainingConducted.setEnabled(false);


                tvInputLastTrainingConductedDate.setVisibility(View.GONE);
                tvInputLastTrainingConductedReason.setVisibility(View.GONE);
                if (siteVisitModel.isLastTrainingConducted()) {
                    radioButton1LastTrainingConducted.setChecked(true);
                    tvInputLastTrainingConductedDate.setVisibility(View.VISIBLE);
                    tvInputLastTrainingConductedReason.setVisibility(View.VISIBLE);
                } else {
                    radioButton2LastTrainingConducted.setChecked(true);
                }

                radioButton1AnyincIdentInPlaceSinceLastWeek.setEnabled(false);
                radioButton2AnyincIdentInPlaceSinceLastWeek.setEnabled(false);

                if (siteVisitModel.isAnyIncidentInLastWeek()) {
                    radioButton1AnyincIdentInPlaceSinceLastWeek.setChecked(true);
                } else {
                    radioButton2AnyincIdentInPlaceSinceLastWeek.setChecked(true);

                }

                txtLastVisitDate.setText("Last Visit Date: " + siteVisitModel.getVisitDate());
                etSiteCode.setText(siteVisitModel.getSiteCode());
                etSiteCode.setEnabled(false);
                etClientName.setText(siteVisitModel.getClientName());
                etClientName.setEnabled(false);
                etDeploymentLocation.setText(siteVisitModel.getDeploymentLocation());
                etDeploymentLocation.setEnabled(false);
                etServiceType.setText(siteVisitModel.getServiceType());
                etServiceType.setEnabled(false);
                etServiceTypeOther.setText(siteVisitModel.getServiceTypeOther());
                etServiceTypeOther.setEnabled(false);
                etNoOfManpower.setText(siteVisitModel.getNumberOfManpower());
                etNoOfManpower.setEnabled(false);
                etShortage.setText(siteVisitModel.getShortage());
                etShortage.setEnabled(false);
                etReasonForNonIssuanceOfSOPs.setText(siteVisitModel.getSopReason());
                etReasonForNonIssuanceOfSOPs.setEnabled(false);
//                etUniformNAccessories.setText(siteVisitModel.getUniformAccessory());
//                etUniformNAccessories.setEnabled(false);
//                etGuardGrooming.setText(siteVisitModel.getGuardGrooming());
//                etGuardGrooming.setEnabled(false);
//                etGuardUniform.setText(siteVisitModel.getGuardUniform());
//                etGuardUniform.setEnabled(false);
                etGuardAwarenessOfDuty.setText(siteVisitModel.getGuardAwarenessOfDuty());
                etGuardAwarenessOfDuty.setEnabled(false);
                txtLastNightCheckDate.setText(siteVisitModel.getLastNightCheckDate());
                txtLastNightCheckDate.setEnabled(false);
//                etLastNightCheckReason.setText(siteVisitModel.getLastNightCheckReason());
                etLastNightCheckReason.setEnabled(false);

                etLastTrainingConductedDate.setText(siteVisitModel.getLastTrainingDate());
                etLastTrainingConductedDate.setEnabled(false);
//                etLastTrainingConductedReason.setText(siteVisitModel.getLastTrainingReason());
                etLastTrainingConductedReason.setEnabled(false);
                etLastInvoiceSubmitted.setText(siteVisitModel.getLastInvoice());
                etLastInvoiceSubmitted.setEnabled(false);

                etPaymentReceivedTillWhichMonth.setText(siteVisitModel.getPaymentReceivedMonth());
                etPaymentReceivedTillWhichMonth.setEnabled(false);
                etMetWhom.setText(siteVisitModel.getMetWhom());
                etMetWhom.setEnabled(false);
                etAnyClientConcern.setText(siteVisitModel.getAnyClientConcern());
                etAnyClientConcern.setEnabled(false);
//                etAnySecurityHazardNoticed.setText(siteVisitModel.getAnySecurityHazard());
//                etAnySecurityHazardNoticed.setEnabled(false);
                etAnyincIdentInPlaceSinceLastWeedDate.setText(siteVisitModel.getIncidentDate());
                etAnyincIdentInPlaceSinceLastWeedDate.setEnabled(false);
                etAnyincIdentInPlaceSinceLastWeekReason.setText(siteVisitModel.getIncidentReason());
                etAnyincIdentInPlaceSinceLastWeekReason.setEnabled(false);
                etSecurityEquipmentStatusProvidedByUsDate.setText(siteVisitModel.getSecurityDate());
                etSecurityEquipmentStatusProvidedByUsDate.setEnabled(false);
                etSecurityEquipmentStatusProvidedByUsReason.setText(siteVisitModel.getSecurityReason());
                etSecurityEquipmentStatusProvidedByUsReason.setEnabled(false);
                etRemark.setText(siteVisitModel.getRemark());
                etRemark.setEnabled(false);
                if (siteVisitModel.getImages() != null && siteVisitModel.getImages().size() > 0) {
                    ArrayList<MediaDetails> arrayListImages = new ArrayList<>();
                    ;
                    for (String imageurl : siteVisitModel.getImages()) {

                        MediaDetails mediaDetails = new MediaDetails();
                        mediaDetails.setImageUrl(imageurl);
                        arrayListImages.add(mediaDetails);


                    }
                    imageAdapter.updateListData(arrayListImages);
                }


            }
        }

    }

    public void setSingleChoice(final CharSequence[] options, final CustomEditText customEditText) {
        int selectedPosition = 0;
        try {
            selectedPosition = (int) customEditText.getTag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select item");
        builder.setSingleChoiceItems(options, selectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                customEditText.setText(options[i]);
                customEditText.setTag(i);
                tvInputOtherService.setVisibility(View.GONE);
                if (options[i].toString().equalsIgnoreCase("other")) {
                    tvInputOtherService.setVisibility(View.VISIBLE);
                }

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void datePicker(final CustomEditText customEditText) {
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
                        customEditText.setText(Utility.getDateFromSec(c.getTimeInMillis()));


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private boolean isFieldChecked() {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isNullOrEmpty(etClientName.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Client Name.");
            return false;
        }
        if (StringUtils.isNullOrEmpty(etDeploymentLocation.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Deployment Location.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etServiceType.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Service Type.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etNoOfManpower.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Number of Manpower.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etShortage.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Any Shortage.");
            return false;
        }

        if (!isSOPClicked) {
            ToastUtils.showToast(this, "Please enter Site Post Standing orders / SOP issued at site.");
            return false;
        }

        if (!isUniformAccessoryClicked) {
            ToastUtils.showToast(this, "Please enter Uniform and Accessory.");
            return false;
        }

        if (!isGuardGroomingClicked) {
            ToastUtils.showToast(this, "Please enter Guard Grooming.");
            return false;
        }


        if (!isGuardUniformClicked) {
            ToastUtils.showToast(this, "Please enter Guard Uniform.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etGuardAwarenessOfDuty.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Guard Awareness of Duty.");
            return false;
        }

        if (isLastNightCheckClicked) {
            if (siteVisitModel.isLastNightCheck()) {
                if (StringUtils.isNullOrEmpty(txtLastNightCheckDate.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter Last Night Check Date.");
                    return false;
                }
                if (StringUtils.isNullOrEmpty(etLastNightCheckReason.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter Last Night Observation.");
                    return false;
                }

            }
        } else {
            ToastUtils.showToast(this, "Please enter Last Night Check.");
            return false;
        }


        if (isLastNightTrainingClicked) {
            if (siteVisitModel.isLastTrainingConducted()) {
                if (StringUtils.isNullOrEmpty(etLastTrainingConductedDate.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter Date of Last Night Training Conducted.");
                    return false;
                }
                if (StringUtils.isNullOrEmpty(etLastTrainingConductedReason.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter Subject covered & No. of Staff Attended.");
                    return false;
                }
            }
        } else {
            ToastUtils.showToast(this, "Please enter Last Night Training Conducted.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etLastInvoiceSubmitted.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter last Invoice Submitted.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etPaymentReceivedTillWhichMonth.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Payment Received Month.");
            return false;
        }
        if (StringUtils.isNullOrEmpty(etMetWhom.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Meet Whom.");
            return false;
        }
        if (StringUtils.isNullOrEmpty(etAnyClientConcern.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Client Concern.");
            return false;
        }

        if (isSecurityHazardNoticedClicked) {
            if (siteVisitModel.isSecurityHazardStatus()) {
                if (securityHazardAdapter.getSelectedItemList() == null || securityHazardAdapter.getSelectedItemList().size() == 0) {
                    ToastUtils.showToast(this, "Please enter any Security Hazard Noticed.");
                    return false;
                }
            }
        } else {
            ToastUtils.showToast(this, "Please enter Security Hazard Noticed.");
            return false;
        }


        if (isAnyIncidentTookPlaceClicked) {
            if (siteVisitModel.isAnyIncidentInLastWeek()) {
                if (StringUtils.isNullOrEmpty(etAnyincIdentInPlaceSinceLastWeekReason.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter reason.");
                    return false;
                }
            }
        } else {
            ToastUtils.showToast(this, "Please enter Incident took in place since last week.");
            return false;
        }
        if (isSecurityEquipmentProvidedClicked) {
            if (siteVisitModel.isSecurityEquipmentProvided()) {
                if (securityEquipmentAdapter.getSelectedItemList() == null || securityEquipmentAdapter.getSelectedItemList().size() == 0) {
                    ToastUtils.showToast(this, "Please enter any Security Equipment Provided.");
                    return false;
                }
            }
        } else {
            ToastUtils.showToast(this, "Please enter Security Equipment Provided.");
            return false;
        }

        return true;
    }

    private void setDataToModel() {

        if (!isFieldChecked()) {
            tvSubmit.setEnabled(true);
            return;
        }
        try {

            siteVisitModel.setCreatedDate(Utility.getFormattedDateAndTime(System.currentTimeMillis()));
            siteVisitModel.setVisitDate(Utility.getDateForPayment(System.currentTimeMillis()));
            siteVisitModel.setLastVisitDate(Utility.getDateTransaction(System.currentTimeMillis() / 1000));
            siteVisitModel.setMobile(preferences.getPhone());
            siteVisitModel.setCountryCode(preferences.getCountryCode());

//        if (StringUtils.isNullOrEmpty(etSiteCode.getText().toString().trim())) {
//            ToastUtils.showToast(this, "Please enter Site Code.");
//            return;
//        }


            progressBar.setVisibility(View.VISIBLE);
            siteVisitModel.setSiteCode(etSiteCode.getText().toString().trim());
            siteVisitModel.setClientName(etClientName.getText().toString().trim());
            siteVisitModel.setDeploymentLocation(etDeploymentLocation.getText().toString().trim());
            siteVisitModel.setLatitude(CreateEventModel.getInstance(false).getLatitude());
            siteVisitModel.setLongitude(CreateEventModel.getInstance(false).getLongitude());
            siteVisitModel.setServiceType(etServiceType.getText().toString().trim());
            siteVisitModel.setServiceTypeOther(etServiceTypeOther.getText().toString().trim());
            siteVisitModel.setNumberOfManpower(etNoOfManpower.getText().toString().trim());
            siteVisitModel.setShortage(etShortage.getText().toString().trim());
            siteVisitModel.setSopReason(etReasonForNonIssuanceOfSOPs.getText().toString().trim());
//        siteVisitModel.setUniformAccessory(etUniformNAccessories.getText().toString().trim());
//        siteVisitModel.setGuardGrooming(etGuardGrooming.getText().toString().trim());
//        siteVisitModel.setGuardUniform(etGuardUniform.getText().toString().trim());
            siteVisitModel.setGuardAwarenessOfDuty(etGuardAwarenessOfDuty.getText().toString().trim());
            siteVisitModel.setLastNightCheckDate(txtLastNightCheckDate.getText().toString().trim());
//        siteVisitModel.setLastNightCheckReason(etLastNightCheckReason.getText().toString().trim());
            siteVisitModel.setLastNightChecObservation(etLastNightCheckReason.getText().toString().trim());
            siteVisitModel.setLastTrainingDate(etLastTrainingConductedDate.getText().toString().trim());
//        siteVisitModel.setLastTrainingReason(etLastTrainingConductedReason.getText().toString().trim());
            siteVisitModel.setLastTrainingSubjectCovered(etLastTrainingConductedReason.getText().toString().trim());
            siteVisitModel.setLastInvoice(etLastInvoiceSubmitted.getText().toString().trim());
            siteVisitModel.setPaymentReceivedMonth(etPaymentReceivedTillWhichMonth.getText().toString().trim());
            siteVisitModel.setMetWhom(etMetWhom.getText().toString().trim());
            siteVisitModel.setAnyClientConcern(etAnyClientConcern.getText().toString().trim());
            siteVisitModel.setSecurityHazardList(securityHazardAdapter.getSelectedItemList());
            siteVisitModel.setSecurityEquipmentProvidedList(securityEquipmentAdapter.getSelectedItemList());
//        siteVisitModel.setAnySecurityHazard(etAnySecurityHazardNoticed.getText().toString().trim());
            siteVisitModel.setIncidentDate(etAnyincIdentInPlaceSinceLastWeedDate.getText().toString().trim());
            siteVisitModel.setIncidentReason(etAnyincIdentInPlaceSinceLastWeekReason.getText().toString().trim());
            siteVisitModel.setSecurityDate(etSecurityEquipmentStatusProvidedByUsDate.getText().toString().trim());
            siteVisitModel.setSecurityReason(etSecurityEquipmentStatusProvidedByUsReason.getText().toString().trim());
            siteVisitModel.setRemark(etRemark.getText().toString().trim());
            siteVisitModel.setCountryCode(preferences.getCountry().countryCode);
            siteVisitModel.setMobile(preferences.getPhone());
            siteVisitModel.setName(preferences.getName());
            siteVisitModel.setEmpCode(preferences.getEmpCode());
            siteVisitModel.setUserId(preferences.getUserId());
            siteVisitModel.setClientLocation(clientLocation);
            siteVisitModel.setDesignation(preferences.getUserType());
            siteVisitModel.setBranchId(preferences.getBranchId());
            siteVisitModel.setBranchName(preferences.getBranchName());


//        saveDataToFirebase();
            getDownloadUrl();
        } catch (Exception e) {
            e.printStackTrace();
            tvSubmit.setEnabled(true);
        }
    }

    private void saveDataToFirebase() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        hitApiSaveIncidentReport();
//        db.collection("site_visit")
//                .add(siteVisitModel)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        String taskId = documentReference.getId();
//                        progressBar.setVisibility(View.GONE);
//                        showAlertDialogWithFinish(SiteObservationActivity.this, "", "Data Submitted Successfully.");
//                        finish();
//                    }
//                });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvSubmit:
                tvSubmit.setEnabled(false);
                setDataToModel();

                break;
            case R.id.etDeploymentLocation:
                Intent intent_location = new Intent(this, LocationFinderActivity.class);
                startActivityForResult(intent_location, Constants.Location.LOCATION);
                break;
            case R.id.etServiceType:
                CharSequence[] options = getResources().getStringArray(R.array.service_type);
                setSingleChoice(options, etServiceType);
                break;
            case R.id.txtLastNightCheckDate:
                datePicker(txtLastNightCheckDate);
                break;
            case R.id.etAnyincIdentInPlaceSinceLastWeedDate:
                datePicker(etAnyincIdentInPlaceSinceLastWeedDate);
                break;

            case R.id.etSecurityEquipmentStatusProvidedByUsDate:
                datePicker(etSecurityEquipmentStatusProvidedByUsDate);
                break;

            case R.id.etLastTrainingConductedDate:
                datePicker(etLastTrainingConductedDate);
                break;
            case R.id.llQRView:
            case R.id.ivScanQRCode:
                Intent intent = new Intent(this, ScanQRCodeActivity.class);
                startActivityForResult(intent, Constants.REQUEST_QR_CODE);
                break;

            case R.id.etClientName:
                intent = new Intent(SiteObservationActivity.this, ClientsListActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.ivCamera:
                checkPermission();
                break;
            case R.id.etPaymentReceivedTillWhichMonth:
                monthMenu(etPaymentReceivedTillWhichMonth);
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

        if (listImage != null && listImage.size() >= 4) {
            ToastUtils.showToast(this, "You can not upload more than 4 images.");
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


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        String qrResponse = CreateEventModel.getInstance(false).getQRDescription();
        if (!StringUtils.isNullOrEmpty(qrResponse)) {
            try {
                JSONObject jsonObject = new JSONObject(qrResponse);
                String id = jsonObject.optString("id");
                String address = jsonObject.optString("address");

                tvQRResponse.setText("ID: " + id + (!StringUtils.isNullOrEmpty(address) ? "\n" + "Address: " + address : ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void hitApiForFetchingClinetList(String qrData) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "Please check internet connection");
            return;
        }
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.GET_CLIENT_BY_QR_CODE + qrData, this, GET_CLIENT_BY_QR_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case Constants.REQUEST_QR_CODE:
                String qrData = data.getStringExtra(Constants.EXTRA_DATA);
                if (!StringUtils.isNullOrEmpty(qrData)) {
                    hitApiForFetchingClinetList(qrData);
                }
                break;
            case Constants.Location.LOCATION:
//                String location = data.getStringExtra(Constants.Params.NAME_LOCATION);
                CreateEventModel createEventModel = CreateEventModel.getInstance(false);
                if (!StringUtils.isNullOrEmpty(createEventModel.getLocation()))
                    etDeploymentLocation.setText(createEventModel.getLocation());

                break;

            case Constants.REQUEST_CODE:
                if (resultCode != RESULT_OK) return;
                ClientListModel.Data listSelected = data.getParcelableExtra(Constants.EXTRA_DATA);
                etClientName.setText(listSelected.getUnit_name());
                clientLocation = listSelected.getLocation();
//                etLocation.setText(listSelected.getLocation());
//                etLocation.setVisibility(View.VISIBLE);
//                etLocation.setEnabled(false);
//                frameClientName.setVisibility(View.VISIBLE);
                clientCode = listSelected.getUnit_code();
                etSiteCode.setText(clientCode);

                break;
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageAdapter.updateListData(listImage);
                                        tvLocationCapture.setText(Utility.getFormattedDateAndTime(System.currentTimeMillis())+",\n"+etDeploymentLocation.getText().toString().trim());

                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();


                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


        }
    }


    public void hitApiSaveIncidentReport() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestParams requestParams = new RequestParams();
            try {
                String jsonObject = gson.toJson(siteVisitModel);
                requestParams.put("data", new JSONObject(jsonObject));

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethod(Constants.SAVE_SITE_OBSERVATION, requestParams, this, SAVE_SITE_OBSERVATION);
        } catch (Exception e) {
            e.printStackTrace();
            tvSubmit.setEnabled(true);
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        tvSubmit.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        if (responseCode == Constants.RequestType.SEND_OTP) {
            UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
            if (otpResponseModel != null) {
//                if (otpResponseModel.isStatus()) {
//                    if (otpResponseModel.getData() != null && otpResponseModel.getData().size() > 0) {
//                        preferences.setName(otpResponseModel.getData().get(0).getUserName());
//                        preferences.setUserType(otpResponseModel.getData().get(0).getDesignation());
//                        preferences.setEmail(otpResponseModel.getData().get(0).getEmail());
//                        preferences.setEmpCode(otpResponseModel.getData().get(0).getEmpCode());
//                        preferences.setUserId(otpResponseModel.getData().get(0).getUserId());
//                        preferences.setTeamLead(otpResponseModel.getData().get(0).getTeamlead());
//                        preferences.setTeamLeadID(otpResponseModel.getData().get(0).getTeam_lead_id());
//                        preferences.setDepartment(otpResponseModel.getData().get(0).getDepartment());
//                        preferences.setBranchId(otpResponseModel.getData().get(0).getBranchId());
//                        preferences.setBranchName(otpResponseModel.getData().get(0).getBranchName());
//
//                    } else {
//                        showAlertDialog(this, "", otpResponseModel.getMsg());
//                    }
//                } else {
//                    showAlertDialogForNotRegistered(this, "", otpResponseModel.getMsg());
//                }
            }


        } else if (responseCode == SAVE_SITE_OBSERVATION) {
            UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
//            showAlertDialogWithFinish(SiteObservationActivity.this, "", otpResponseModel.getMsg());
        }else if(responseCode==GET_CLIENT_BY_QR_CODE){
            try {
                ClientDataForPOModel clientDataForPOModel = gson.fromJson(response, ClientDataForPOModel.class);
                if (clientDataForPOModel.getData() != null && clientDataForPOModel.getData().size() > 0) {
                    etClientName.setText(clientDataForPOModel.getData().get(0).getClientName());
                    etSiteCode.setText(clientDataForPOModel.getData().get(0).getClientCode());

                    clientCode = clientDataForPOModel.getData().get(0).getClientCode();
//                frameClientName.setVisibility(View.VISIBLE);
                } else {
//                frameClientName.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void monthMenu(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.months_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(SiteObservationActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                siteVisitModel.setPaymentReceivedMonth(item.getTitle().toString());
                etPaymentReceivedTillWhichMonth.setText(item.getTitle().toString());
                return true;
            }
        });

        popup.show();//showing popup menu
    }
}


