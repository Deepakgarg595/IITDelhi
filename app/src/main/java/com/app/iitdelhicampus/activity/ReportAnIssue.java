package com.app.iitdelhicampus.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.ToastUtils;

import org.json.JSONObject;

public class ReportAnIssue extends AppCompatActivity implements OnUpdateResponse {
private TextView txtMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg=findViewById(R.id.txtMsg);
        ImageView imgBack=(ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        CustomTextViewBold btnSubmit=(CustomTextViewBold)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtMsg.getText().toString().trim().length()!=0)
                {
                    hitApiUpdateAttendance(ReportAnIssue.this);
                }
                else {
                    ToastUtils.showToast(ReportAnIssue.this,"Please enter issue.");
                }
            }
        });
    }

    public void hitApiUpdateAttendance(Context context) {
        AppPreferences preferences = AppPreferences.getInstance(context);
        try {

            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("Description", txtMsg.getText().toString().trim());
                requestParams.put("EmployeeCode", preferences.getEmpCode());

            } catch (Exception e) {
                e.printStackTrace();
            }
//            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(LOG_COMPLAIN, requestParams, this, Constants.RequestType.LOG_COMPLAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (isSuccess)
        {
            ToastUtils.showToast(ReportAnIssue.this,"Issue Reported Successfully.");
            finish();
        }

    }
}
