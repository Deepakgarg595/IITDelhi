package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.CorrectionActionAdapter;
import com.app.iitdelhicampus.adapter.CourseOfActionAdapter;
import com.app.iitdelhicampus.adapter.ImageAdapter;
import com.app.iitdelhicampus.adapter.IncidentTypeAdapter;
import com.app.iitdelhicampus.adapter.MultiSelectionAdapter;
import com.app.iitdelhicampus.adapter.MultiSelectionAdapterForPolice;
import com.app.iitdelhicampus.adapter.ObservationAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.ClientDataForPOModel;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.model.Country;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.LocalIncidentReportedModel;
import com.app.iitdelhicampus.model.LossItemModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.model.ReportIncidentModel;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.Datastatic;
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
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PERMISSION;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;
import static com.app.iitdelhicampus.constants.Constants.RequestType.GET_CLIENT_BY_QR_CODE;
import static com.app.iitdelhicampus.constants.Constants.RequestType.SAVE_INCIDENT_REPORT;


public class ReportIncidentActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {

    private static final int REQUEST_SELECT_COUNTRY = 12030;
    private final ArrayList<MediaDetails> listImage = new ArrayList<>();
    //    private final ArrayList<MediaDetails> listImageUpload = new ArrayList<>();
    Gson gson;
    boolean lossType;
    private IncidentTypeAdapter singleSelectionAdapter = new IncidentTypeAdapter(this);
    private ImageView iv_back;
    private TextView btnVerifyNumber;
    private Country country;
    private ProgressBar pbar;
    private AppPreferences preferences;
    private ProgressDialog progressDialog;
    private CustomEditText tvDateOfIncident, tvTimeOfIncident, tvTimeOfIncidentReporting;
    private Dialog dialog;
    private CustomEditText txtIncidentReported, txtLossOfAsset, txtAnyMedicalEmergencies;
    private RadioGroup radioGroupLoosType, radioGroupIncidentReported, radioGroupAnyMedicalEmergencies;
    private TextInputLayout tvInputAnyMedicalEmergencies;
    private TextInputLayout tvInputIncidentReported;
    private TextInputLayout tvInputLossOfAsset;
    //    private CustomEditText etCourseOfAction;
//    private CustomEditText tvObservations;
//    private CustomEditText etCorrectiveActions;
    private CustomEditText etFirNumber;
    private CustomEditText etFirStatus;
    private LinearLayout llFirVIEW;
    private CustomEditText txtTypeOfIncident;
    private CustomEditText txtSeverity;
    private CustomEditText etPlaceOfIncident;
    private CustomEditText etOfficerReport;
    private CustomEditText etNumberOfPersonInvolved;
    private CustomEditText etNumberOfSecurityPersonnelInvolved;
    private CustomEditText etNameOfSecurityAndFacilityPersonnel;
    private LinearLayout llIncidentView;
    private ArrayList<CommonDropdownModel> listData = new ArrayList<>();
    private ImageView ivCamera;
    private ImageView ivScanQRCode;
    private CustomEditText txtLossOfEquipment;
    private CustomEditText txtLossOfProperty;
    private CustomEditText txtLossOfLife;
    private TextInputLayout tvInputLossOfEquipment;
    private TextInputLayout tvInputLossOfProperty;
    private TextInputLayout tvInputLossOfLife;
    private LinearLayout llLossType;
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private ReportIncidentModel reportIncidentModel = new ReportIncidentModel();
    private CourseOfActionAdapter courseOfActionAdapter;
    private ObservationAdapter observationAdapter;
    private CorrectionActionAdapter correctionActionAdapter;
    private CustomEditText etMedicalEmergencyDescription;
    private CustomEditText etMedicalEmergencyTime;
    private TextInputLayout tvInputMedicalEmergencyTime;
    private TextInputLayout tvInputMedicalEmergencyDescription;
    private boolean isPoliceReported;
    private boolean anyMedicalEMergency;
    private CustomTextView tvQRResponse;
    private int MY_CAMERA_REQUEST_CODE = 101;
    private StorageReference storageReference;
    private ArrayList<String> jsonArrayImage = new ArrayList<>();
    private boolean enableDialog = true;
    private boolean enableGDPoliceDialog = true;

    private boolean needEdit;
    private CustomEditText etGDRecordedPolice;
    private TextInputLayout tvInputGDRecordedPolice;
    private TextInputLayout tvInputFirstInvestigationOfficer;
    private CustomEditText etInputFirstInvestigationOfficer;
    private LinearLayout llQrView;
    private TextInputLayout inputClientName;
    private CustomEditText txtClientName;
    private String clientLocation;
    private String clientCode;
    private RadioButton radioButton1LoosType;
    private RadioButton radioButton2LoosType;
    private RadioButton radioButton2IncidentReported;
    private CustomTextView tvLocationCapture;

    public static boolean isValidUsername(String name) {

        // Regex to check valid username.
        String regex = "^[A-Za-z]\\w{0,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the username is empty
        // return false
        if (name == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(name);

        // Return if the username
        // matched the ReGex
        return m.matches();
    }
    String reportedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        CreateEventModel.getInstance(false).setQRDescription("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        reportedDate = sdf.format(new Date());
        TimeZone timeZone=TimeZone.getTimeZone("IST");
        sdf.setTimeZone(timeZone);

        tvLocationCapture=(CustomTextView)findViewById(R.id.tvLocationCapture);


        gson = new Gson();
        btnVerifyNumber = (TextView) findViewById(R.id.btnVerifyNumber);
        btnVerifyNumber.setOnClickListener(this);
        preferences = AppPreferences.getInstance(this);
        iv_back.setOnClickListener(this);
        Datastatic.getInstance().loadIncidentData();
        listData = Datastatic.getInstance().getListIncidentType();
        llFirVIEW = (LinearLayout) findViewById(R.id.llFirVIEW);
        llFirVIEW.setVisibility(View.GONE);
        llIncidentView = (LinearLayout) findViewById(R.id.llIncidentView);
        llIncidentView.setVisibility(View.GONE);
        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerViewImage);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImage.setLayoutManager(mLayoutManager);

        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.smoothScrollToPosition(0);


        radioButton1LoosType = findViewById(R.id.radioButton1LoosType);
        radioButton2LoosType = findViewById(R.id.radioButton2LoosType);


        radioButton2IncidentReported = findViewById(R.id.radioButton2IncidentReported);


        llLossType = (LinearLayout) findViewById(R.id.llLossType);
        llQrView = (LinearLayout) findViewById(R.id.llQrView);
        //todo uncomment it llQrView.setVisibility(View.GONE);
        llQrView.setOnClickListener(this);

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);
        ivScanQRCode = (ImageView) findViewById(R.id.ivScanQRCode);
        ivScanQRCode.setOnClickListener(this);

        tvDateOfIncident = (CustomEditText) findViewById(R.id.tvDateOfIncident);
        tvDateOfIncident.setOnClickListener(this);

        tvTimeOfIncident = (CustomEditText) findViewById(R.id.tvTimeOfIncident);
        tvTimeOfIncident.setOnClickListener(this);
        txtClientName = (CustomEditText) findViewById(R.id.txtClientName);
        txtClientName.setOnClickListener(this);


        tvTimeOfIncidentReporting = (CustomEditText) findViewById(R.id.tvTimeOfIncidentReporting);
        tvTimeOfIncidentReporting.setText(Utility.getEventTime(System.currentTimeMillis()));

        tvTimeOfIncidentReporting.setEnabled(false);


        txtIncidentReported = (CustomEditText) findViewById(R.id.txtIncidentReported);
        txtIncidentReported.setOnClickListener(this);

        etInputFirstInvestigationOfficer = (CustomEditText) findViewById(R.id.etInputFirstInvestigationOfficer);


        tvInputLossOfAsset = (TextInputLayout) findViewById(R.id.tvInputLossOffAsset);
        tvInputLossOfAsset.setVisibility(View.GONE);
        tvInputLossOfEquipment = (TextInputLayout) findViewById(R.id.tvInputLossOfEquipment);
        tvInputLossOfEquipment.setVisibility(View.GONE);
        tvInputLossOfProperty = (TextInputLayout) findViewById(R.id.tvInputLossOfProperty);
        tvInputLossOfProperty.setVisibility(View.GONE);
        tvInputLossOfLife = (TextInputLayout) findViewById(R.id.tvInputLossOfLife);
        tvInputLossOfLife.setVisibility(View.GONE);

        tvInputFirstInvestigationOfficer = (TextInputLayout) findViewById(R.id.tvInputFirstInvestigationOfficer);
        tvInputFirstInvestigationOfficer.setVisibility(View.GONE);

        inputClientName = (TextInputLayout) findViewById(R.id.inputClientName);
        inputClientName.setVisibility(View.VISIBLE);


        tvInputGDRecordedPolice = (TextInputLayout) findViewById(R.id.tvInputGDRecordedPolice);
        tvInputGDRecordedPolice.setVisibility(View.GONE);


        txtLossOfAsset = (CustomEditText) findViewById(R.id.txtLossOfAsset);
        txtLossOfEquipment = (CustomEditText) findViewById(R.id.txtLossOfEquipment);
        txtLossOfProperty = (CustomEditText) findViewById(R.id.txtLossOfProperty);
        txtLossOfLife = (CustomEditText) findViewById(R.id.txtLossOfLife);
        hideSoftKeypad(txtLossOfAsset);
        etMedicalEmergencyDescription = (CustomEditText) findViewById(R.id.etMedicalEmergencyDescription);
        etMedicalEmergencyTime = (CustomEditText) findViewById(R.id.etMedicalEmergencyTime);
        etMedicalEmergencyTime.setOnClickListener(this);

        tvInputMedicalEmergencyDescription = (TextInputLayout) findViewById(R.id.tvInputMedicalEmergencyDescription);
        tvInputMedicalEmergencyDescription.setVisibility(View.GONE);

        tvInputMedicalEmergencyTime = (TextInputLayout) findViewById(R.id.tvInputMedicalEmergencyTime);
        tvInputMedicalEmergencyTime.setVisibility(View.GONE);


        txtAnyMedicalEmergencies = (CustomEditText) findViewById(R.id.txtAnyMedicalEmergencies);
        txtAnyMedicalEmergencies.setOnClickListener(this);

        txtTypeOfIncident = (CustomEditText) findViewById(R.id.txtTypeOfIncident);
        txtTypeOfIncident.setOnClickListener(this);

        etPlaceOfIncident = (CustomEditText) findViewById(R.id.etPlaceOfIncident);
        etPlaceOfIncident.setOnClickListener(this);

        tvQRResponse = (CustomTextView) findViewById(R.id.tvQRResponse);


        etOfficerReport = (CustomEditText) findViewById(R.id.etOfficerReport);
        etGDRecordedPolice = (CustomEditText) findViewById(R.id.etGDRecordedPolice);


        etNumberOfPersonInvolved = (CustomEditText) findViewById(R.id.etNumberOfPersonInvolved);

        etNumberOfSecurityPersonnelInvolved = (CustomEditText) findViewById(R.id.etNumberOfSecurityPersonnelInvolved);


        etNameOfSecurityAndFacilityPersonnel = (CustomEditText) findViewById(R.id.etNameOfSecurityAndFacilityPersonnel);


        txtSeverity = (CustomEditText) findViewById(R.id.txtSeverity);

//        etCourseOfAction = (CustomEditText) findViewById(R.id.etCourseOfAction);

//        tvObservations = (CustomEditText) findViewById(R.id.tvObservations);

//        etCorrectiveActions = (CustomEditText) findViewById(R.id.etCorrectiveActions);

        etFirNumber = (CustomEditText) findViewById(R.id.etFirNumber);

        etFirStatus = (CustomEditText) findViewById(R.id.etFirStatus);


        tvInputAnyMedicalEmergencies = (TextInputLayout) findViewById(R.id.tvInputAnyMedicalEmergencies);
        tvInputAnyMedicalEmergencies.setVisibility(View.GONE);


        tvInputIncidentReported = (TextInputLayout) findViewById(R.id.tvInputIncidentReported);
        tvInputIncidentReported.setVisibility(View.GONE);

        needEdit = getIntent().getBooleanExtra(Constants.NEED_TO_EDIT, false);
        if (needEdit) {
            enableDialog = false;
            enableGDPoliceDialog = false;
            btnVerifyNumber.setVisibility(View.GONE);
            btnVerifyNumber.setBackgroundResource(R.drawable.background_rounded_bg_gray);
        }
        radioGroupLoosType = (RadioGroup) findViewById(R.id.radioGroupLoosType);
        radioGroupLoosType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupLoosType.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                lossType = true;

                if (radioSexButton.getText().toString().equalsIgnoreCase("yes")) {
                    reportIncidentModel.setLossStatus(true);
                    ArrayList<CommonDropdownModel> list = new ArrayList<>();
                    String[] options = getResources().getStringArray(R.array.loss_off_asset);
                    for (String name : options) {
                        CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
                        commonDropdownModel.setName(name);
                        list.add(commonDropdownModel);

                    }
                    if (enableDialog) {
                        initMultiSelectionSimpleDialog(list);
                    }
                    llLossType.setVisibility(View.VISIBLE);
                } else {
                    enableDialog = true;
                    reportIncidentModel.setLossStatus(false);
                    llLossType.setVisibility(View.GONE);
                }
            }
        });


        tvInputGDRecordedPolice.setVisibility(View.GONE);
        tvInputFirstInvestigationOfficer.setVisibility(View.GONE);
        llFirVIEW.setVisibility(View.GONE);

        radioGroupIncidentReported = (RadioGroup) findViewById(R.id.radioGroupIncidentReported);
        radioGroupIncidentReported.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroupIncidentReported.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                isPoliceReported = true;
                if (radioSexButton.getText().toString().equalsIgnoreCase("yes")) {
                    reportIncidentModel.setIncidentReported(true);
//                    String[] options = getResources().getStringArray(R.array.inciden_reported);
//                    setSingleChoice(options, txtIncidentReported);
                    llIncidentView.setVisibility(View.VISIBLE);
//                    tvInputIncidentReported.setVisibility(View.VISIBLE);
                    tvInputIncidentReported.setVisibility(View.GONE);
//                    tvInputGDRecordedPolice.setVisibility(View.VISIBLE);
//                    tvInputFirstInvestigationOfficer.setVisibility(View.VISIBLE);
//                    llFirVIEW.setVisibility(View.VISIBLE);


                    ArrayList<CommonDropdownModel> list = new ArrayList<>();
                    String[] options = getResources().getStringArray(R.array.inciden_reported);
                    for (String name : options) {
                        CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
                        commonDropdownModel.setName(name);
                        list.add(commonDropdownModel);

                    }
                    if (!needEdit) {
                        initMultiSelectionDialogForPolice(list);
                    }


                } else {
                    enableGDPoliceDialog = true;
                    llIncidentView.setVisibility(View.GONE);
                    reportIncidentModel.setIncidentReported(false);
                    llFirVIEW.setVisibility(View.GONE);
                    tvInputIncidentReported.setVisibility(View.GONE);
                    tvInputGDRecordedPolice.setVisibility(View.GONE);
                    tvInputFirstInvestigationOfficer.setVisibility(View.GONE);
                }
            }
        });

        radioGroupAnyMedicalEmergencies = (RadioGroup) findViewById(R.id.radioGroupAnyMedicalEmergencies);
        radioGroupAnyMedicalEmergencies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!anyMedicalEMergency) {
                    initObservation(true);
                    initCorrectiveAction(true);
                    initCourseOfAction(true);
                }
                int selectedId = radioGroupAnyMedicalEmergencies.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                anyMedicalEMergency = true;
//                initCourseOfAction();
//                initObservation();
//                initCorrectiveAction();


                if (radioSexButton.getText().toString().equalsIgnoreCase("yes")) {
                    reportIncidentModel.setAnyMedicalEmergency(true);
//                    String[] options = getResources().getStringArray(R.array.any_medical_agenecy);
//                    setSingleChoice(options, txtAnyMedicalEmergencies);
                    tvInputAnyMedicalEmergencies.setVisibility(View.VISIBLE);
                    tvInputMedicalEmergencyDescription.setVisibility(View.VISIBLE);
                } else {
                    tvInputAnyMedicalEmergencies.setVisibility(View.GONE);
                    reportIncidentModel.setAnyMedicalEmergency(false);
                    tvInputMedicalEmergencyDescription.setVisibility(View.GONE);
                }
            }
        });


//        etNameOfSecurityAndFacilityPersonnel.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!StringUtils.isNullOrEmpty(charSequence.toString()))
//                if (!isValidUsername(charSequence.toString())) {
//                    ToastUtils.showToast(ReportIncidentActivity.this, "Please enter valid name.");
//                    etNameOfSecurityAndFacilityPersonnel.getText().clear();
//                    return;
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        initMultiSelectionDialog();

        if (needEdit) {
            try {
                pbar.setVisibility(View.GONE);
                setPreFilledData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.CHECK_USER + preferences.getPhone(), this, Constants.RequestType.SEND_OTP);

    }

    private void setPreFilledData() {
        reportIncidentModel = getIntent().getParcelableExtra(Constants.EXTRA_DATA);
        if (reportIncidentModel == null) return;

        llQrView.setVisibility(View.GONE);
        ivCamera.setVisibility(View.GONE);
        txtTypeOfIncident.setEnabled(false);
//        txtTypeOfIncident.setText("In progress");
//        txtSeverity.setText("Severity In progress");
        txtClientName.setEnabled(false);
        txtClientName.setText(reportIncidentModel.getClientName());
        tvDateOfIncident.setText(reportIncidentModel.getDateOfIncident());
        tvDateOfIncident.setEnabled(false);
        tvTimeOfIncident.setText(reportIncidentModel.getTimeOfIncident());
        tvTimeOfIncident.setEnabled(false);
        etPlaceOfIncident.setText(reportIncidentModel.getPlaceOfIncident());
        etPlaceOfIncident.setEnabled(false);


        etOfficerReport.setEnabled(false);
        etNumberOfPersonInvolved.setEnabled(false);
        etNumberOfSecurityPersonnelInvolved.setEnabled(false);
        etNameOfSecurityAndFacilityPersonnel.setEnabled(false);
        txtLossOfAsset.setEnabled(false);
        txtLossOfEquipment.setEnabled(false);
        txtLossOfProperty.setEnabled(false);
        txtLossOfLife.setEnabled(false);
        txtIncidentReported.setEnabled(false);
        etGDRecordedPolice.setEnabled(false);
        etInputFirstInvestigationOfficer.setEnabled(false);


        etFirNumber.setEnabled(false);
        etMedicalEmergencyDescription.setEnabled(false);


//        tvTimeOfIncidentReporting.setText(reportIncidentModel.getTimeOfReporting());
        etOfficerReport.setText(reportIncidentModel.getOfficerReportedAtSite());
        etOfficerReport.setEnabled(false);
        if (!StringUtils.isNullOrEmpty(reportIncidentModel.getNumberOfPersonInvolved()))
            etNumberOfPersonInvolved.setText(reportIncidentModel.getNumberOfPersonInvolved());
        etNumberOfPersonInvolved.setEnabled(false);
        if (!StringUtils.isNullOrEmpty(reportIncidentModel.getNumberOfSecurityPersonInvolved()))
            etNumberOfSecurityPersonnelInvolved.setText(reportIncidentModel.getNumberOfSecurityPersonInvolved());
        etNumberOfSecurityPersonnelInvolved.setEnabled(false);
        etNameOfSecurityAndFacilityPersonnel.setText(reportIncidentModel.getNameOfSecurityPersonInvolved());
        etNameOfSecurityAndFacilityPersonnel.setEnabled(false);

        radioButton1LoosType.setEnabled(false);

        radioButton2LoosType.setEnabled(false);
        if (reportIncidentModel.isLossStatus()) {
            radioButton1LoosType.setChecked(true);
            llLossType.setVisibility(View.VISIBLE);
            if (reportIncidentModel.getLossItemList() != null)
                for (int i = 0; i < reportIncidentModel.getLossItemList().size(); i++) {
                    switch (i) {
                        case 0:
                            if (!StringUtils.isNullOrEmpty(reportIncidentModel.getLossItemList().get(i).getLossItem())) {
                                txtLossOfAsset.setText(reportIncidentModel.getLossItemList().get(i).getLossItem());
                                txtLossOfAsset.setEnabled(false);
                                tvInputLossOfAsset.setVisibility(View.VISIBLE);
                                tvInputLossOfAsset.setEnabled(false);
                            }
                            break;
                        case 1:
                            if (!StringUtils.isNullOrEmpty(reportIncidentModel.getLossItemList().get(i).getLossItem())) {
                                txtLossOfEquipment.setText(reportIncidentModel.getLossItemList().get(i).getLossItem());
                                txtLossOfEquipment.setEnabled(false);
                                tvInputLossOfEquipment.setVisibility(View.VISIBLE);
                                tvInputLossOfEquipment.setEnabled(false);
                            }

                            break;
                        case 2:
                            if (!StringUtils.isNullOrEmpty(reportIncidentModel.getLossItemList().get(i).getLossItem())) {
                                txtLossOfProperty.setText(reportIncidentModel.getLossItemList().get(i).getLossItem());
                                txtLossOfProperty.setEnabled(false);
                                tvInputLossOfProperty.setVisibility(View.VISIBLE);
                                tvInputLossOfProperty.setEnabled(false);
                            }

                            break;
                        case 3:
                            if (!StringUtils.isNullOrEmpty(reportIncidentModel.getLossItemList().get(i).getLossItem())) {
                                txtLossOfLife.setText(reportIncidentModel.getLossItemList().get(i).getLossItem());
                                txtLossOfLife.setEnabled(false);
                                tvInputLossOfLife.setVisibility(View.VISIBLE);
                                tvInputLossOfLife.setEnabled(false);
                            }

                            break;

                    }
                }
        } else {
            radioButton2LoosType.setChecked(true);
            llLossType.setVisibility(View.GONE);
        }


        RadioButton radioButton1IncidentReported = findViewById(R.id.radioButton1IncidentReported);
        radioButton1IncidentReported.setEnabled(false);
        radioButton2IncidentReported.setEnabled(false);
        if (reportIncidentModel.isIncidentReported()) {
            radioButton1IncidentReported.setChecked(true);
            tvInputGDRecordedPolice.setVisibility(View.GONE);
            tvInputFirstInvestigationOfficer.setVisibility(View.GONE);
            llFirVIEW.setVisibility(View.GONE);
            if (reportIncidentModel.getPoliceReportList() != null && reportIncidentModel.getPoliceReportList().size() > 0) {
                for (int i = 0; i < reportIncidentModel.getPoliceReportList().size(); i++) {
                    switch (i) {
                        case 0:
                            tvInputGDRecordedPolice.setVisibility(View.VISIBLE);
                            tvInputGDRecordedPolice.setEnabled(false);
                            etGDRecordedPolice.setText(reportIncidentModel.getPoliceReportList().get(i).getReportedDescription());
                            etGDRecordedPolice.setEnabled(false);
                            break;
                        case 1:
                            tvInputFirstInvestigationOfficer.setVisibility(View.VISIBLE);
                            tvInputFirstInvestigationOfficer.setEnabled(false);
                            etInputFirstInvestigationOfficer.setText(reportIncidentModel.getPoliceReportList().get(i).getReportedDescription());
                            etInputFirstInvestigationOfficer.setEnabled(false);
                            break;
                        case 2:
                            llFirVIEW.setVisibility(View.VISIBLE);
                            etFirNumber.setText(reportIncidentModel.getPoliceReportList().get(i).getFirNumber());
                            etFirNumber.setEnabled(false);
                            etFirStatus.setText(reportIncidentModel.getPoliceReportList().get(i).getFirStatus());
                            etFirStatus.setEnabled(false);
                            break;
                        default:
                            break;
                    }
                }
            }


//            if (reportIncidentModel.getPoliceReport() != null) {
//                txtIncidentReported.setText(reportIncidentModel.getPoliceReport().getReportedTitle());
//                if (reportIncidentModel.getPoliceReport().getReportedTitle().equalsIgnoreCase("FIR lodged at Local Police Station")) {
//                    llFirVIEW.setVisibility(View.VISIBLE);
//                    etFirNumber.setText(reportIncidentModel.getPoliceReport().getFirNumber());
//                    etFirStatus.setText(reportIncidentModel.getPoliceReport().getFirStatus());
//                    tvInputGDRecordedPolice.setVisibility(View.GONE);
//                } else {
//                    llFirVIEW.setVisibility(View.GONE);
//                    tvInputGDRecordedPolice.setVisibility(View.VISIBLE);
//                }
//            }
        } else {
            radioButton2IncidentReported.setChecked(true);
        }


        RadioButton radioButton1AnyMedicalEmergencies = findViewById(R.id.radioButton1AnyMedicalEmergencies);
        radioButton1AnyMedicalEmergencies.setEnabled(false);
        RadioButton radioButton2AnyMedicalEmergencies = findViewById(R.id.radioButton2AnyMedicalEmergencies);
        radioButton2AnyMedicalEmergencies.setEnabled(false);
        if (reportIncidentModel.isAnyMedicalEmergency()) {
            radioButton1AnyMedicalEmergencies.setChecked(true);
            tvInputAnyMedicalEmergencies.setVisibility(View.VISIBLE);
            tvInputAnyMedicalEmergencies.setEnabled(false);
            txtAnyMedicalEmergencies.setText(reportIncidentModel.getMedicalEmergencyData());
            txtAnyMedicalEmergencies.setEnabled(false);
            if (reportIncidentModel.getMedicalDescription().equalsIgnoreCase("Time of Evacuation to sick bay")) {
                etMedicalEmergencyTime.setText(reportIncidentModel.getMedicalEvacuationTime());
                etMedicalEmergencyTime.setEnabled(false);
                tvInputMedicalEmergencyTime.setVisibility(View.VISIBLE);
                tvInputMedicalEmergencyTime.setEnabled(false);
                tvInputMedicalEmergencyDescription.setVisibility(View.GONE);

            } else {
                tvInputMedicalEmergencyDescription.setVisibility(View.VISIBLE);
                tvInputMedicalEmergencyDescription.setEnabled(false);
                etMedicalEmergencyDescription.setText(reportIncidentModel.getMedicalDescription());
                etMedicalEmergencyDescription.setEnabled(false);
                tvInputMedicalEmergencyTime.setVisibility(View.GONE);

            }
        } else {
            radioButton2AnyMedicalEmergencies.setChecked(true);
            radioButton2AnyMedicalEmergencies.setEnabled(false);
            tvInputAnyMedicalEmergencies.setVisibility(View.GONE);
            tvInputMedicalEmergencyTime.setVisibility(View.GONE);
            tvInputMedicalEmergencyDescription.setVisibility(View.GONE);
        }


        if (reportIncidentModel.getCourseOfAction() != null && reportIncidentModel.getCourseOfAction().size() > 0) {
            initCourseOfAction(false);
            courseOfActionAdapter.updateListData(reportIncidentModel.getCourseOfAction());

        }


        if (reportIncidentModel.getObservation() != null && reportIncidentModel.getObservation().size() > 0) {
            initObservation(false);
            observationAdapter.updateListData(reportIncidentModel.getObservation());

        }


        if (reportIncidentModel.getCorrectionAction() != null && reportIncidentModel.getCorrectionAction().size() > 0) {
            initCorrectiveAction(false);
            correctionActionAdapter.updateListData(reportIncidentModel.getCorrectionAction());

        }
        setIncidentTypeAndSeverityData();
        if (reportIncidentModel.getImages() != null)
            for (int i = 0; i < reportIncidentModel.getImages().size(); i++) {
                MediaDetails eventMediaModel = new MediaDetails();
                eventMediaModel.setImageUrl(reportIncidentModel.getImages().get(i));
                eventMediaModel.setVideo(false);
                eventMediaModel.setType(Constants.MediaType.PIC);
                eventMediaModel.setSelected(true);
                listImage.add(eventMediaModel);
            }
        imageAdapter.updateListData(listImage);

    }

    public String getSelectedItemString(ArrayList<CommonDropdownModel> details) {
        StringBuilder skills = new StringBuilder();
        if (details != null)
            for (CommonDropdownModel skillModel : details) {
                if (skillModel.isSelected()) {
                    skills.append(", ").append(skillModel.getName());
                }
            }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        return skills.toString().trim();
    }

    private void setIncidentTypeAndSeverityData() {

        txtTypeOfIncident.setText(getSelectedItemString(reportIncidentModel.getTypeOfIncident()));

        for (int i = 0; i < reportIncidentModel.getTypeOfIncident().size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(j).getName().equalsIgnoreCase(reportIncidentModel.getTypeOfIncident().get(i).getName())) {
                    listData.get(j).setSelected(true);
                }
            }

        }


        int countHigh = 0;
        int countModerate = 0;
        int countLow = 0;
        for (CommonDropdownModel commonDropdownModel : reportIncidentModel.getTypeOfIncident()) {


            switch (commonDropdownModel.getSeverity()) {
                case "High":
                    countHigh = countHigh + 1;
                    break;
                case "Moderate":
                    countModerate = countModerate + 1;
                    break;
                case "Low":
                    countLow = countLow + 1;
                    break;
            }
        }
        StringBuilder senstivity = new StringBuilder();

        if (countHigh != 0) {
            String strHigh = "<font color='#ec3338'>High(" + countHigh + ")</font>";
            senstivity.append(strHigh);
            if (countModerate > 0)
                senstivity.append(", ");
        }
        if (countModerate != 0) {
            String strModerate = "<font color='#f1af25'>Moderate(" + countModerate + ")</font>";
            senstivity.append(strModerate);
            if (countLow > 0)
                senstivity.append(", ");
        }
        if (countLow != 0) {
            String strLow = "<font color='#7500ad14'>Low(" + countLow + ")</font>";
            senstivity.append(strLow);
        }

        txtSeverity.setText(Html.fromHtml(senstivity.toString()));
    }

    private void initCourseOfAction(boolean textEnabled) {
        RecyclerView recyclerViewCourseOfAction = (RecyclerView) findViewById(R.id.recyclerViewCourseOfAction);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewCourseOfAction.setLayoutManager(mLayoutManager);
        courseOfActionAdapter = new CourseOfActionAdapter(this, textEnabled);
        recyclerViewCourseOfAction.setAdapter(courseOfActionAdapter);
        courseOfActionAdapter.updateListData(reportIncidentModel.getCourseOfAction());


    }

    private void initObservation(boolean textEnabled) {
        RecyclerView recyclerViewCourseOfAction = (RecyclerView) findViewById(R.id.recyclerViewObservation);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewCourseOfAction.setLayoutManager(mLayoutManager);
        observationAdapter = new ObservationAdapter(this, textEnabled);
        recyclerViewCourseOfAction.setAdapter(observationAdapter);
        observationAdapter.updateListData(reportIncidentModel.getObservation());


    }

    private void initCorrectiveAction(boolean textEnabled) {
        RecyclerView recyclerViewCourseOfAction = (RecyclerView) findViewById(R.id.recyclerViewCorrectiveAction);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewCourseOfAction.setLayoutManager(mLayoutManager);
        correctionActionAdapter = new CorrectionActionAdapter(this, textEnabled);
        recyclerViewCourseOfAction.setAdapter(correctionActionAdapter);
        correctionActionAdapter.updateListData(reportIncidentModel.getCorrectionAction());


    }

    private void initMultiSelectionDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_category_selection);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewItems);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(singleSelectionAdapter);
        singleSelectionAdapter.updateListData(listData);
        CustomTextView tvTitle = (CustomTextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Select a item");
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
                ArrayList<CommonDropdownModel> listSelected = singleSelectionAdapter.getSelectedItemList();
                reportIncidentModel.setTypeOfIncident(listSelected);

                txtTypeOfIncident.setText(singleSelectionAdapter.getSelectedItemString());

                int countHigh = 0;
                int countModerate = 0;
                int countLow = 0;
                for (CommonDropdownModel commonDropdownModel : listSelected) {

                    switch (commonDropdownModel.getSeverity()) {
                        case "High":
                            countHigh = countHigh + 1;
                            break;
                        case "Moderate":
                            countModerate = countModerate + 1;
                            break;
                        case "Low":
                            countLow = countLow + 1;
                            break;
                    }
                }
                StringBuilder senstivity = new StringBuilder();

                if (countHigh != 0) {
                    String strHigh = "<font color='#ec3338'>High(" + countHigh + ")</font>";
                    senstivity.append(strHigh);
                    if (countModerate > 0)
                        senstivity.append(", ");
                }
                if (countModerate != 0) {
                    String strModerate = "<font color='#f1af25'>Moderate(" + countModerate + ")</font>";
                    senstivity.append(strModerate);
                    if (countLow > 0)
                        senstivity.append(", ");
                }
                if (countLow != 0) {
                    String strLow = "<font color='#7500ad14'>Low(" + countLow + ")</font>";
                    senstivity.append(strLow);
                }

                txtSeverity.setText(Html.fromHtml(senstivity.toString()));
                hideSoftKeypad(view);
                dialog.dismiss();
//                todo perform action

            }
        });


        final ArrayList<CommonDropdownModel> listTemp = new ArrayList<>();
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
                    for (CommonDropdownModel commonDropdownModel : listData) {
                        if (commonDropdownModel.getName().toLowerCase().startsWith(charSequence.toString().trim())) {
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
        dialog.setCancelable(false);
        dialog.show();

    }

    private void initMultiSelectionSimpleDialog(final ArrayList<CommonDropdownModel> listData) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_category_selection);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewItems);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final MultiSelectionAdapter multiSelectionAdapter = new MultiSelectionAdapter(this);
        recyclerView.setAdapter(multiSelectionAdapter);

        multiSelectionAdapter.updateListData(listData);
        CustomTextView tvTitle = (CustomTextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Select a item");
        CustomTextView txtCancel = (CustomTextView) dialog.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                radioButton2LoosType.setChecked(true);
            }
        });
        CustomTextView txtDone = (CustomTextView) dialog.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CommonDropdownModel> listSelected = multiSelectionAdapter.getSelectedItemList();
                if (listSelected == null || listSelected.size() == 0) {
                    ToastUtils.showToast(ReportIncidentActivity.this, "Please select an item.");
                    return;
                }
                hideSoftKeypad(view);
                tvInputLossOfAsset.setVisibility(View.GONE);
                tvInputLossOfEquipment.setVisibility(View.GONE);
                tvInputLossOfProperty.setVisibility(View.GONE);
                tvInputLossOfLife.setVisibility(View.GONE);
                for (CommonDropdownModel commonDropdownModel : listSelected) {
                    if (commonDropdownModel.getName().equalsIgnoreCase("Loss of Assets")) {
                        tvInputLossOfAsset.setVisibility(View.VISIBLE);
                    } else if (commonDropdownModel.getName().equalsIgnoreCase("Loss of Equipment")) {
                        tvInputLossOfEquipment.setVisibility(View.VISIBLE);
                    } else if (commonDropdownModel.getName().equalsIgnoreCase("Loss of Property")) {
                        tvInputLossOfProperty.setVisibility(View.VISIBLE);
                    } else if (commonDropdownModel.getName().equalsIgnoreCase("Loss of Life")) {
                        tvInputLossOfLife.setVisibility(View.VISIBLE);
                    }
                }

                dialog.dismiss();
//                todo perform action

            }
        });


//        final ArrayList<CommonDropdownModel> listTemp = new ArrayList<>();
        ImageView ivSearch = (ImageView) dialog.findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.GONE);


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
        dialog.setCancelable(false);
        dialog.show();
    }

    private void initMultiSelectionDialogForPolice(final ArrayList<CommonDropdownModel> listData) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_category_selection);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewItems);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final MultiSelectionAdapterForPolice multiSelectionAdapter = new MultiSelectionAdapterForPolice(this);
        recyclerView.setAdapter(multiSelectionAdapter);

        multiSelectionAdapter.updateListData(listData);
        CustomTextView tvTitle = (CustomTextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Select a item");
        CustomTextView txtCancel = (CustomTextView) dialog.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                radioButton2IncidentReported.setChecked(true);
            }
        });
        CustomTextView txtDone = (CustomTextView) dialog.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CommonDropdownModel> listSelected = multiSelectionAdapter.getSelectedItemList();
                if (listSelected == null || listSelected.size() == 0) {
                    ToastUtils.showToast(ReportIncidentActivity.this, "Please select an item.");
                    return;
                }
                hideSoftKeypad(view);
                tvInputFirstInvestigationOfficer.setVisibility(View.GONE);
                tvInputGDRecordedPolice.setVisibility(View.GONE);
                llFirVIEW.setVisibility(View.GONE);
                for (CommonDropdownModel commonDropdownModel : listSelected) {
                    if (commonDropdownModel.getName().equalsIgnoreCase("First Information / GD recorded in Police Station")) {
                        tvInputGDRecordedPolice.setVisibility(View.VISIBLE);
                    } else if (commonDropdownModel.getName().equalsIgnoreCase("Investigation Officer / Police Inspector / Sub insp")) {
                        tvInputFirstInvestigationOfficer.setVisibility(View.VISIBLE);
                    } else if (commonDropdownModel.getName().equalsIgnoreCase("FIR lodged at Local Police Station")) {
                        llFirVIEW.setVisibility(View.VISIBLE);
                    }
                }
                dialog.setCancelable(false);
                dialog.dismiss();
//                todo perform action

            }
        });


//        final ArrayList<CommonDropdownModel> listTemp = new ArrayList<>();
        ImageView ivSearch = (ImageView) dialog.findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.GONE);


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

        dialog.show();
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
                if (options[i].toString().equalsIgnoreCase("FIR lodged at Local Police Station")) {
                    llFirVIEW.setVisibility(View.VISIBLE);
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void setSingleForGDRecordPoliceChoice(final CharSequence[] options, final CustomEditText customEditText) {
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
                if (options[i].toString().equalsIgnoreCase("FIR lodged at Local Police Station")) {
                    llFirVIEW.setVisibility(View.VISIBLE);
                    tvInputGDRecordedPolice.setVisibility(View.GONE);
                } else {
                    tvInputGDRecordedPolice.setVisibility(View.VISIBLE);
                    llFirVIEW.setVisibility(View.GONE);
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void setSingleChoiceMedical(final CharSequence[] options, final CustomEditText customEditText) {
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
                if (options[i].toString().equalsIgnoreCase("Time of Evacuation to sick bay")) {
                    tvInputMedicalEmergencyTime.setVisibility(View.VISIBLE);
                    tvInputMedicalEmergencyDescription.setVisibility(View.GONE);
                } else {
                    tvInputMedicalEmergencyTime.setVisibility(View.GONE);
                    tvInputMedicalEmergencyDescription.setVisibility(View.VISIBLE);
                    etMedicalEmergencyDescription.getText().clear();
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private boolean isFiledChecked() {

        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "Please check internet connection");
            return false;
        }

        if (StringUtils.isNullOrEmpty(txtClientName.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Client Name.");
            return false;
        }


        if (reportIncidentModel.getTypeOfIncident() == null || reportIncidentModel.getTypeOfIncident().size() == 0) {
            ToastUtils.showToast(this, "Please select type of Incident.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(tvDateOfIncident.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter date of Incident.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(tvTimeOfIncident.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter time of Incident.");
            return false;
        }


        if (StringUtils.isNullOrEmpty(etPlaceOfIncident.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter place of Incident.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(tvTimeOfIncidentReporting.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter time of Reporting.");
            return false;
        }
        if (StringUtils.isNullOrEmpty(etOfficerReport.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter Reporting Officer.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etNumberOfPersonInvolved.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter number of person involved.");
            return false;
        }
        if (StringUtils.isNullOrEmpty(etNumberOfSecurityPersonnelInvolved.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter number of security person involved.");
            return false;
        }

        if (StringUtils.isNullOrEmpty(etNameOfSecurityAndFacilityPersonnel.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter name of security personnel.");
            return false;
        }

        if (lossType) {

            if (tvInputLossOfAsset.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(txtLossOfAsset.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Loss of Assets.");
                return false;
            }
            if (tvInputLossOfEquipment.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(txtLossOfEquipment.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Loss of Equipment.");
                return false;
            }
            if (tvInputLossOfProperty.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(txtLossOfProperty.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Loss of Property.");
                return false;
            }
            if (tvInputLossOfLife.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(txtLossOfLife.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Loss of Life.");
                return false;
            }
        } else {
            ToastUtils.showToast(this, "Please check loss type.");
            return false;
        }


        if (isPoliceReported) {

            if (tvInputGDRecordedPolice.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(etGDRecordedPolice.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter GD Recorded Police.");
                return false;
            }
            if (tvInputFirstInvestigationOfficer.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(etInputFirstInvestigationOfficer.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter First Investigation Officer Name.");
                return false;
            }
            if (llFirVIEW.getVisibility() == View.VISIBLE) {

                if (StringUtils.isNullOrEmpty(etFirNumber.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter FIR Number.");
                    return false;
                }
                if (StringUtils.isNullOrEmpty(etFirStatus.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter FIR Status.");
                    return false;
                }
            }
        } else {
            ToastUtils.showToast(this, "Please select Local Police Report.");
            return false;
        }


        if (anyMedicalEMergency) {
            if (tvInputAnyMedicalEmergencies.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(txtAnyMedicalEmergencies.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Any Medical Emergency type.");
                return false;
            }


            if (tvInputMedicalEmergencyTime.getVisibility() == View.VISIBLE && StringUtils.isNullOrEmpty(etMedicalEmergencyTime.getText().toString().trim())) {
                ToastUtils.showToast(this, "Please enter Evacuation time.");
                return false;
            }


        } else {
            ToastUtils.showToast(this, "Please select Medical Emergency.");
            return false;
        }


        if (StringUtils.isNullOrEmpty(tvDateOfIncident.getText().toString().trim())) {
            ToastUtils.showToast(this, "Please enter date of Incident.");
            return false;
        }

        if (courseOfActionAdapter.getSelectedItemList() == null || courseOfActionAdapter.getSelectedItemList().size() == 0) {
            ToastUtils.showToast(this, "Please enter Course of Action.");
            return false;
        }

        if (observationAdapter.getSelectedItemList() == null || observationAdapter.getSelectedItemList().size() == 0) {
            ToastUtils.showToast(this, "Please enter Observation.");
            return false;
        }

        if (correctionActionAdapter.getSelectedItemList() == null || correctionActionAdapter.getSelectedItemList().size() == 0) {
            ToastUtils.showToast(this, "Please enter Correction Action.");
            return false;
        }
        return true;
    }

    private void setDataToModel() {
        if (!isFiledChecked()) {
            btnVerifyNumber.setEnabled(true);
            return;
        }
        try {
            reportIncidentModel.setCreatedDate(Utility.getReportDate(System.currentTimeMillis()));


            pbar.setVisibility(View.VISIBLE);
//        reportIncidentModel.setTypeOfIncident(singleSelectionAdapter.getSelectedItemList());
            reportIncidentModel.setLatitude(CreateEventModel.getInstance(false).getLatitude());
            reportIncidentModel.setLongitude(CreateEventModel.getInstance(false).getLongitude());
            reportIncidentModel.setCountryCode(preferences.getCountryCode());
            reportIncidentModel.setMobile(preferences.getPhone());
            reportIncidentModel.setMedicalDescription(etMedicalEmergencyDescription.getText().toString().trim());
            reportIncidentModel.setMedicalEvacuationTime(etMedicalEmergencyTime.getText().toString().trim());

            reportIncidentModel.setDateOfIncident(tvDateOfIncident.getText().toString().trim());
            reportIncidentModel.setTimeOfIncident(tvTimeOfIncident.getText().toString().trim());
            reportIncidentModel.setPlaceOfIncident(etPlaceOfIncident.getText().toString().trim());
            reportIncidentModel.setTimeOfReporting(tvTimeOfIncidentReporting.getText().toString().trim());
            reportIncidentModel.setOfficerReportedAtSite(etOfficerReport.getText().toString().trim());
            reportIncidentModel.setNumberOfPersonInvolved(etNumberOfPersonInvolved.getText().toString().trim());
            reportIncidentModel.setNumberOfSecurityPersonInvolved(etNumberOfSecurityPersonnelInvolved.getText().toString().trim());
            reportIncidentModel.setNameOfPersonInvolved(etNameOfSecurityAndFacilityPersonnel.getText().toString().trim());
            reportIncidentModel.setNameOfSecurityPersonInvolved(etNameOfSecurityAndFacilityPersonnel.getText().toString().trim());

            ArrayList<LossItemModel> lossList = new ArrayList<>();
            String[] listItem = getResources().getStringArray(R.array.loss_off_asset);
            for (int i = 0; i < listItem.length; i++) {
                LossItemModel incidentTypeModel = new LossItemModel();
                incidentTypeModel.setLossType(listItem[i]);
                switch (i) {
                    case 0:
                        incidentTypeModel.setLossItem(txtLossOfAsset.getText().toString().trim());
                        break;
                    case 1:
                        incidentTypeModel.setLossItem(txtLossOfEquipment.getText().toString().trim());
                        break;
                    case 2:
                        incidentTypeModel.setLossItem(txtLossOfProperty.getText().toString().trim());
                        break;
                    case 3:
                        incidentTypeModel.setLossItem(txtLossOfLife.getText().toString().trim());
                        break;

                }
                lossList.add(incidentTypeModel);
            }
            reportIncidentModel.setLossItemList(lossList);


            ArrayList<LocalIncidentReportedModel> reportPoliceList = new ArrayList<>();
            String[] listItemPoliceReport = getResources().getStringArray(R.array.inciden_reported);
            for (int i = 0; i < listItemPoliceReport.length; i++) {
                LocalIncidentReportedModel incidentTypeModel = new LocalIncidentReportedModel();
                incidentTypeModel.setReportedTitle(listItemPoliceReport[i]);
                switch (i) {
                    case 0:
                        incidentTypeModel.setReportedDescription(etGDRecordedPolice.getText().toString().trim());
                        break;
                    case 1:
                        incidentTypeModel.setReportedDescription(etInputFirstInvestigationOfficer.getText().toString().trim());
                        break;
                    case 2:
                        incidentTypeModel.setFirNumber(etFirNumber.getText().toString().trim());
                        incidentTypeModel.setFirStatus(etFirStatus.getText().toString().trim());
                        break;

                }
                reportPoliceList.add(incidentTypeModel);
            }
            reportIncidentModel.setPoliceReportList(reportPoliceList);


//        LocalIncidentReportedModel localIncidentReportedModel = new LocalIncidentReportedModel();
//        localIncidentReportedModel.setReportedTitle(txtIncidentReported.getText().toString().trim());
//        localIncidentReportedModel.setReportedDescription(etGDRecordedPolice.getText().toString().trim());
//        localIncidentReportedModel.setFirNumber(etFirNumber.getText().toString().trim());
//        localIncidentReportedModel.setFirStatus(etFirStatus.getText().toString().trim());


            reportIncidentModel.setMedicalEmergencyData(txtAnyMedicalEmergencies.getText().toString());

            reportIncidentModel.setCourseOfAction(courseOfActionAdapter.getSelectedItemList());
            reportIncidentModel.setObservation(observationAdapter.getSelectedItemList());
            reportIncidentModel.setCorrectionAction(correctionActionAdapter.getSelectedItemList());
            reportIncidentModel.setCountryCode(preferences.getCountry().countryCode);
            reportIncidentModel.setMobile(preferences.getPhone());
            reportIncidentModel.setName(preferences.getName());
            reportIncidentModel.setEmpCode(preferences.getEmpCode());
            reportIncidentModel.setUserId(preferences.getUserId());
            reportIncidentModel.setTeam_lead_id(null);
            reportIncidentModel.setTeamlead(null);
            reportIncidentModel.setDepartment(preferences.getDepartment());
            reportIncidentModel.setClientName(txtClientName.getText().toString().trim());
            reportIncidentModel.setClientCode(clientCode);
            reportIncidentModel.setClientLocation(clientLocation);
            reportIncidentModel.setDesignation(preferences.getUserType());
            reportIncidentModel.setBranchId(preferences.getBranchId());
            reportIncidentModel.setBranchName(preferences.getBranchName());
            reportIncidentModel.setReportedDate(reportedDate);

            getDownloadUrl();
        } catch (Exception e) {
            e.printStackTrace();
            btnVerifyNumber.setEnabled(true);
        }
//        saveDataToFirebase();
    }

    private void saveDataToFirebase() {
        reportIncidentModel.setImages(jsonArrayImage);


        hitApiSaveIncidentReport();


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        if (needEdit) {
//            db.collection("incident_report").document(reportIncidentModel.getReportId()).set(reportIncidentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    ToastUtils.showToast(ReportIncidentActivity.this, "Report Updated Successfully.");
//                    pbar.setVisibility(View.GONE);
//                    onBackPressed();
//                }
//            });
//        } else {
////        FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("incident_report")
//                    .add(reportIncidentModel)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            String taskId = documentReference.getId();
//                            pbar.setVisibility(View.GONE);
//                            showAlertDialogWithFinish(ReportIncidentActivity.this,"","Report Submitted Successfully.");
//                        }
//                    });
//        }
    }

    @Override
    public void onBackPressed() {
        try {
            Datastatic.getInstance().getListIncidentType().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtClientName:
                Intent intent = new Intent(ReportIncidentActivity.this, ClientsListActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;

            case R.id.iv_back:
                hideSoftKeyBoard();
                onBackPressed();

                break;
            case R.id.btnVerifyNumber:
                btnVerifyNumber.setEnabled(false);
                setDataToModel();


                break;
            case R.id.selectCountry:
                intent = new Intent(this, CountrySelectionActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_COUNTRY);
                break;

            case R.id.tvDateOfIncident:
                datePicker();
                break;
            case R.id.tvTimeOfIncident:
                timePicker(tvTimeOfIncident);
                break;

            case R.id.txtTypeOfIncident:
                initMultiSelectionDialog();
                break;


            case R.id.etPlaceOfIncident:
                Intent intent_location = new Intent(this, LocationFinderActivity.class);
                startActivityForResult(intent_location, Constants.Location.LOCATION);
                break;


            case R.id.tvTimeOfIncidentReporting:
                timePicker(tvTimeOfIncidentReporting);
                break;

            case R.id.txtIncidentReported:
                llFirVIEW.setVisibility(View.GONE);
                CharSequence[] options = getResources().getStringArray(R.array.inciden_reported);
//                setSingleForGDRecordPoliceChoice(options, txtIncidentReported);
//                initMultiSelectionDialogForPolice(options);
                break;

            case R.id.txtLossOfAsset:
                options = getResources().getStringArray(R.array.loss_off_asset);

                setSingleChoice(options, txtLossOfAsset);
                break;

            case R.id.txtAnyMedicalEmergencies:
                options = getResources().getStringArray(R.array.any_medical_agenecy);
                setSingleChoiceMedical(options, txtAnyMedicalEmergencies);

                break;

            case R.id.ivCamera:
                checkPermission();
//                selectImage();
//                if (isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(ReportIncidentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestAppPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT, ReportIncidentActivity.this);
//                } else {
//                    startCameraForEvent();
//                }
                break;
            case R.id.llQrView:
            case R.id.ivScanQRCode:
                intent = new Intent(this, ScanQRCodeActivity.class);
                startActivityForResult(intent, Constants.REQUEST_QR_CODE);
                break;
            case R.id.etMedicalEmergencyTime:
                timePicker(etMedicalEmergencyTime);
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

    private void selectImage() {

        final CharSequence[] options = {"Photo from Camera", "Photo from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].toString().equalsIgnoreCase(options[0].toString())) {
                    if (isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(ReportIncidentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestAppPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT, ReportIncidentActivity.this);
                    } else {
                        startCameraForEvent();
                    }
                } else if (options[item].toString().equalsIgnoreCase(options[1].toString())) {
                    if (isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(ReportIncidentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION, REQUEST_PERMISSION, ReportIncidentActivity.this);
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

    public void startCameraForEvent() {
        String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis());
        tempFile = FileUtils.getFile(FileUtils.DIR_IMAGE, fileName + ".png");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUri(tempFile, this));
        startActivityForResult(intent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);

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
                        tvDateOfIncident.setText(Utility.getDateFromSec(c.getTimeInMillis()));


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void timePicker(final CustomEditText textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                String min = selectedMinute < 10 ? "0" + selectedMinute : selectedMinute + "";

                String time = (hourOfDay % 12 == 0 ? 12 : hourOfDay % 12) + ":" + min + " " + ((hourOfDay >= 12) ? "PM" : "AM");

                textView.setText(time);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_PICK_IMAGE:
                File galleryFile = getFileFromGallery(data);
                onImageSelected(galleryFile);
                FileUtils.getFullPic(this, galleryFile, ivCamera, R.drawable.camera_icon, preferences.getAvatarHash(), false);

                break;
            case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                if (resultCode != RESULT_OK) return;
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis() + ".png");
                            if (data == null) return;
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            final File imageUrl = FileUtils.saveToInternalStorage(imageBitmap, fileName);


                            MediaDetails eventMediaModel = new MediaDetails();
                            eventMediaModel.setImageUrl(imageUrl + "");
                            eventMediaModel.setVideo(false);
                            eventMediaModel.setType(Constants.MediaType.PIC);
                            eventMediaModel.setSelected(true);
                            listImage.add(eventMediaModel);
//                            listImageUpload.add(eventMediaModel);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageAdapter.updateListData(listImage);
                                    tvLocationCapture.setText(Utility.getFormattedDateAndTime(System.currentTimeMillis())+",\n"+etPlaceOfIncident.getText().toString().trim());

//                                    uploadImage();

//                                    getDownloadUrl();
                                }
                            });

                        }
                    }).start();


                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case REQUEST_SELECT_COUNTRY:
                if (resultCode != RESULT_OK) return;
                String countryInfo = data.getStringExtra(Constants.EXTRA_SELECTED_COUNTRY);
                country = new Country(countryInfo);
//            txvCountryCode.setText(country.countryCode);
//            txvCountry.setText(country.name);
                preferences.setCountry(country);
                break;
            case Constants.Location.LOCATION:
//                String location = data.getStringExtra(Constants.Params.NAME_LOCATION);
                CreateEventModel createEventModel = CreateEventModel.getInstance(false);
                if (!StringUtils.isNullOrEmpty(createEventModel.getLocation()))
                    etPlaceOfIncident.setText(createEventModel.getLocation());

                break;

            case Constants.REQUEST_QR_CODE:
                if (resultCode != RESULT_OK) return;
                String qrData = data.getStringExtra(Constants.EXTRA_DATA);
                if (!StringUtils.isNullOrEmpty(qrData)) {
                    hitApiForFetchingClientList(qrData);
                }
                break;
            case Constants.REQUEST_CODE:
                if (resultCode != RESULT_OK) return;
                ClientListModel.Data listSelected = data.getParcelableExtra(Constants.EXTRA_DATA);
                txtClientName.setText(listSelected.getUnit_name());
                clientLocation = listSelected.getLocation();
//                etLocation.setText(listSelected.getLocation());
//                etLocation.setVisibility(View.VISIBLE);
//                etLocation.setEnabled(false);
//                frameClientName.setVisibility(View.VISIBLE);
                clientCode = listSelected.getUnit_code();

                break;


        }
    }

    private void hitApiForFetchingClientList(String qrData) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "Please check internet connection");
            return;
        }
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.GET_CLIENT_BY_QR_CODE + qrData, this, GET_CLIENT_BY_QR_CODE);

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                String jsonObject = gson.toJson(reportIncidentModel);
                requestParams.put("data", new JSONObject(jsonObject));

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethod(Constants.SAVE_INCIDENT_REPORT, requestParams, this, SAVE_INCIDENT_REPORT);
        } catch (Exception e) {
            e.printStackTrace();
            btnVerifyNumber.setEnabled(true);
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

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
//        progressDialog.dismiss();
        btnVerifyNumber.setEnabled(true);
        pbar.setVisibility(View.GONE);
        if (responseCode == SAVE_INCIDENT_REPORT) {
            UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
            pbar.setVisibility(View.GONE);
//            showAlertDialogWithFinish(ReportIncidentActivity.this, "", otpResponseModel.getMsg());
        } else if (responseCode == Constants.RequestType.SEND_OTP) {
            UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
            btnVerifyNumber.setEnabled(true);
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
//                        pbar.setVisibility(View.GONE);
//                    } else {
//                        showAlertDialog(this, "", otpResponseModel.getMsg());
//                    }
//                } else {
//                }
            }


        } else {
            try {
                ClientDataForPOModel clientDataForPOModel = gson.fromJson(response, ClientDataForPOModel.class);
                if (clientDataForPOModel.getData() != null && clientDataForPOModel.getData().size() > 0) {
                    txtClientName.setText(clientDataForPOModel.getData().get(0).getClientName());

                    clientLocation = clientDataForPOModel.getData().get(0).getLocation();
                    clientCode = clientDataForPOModel.getData().get(0).getClientCode();

                    inputClientName.setVisibility(View.VISIBLE);
                } else {
                    inputClientName.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
            }
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
//                    reportIncidentModel.setImages(jsonArrayImage);
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

}