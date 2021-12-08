package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRandClientListActivity extends BaseActivity implements ZXingScannerView.ResultHandler, OnUpdateResponse {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private String QRCode;
    private ImageView  imgBack;
    private String cCode;
    private String Phone;
    private LinearLayout llClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_and_client_list);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        mScannerView = (ZXingScannerView) findViewById(R.id.scannerView);
        llClient=(LinearLayout)findViewById(R.id.llClient);
        llClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ScanQRandClientListActivity.this,ClientListActivity.class);
//                startActivity(intent);
            }
        });
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
        } else if (ScanQRandClientListActivity.this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
        finish();
    }



    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        removeProgressDialog();

        }
    }
