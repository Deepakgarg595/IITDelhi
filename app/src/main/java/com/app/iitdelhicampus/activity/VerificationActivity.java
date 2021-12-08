package com.app.iitdelhicampus.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.LoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {

    private ImageView iv_back;
    private CustomTextView txtNext;
    private CustomEditText etOtpHolder;
    private CustomTextView etVerificationCode1, etVerificationCode2, etVerificationCode3, etVerificationCode4, etVerificationCode5, etVerificationCode6;
    private AppPreferences preferences;
    private CustomTextView txvNum;
    private String code = "";
    private Button btnResendCode;
    private ProgressBar progressBar;
//    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txtNext = (CustomTextView) findViewById(R.id.txtNext);
        txvNum = (CustomTextView) findViewById(R.id.txvNum);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        btnResendCode = (Button) findViewById(R.id.btnResendCode);
        etOtpHolder = (CustomEditText) findViewById(R.id.etOtpHolder);
        etVerificationCode1 = (CustomTextView) findViewById(R.id.etVerificationCode1);
        etVerificationCode2 = (CustomTextView) findViewById(R.id.etVerificationCode2);
        etVerificationCode3 = (CustomTextView) findViewById(R.id.etVerificationCode3);
        etVerificationCode4 = (CustomTextView) findViewById(R.id.etVerificationCode4);
        etVerificationCode5 = (CustomTextView) findViewById(R.id.etVerificationCode5);
        etVerificationCode6 = (CustomTextView) findViewById(R.id.etVerificationCode6);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        etVerificationCode1.setOnClickListener(this);
        etVerificationCode2.setOnClickListener(this);
        etVerificationCode3.setOnClickListener(this);
        etVerificationCode4.setOnClickListener(this);
        etVerificationCode5.setOnClickListener(this);
        etVerificationCode6.setOnClickListener(this);
        btnResendCode.setOnClickListener(this);


        preferences = AppPreferences.getInstance(this);
        txvNum.setText(preferences.getCountryCode() + "-" + preferences.getPhone());
        txtNext.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        etOtpHolder.addTextChangedListener(new MyTextWatcher(etOtpHolder));
        etOtpHolder.requestFocus();
        statrtTimer();

//        requestContactPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 5151);

    }

    private void statrtTimer() {
        btnResendCode.setBackground(ContextCompat.getDrawable(context, R.drawable.background_resend_button_disable));
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                btnResendCode.setEnabled(false);
                String timeRemaining = millisUntilFinished / 1000 + "";
                if ((millisUntilFinished / 1000) < 10) {
                    timeRemaining = "0" + timeRemaining;
                }

                btnResendCode.setText("Resend Verification Code  00:" + timeRemaining);
            }

            public void onFinish() {
                btnResendCode.setBackground(ContextCompat.getDrawable(context, R.drawable.background_resend_button_enable));
                btnResendCode.setEnabled(true);
                btnResendCode.setText("Resend Verification Code");
            }
        }.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btnResendCode:
//                progressDialog = new ProgressDialog(VerificationActivity.this);
//                progressDialog.setMessage("Please wait...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                showProgressDialog();
                hitSendotp(preferences.getPhone());


                break;
            case R.id.txtNext:
                if (code.trim().length() < 6) {
                    showAlert();
                    return;
                }
                hitVerifyUser();
//                showProgressDialog();
                break;
            case R.id.etVerificationCode1:
            case R.id.etVerificationCode2:
            case R.id.etVerificationCode3:
            case R.id.etVerificationCode4:
            case R.id.etVerificationCode5:
            case R.id.etVerificationCode6:
//                etOtpHolder.setCursorVisible(true);
//                etOtpHolder.requestFocus();
                showSoftKeyBoard();
                break;
        }
    }


    class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub
            etVerificationCode1.setText(" ");
            etVerificationCode2.setText(" ");
            etVerificationCode3.setText(" ");
            etVerificationCode4.setText(" ");
            etVerificationCode5.setText(" ");
            etVerificationCode6.setText(" ");
            String str = s.toString();
            etOtpHolder.setSelection(etOtpHolder.getText().length());
            int length = str.length();
            if (length == 0) return;
            switch (length) {
                case 1:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    break;
                case 2:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    etVerificationCode2.setText(String.valueOf(str.charAt(1)));

                    break;
                case 3:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    etVerificationCode2.setText(String.valueOf(str.charAt(1)));
                    etVerificationCode3.setText(String.valueOf(str.charAt(2)));
                    break;
                case 4:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    etVerificationCode2.setText(String.valueOf(str.charAt(1)));
                    etVerificationCode3.setText(String.valueOf(str.charAt(2)));
                    etVerificationCode4.setText(String.valueOf(str.charAt(3)));
                    break;
                case 5:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    etVerificationCode2.setText(String.valueOf(str.charAt(1)));
                    etVerificationCode3.setText(String.valueOf(str.charAt(2)));
                    etVerificationCode4.setText(String.valueOf(str.charAt(3)));
                    etVerificationCode5.setText(String.valueOf(str.charAt(4)));
                    break;
                case 6:
                    etVerificationCode1.setText(String.valueOf(str.charAt(0)));
                    etVerificationCode2.setText(String.valueOf(str.charAt(1)));
                    etVerificationCode3.setText(String.valueOf(str.charAt(2)));
                    etVerificationCode4.setText(String.valueOf(str.charAt(3)));
                    etVerificationCode5.setText(String.valueOf(str.charAt(4)));
                    etVerificationCode6.setText(String.valueOf(str.charAt(5)));

                    break;
            }

            code = etOtpHolder.getText().toString();
            if (code.length() == 6) {
//                hitVerifyUser();
                //--------------Comment after api integrated--------------
                Class clz=TLProfileActivity.class;
                if(preferences.getUserType().equalsIgnoreCase("client")){
                    clz=DashBoardActivityKotlin.class;
                }
                Intent intent = new Intent(VerificationActivity.this, clz);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                preferences.setLoggedIn(true);
                //-------------------------------------------------------
                finish();
            }


        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        etOtpHolder.setSelection(etOtpHolder.getText().toString().length());
    }

    public void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage("Please enter correct OTP");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void hitVerifyUser() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
//                removeProgressDialog();
                return;
            }
            btnResendCode.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject requestParams = new JSONObject();
            try {

                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.CODE, code);
                requestParams.put(Constants.Params.REQUEST_ID, String.valueOf(Constants.RequestType.VERIFY_USER));
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getUserType());
//                requestParams.put(Constants.Params.PHONE, preferences.getPhone());
//                requestParams.put(Constants.Params.COUNTRY_CODE, preferences.getCountryCode());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.VERIFY_USER, requestParams, this, Constants.RequestType.VERIFY_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hitSendotp(String phoneNumber) {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
//                removeProgressDialog();
                return;
            }
            progressBar.setVisibility(View.GONE);
            btnResendCode.setEnabled(false);

//            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//            AppPreferences.getInstance(this).setDeviceId(deviceId);
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.PHONE, phoneNumber);
                requestParams.put(Constants.Params.REQUEST_ID, String.valueOf(Constants.RequestType.SEND_OTP));
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
//                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.COUNTRY_CODE, preferences.getCountryCode());

                requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getUserType());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.SEND_OTP, requestParams, this, Constants.RequestType.SEND_OTP);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
//        removeProgressDialog();
        btnResendCode.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        try {
            if (isSuccess && responseCode == Constants.RequestType.VERIFY_USER) {


                LoginModel otpResponseModel = gson.fromJson(response, LoginModel.class);
                if (otpResponseModel.isStatus()) {
//                    progressBar.setVisibility(View.VISIBLE);
                    preferences.setCategoryId(otpResponseModel.getCategoryId());
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
                    preferences.setSessionToken(otpResponseModel.getSessionToken());
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
//                    preferences.setVisitTiming(otpResponseModel.getTimings());
                    preferences.setRegion(otpResponseModel.getRegion());
                    preferences.setTelephone(otpResponseModel.getTelephone());
                    preferences.setSocialProfile(otpResponseModel.getSocialProfile());
                    preferences.setWeeklyOff(otpResponseModel.getWeeklyOff());
                    preferences.setProductSupplies(otpResponseModel.getProductSupplies());
                    preferences.setProductListing(otpResponseModel.getProductListing());
                    preferences.setServicesOffered(otpResponseModel.getServicesOffered());
                    preferences.setLoggedIn(true);


//
                    Class clz=TLProfileActivity.class;
                    if(preferences.getUserType().equalsIgnoreCase("client")){
                        clz=DashBoardActivityKotlin.class;
                    }
                    Intent intent = new Intent(this, clz);
                    startActivity(intent);


//                    preferences.setup
//                    preferences.setPassport(otpResponseModel.getPassportNo());
//                    preferences.setNickName(otpResponseModel.getNickName());
//                    preferences.setPhone(otpResponseModel.getMobile());
//                    preferences.setBucketUrl(otpResponseModel.getBucketUrl());
//                    preferences.setWeddingSide(otpResponseModel.getWeddingSide());
//                    preferences.setCrewManagement(otpResponseModel.getCrewManagement());


//                    XMPPManager.shutdown();
//                    XMPPManager.getInstance(this, null, this);


//                    ArrayList<Contact> contactArrayList = new ArrayList<>();
//                    boolean isGuest = otpResponseModel.getLoginType().equalsIgnoreCase("Guest");
//                    preferences.setGuest(isGuest);


//                    for (AllUserDetailModel allUserDetailModel : otpResponseModel.getAllGroup()) {
//                        Contact contact = new Contact();
//                        contact.setContactMoveTo(Contact.CONTACT_MOVE_TO.ALL_GROUP);
//                        contact.setIsAppUser(true);
//                        contact.setName(allUserDetailModel.getFirstName());
//                        contact.setPhone(allUserDetailModel.getPhone());
//                        contact.setContactId(allUserDetailModel.getUserId());
//                        contact.setUserId(allUserDetailModel.getUserId());
//                        contact.setCustomChat(Contact.CUSTOM_CHAT.NONE);
//                        contact.setStatus(allUserDetailModel.getGuestType());
//                        contact.setRoomNumber(allUserDetailModel.getRoomNo());
////                        contact.setAdminPriority(allUserDetailModel.getAdminPriority());
//                        contactArrayList.add(contact);
////                        RealmContactDBHelper.checkAndAddPushContact(allUserDetailModel.getUserId(),allUserDetailModel.getPhone(),allUserDetailModel.getFirstName(),allUserDetailModel.getStatusNote(), UUID.randomUUID().toString(), "","","");
//                    }


//                    for (AllUserDetailModel allUserDetailModel : otpResponseModel.getAllUsers()) {
//                        Contact contact = new Contact();
//                        contact.setContactMoveTo(Contact.CONTACT_MOVE_TO.CONTACT_LIST);
//                        contact.setIsAppUser(true);
//                        contact.setName(allUserDetailModel.getFirstName());
//                        contact.setPhone(allUserDetailModel.getPhone());
//                        contact.setContactId(allUserDetailModel.getUserId());
//                        contact.setUserId(allUserDetailModel.getUserId());
//                        contact.setCustomChat(Contact.CUSTOM_CHAT.NONE);
//                        contact.setStatus(allUserDetailModel.getGuestType());
//                        contact.setRoomNumber(allUserDetailModel.getRoomNo());
////                        contact.setAdminPriority(allUserDetailModel.getAdminPriority());
//                        contactArrayList.add(contact);
////                        RealmContactDBHelper.checkAndAddPushContact(allUserDetailModel.getUserId(),allUserDetailModel.getPhone(),allUserDetailModel.getFirstName(),allUserDetailModel.getStatusNote(), UUID.randomUUID().toString(), "","","");
//                    }


//
//                    RealmContactDBHelper.saveContacts(contactArrayList);


//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            XMPPConnection mConnection = XMPPManager.getAuthenticatedConnection();
//                            if (mConnection == null && com.chatmodule.krapps.utils.ConnectivityUtils.isNetworkEnabled(VerificationActivity.this)) {
////                    XMPPManager.getInstance(DashBoardActivity.this);
//                                XMPPManager.getInstance(VerificationActivity.this, null, VerificationActivity.this);
//                            }
//                        }
//                    }, 1000);


//                    checkConnection();


//                    setReminder();
//                    if (StringUtils.isNullOrEmpty(preferences.getWeddingSide()) && otpResponseModel!=null && otpResponseModel.getIntroPost()!=null && otpResponseModel.getIntroPost().size()<=0) {
//                        Intent intent = new Intent(LoginWithNumberActivity.this, DashBoardActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    } else {
//                        Intent intent_video = new Intent(this, IntroVideoActivity.class);
//                        intent_video.putParcelableArrayListExtra("key", otpResponseModel.getIntroPost());
//                        startActivity(intent_video);
//                    }


//                    Utility.addActivities(1, LoginWithNumberActivity.this);
                } else {
                    Utility.showToast(this, otpResponseModel.getMessage());
                }
            } else if (isSuccess && responseCode == Constants.RequestType.SEND_OTP) {
                ToastUtils.showToast(this, "Otp sent successfully.");
            } else {
                Utility.showToast(this, getString(R.string.some_error_occured));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            if (isSuccess && responseCode == Constants.RequestType.VERIFY_USER) {
//
//                removeProgressDialog();
//                if (StringUtils.isNullOrEmpty(response)) return;
//
//
//                VerifyUserResponseModel verifyUserResponseModel = gson.fromJson(response, VerifyUserResponseModel.class);
//
//                if (verifyUserResponseModel.isStatus() == true) {
//
//
//                    preferences.setUserId(verifyUserResponseModel.getUserId());
//                    preferences.setSessionToken(verifyUserResponseModel.getSessionToken());
//                    preferences.setEmail(verifyUserResponseModel.getEmail());
//                    preferences.setName(verifyUserResponseModel.getFirstName());
//                    preferences.setDescription(verifyUserResponseModel.getDescription());
//                    preferences.setBucketUrl(verifyUserResponseModel.getBucketUrl());
////            double dob = Double.parseDouble(verifyUserResponseModel.getDob());
////            String dobInSec = Double.toString(Math.floor(dob));
//                    preferences.setDateOfBirth(verifyUserResponseModel.getDob());
//                    preferences.setGender(verifyUserResponseModel.getGender());
//                    preferences.setChatPassword(verifyUserResponseModel.getLoginKey());
//                    preferences.setAppState(AppConstants.AppState.NUMBER_VERIFIED);
//                    preferences.setIsExist(verifyUserResponseModel.getIsProfileUpdated());
//                    preferences.setCountryCode(verifyUserResponseModel.getCountryCode());
//                    preferences.setProfilePicType(verifyUserResponseModel.getProfileType());
//                    preferences.setUname(verifyUserResponseModel.getUname());
//                    preferences.setRegisterdOn(verifyUserResponseModel.getRegisteredOn());
//                    preferences.setStatusNote(verifyUserResponseModel.getStatusNote());
//                    preferences.setEmailVerified(verifyUserResponseModel.getEmailVerfied());
//                    preferences.setCustomPayStatus(verifyUserResponseModel.getAllowCustomEvent());
//
//
//                    if (verifyUserResponseModel.getIsProfileUpdated().equals("1")) {
//                        XMPPManager.getInstance(this, null, this);
//                        preferences.setLoggedIn(true);
//                        preferences.setAppState(PROFILE_UPDATED);
//                        Realm.init(this);
////                        startService(new Intent(this, XMPPMainService.class));
//                        flushContact();
//                        preferences.setDateOfBirth(verifyUserResponseModel.getDob());
//                        preferences.setGender(verifyUserResponseModel.getGender());
//                        preferences.setChatPassword(verifyUserResponseModel.getLoginKey());
//                        preferences.setAppState(AppConstants.AppState.NUMBER_VERIFIED);
//                        preferences.setIsExist(verifyUserResponseModel.getIsProfileUpdated());
//                        preferences.setCountryCode(verifyUserResponseModel.getCountryCode());
//                        preferences.setProfilePicType(verifyUserResponseModel.getProfileType());
//                        preferences.setUname(verifyUserResponseModel.getUname());
//                        preferences.setRegisterdOn(verifyUserResponseModel.getRegisteredOn());
//                        preferences.setStatusNote(verifyUserResponseModel.getStatusNote());
//                        preferences.setEmailVerified(verifyUserResponseModel.getEmailVerfied());
//                        preferences.setCustomPayStatus(verifyUserResponseModel.getAllowCustomEvent());
//                        if (!StringUtils.isNullOrEmpty(verifyUserResponseModel.getAdminNotify())) {
//                            final NotificationMessageModel notificationMessageModel = new Gson().fromJson(verifyUserResponseModel.getAdminNotify(), NotificationMessageModel.class);
//                            preferences.setMajorMinorState(notificationMessageModel.getPushType());
//                            preferences.setAppVersionTime(notificationMessageModel.getAppVersion());
//                            preferences.setUpdateVersionMessage(notificationMessageModel.getMsg());
//                        }
//                        Utility.removeActivity(1);// for removing login acivity
//
//                        if (isMarshMallowOrAvobe()) {
//                            requestAppPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 0, REQUEST_SYNC_CONTACT_AFTER_PERMISSION, this);
//                        } else {
//                            startService(new Intent(this, SyncContactService.class));
//                        }
//
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (hasCredentials()) {
//                                    try {
//                                        // getDefaultSession could trigger an exception if the login data are corrupted
//                                        CommonActivityUtils.logout((Activity) context);
//                                    } catch (Exception e) {
//                                    }
//                                } else if (!hasCredentials()) {
//                                    onLoginClick(getHsConfig(), Nlh.i().mcallhsu()
//                                            , Nlh.i().mcallisu(), preferences.getUserId()
//                                            , preferences.getChatPassword());
//                                }
//                            }
//                        }).start();
//                        finish();
//
//
//                    } else {
//                        Utility.removeActivity(1);// for removing login acivity
//                        Intent intent = new Intent(VerificationActivity.this, UpdateProfileActivity.class);
//                        intent.putExtra(Constants.Params.TITLE, "Sign Up");
//                        intent.putExtra(Constants.UPDATE_PROFILE, false);
//                        startActivity(intent);
//                        finish();
//                    }
//
//
//                } else {
//                    removeProgressDialog();
//                    Utility.showToast(this, verifyUserResponseModel.getMessage());
//                }
//            } else if (isSuccess && responseCode == Constants.RequestType.SEND_OTP) {
//                statrtTimer();
//                progressDialog.dismiss();
//                OtpResponseModel otpResponseModel = new Gson().fromJson(response, OtpResponseModel.class);
//                if (otpResponseModel.getUserId() != null)
//                    preferences.setUserId(otpResponseModel.getUserId());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionGranted(requestCode, true);
        } else {
            onPermissionGranted(requestCode, false); // just for testing need to comment it
        }

    }

    public void onPermissionGranted(int requestCode, boolean status) {


    }

}
