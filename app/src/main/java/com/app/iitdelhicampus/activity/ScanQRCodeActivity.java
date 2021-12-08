package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler, OnUpdateResponse {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private String QRCode;
    private ImageView  imgBack;
    private String cCode;
    private String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        mScannerView = (ZXingScannerView) findViewById(R.id.scannerView);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mayRequestCamera();
    }

    @Override
    public void onBackPressed() {
        CreateEventModel.getInstance(false).setQRDescription(QRCode);
        finish();
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openScanner();
            return true;
        } else if (ScanQRCodeActivity.this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
    public void onResume() {
        super.onResume();

        mayRequestCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

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
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            QRCode = rawResult.getText();
            mScannerView.resumeCameraPreview(this);
            mScannerView.stopCamera();
            CreateEventModel.getInstance(false).setQRDescription(QRCode);
            ToastUtils.showToast(this,"QR Code Scanned Successfully.");
            if (ConnectivityUtils.isNetworkEnabled(this)) {
//                hitLoginWithQRCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent=new Intent();
        intent.putExtra(Constants.EXTRA_DATA,QRCode);
        setResult(RESULT_OK,intent);
        finish();
    }



    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        removeProgressDialog();

        }
    }
