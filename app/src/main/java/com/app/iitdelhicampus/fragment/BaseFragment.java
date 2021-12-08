package com.app.iitdelhicampus.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();

    public BaseActivity mActivity;
    public ProgressDialog progressDialog;
    private float density;
    protected AppPreferences preferences;
    private SparseArray mErrorString;
    public Gson gson=new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = mActivity.getResources().getDisplayMetrics().density;
        progressDialog = new ProgressDialog(getActivity());
        preferences= AppPreferences.getInstance(mActivity);
        progressDialog.setCancelable(false);
        String Load = getString(R.string.load);
        progressDialog.setMessage(Load);
        mErrorString =new SparseArray();
    }

    public float getDensity() {
        return density;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void onImageSelectionComplete(File file) {
        mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, FileUtils.getUri(file, mActivity)));
    }

    public void removeProgressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) return;
        progressDialog.dismiss();
    }


    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
        }

        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        progressDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    progressDialog.setCancelable(true);
                    progressDialog.dismiss();
                }
                return true;
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }



    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    public File createImageFileWith() throws IOException {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "MEDIA_" + timestamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Video");
        storageDir.mkdirs();
        return File.createTempFile(imageFileName, ".mp4", storageDir);
    }




    public void requestAppPermissions(final String[] requestPermissions, final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        boolean showRequestPermission = false;
        for (String permission : requestPermissions) {
            if (permissionCheck != ContextCompat.checkSelfPermission(getActivity(), permission))
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(getActivity(), permission);

//            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), requestPermissions, requestCode);

        } else {
            onPermissionGranted(requestCode);
        }
    }

    private void onPermissionGranted(int requestCode) {

    }

}
