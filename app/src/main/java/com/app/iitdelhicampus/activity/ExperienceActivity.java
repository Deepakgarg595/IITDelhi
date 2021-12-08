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
import com.app.iitdelhicampus.adapter.ExperienceAdapter;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.MetaDataResponseModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ExperienceActivity extends BaseActivity implements View.OnClickListener, OnUpdateResponse {
    private ImageView imgBack;
    private RecyclerView rec;
    private LinearLayoutManager linearLayoutManager;
    private ExperienceAdapter experienceAdapter;
    private String leadId;
    private boolean loading = true;
    private int pageNum = 1;
    private TextView txvMaintitle;
    private String title;
    ProgressBar pbar;
    private int pos;
    MetaDataResponseModel metaDataResponseModel;
    //    private ArrayList<AssignLeadDetailModel> assignLeadDetailModels;
    private String totalCount = "";
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_list);
        pos = getIntent().getIntExtra("pos", 0);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        pbar = (ProgressBar) findViewById(R.id.pbar);

        pbar.setVisibility(View.GONE);
        txvMaintitle = (TextView) findViewById(R.id.txvMaintitle);
        imgBack.setOnClickListener(this);
        title = getIntent().getStringExtra("title");
        txvMaintitle.setText(title);
        linearLayoutManager = new LinearLayoutManager(ExperienceActivity.this);
        rec = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(linearLayoutManager);
        experienceAdapter = new ExperienceAdapter(ExperienceActivity.this);
        rec.setAdapter(experienceAdapter);

        hitMetaData();


        if (!StringUtils.isNullOrEmpty(preferences.getMetaDataResponse())) {
            metaDataResponseModel = gson.fromJson(preferences.getMetaDataResponse(), MetaDataResponseModel.class);
            experienceAdapter.updateList(metaDataResponseModel.getExperience());
            pbar.setVisibility(View.GONE);
        }

//        hitMetaData();
//        rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0) //check for scroll down
//                {
//                    visibleItemCount = linearLayoutManager.getChildCount();
//                    totalItemCount = linearLayoutManager.getItemCount();
//                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
//
//                    if (loading) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            loading = false;
//                            Log.v("...", "Last Item Wow !");
//                            if (!String.valueOf(assignLeadDetailModels.size()).equalsIgnoreCase(totalCount)) {
//                                hitMetaData();
//                            }
//                            //Do pagination.. i.e. fetch new data
//                        }
//                    }
//                }
//            }
//        });

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
        }
    }


    public void list(String experience) {
        Intent i = new Intent();
        i.putExtra(AppConstants.EXTRA_MULTIPLE_IMAGES, experience);
        i.putExtra("pos", pos);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (StringUtils.isNullOrEmpty(response)){
            return;
        }
        if (isSuccess) {
            switch (responseCode){
                case Constants.RequestType.GET_META_DATA:
                    MetaDataResponseModel metaDataResponseModel= gson.fromJson(response,MetaDataResponseModel.class);
                    preferences.setMetaDataResponse(response);
                    break;
            }
        }

    }
}
