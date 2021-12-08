package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.Country;
import com.app.iitdelhicampus.model.EmployeeModel;
import com.app.iitdelhicampus.model.FcmDataModel;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.app.iitdelhicampus.constants.Constants.USER_LOGIN;

//import com.google.firebase.crashlytics.FirebaseCrashlytics;


public class LoginWithNumberActivity2 extends BaseActivity implements View.OnClickListener, OnUpdateResponse {

    private static final int REQUEST_SELECT_COUNTRY = 12030;
    Gson gson;
    private ImageView iv_back;
    private Button btnVerifyNumber;
    private Country country;
    private CustomTextView txvCountryCode;
    private CustomTextView txvCountry;
    private LinearLayout selectCountry;
    private CustomEditText phoneNumber;
    private ProgressBar pbar;
    private AppPreferences preferences;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private CustomEditText etOtpHolder;
    private CustomTextView etVerificationCode1, etVerificationCode2, etVerificationCode3, etVerificationCode4, etVerificationCode5, etVerificationCode6;
    private Button btnVerifyOTP;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private LinearLayout ll_verification, llNumberView;
    private CustomTextView tvVerificationCode, tvReSendCode;
//    private String uid;
//    private FirebaseCrashlytics mCrashlytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new2);
        tvVerificationCode = (CustomTextView) findViewById(R.id.tvVerificationCode);
        ll_verification = (LinearLayout) findViewById(R.id.ll_verification);
        llNumberView = (LinearLayout) findViewById(R.id.llNumberView);
        btnVerifyOTP = (Button) findViewById(R.id.btnVerifyOTP);
        btnVerifyOTP.setOnClickListener(this);
        tvReSendCode = (CustomTextView) findViewById(R.id.tvReSendCode);
        tvReSendCode.setOnClickListener(this);
        etOtpHolder = (CustomEditText) findViewById(R.id.etOtpHolder);
        etVerificationCode1 = (CustomTextView) findViewById(R.id.etVerificationCode1);
        etVerificationCode2 = (CustomTextView) findViewById(R.id.etVerificationCode2);
        etVerificationCode3 = (CustomTextView) findViewById(R.id.etVerificationCode3);
        etVerificationCode4 = (CustomTextView) findViewById(R.id.etVerificationCode4);
        etVerificationCode5 = (CustomTextView) findViewById(R.id.etVerificationCode5);
        etVerificationCode6 = (CustomTextView) findViewById(R.id.etVerificationCode6);


        etVerificationCode1.setOnClickListener(this);
        etVerificationCode2.setOnClickListener(this);
        etVerificationCode3.setOnClickListener(this);
        etVerificationCode4.setOnClickListener(this);
        etVerificationCode5.setOnClickListener(this);
        etVerificationCode6.setOnClickListener(this);
        etOtpHolder.addTextChangedListener(new MyTextWatcher(etOtpHolder));


        iv_back = (ImageView) findViewById(R.id.iv_back);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        gson = new Gson();
        btnVerifyNumber = (Button) findViewById(R.id.btnVerifyNumber);
        txvCountry = (CustomTextView) findViewById(R.id.txtCountryName);
        txvCountryCode = (CustomTextView) findViewById(R.id.txtCountryCode);
        selectCountry = (LinearLayout) findViewById(R.id.selectCountry);
        phoneNumber = (CustomEditText) findViewById(R.id.phoneNumber);
        requestAppPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, /*Manifest.permission.ACCESS_BACKGROUND_LOCATION,*/ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0, 0, this);
        showSoftKeyBoard();
        preferences = AppPreferences.getInstance(this);
        iv_back.setOnClickListener(this);
        btnVerifyNumber.setOnClickListener(this);
        selectCountry.setOnClickListener(this);
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        preferences.setDeviceId(deviceId);
        if (country == null) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

            String countryIso = telephonyManager.getSimCountryIso() + "".toUpperCase();
            if (!StringUtils.isNullOrEmpty(countryIso)) {

                String[] countriesInfo = getResources().getStringArray(R.array.CountryCodes);
                for (String countryInfo : countriesInfo) {
                    Country country = new Country(countryInfo);
                    if (country.isoCode.equalsIgnoreCase(countryIso)) {
                        this.country = country;
                        break;
                    }
                }
                if (country != null) {
                    preferences.setCountry(country);
                } else {
                    country = new Country("91,IN");
                    preferences.setCountry(country);
                }
            } else {
                country = new Country("91,IN");
                preferences.setCountry(country);
            }

            setCountryCode();
        }

        if (StringUtils.isNullOrEmpty(preferences.getDeviceToken())) {
//            showDeviceTokenAlertDialog(this, "", "Device token is not updated, Please refresh token.");
            try {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginWithNumberActivity2.this, new OnSuccessListener<InstanceIdResult>() {
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


        mAuth = FirebaseAuth.getInstance();

        initFireBaseCallbacks();

    }


    private void startCountDownTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvReSendCode.setText(getResources().getString(R.string.resend_code) + " in: " + millisUntilFinished / 1000 + " Sec");
                tvReSendCode.setEnabled(false);
            }

            public void onFinish() {
                tvReSendCode.setText(getResources().getString(R.string.resend_code));
                tvReSendCode.setEnabled(true);
            }
        }.start();
    }


    private void verifiedSuccess() {
        Class clz = DashBoardActivityKotlin2.class;
        if (preferences.getUserType().equalsIgnoreCase("client")) {
            clz = DashBoardActivityKotlin2.class;
        }
        Intent intent = new Intent(LoginWithNumberActivity2.this, clz);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        preferences.setLoggedIn(true);
        //-------------------------------------------------------
        finish();
    }

    public void setCountryCode() {
        String countryISO = "";
        String countryCode = "";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        countryISO = manager.getSimCountryIso().toUpperCase();
        countryISO = StringUtils.isNullOrEmpty(countryISO) ? "IN" : countryISO;
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equalsIgnoreCase(countryISO.trim())) {
                countryCode = g[0];
                break;
            }
        }

        String countryName = new Locale("en-US", countryISO).getDisplayCountry();
        if (!StringUtils.isNullOrEmpty(countryName)) {
            txvCountry.setText(countryName);
            txvCountryCode.setText("+" + countryCode);
        }
    }

    public void removeErrorEditText(final EditText textInputLayout, final String error) {

        textInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    textInputLayout.setError(error);
                }

                if (s.length() > 0) {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    @Override
    public void onBackPressed() {

//        if (ll_verification.getVisibility() == View.VISIBLE) {
//            ll_verification.setVisibility(View.GONE);
//            llNumberView.setVisibility(View.VISIBLE);
//            btnVerifyNumber.setEnabled(true);
//            btnVerifyOTP.setEnabled(true);
//        } else {
//            etOtpHolder.getText().clear();
//            super.onBackPressed();
//        }
        finish();

    }

    @Override
    public void onClick(View v) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "No Internet Connection!!");
        }
        switch (v.getId()) {
            case R.id.etVerificationCode1:
            case R.id.etVerificationCode2:
            case R.id.etVerificationCode3:
            case R.id.etVerificationCode4:
            case R.id.etVerificationCode5:
            case R.id.etVerificationCode6:
                etOtpHolder.requestFocus();
                etOtpHolder.requestFocus(0);

                break;
            case R.id.iv_back:
                hideSoftKeyBoard();
                onBackPressed();
                break;
            case R.id.tvReSendCode:
                pbar.setVisibility(View.VISIBLE);
                reSendCodeFCM(txvCountryCode.getText().toString().trim() + phoneNumber.getText().toString().trim());
                break;
            case R.id.btnVerifyOTP:
                if (StringUtils.isNullOrEmpty(etOtpHolder.getText().toString().trim())) {
                    ToastUtils.showToast(this, "Please enter verification code.");
                    return;
                }
                pbar.setVisibility(View.VISIBLE);
                btnVerifyOTP.setEnabled(false);
                verifyOTPFCM(etOtpHolder.getText().toString().trim());
//                verifyPhoneNumberWithCode(mVerificationId, etOtpHolder.getText().toString().trim());
                break;
            case R.id.btnVerifyNumber:
                Log.e("testttt", txvCountryCode.getText().toString());

                String phone = phoneNumber.getText().toString();
                if (!(StringUtils.isNullOrEmpty(phoneNumber.getText().toString()))) {
                    if (phone.length() < 6) {
                        Utility.showToast(this, getString(R.string.valid_number));
//                        phoneNumber.setError(getString(R.string.valid_phone_number));
//                        removeErrorEditText(phoneNumber,getString(R.string.fill_phone_number));
                        return;
                    }

                } else {

//                    Utility.showToast(LoginWithNumberActivity.this, getString(R.string.fill_phone_number));
                    phoneNumber.setError(getString(R.string.fill_phone_number));
                    removeErrorEditText(phoneNumber, getString(R.string.fill_phone_number));
                    return;
                }
                hideKeyboard();
                preferences.setCountryCode("+91");
                preferences.setPhone(phoneNumber.getText().toString().trim());

//                sendOTPFCM(preferences.getCountryCode()+ phoneNumber.getText().toString().trim());
                btnVerifyNumber.setEnabled(false);
                pbar.setVisibility(View.VISIBLE);
//                getApiServer();
//                numberVerificationDialog();
                hitApiUserLogin();
                break;
            case R.id.selectCountry:
                Intent intent = new Intent(this, CountrySelectionActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_COUNTRY);
                break;
        }
    }

    public void numberVerificationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirmation_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.txtConrifrm);
        final CustomTextView txtNumber = (CustomTextView) dialog.findViewById(R.id.txtNumber);
//        preferences.setPhoneWCC(phoneNumber.getText().toString().trim());
        txtNumber.setText(txvCountryCode.getText().toString() + Utility.replaceFirstZero(phoneNumber.getText().toString()));
        text.setText("Is your phone number above correct ?");
        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                preferences.setCountryCode(txvCountryCode.getText().toString());
//                preferences.setPhone(Utility.replaceFirstZero(phoneNumber.getText().toString().trim()));
                dialog.dismiss();
                Intent intent = new Intent(LoginWithNumberActivity2.this, VerificationActivity.class);
                startActivity(intent);
//                hitSendotp(preferences.getPhone());/ uncomment when api integrated
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_COUNTRY) {
            String countryInfo = data.getStringExtra(Constants.EXTRA_SELECTED_COUNTRY);
            country = new Country(countryInfo);
            txvCountryCode.setText(country.countryCode);
            txvCountry.setText(country.name);
            preferences.setCountry(country);
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void hitSendotp(String phoneNumber) {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
//                progressDialog.dismiss();
                pbar.setVisibility(View.GONE);
                return;
            }
            pbar.setVisibility(View.VISIBLE);

//            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//            AppPreferences.getInstance(this).setDeviceId(deviceId);
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.PHONE, phoneNumber);
                requestParams.put(Constants.Params.REQUEST_ID, String.valueOf(Constants.RequestType.SEND_OTP));
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
                requestParams.put(Constants.Params.COUNTRY_CODE, txvCountryCode.getText().toString().trim());
                requestParams.put(Constants.Params.TYPE, "1");

                if (preferences.getUserType().equalsIgnoreCase(Constants.LoginType.FB)) {
                    requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                    requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getUserType());
                    requestParams.put(Constants.Params.SOCIAL_ID, getIntent().getStringExtra(Constants.Params.SOCIAL_ID));

                } else {
                    requestParams.put(Constants.Params.LOGIN_TYPE, preferences.getUserType());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.SEND_OTP, requestParams, this, Constants.RequestType.SEND_OTP);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
//        progressDialog.dismiss();
        btnVerifyOTP.setEnabled(true);
        btnVerifyNumber.setEnabled(true);
        try {
            pbar.setVisibility(View.GONE);
            etOtpHolder.getText().clear();
            if (isSuccess && responseCode == Constants.RequestType.SEND_OTP) {
                UserLoginModel otpResponseModel = gson.fromJson(response, UserLoginModel.class);
                btnVerifyOTP.setEnabled(true);
                btnVerifyNumber.setEnabled(true);
                if (otpResponseModel != null) {
                    if (otpResponseModel.getStatus().equalsIgnoreCase("TRUE")) {
//                        if (otpResponseModel.getData() != null && otpResponseModel.getData().size() > 0) 
//                        {
//                            preferences.setName(otpResponseModel.getSuccess().getFirstName()+" "+otpResponseModel.getSuccess().getLastName());
//                            preferences.setUserType(otpResponseModel.getSuccess().getDesignation());
//                            preferences.setEmail(otpResponseModel.getSuccess().getEmail());
//                            preferences.setEmpCode(otpResponseModel.getSuccess().getEmpCode());
//                            preferences.setUserId(otpResponseModel.getSuccess().getUserId());
//                            preferences.setTeamLead(otpResponseModel.getSuccess().getTeamlead());
//                            preferences.setTeamLeadID(otpResponseModel.getSuccess().getTeam_lead_id());
//                            preferences.setDepartment(otpResponseModel.getSuccess().getDepartment());

//                            ToastUtils.showToast(LoginWithNumberActivity.this, "Verified successfully.");
                        pbar.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(LoginWithNumberActivity2.this, OTPVerificationActivity.class);
                        startActivity(intent);
//                     todo  NC     sendOTPFCM(preferences.getCountryCode()+ phoneNumber.getText().toString().trim());
//                            verifiedSuccess();
//                        } 
//                        else {
//                            showAlertDialog(this, "Alert!", otpResponseModel.getMsg());
//
//                        }
                    } else {
                        showAlertDialog(this, "Alert!", otpResponseModel.getMessage());

                    }
                }

            } else if (isSuccess && responseCode == Constants.RequestType.USER_LOGIN) {
                UserLoginModel employeeModel = gson.fromJson(response, UserLoginModel.class);
                preferences.setBannerResponse(response);
                if (employeeModel != null) {
                    if (!employeeModel.getStatus().equalsIgnoreCase("TRUE")) {
                        showAlertDialog(this, "", "Number does not exist.");
                        return;
                    }
//                    for (EmployeeModel.EmpList employeeModel.getSuccess() : employeeModel.getSuccess()) {

//                        if (!(preferences.getDeviceId()+"_"+preferences.getPhone()).equalsIgnoreCase(employeeModel.getSuccess().getDeviceId())) {
//                            showAlertDialog(this, "", "Your Number and deviceId did not mach, Please contact to Admin");
//                            return;
//                        }
                    preferences.setName(employeeModel.getSuccess().get(0).getFirstName() + " " + employeeModel.getSuccess().get(0).getLastName());
                    preferences.setRoleName(employeeModel.getSuccess().get(0).getRoleName());
//                    preferences.setEmail(employeeModel.getSuccess().get(0).getEmail());
                    preferences.setMobile(employeeModel.getSuccess().get(0).getMobile());
                    preferences.setGender(employeeModel.getSuccess().get(0).getGender());
                    preferences.setEmpCode_(employeeModel.getSuccess().get(0).getEmployeeCode());
                    preferences.setEmpCode(employeeModel.getSuccess().get(0).getEmployee_card());
                    preferences.setUserId(employeeModel.getSuccess().get(0).getEmployee_card());
                    preferences.setStaticLatitude(employeeModel.getSuccess().get(0).getLatitude());
                    preferences.setStaticLongitude(employeeModel.getSuccess().get(0).getLongitude());
                    preferences.setBranchId(employeeModel.getSuccess().get(0).getBranchId());
                    preferences.setRadiusInMeter(employeeModel.getSuccess().get(0).getRadius());
//                    preferences.setTeamLead(employeeModel.getSuccess().get(0).getTeamlead());
//                    preferences.setTeamLeadID(employeeModel.getSuccess().get(0).getTeam_lead_id());
                    preferences.setDepartment(employeeModel.getSuccess().get(0).getDepartmentName());
                    pbar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(LoginWithNumberActivity2.this, OTPVerificationActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(this, "Some error occurred.");
//            showAlertDialog(this, getString(R.string.app_name), otpResponseModel.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendOTPFCM(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone.trim(),        // Phone number to verify
                1,                 // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyOTPFCM(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code.trim());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            checkExistingUser();
//                            Toast.makeText(LoginWithNumberActivity.this, "Verification Success", Toast.LENGTH_LONG).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginWithNumberActivity2.this, "Verification Failed, Invalid credentials", Toast.LENGTH_LONG).show();
                            }
                            etOtpHolder.getText().clear();
                            btnVerifyOTP.setEnabled(true);
                            pbar.setVisibility(View.GONE);
                            // The verification code enter
                        }
                    }
                });

    }

    private void reSendCodeFCM(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone.trim(),        // Phone number to verify
                1,               // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);
    }

    void initFireBaseCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                Toast.makeText(LoginWithNumberActivity.this, "Verification Completed.", Toast.LENGTH_LONG).show();
                checkExistingUser();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginWithNumberActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                btnVerifyOTP.setEnabled(true);
                btnVerifyNumber.setEnabled(true);
                pbar.setVisibility(View.GONE);
                etOtpHolder.getText().clear();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                Toast.makeText(LoginWithNumberActivity2.this, "Verification Code Sent.", Toast.LENGTH_LONG).show();
                pbar.setVisibility(View.GONE);
                tvVerificationCode.setText("Enter verification code that we have sent on " + txvCountryCode.getText().toString().trim() + phoneNumber.getText().toString().trim());

                llNumberView.setVisibility(View.GONE);
                ll_verification.setVisibility(View.VISIBLE);
                btnVerifyOTP.setEnabled(true);


                mVerificationId = verificationId;
                mResendToken = token;
                startCountDownTimer();
            }
        };
    }


    private void getApiServer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("api_server")/*.whereEqualTo("author", uid)*/.get()
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
                                    LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.CHECK_USER + preferences.getPhone(), LoginWithNumberActivity2.this, Constants.RequestType.SEND_OTP);

                                }
                            } else {
                                LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.CHECK_USER + preferences.getPhone(), LoginWithNumberActivity2.this, Constants.RequestType.SEND_OTP);

                            }
                        }
                    }
                });
    }


    public void hitApiUserLogin() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("token_id", preferences.getDeviceToken());
                requestParams.put("mobile", preferences.getPhone());
                requestParams.put("password", "");
                requestParams.put("device_id", preferences.getDeviceId() + "_" + preferences.getPhone());

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(USER_LOGIN, requestParams, this, Constants.RequestType.USER_LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkExistingUser() {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "No Internet Connection!!");
        }

        pbar.setVisibility(View.GONE);
        verifiedSuccess();
//        getApiServer();


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("orion_tl_collection")
//                .whereEqualTo("mobile", txvCountryCode.getText().toString().trim() + phoneNumber.getText().toString().trim()).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            if (task.getResult().size() > 0) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    FcmDataModel taskModel = document.toObject(FcmDataModel.class);
//                                    preferences.setName(taskModel.getName());
//                                    preferences.setPhone(taskModel.getMobile());
//                                    preferences.setUserType(taskModel.getUserType());
//                                    preferences.setCountryCode(taskModel.getCountryCode());
//                                    Class clz = TLProfileActivity.class;
//                                    if (taskModel.getUserType().equalsIgnoreCase("po")) {
//                                        clz = POProfileActivity.class;
//                                    }
//                                    ToastUtils.showToast(LoginWithNumberActivity.this, "Verified successfully.");
//                                    pbar.setVisibility(View.GONE);
//                                    verifiedSuccess(clz);
//
//                                }
//                            } else {
//                                Toast.makeText(LoginWithNumberActivity.this, "User does not exist.", Toast.LENGTH_LONG).show();
//                                btnVerifyOTP.setEnabled(true);
//                            }
//
//                        } else {
//                            Toast.makeText(LoginWithNumberActivity.this, "Error Getting Details.", Toast.LENGTH_LONG).show();
//                            btnVerifyOTP.setEnabled(true);
//                        }
//                        btnVerifyOTP.setEnabled(true);
//                        pbar.setVisibility(View.GONE);
//                    }
//                });
    }

    class MyTextWatcher implements TextWatcher {

        private final View view;

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

            String code = etOtpHolder.getText().toString();
            if (code.length() == 6) {
//                verifiedSuccess();
                pbar.setVisibility(View.VISIBLE);
                verifyOTPFCM(code);
            }


        }
    }

}
