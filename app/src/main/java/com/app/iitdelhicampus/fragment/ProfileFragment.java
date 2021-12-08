package com.app.iitdelhicampus.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ExperienceActivity;
import com.app.iitdelhicampus.activity.GenderListActivity;
import com.app.iitdelhicampus.activity.LocationFinderActivity;
import com.app.iitdelhicampus.activity.LoginWithNumberActivity2;
import com.app.iitdelhicampus.activity.MultipleSelectionActivity;
import com.app.iitdelhicampus.activity.VisitTimingsActivity;
import com.app.iitdelhicampus.activity.WeeklyOffActivity;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.LoginModel;
import com.app.iitdelhicampus.model.LogoutModel;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.model.MetaDataDetailModel;
import com.app.iitdelhicampus.model.VisitTimingModel;
import com.app.iitdelhicampus.model.WeeklyOffModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.BitmapUtils;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.Securities;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_CODE_CAMERA_FOR_CREATE_EVENT;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PERMISSION;
import static com.app.iitdelhicampus.constants.AppConstants.REQUEST_PICK_IMAGE;


public class ProfileFragment extends BaseFragment implements OnUpdateResponse, View.OnClickListener /*implements View.OnClickListener, AdapterView.OnItemSelectedListener*/ {
    private static ProfileFragment instance;
    public File file;
    View view;
    String emailPattern;
    //    static UpdateMediaCallback updateCallback;
//    private ArrayList<FileModel> imagesToUpload;
//    private List<MediaDetails> alEventMediaImages;
    private File filePath;
    private boolean isFromMyProfile;
    private RelativeLayout rlEdit;
    private ImageView imgProfile;
    private ImageView imgEdit;
    private ImageView imgCamera;
    private ImageView imgLogout;
    private TextView txtDOB;
    private TextView txtGender;
    private TextView txtAddress;
    private TextView txtQualification;
    private TextView txtSpecialisationPet;
    private TextView txtExperience;
    private TextView txtServices;
    private TextView txtSpeciality;
    private TextView txtVisitTimings;
    private TextView txtWeeklyOff;
    private TextView txtRating;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtClinicName;
    private EditText edtRegistrationNumber;
    private EditText edtFees;
    private EditText edtGSTnumber;
    private EditText edtTelephone;
    private EditText edtSocialProfile;
    private LinearLayout llQualification;
    private LinearLayout llPetSpecialisation;
    private LinearLayout llExperience;
    private LinearLayout llSpeciality;
    private LinearLayout llServices;
    private LinearLayout llTimings;
    private LinearLayout llWeeklyOff;
    private LinearLayout llDOB;
    private LinearLayout llGender;
    private LinearLayout llAddress;
    private CheckBox checkboxTerms;
    private Button btnSubmit;
    private String name, item;
    private Double lat, lang;
    private ProgressBar pbar;
    private String path;

    public static ProfileFragment getInstance1() {
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_pet, container, false);
        init();
        return view;
    }

    private void init() {
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        rlEdit = (RelativeLayout) view.findViewById(R.id.rlEdit);
        llQualification = (LinearLayout) view.findViewById(R.id.llQualification);
        llPetSpecialisation = (LinearLayout) view.findViewById(R.id.llPetSpecialisation);
        llExperience = (LinearLayout) view.findViewById(R.id.llExperience);
        llSpeciality = (LinearLayout) view.findViewById(R.id.llSpeciality);
        llServices = (LinearLayout) view.findViewById(R.id.llServices);
        llTimings = (LinearLayout) view.findViewById(R.id.llTimings);
        llWeeklyOff = (LinearLayout) view.findViewById(R.id.llWeeklyOff);
        llDOB = (LinearLayout) view.findViewById(R.id.llDOB);
        llGender = (LinearLayout) view.findViewById(R.id.llGender);
        llAddress = (LinearLayout) view.findViewById(R.id.llAddress);

        txtDOB = (TextView) view.findViewById(R.id.txtDOB);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtGender = (TextView) view.findViewById(R.id.txtGender);
        txtRating = (TextView) view.findViewById(R.id.txtRating);

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        edtClinicName = (EditText) view.findViewById(R.id.edtClinicName);
        txtQualification = (TextView) view.findViewById(R.id.txtQualification);
        txtSpecialisationPet = (TextView) view.findViewById(R.id.txtSpecialisationPet);
        edtRegistrationNumber = (EditText) view.findViewById(R.id.edtRegistrationNumber);
        txtExperience = (TextView) view.findViewById(R.id.txtExperience);
        txtServices = (TextView) view.findViewById(R.id.txtServices);
        txtSpeciality = (TextView) view.findViewById(R.id.txtSpeciality);
        edtFees = (EditText) view.findViewById(R.id.edtFees);
        txtVisitTimings = (TextView) view.findViewById(R.id.txtVisitTimings);
        txtWeeklyOff = (TextView) view.findViewById(R.id.txtWeeklyOff);
        edtGSTnumber = (EditText) view.findViewById(R.id.edtGSTnumber);
        edtTelephone = (EditText) view.findViewById(R.id.edtTelephone);
        edtSocialProfile = (EditText) view.findViewById(R.id.edtSocialProfile);

        imgCamera = (ImageView) view.findViewById(R.id.imgCamera);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        imgLogout = (ImageView) view.findViewById(R.id.imgLogout);
        imgLogout.setOnClickListener(this);
        imgEdit = (ImageView) view.findViewById(R.id.imgEdit);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        pbar = (ProgressBar) view.findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);

        btnSubmit.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
        rlEdit.setOnClickListener(this);
        txtDOB.setOnClickListener(this);
        llQualification.setOnClickListener(this);
        llPetSpecialisation.setOnClickListener(this);
        llExperience.setOnClickListener(this);
        llSpeciality.setOnClickListener(this);
        llServices.setOnClickListener(this);
        llWeeklyOff.setOnClickListener(this);
        txtWeeklyOff.setOnClickListener(this);
        llTimings.setOnClickListener(this);
        llDOB.setOnClickListener(this);
        txtVisitTimings.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        setdata();


        if (StringUtils.isNullOrEmpty(preferences.getDeviceToken())) {
//            showDeviceTokenAlertDialog(this, "", "Device token is not updated, Please refresh token.");
            try {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(mActivity, new OnSuccessListener<InstanceIdResult>() {
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


    }

    private void setdata() {
        if (!StringUtils.isNullOrEmpty(preferences.getCategoryName())) {
            edtName.setText(preferences.getCategoryName());
        }
        if (!StringUtils.isNullOrEmpty(preferences.getPhone())) {
            edtMobile.setText(preferences.getPhone());
        }
        if (!StringUtils.isNullOrEmpty(preferences.getEmail())) {
            edtEmail.setText(preferences.getEmail());
        }
        if (!StringUtils.isNullOrEmpty(preferences.getGender())) {
            txtGender.setText(preferences.getGender());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getAddress())) {
            txtAddress.setText(preferences.getAddress());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getDateOfBirth())) {
            txtDOB.setText(preferences.getDateOfBirth());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getClinicName())) {
            edtClinicName.setText(preferences.getClinicName());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getQualifications())) {
            txtQualification.setText(preferences.getQualifications());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getSpeciesSpecialisation())) {
            txtSpecialisationPet.setText(preferences.getSpeciesSpecialisation());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getRegistrationNo())) {
            edtRegistrationNumber.setText(preferences.getRegistrationNo());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getExperience())) {
            txtExperience.setText(preferences.getExperience());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getServiceFor())) {
            txtServices.setText(preferences.getServiceFor());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getSpecialities())) {
            txtSpeciality.setText(preferences.getSpecialities());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getCharges())) {
            edtFees.setText(preferences.getCharges());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getVisitTiming())) {
            txtVisitTimings.setText(preferences.getVisitTiming());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getWeeklyOff())) {
            txtWeeklyOff.setText(preferences.getWeeklyOff());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getGST())) {
            edtGSTnumber.setText(preferences.getGST());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getTelephone())) {
            edtTelephone.setText(preferences.getTelephone());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getSocialProfile())) {
            edtSocialProfile.setText(preferences.getSocialProfile());
        }
        if (!StringUtils.isNullOrEmptyOrZero(preferences.getStarRating())) {
            txtRating.setText(preferences.getStarRating());
        }

        FileUtils.getFullPic(mActivity, FileUtils.getProfilePicUrl(mActivity, preferences.getUserId()).trim(), imgProfile, R.mipmap.default_user_image, preferences.getAvatarHash(), true);

//        FileUtils.getProfilePicPet(mActivity, preferences.getUserId(), imgProfile, R.mipmap.default_user_image, preferences.getAvatarHash(), true);
//        checkboxTerms.setChecked(true);


//        edtName.setEnabled(false);
//        edtClinicName.setEnabled(false);
//        edtRegistrationNumber.setEnabled(false);
//        edtFees.setEnabled(false);
//        edtGSTnumber.setEnabled(false);
//        edtEmail.setEnabled(false);
//        edtMobile.setEnabled(false);
//        edtTelephone.setEnabled(false);
//        edtSocialProfile.setEnabled(false);
//
//        llQualification.setEnabled(false);
//        txtQualification.setEnabled(false);
//
//        llPetSpecialisation.setEnabled(false);
//        txtSpecialisationPet.setEnabled(false);
//
//        llExperience.setEnabled(false);
//        txtExperience.setEnabled(false);
//
//        llSpeciality.setEnabled(false);
//        txtSpeciality.setEnabled(false);
//
//        llServices.setEnabled(false);
//        txtServices.setEnabled(false);
//
//        llTimings.setEnabled(false);
//        txtVisitTimings.setEnabled(false);
//
//        llWeeklyOff.setEnabled(false);
//        txtWeeklyOff.setEnabled(false);
//
//        llGender.setEnabled(false);
//        txtGender.setEnabled(false);
//
//        llDOB.setEnabled(false);
//        txtDOB.setEnabled(false);
//
//        llAddress.setEnabled(false);
//        txtAddress.setEnabled(false);
//
//        llTimings.setEnabled(false);
//        llWeeklyOff.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgLogout:
                LogoutDialog();
                break;

            case R.id.rlEdit:
            case R.id.imgEdit:
                setfieldsEnable();
                Toast.makeText(mActivity, "Edit your profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.txtDOB:
            case R.id.llDOB:
                datePicker();
                break;

            case R.id.txtAddress:
                Intent intent_location = new Intent(mActivity, LocationFinderActivity.class);
                mActivity.startActivityForResult(intent_location, Constants.Location.LOCATION);
                break;


            case R.id.txtGender:
                Intent intent = new Intent(mActivity, GenderListActivity.class);
                intent.putExtra("title", "Select Gender");
                mActivity.startActivityForResult(intent, 1201);

                break;

            case R.id.llWeeklyOff:
            case R.id.txtWeeklyOff:
                Intent intent1 = new Intent(mActivity, WeeklyOffActivity.class);
//                intentT.putExtra("title", "Select Gender");
                mActivity.startActivityForResult(intent1, 1202);
                break;

            case R.id.llTimings:
            case R.id.txtVisitTimings:
                Intent intentt = new Intent(mActivity, VisitTimingsActivity.class);
                mActivity.startActivityForResult(intentt, 1203);
                break;

            case R.id.llExperience:
            case R.id.txtExperience:
                Intent intent2 = new Intent(mActivity, ExperienceActivity.class);
                intent2.putExtra("title", "Select Experience");

                mActivity.startActivityForResult(intent2, 1204);
                break;

            case R.id.llQualification:
            case R.id.txtQualification:
                Intent intent3 = new Intent(mActivity, MultipleSelectionActivity.class);
                intent3.putExtra("title", "Select Qualification");
                intent3.putExtra("type", "Qualification");
                mActivity.startActivityForResult(intent3, 1205);
                break;

            case R.id.llPetSpecialisation:
            case R.id.txtSpecialisationPet:
                Intent intent4 = new Intent(mActivity, MultipleSelectionActivity.class);
                intent4.putExtra("title", "Select Pet Specialisation");
                intent4.putExtra("type", "Pet");
                mActivity.startActivityForResult(intent4, 1206);
                break;

            case R.id.llServices:
            case R.id.txtServices:
                Intent intent5 = new Intent(mActivity, MultipleSelectionActivity.class);
                intent5.putExtra("title", "Select Services");
                intent5.putExtra("type", "Services");
                mActivity.startActivityForResult(intent5, 1207);
                break;

            case R.id.llSpeciality:
            case R.id.txtSpeciality:
                Intent intent6 = new Intent(mActivity, MultipleSelectionActivity.class);
                intent6.putExtra("title", "Select Speciality");
                intent6.putExtra("type", "Speciality");
                mActivity.startActivityForResult(intent6, 1208);
                break;

            case R.id.imgCamera:
                selectImage();

                break;

            case R.id.btnSubmit:
                checkFields();

                break;
        }
    }

    public void hitVetProfile() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                pbar.setVisibility(View.GONE);
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            pbar.setVisibility(View.VISIBLE);
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.VET_PROFILE);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
                requestParams.put(Constants.Params.NAME, preferences.getName());
                requestParams.put(Constants.Params.GENDER, txtGender.getText().toString());
                requestParams.put(Constants.Params.EMAIL, edtEmail.getText().toString());
                requestParams.put(Constants.Params.PHONE, edtMobile.getText().toString());
                requestParams.put(Constants.Params.DOB, txtDOB.getText().toString());
                requestParams.put(Constants.Params.ADDRESS, txtAddress.getText().toString());
                requestParams.put(Constants.Params.CATEGORY_NAME, edtName.getText().toString());
                requestParams.put(Constants.Params.OWNER_NAME, edtClinicName.getText().toString());
                requestParams.put(Constants.Params.SPECIES_SPECIALISATION, txtSpecialisationPet.getText().toString());
                requestParams.put(Constants.Params.REGISTRATION_NO, edtRegistrationNumber.getText().toString());
                requestParams.put(Constants.Params.EXPERIENCE, txtExperience.getText().toString());
                requestParams.put(Constants.Params.ON_BOARD, preferences.getOnBoard());
                requestParams.put(Constants.Params.SERVICE_FOR, txtServices.getText().toString());
                requestParams.put(Constants.Params.SPECIALITIES, txtSpeciality.getText().toString());
                requestParams.put(Constants.Params.CHARGES, edtFees.getText().toString());
                requestParams.put(Constants.Params.GST, edtGSTnumber.getText().toString());
                requestParams.put(Constants.Params.PROPERTY_TYPE, preferences.getPropertyType());
                requestParams.put(Constants.Params.STYLE, "");
                requestParams.put(Constants.Params.STAR_RATING, preferences.getStarRating());
                requestParams.put(Constants.Params.TIMING, txtVisitTimings.getText().toString());
                requestParams.put(Constants.Params.REGION, /*preferences.getRegion()*/"Noida");
                requestParams.put(Constants.Params.TELEPHONE, edtTelephone.getText().toString());
                requestParams.put(Constants.Params.MOBILE, edtMobile.getText().toString());
                requestParams.put(Constants.Params.SOCIAL_PROFILE, edtSocialProfile.getText().toString());
                requestParams.put(Constants.Params.WEEKLY_OFF, txtWeeklyOff.getText().toString());
                requestParams.put(Constants.Params.PRODUCT_SUPPLIES, "");
                requestParams.put(Constants.Params.PRODUCT_LISTING, "");
                requestParams.put(Constants.Params.SERVICES_OFFERED, "");
                requestParams.put(Constants.Params.QUALIFICATIONS, txtQualification.getText().toString());
                requestParams.put("categoryId", preferences.getCategoryId());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.VET_PROFILE, requestParams, this, Constants.RequestType.VET_PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
//
    private void uploadMediaAsset(File filePath) {
        try {
//            String root = Environment.getExternalStorageDirectory().toString();
//            String directoryPath = String.format(Locale.ENGLISH, "%s/%s/%s", root, APP_DIRECTORY, dir);
//            File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_DIRECTORY + "/" + DIR_PROFILE + "/" + fileName + ".png");

//            String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis());
//            File destFile = FileUtils.getFile(com.chatmodule.file.FileUtils.DIR_IMAGE, fileName);
//
//            File imagePath = FileUtils.getResizedEventImage(destFile.toString(), filePath);
//            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//            File newFile = new File(dir, "POST_" + System.currentTimeMillis() + ".png");
//            copy(filePath,newFile);


            RequestParams requestParams = new RequestParams();
            requestParams.put(Constants.Params.MEDIA_URL, renameImage(filePath), "application/octet-stream");
            requestParams.put(Constants.Params.USER_ID, Securities.getInstance().encriptedData(preferences.getUserId()));
            requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.UPDATE_PROFILE_PIC);
            requestParams.put(Constants.Params.DEVICE_ID, Securities.getInstance().encriptedData(preferences.getDeviceId()));
            requestParams.put(Constants.Params.SESSION_TOCKEN, Securities.getInstance().encriptedData(preferences.getSessionToken()));
            requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
            requestParams.put(Constants.Params.REQUEST_DATE, Securities.getInstance().encriptedData(Utility.getTimeZone()));

            LoopJRequestHandler.getInstance().hitApiUploadMedia(Constants.Services.UPDATE_PROFILE_PIC, requestParams, this, Constants.RequestType.UPDATE_PROFILE_PIC);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void copy(File source, File target) {
        try {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File renameImage(File file) {
        String saveLocation = null;
        if (file != null)
            saveLocation = FileUtils.getDirectory(FileUtils.DIR_EVENT).toString();
        file = FileUtils.getResizedEventImage(saveLocation, file);
        return file;
    }


    private void checkFields() {
        if (StringUtils.isNullOrEmpty(edtName.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter name.");
            return;
        }
        if (StringUtils.isNullOrEmpty(edtClinicName.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter clinic name.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtQualification.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select qualification.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtSpecialisationPet.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select pet.");
            return;
        }
        if (StringUtils.isNullOrEmpty(edtRegistrationNumber.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter medical registration number.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtExperience.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select experience.");
            return;
        }
        if (StringUtils.isNullOrEmpty(edtFees.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter consultation fees.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtSpeciality.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select speciality.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtServices.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter services offered.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtVisitTimings.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select visit timings.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtWeeklyOff.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select weekly off.");
            return;
        }
//        if (StringUtils.isNullOrEmpty(edtGSTnumber.getText().toString())) {
//            ToastUtils.showToast(mActivity, "Please enter name.");
//            return;
//        }
        if (StringUtils.isNullOrEmptyOrZero(edtEmail.getText().toString().trim())) {
            ToastUtils.showToast(mActivity, "Please enter email address.");
            return;
        } else if (!StringUtils.isNullOrEmptyOrZero(edtEmail.getText().toString())) {
            if (!edtEmail.getText().toString().matches(emailPattern)) {
                ToastUtils.showToast(mActivity, "Please enter valid email address.");
                return;
            }
        }
        if (StringUtils.isNullOrEmpty(txtGender.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please select gender.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtDOB.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter date of birth.");
            return;
        }
        if (StringUtils.isNullOrEmpty(edtMobile.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter mobile number.");
            return;
        }
        if (StringUtils.isNullOrEmpty(edtTelephone.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter telephone number.");
            return;
        }
        if (StringUtils.isNullOrEmpty(txtAddress.getText().toString())) {
            ToastUtils.showToast(mActivity, "Please enter address.");
            return;
        }

        hitVetProfile();
    }


    private void selectImage() {

        final CharSequence[] options = {"Photo from Camera", "Photo from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].toString().equalsIgnoreCase(options[0].toString())) {
                    if (mActivity.isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        mActivity.requestAppPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT, mActivity);
                    } else {
                        mActivity.startCameraForEvent();
                    }
                } else if (options[item].toString().equalsIgnoreCase(options[1].toString())) {
                    if (mActivity.isMarshMallowOrAvobe() && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        mActivity.requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION, REQUEST_PERMISSION, mActivity);
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
        mActivity.startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
    }

    public void startCameraForEvent() {
        String fileName = String.format(Locale.getDefault(), "image_%s", System.currentTimeMillis());
        mActivity.tempFile = FileUtils.getFile(FileUtils.DIR_IMAGE, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUri(mActivity.tempFile, mActivity));
        mActivity.startActivityForResult(intent, REQUEST_CODE_CAMERA_FOR_CREATE_EVENT);

    }

    //
    public void LogoutDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        TextView numberConfimation = (TextView) dialog.findViewById(R.id.numberConfimation);

        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hitLogout();
            }
        });
        dialog.show();
    }


    private void hitLogout() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                pbar.setVisibility(View.GONE);
                return;
            }
            pbar.setVisibility(View.VISIBLE);

//            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//            AppPreferences.getInstance(this).setDeviceId(deviceId);
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.LOGOUT_PET);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.LOGOUT_PET, requestParams, this, Constants.RequestType.LOGOUT_PET);
        } catch (Exception e) {
        }
    }


    public void setAddress(String addr) {
        txtAddress.setText(addr);
    }


    public void setGender(String gender) {
        txtGender.setText(gender);
    }

    public void setExperience(String experience) {
        txtExperience.setText(experience);
    }

    public void setVisitTiming(ArrayList<VisitTimingModel> visitTiming) {
        Log.d(TAG, "setVisitTiming: ");
        setSelectedVisit(visitTiming);
    }

    public void setQualification(ArrayList<MetaDataDetailModel> detailModels, String type) {
        Log.d(TAG, "qualification");
        setSelectedQualification(detailModels, type);
    }

    public void setWeekOff(ArrayList<WeeklyOffModel> weekOff) {
        Log.d(TAG, "setWeekOff: ");
        setSelectedData(weekOff);
    }

    private void setSelectedQualification(ArrayList<MetaDataDetailModel> qualification, String type) {
        String finalData = "";

        if (qualification != null && qualification.size() != 0) {
            for (int i = 0; i < qualification.size(); i++) {
                if (qualification.get(i).isSelected()) {
                    switch (type) {
                        case "Qualification":
                            if (qualification.get(i).getQualification() != null) {
                                if (!qualification.get(i).getQualification().equalsIgnoreCase("Specialisation")) {
                                    finalData = finalData + qualification.get(i).getQualification() + ",";
                                }
                            } else {

                                finalData = finalData + qualification.get(i).getSpecialities() + ",";
                            }

                            break;
                        case "Pet":
                            if (qualification.get(i).getSpeciesSpecialisation().equalsIgnoreCase("Other Pets")) {
                                finalData = finalData + qualification.get(i).getOtherName() + ",";

                            } else {
                                finalData = finalData + qualification.get(i).getSpeciesSpecialisation() + ",";
                            }
                            break;
                        case "Services":
                            finalData = finalData + qualification.get(i).getServices() + ",";
                            break;
                        case "Speciality":
                            finalData = finalData + qualification.get(i).getSpecialities() + ",";
                            break;
                    }
                }
            }
            if (!StringUtils.isNullOrEmpty(finalData)) {

                switch (type) {
                    case "Qualification":
                        txtQualification.setText(finalData.substring(0, finalData.length() - 1));

                        break;
                    case "Pet":
                        txtSpecialisationPet.setText(finalData.substring(0, finalData.length() - 1));

                        break;
                    case "Services":
                        txtServices.setText(finalData.substring(0, finalData.length() - 1));

                        break;
                    case "Speciality":
                        txtSpeciality.setText(finalData.substring(0, finalData.length() - 1));

                        break;
                }
            }
        } else {

        }
    }


    private void setSelectedVisit(ArrayList<VisitTimingModel> weekOff) {
        String finalData = "";

        if (weekOff != null && weekOff.size() != 0) {
            for (int i = 0; i < weekOff.size(); i++) {
                if (weekOff.get(i).getDay().equalsIgnoreCase("Preferred Time Slot")) {
                    finalData = finalData + weekOff.get(i).getDay() + "  ";
                } else if (!StringUtils.isNullOrEmpty(weekOff.get(i).getTimeModel().getMorningFrom()) && !StringUtils.isNullOrEmpty(weekOff.get(i).getTimeModel().getEveningFrom())) {
                    finalData = finalData + weekOff.get(i).getDay() + " (Morning " + weekOff.get(i).getTimeModel().getMorningFrom() + " TO " + weekOff.get(i).getTimeModel().getMorningTo() + ", Evening " + weekOff.get(i).getTimeModel().getEveningFrom() + " TO " + weekOff.get(i).getTimeModel().getEveningTo() + "),\n";

                } else if (!StringUtils.isNullOrEmpty(weekOff.get(i).getTimeModel().getMorningFrom())) {
                    finalData = finalData + weekOff.get(i).getDay() + " (" + weekOff.get(i).getTimeModel().getMorningFrom() + " TO " + weekOff.get(i).getTimeModel().getMorningTo() + "),\n";

                } else if (!StringUtils.isNullOrEmpty(weekOff.get(i).getTimeModel().getEveningFrom())) {
                    finalData = finalData + weekOff.get(i).getDay() + " (" + weekOff.get(i).getTimeModel().getEveningFrom() + " TO " + weekOff.get(i).getTimeModel().getEveningTo() + "),\n";

                }
            }
            txtVisitTimings.setText(finalData.substring(0, finalData.length() - 2));

        } else {
//            Toast.makeText(mActivity, "Please select at least a day", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSelectedData(ArrayList<WeeklyOffModel> weekOff) {
        String finalData = "";
        if (weekOff != null && weekOff.size() != 0) {
            for (int i = 0; i < weekOff.size(); i++) {
                if (weekOff.get(i).getDay().equalsIgnoreCase("All 7 days Working")) {
                    finalData = finalData + weekOff.get(i).getDay() + "  ";
                } else {
                    finalData = finalData + weekOff.get(i).getDay() + " (" + weekOff.get(i).getTimeModel().getFromTime() + " TO " + weekOff.get(i).getTimeModel().getToTime() + "),\n";
                }
            }
            txtWeeklyOff.setText(finalData.substring(0, finalData.length() - 2));

        } else {
//            Toast.makeText(mActivity, "Please select at least a time", Toast.LENGTH_SHORT).show();

        }
    }

//
//    public String setSelectedQualificationList(ArrayList<MetaDataDetailModel> detailModels) {
//        String finalData = "";
//        if (detailModels != null && detailModels.size() != 0) {
//            for (int i = 0; i < detailModels.size(); i++) {
//                if (!detailModels.get(i).getQualification().equalsIgnoreCase("Specialisation")) {
//                    finalData = finalData + detailModels.get(i).getQualification() + ",";
//                } else {
//                    String special = "";
//                    if (!StringUtils.isNullOrEmpty(detailModels.get(i).getSpecialities()))
//                        special = special + detailModels.get(i).getSpecialities() + ",";
//                    finalData = finalData + special;
//
//                }
//            }
//            return (finalData.substring(0, finalData.length() - 1));
//
//
//        } else {
//        }
//        return (finalData.substring(0, finalData.length() - 1));
//
//    }

    public void setImage(File file) {
        this.file = file;
        FileUtils.getFullPic(mActivity, file, imgProfile, R.mipmap.default_user_image, preferences.getAvatarHash(), true);
        uploadMediaAsset(file);
    }


    @Override
    public void onImageSelectionComplete(File file) {
        super.onImageSelectionComplete(file);
//        if (!isFromMyProfile) {
//            preferences.setAppState(AppConstants.AppState.PIC_SELECTED);
//        }
        filePath = file;
        preferences.setAvatarHash(String.valueOf(System.currentTimeMillis()));
        // FileUtils.setProfilePic(this, preferences.getUserId(), imvProfilePic, R.drawable.default_profile_circle, System.currentTimeMillis() + "", true);
//        loadImage(file.toString().contains(".mp4"));
//        updateProfilePic(file);
        preferences.setProfilePic(file.getPath());
        FileUtils.getFullPic(mActivity, file.getPath(), imgProfile, R.mipmap.default_profile_circle, preferences.getAvatarHash(), false);
        uploadMediaAsset(file);
//        if (!isFromMyProfile && preferences.getAppState() == AppState.PIC_SELECTED || preferences.getAppState() == AppState.NUMBER_VERIFIED)
//            ;
    }


    public void datePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        txtDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        try {
            MediaDetails eventMediaModel = new MediaDetails();
            switch (requestCode) {
                case Constants.REQUEST_IMAGE_CAMERA:
                case REQUEST_CODE_CAMERA_FOR_CREATE_EVENT:
                    eventMediaModel.setImageUrl(mActivity.tempFile.getPath());
                    eventMediaModel.setVideo(false);
                    eventMediaModel.setType(Constants.MediaType.PIC);
                    eventMediaModel.setSelected(true);
                    Glide.with(mActivity).load(eventMediaModel.getImageUrl()).fitCenter().into(imgProfile);
                    path = eventMediaModel.getImageUrl();
                    break;
//                case REQUEST_MULTIPLE_IMAGE_CHOOSER:
//                    imagesToUpload = data.getParcelableArrayListExtra(EXTRA_MULTIPLE_IMAGES);
//                    FileModel fileModel = new FileModel();
//                    eventMediaModel = new MediaDetails();
//                    eventMediaModel.setImageUrl(imagesToUpload.get(0).getSdCardPath());
//                    eventMediaModel.setVideo(false);
//                    eventMediaModel.setSelected(true);
//                    eventMediaModel.setType(Constants.MediaType.PIC);
//                    eventMediaModel.setMode(imagesToUpload.get(0).getMode());
//                    Glide.with(mActivity).load(eventMediaModel.getImageUrl()).placeholder(R.mipmap.default_profile_circle).fitCenter().into(imgProfile);
//                    path = eventMediaModel.getImageUrl();
//                    break;

                case 1201:
                    if (data.getStringExtra(AppConstants.EXTRA_MULTIPLE_IMAGES) != null)
                        txtGender.setText(data.getStringExtra(AppConstants.EXTRA_MULTIPLE_IMAGES));
                    break;

                case REQUEST_PICK_IMAGE:
                    File galleryFile = mActivity.getFileFromGallery(data);
                    onImageSelected(galleryFile);
                    break;


                case Constants.Location.LOCATION:

                    if (data == null) return;
                    lat = data.getDoubleExtra(Constants.Params.LATITUDE_LOCATION, 0);
                    lang = data.getDoubleExtra(Constants.Params.LONGITUDE_LOCATION, 0);
                    name = data.getStringExtra(Constants.Params.NAME_LOCATION);

                    if (name != null)
                        txtAddress.setText(name);

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onImageSelected(File file) {
        if (file == null) return;
//        if (shouldCrop) {
////            startCroping(file);
//        } else {
        try {
            Bitmap bitmap = BitmapUtils.getOrientatedScaledBitmap(file, mActivity, true);
            if (bitmap == null) return;
            BitmapUtils.writeBitmapToFile(file, bitmap);
        } catch (IOException e) {
            Toast.makeText(mActivity, getString(R.string.image_not_supported), Toast.LENGTH_LONG).show();
            return;
        }
        onImageSelectionComplete(file);// added extra
//        }

//        if (shouldCrop)
//            beginCrop(file);
//        else onImageSelectionComplete(file);
    }

//    public void setImage(Uri uri, File file) {
//        onImageSelectionComplete(file);
//    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        pbar.setVisibility(View.GONE);

        if (StringUtils.isNullOrEmpty(response)) {
//            pbar.setVisibility(View.GONE);

            return;
        }
        if (isSuccess) {
            switch (responseCode) {
                case Constants.RequestType.LOGOUT_PET:
                    LogoutModel logoutModel = new Gson().fromJson(response, LogoutModel.class);
                    if (logoutModel.getStatus().equalsIgnoreCase("true")) {
                        Utility.showToast(mActivity, logoutModel.getMessage());
                        AppPreferences.getInstance(mActivity).clear();
                        Intent intent1 = new Intent(mActivity, LoginWithNumberActivity2.class);
                        startActivity(intent1);
                        mActivity.finish();
                    }
                    break;

                case Constants.RequestType.VET_PROFILE:
                    LoginModel otpResponseModel = gson.fromJson(response, LoginModel.class);

                    preferences.setName(otpResponseModel.getFirstName());
                    preferences.setGender(otpResponseModel.getGender());
//                    preferences.setPhone(otpResponseModel.getMobile());
                    preferences.setEmail(otpResponseModel.getEmail());
                    preferences.setDateOfBirth(otpResponseModel.getDob());
                    preferences.setName(otpResponseModel.getFirstName());
                    preferences.setAddress(otpResponseModel.getAddress());
                    preferences.setUserId(otpResponseModel.getUserId());
                    preferences.setTransport(response);
                    preferences.setIsExist(otpResponseModel.getIsProfileUpdated());
                    preferences.setChatPassword(otpResponseModel.getLoginKey());
//                        preferences.setSessionToken(otpResponseModel.getSessionToken());
                    preferences.setCategoryName(otpResponseModel.getCategoryName());
                    preferences.setClinicName(otpResponseModel.getOwnerName());
                    preferences.setQualifications(otpResponseModel.getQualifications());
                    preferences.setSpeciesSpecialisation(otpResponseModel.getSpeciesSpecialisation());
                    preferences.setRegistrationNo(otpResponseModel.getRegistrationNo());
                    preferences.setExperience(otpResponseModel.getExperience());
                    preferences.setOnBoard(otpResponseModel.getOnBoard());
                    preferences.setServiceFor(otpResponseModel.getServiceFor());
                    preferences.setSpecialities(otpResponseModel.getSpecialities());
                    preferences.setCharges(otpResponseModel.getCharges());
                    preferences.setGST(otpResponseModel.getGst());
                    preferences.setPropertyType(otpResponseModel.getPropertyType());
                    preferences.setStyle(otpResponseModel.getStyle());
                    preferences.setStarRating(otpResponseModel.getStarRating());
//                        preferences.setVisitTiming(otpResponseModel.getTimings());
                    preferences.setRegion(otpResponseModel.getRegion());
                    preferences.setTelephone(otpResponseModel.getTelephone());
                    preferences.setSocialProfile(otpResponseModel.getSocialProfile());
                    preferences.setWeeklyOff(otpResponseModel.getWeeklyOff());
                    preferences.setProductSupplies(otpResponseModel.getProductSupplies());
                    preferences.setProductListing(otpResponseModel.getProductListing());
                    preferences.setServicesOffered(otpResponseModel.getServicesOffered());


                    break;


                case Constants.RequestType.UPDATE_PROFILE_PIC:
                    preferences.setAvatarHash(String.valueOf(System.currentTimeMillis()));
//                    mActivity.finish();
//                    Intent intent = new Intent(mActivity, DashBoardActivity.class);
//                    startActivity(intent);

                    break;
            }
        }

    }


    private void setfieldsEnable() {
        mActivity.showSoftKeyBoard();

        edtName.setEnabled(true);
        edtClinicName.setEnabled(true);
        edtRegistrationNumber.setEnabled(true);
        edtFees.setEnabled(true);
        edtGSTnumber.setEnabled(true);
        edtEmail.setEnabled(true);
        edtMobile.setEnabled(true);
        edtTelephone.setEnabled(true);
        edtSocialProfile.setEnabled(true);

        llQualification.setEnabled(true);
        txtQualification.setEnabled(true);

        llPetSpecialisation.setEnabled(true);
        txtSpecialisationPet.setEnabled(true);

        llExperience.setEnabled(true);
        txtExperience.setEnabled(true);

        llSpeciality.setEnabled(true);
        txtSpeciality.setEnabled(true);

        llServices.setEnabled(true);
        txtServices.setEnabled(true);

        llTimings.setEnabled(true);
        txtVisitTimings.setEnabled(true);

        llWeeklyOff.setEnabled(true);
        txtWeeklyOff.setEnabled(true);

        llGender.setEnabled(true);
        txtGender.setEnabled(true);

        llDOB.setEnabled(true);
        txtDOB.setEnabled(true);

        llAddress.setEnabled(true);
        txtAddress.setEnabled(true);

        llTimings.setEnabled(true);
        llWeeklyOff.setEnabled(true);
    }
}
