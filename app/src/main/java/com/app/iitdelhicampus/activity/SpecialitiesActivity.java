package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.SpecialitiesAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.MetaDataDetailModel;
import com.app.iitdelhicampus.model.MetaDataResponseModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpecialitiesActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {
    private ImageView imgBack;
    private RecyclerView rec;
    private LinearLayoutManager linearLayoutManager;
    private SpecialitiesAdapter specialitiesAdapter;
    private TextView txvMaintitle;
    private String title;
    ProgressBar pbar;
    private TextView txtDone;
    MetaDataResponseModel metaDataResponseModel;
    private String type = "";
    private ArrayList<MetaDataDetailModel> selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_list);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        selectedList = new ArrayList<>();
        txtDone = (TextView) findViewById(R.id.txtDone);
        txtDone.setOnClickListener(this);
        txtDone.setVisibility(View.VISIBLE);
        txvMaintitle = (TextView) findViewById(R.id.txvMaintitle);
        imgBack.setOnClickListener(this);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        txvMaintitle.setText(title);

        linearLayoutManager = new LinearLayoutManager(SpecialitiesActivity.this);
        rec = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(linearLayoutManager);
        specialitiesAdapter = new SpecialitiesAdapter(SpecialitiesActivity.this);
        rec.setAdapter(specialitiesAdapter);


        hitMetaData();

        if (!StringUtils.isNullOrEmpty(preferences.getMetaDataResponse())) {
            metaDataResponseModel = gson.fromJson(preferences.getMetaDataResponse(), MetaDataResponseModel.class);
            specialitiesAdapter.updateList(metaDataResponseModel.getSpecialities(), type);
            pbar.setVisibility(View.GONE);

        }

    }

    public void hitMetaData() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.GET_META_DATA);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.GET_META_DATA, requestParams, this, Constants.RequestType.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtDone:
                Intent i = new Intent();
                switch (type) {

                    case "Speciality":
                        selectedList.clear();
                        i.putExtra("type", "Speciality");
                        selectedList.addAll(getSelectedList(specialitiesAdapter.getList()));
                        i.putExtra("list", selectedList);

                        break;

                }
                setResult(RESULT_OK, i);
                finish();
                break;

        }
    }

    private ArrayList<MetaDataDetailModel> getSelectedList(ArrayList<MetaDataDetailModel> list) {
        ArrayList<MetaDataDetailModel> selectedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                selectedList.add(list.get(i));
            }
        }
        return selectedList;
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (StringUtils.isNullOrEmpty(response)) {
            return;
        }
        if (isSuccess) {
            switch (responseCode) {
                case Constants.RequestType.GET_META_DATA:
                    MetaDataResponseModel metaDataResponseModel = gson.fromJson(response, MetaDataResponseModel.class);
                    preferences.setMetaDataResponse(response);
                    specialitiesAdapter.updateList(metaDataResponseModel.getSpecialities(), type);


            }
        }

    }

}
