package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.ClientDataForPOModel;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.app.iitdelhicampus.constants.Constants.RequestType.GET_CLIENT_BY_QR_CODE;


public class POReportIncidentActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {

    public static POReportIncidentActivity poContext;
    private ImageView iv_back;
    private CustomTextViewBold btnCheckList;
    private ProgressBar pbar;
    private ImageView ivScanQRCode;
    private CustomEditText etTeamLead, etClientName;
    private CustomTextView tvQRResponse;
    private String clientCode;
    private FrameLayout frameClientName;
    private CustomEditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_report_incident);
        etTeamLead = (CustomEditText) findViewById(R.id.etTeamLead);
        etTeamLead.setText(preferences.getTeamLead());
        etClientName = (CustomEditText) findViewById(R.id.etClientName);
        etClientName.setOnClickListener(this);
        etLocation = (CustomEditText) findViewById(R.id.etLocation);

        frameClientName = (FrameLayout) findViewById(R.id.frameClientName);
        frameClientName.setVisibility(View.GONE);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        poContext = this;
        gson = new Gson();
        btnCheckList = (CustomTextViewBold) findViewById(R.id.btnCheckList);
        btnCheckList.setOnClickListener(this);
        CreateEventModel.getInstance(false).setQRDescription("");

        LinearLayout llQRView = (LinearLayout) findViewById(R.id.llQRView);
        llQRView.setOnClickListener(this);

        tvQRResponse = (CustomTextView) findViewById(R.id.tvQRResponse);
        showSoftKeyBoard();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivScanQRCode = (ImageView) findViewById(R.id.ivScanQRCode);
        ivScanQRCode.setOnClickListener(this);
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.etClientName:
                Intent intent = new Intent(POReportIncidentActivity.this, ClientsListActivity.class);
//                intent.putExtra(Constants.EXTRA_DATA,listData);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.llQRView:
            case R.id.ivScanQRCode:
                intent = new Intent(this, ScanQRCodeActivity.class);
                startActivityForResult(intent, Constants.REQUEST_QR_CODE);
                break;
            case R.id.btnCheckList:
                if (StringUtils.isNullOrEmpty(etTeamLead.getText().toString().trim())) {
                    Toast.makeText(this, "Please enter Team Lead", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isNullOrEmpty(clientCode)) {
                    Toast.makeText(this, "Please Scan QR Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this, CheckListActivity.class);
                intent.putExtra("TEAM_LEAD", etTeamLead.getText().toString().trim());
                intent.putExtra("CLIENT_NAME", etClientName.getText().toString().trim());
                intent.putExtra("CLIENT_CODE", clientCode);
                intent.putExtra("LOCATION", etLocation.getText().toString().trim());


                startActivity(intent);
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Constants.REQUEST_QR_CODE:
                String qrData = data.getStringExtra(Constants.EXTRA_DATA);
                if (!StringUtils.isNullOrEmpty(qrData)) {
                    hitApiForFetchingClinetList(qrData);
                }
                break;
            case Constants.REQUEST_CODE:
                if (resultCode != RESULT_OK) return;
                ClientListModel.Data listSelected = data.getParcelableExtra(Constants.EXTRA_DATA);
                etClientName.setText(listSelected.getUnit_name());
                etLocation.setText(listSelected.getLocation());
                etLocation.setVisibility(View.VISIBLE);
                etLocation.setClickable(false);
                frameClientName.setVisibility(View.VISIBLE);
                clientCode = listSelected.getUnit_code();

                break;

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
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
//        progressDialog.dismiss();
        pbar.setVisibility(View.GONE);

        try {
            ClientDataForPOModel clientDataForPOModel = gson.fromJson(response, ClientDataForPOModel.class);
            if (clientDataForPOModel.getData() != null && clientDataForPOModel.getData().size() > 0) {
                etClientName.setText(clientDataForPOModel.getData().get(0).getClientName());
                etLocation.setText(clientDataForPOModel.getData().get(0).getLocation());

                clientCode = clientDataForPOModel.getData().get(0).getClientCode();
                frameClientName.setVisibility(View.VISIBLE);
            } else {
                frameClientName.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
