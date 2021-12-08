package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private Button cvContinue;
    private EditText edtFirst;
    private EditText edtSecond;
    private EditText edtThird;
    private EditText edtFourth;
    private EditText edtFifth;
    private EditText edtSixth;
    private LinearLayout llFirst;
    private LinearLayout llSecond;
    private TextView txtTimer;
    private TextView txtPhoneInfo;
    private LinearLayout llThird;
    private LinearLayout llFourth;
    private LinearLayout llFifth;
    private LinearLayout llSixth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        mAuth = FirebaseAuth.getInstance();

        imgBack = findViewById(R.id.imgBack);   // TextView
        txtTimer = findViewById(R.id.txtTimer);   // TextView
        txtPhoneInfo = findViewById(R.id.txtPhoneInfo);   // TextView
        edtFirst = findViewById(R.id.edtFirst);   // EditText
        edtSecond = findViewById(R.id.edtSecond);   // EditText
        edtThird = findViewById(R.id.edtThird);   // EditText
        edtFourth = findViewById(R.id.edtFourth);   // EditText
        edtFifth = findViewById(R.id.edtFifth);   // EditText
        edtSixth = findViewById(R.id.edtSixth);   // EditText
        txtPhoneInfo.setText("We have sent you an access code via SMS for Mobile number " + preferences.getCountryCode() + " " + preferences.getPhone());
//        txtPhoneInfo!!.text="Ple;   //e Enter the six digits OTP sent on "+preferences!!.countryCode+"."+preferences!!.mobile.toString().substring(0,3)+"."+preferences!!.mobile.toString().substring(4,7)+".✱✱✱✱"
        llFirst = findViewById(R.id.llFirst);   // LinearLayout
        llSecond = findViewById(R.id.llSecond);   // LinearLayout
        llThird = findViewById(R.id.llThird);   // LinearLayout
        llFourth = findViewById(R.id.llFourth);   // LinearLayout
        llFifth = findViewById(R.id.llFifth);   // LinearLayout
        llSixth = findViewById(R.id.llSixth);   // LinearLayout
        cvContinue = findViewById(R.id.btnVerifyNumber);   // ImageView
        initFireBaseCallbacks();

        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 60000) {
                    txtTimer.setText("Expire in 01:" + millisUntilFinished / 2000);
                } else {
                    if (millisUntilFinished < 10000) {
                        txtTimer.setText("Expire in 00:0" + millisUntilFinished / 1000);
                    } else {
                        txtTimer.setText("Expire in 00:" + millisUntilFinished / 1000);
                    }
                }
            }

            @Override
            public void onFinish() {
                txtTimer.setText("Didn't get code? Resend");
            }
        }.start();

        txtTimer.setOnClickListener(this);
        cvContinue.setOnClickListener(this);
        imgBack.setOnClickListener(this);


        edtFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    edtSecond.requestFocus();
                    llFirst.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                } else {
                    llFirst.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                }
            }
        });


        edtSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    edtThird.requestFocus();
                    llSecond.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                } else {
                    llSecond.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                    edtFirst.requestFocus();
                }
            }
        });


        edtThird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    edtFourth.requestFocus();
                    llThird.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                } else {
                    llThird.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                    edtSecond.requestFocus();
                }
            }
        });


        edtFourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    edtFifth.requestFocus();
                    llFourth.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                } else {
                    llFourth.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                    edtThird.requestFocus();
                }
            }
        });


        edtFifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    edtSixth.requestFocus();
                    llFifth.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                } else {
                    llFifth.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                    edtFourth.requestFocus();
                }
            }
        });


        edtSixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    llSixth.setBackground(getResources().getDrawable(R.drawable.boarder_red));
                    hideSoftKey();
//                    verifyOTPFCM(edtFirst.getText().toString().trim()+
//                            edtSecond.getText().toString().trim()+
//                            edtThird.getText().toString().trim()+
//                            edtFourth.getText().toString().trim()+
//                            edtFifth.getText().toString().trim()+
//                            edtSixth.getText().toString().trim());
                } else {
                    edtFifth.requestFocus();
                    llSixth.setBackground(getResources().getDrawable(R.drawable.boarder_grey));
                }
            }
        });

        sendOTPFCM(preferences.getCountryCode() + preferences.getPhone());

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnVerifyNumber:
                if (!StringUtils.isNullOrEmpty(edtSixth.getText().toString().trim())) {
//         todo uncomment it for api  integration
//                    goToNext();
                    verifyOTPFCM(edtFirst.getText().toString().trim() +
                            edtSecond.getText().toString().trim() +
                            edtThird.getText().toString().trim() +
                            edtFourth.getText().toString().trim() +
                            edtFifth.getText().toString().trim() +
                            edtSixth.getText().toString().trim());
                } else {
                    showAlertDialog(this, "", "Please enter OTP.");
                }
                break;
            case R.id.txtTimer:
                if (txtTimer.getText().toString().equals("Didn't get code? Resend")) {
                    reSendCodeFCM(preferences.getCountryCode() + preferences.getPhone());
                }
                break;
            case R.id.imgBack:
                finish();
                break;
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
                            goToNext();
//                            checkExistingUser();
//                            Toast.makeText(LoginActivity.this, "Verification Success", Toast.LENGTH_LONG).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPVerificationActivity.this, "Verification Failed, Invalid credentials", Toast.LENGTH_LONG).show();
                            }

//                            etOtpHolder.getText().clear();
//                            btnVerifyOTP.setEnabled(true);
//                            pbar.setVisibility(View.GONE);
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
                try {
                    edtFirst.setText(credential.getSmsCode().substring(0, 1));
                    edtSecond.setText(credential.getSmsCode().substring(1, 2));
                    edtThird.setText(credential.getSmsCode().substring(2, 3));
                    edtFourth.setText(credential.getSmsCode().substring(3, 4));
                    edtFifth.setText(credential.getSmsCode().substring(4, 5));
                    edtSixth.setText(credential.getSmsCode().substring(5, 6));
                }catch (Exception e){
                    e.printStackTrace();
                }
//                credential.getSmsCode();
//                Toast.makeText(LoginActivity.this, "Verification Completed.", Toast.LENGTH_LONG).show();
                goToNext();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTPVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                btnVerifyOTP.setEnabled(true);
//                btnVerifyNumber.setEnabled(true);
//                pbar.setVisibility(View.GONE);
//                etOtpHolder.getText().clear();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                txtPhoneInfo.setText("We have sent you an access code via SMS for Mobile number " + preferences.getCountryCode() + " " + preferences.getPhone());

//                Toast.makeText(OTPVerificationActivity.this, "Verification Code Sent.", Toast.LENGTH_LONG).show();
//                pbar.setVisibility(View.GONE);
//                tvVerificationCode.setText("Enter verification code that we have sent on " + txvCountryCode.getText().toString().trim() + phoneNumber.getText().toString().trim());
//
//                llNumberView.setVisibility(View.GONE);
//                ll_verification.setVisibility(View.VISIBLE);
//                btnVerifyOTP.setEnabled(true);


                mVerificationId = verificationId;
                mResendToken = token;
//                startCountDownTimer();
            }
        };
    }

    private void goToNext() {

//        Class clz=TLProfileActivity.class;
//        if(preferences.getUserType().equalsIgnoreCase("client")){
//            clz=DashBoardActivityKotlin.class;
//        }
        Intent intent = new Intent(OTPVerificationActivity.this, DashBoardActivityKotlin2.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        preferences.setLoggedIn(true);
        //-------------------------------------------------------
        finish();



//        try {
//            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            preferences.setUserId(userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        updateFcmToken();
//        Intent intent = new Intent(OTPVerificationActivity.this, DashBoardActivity.class);
//        preferences.setLoggedIn(true);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
    }

//    private void updateFcmToken() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        VisitorModel visitorModel = new VisitorModel();
//        visitorModel.setToken(preferences.getDeviceToken());
//        visitorModel.setUser_name(preferences.getName());
//        visitorModel.setMobile(preferences.getPhone());
//        visitorModel.setStatus("Pending");
//
//        db.collection("fcm_token_collection").document(preferences.getPhone()).set(visitorModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.e("FCM", "FCM UPDATED");
//            }
//        });
//    }

}