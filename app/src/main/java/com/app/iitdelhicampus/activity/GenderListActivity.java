package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.GenderListAdapter;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.ui.BaseActivity;

import java.util.ArrayList;

public class GenderListActivity extends BaseActivity implements View.OnClickListener/*, OnUpdateResponse*/ {
    private ImageView imgBack;
    private RecyclerView rec;
    private LinearLayoutManager linearLayoutManager;
    private GenderListAdapter assignLeadAdapter;
    private String leadId;
    private boolean loading = true;
    private int pageNum = 1;
    ArrayList<String> gender;
    private TextView txvMaintitle;
    private String title;
    ProgressBar pbar;
    private int pos;

    //    private ArrayList<AssignLeadDetailModel> assignLeadDetailModels;
    private String totalCount = "";
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_list);
        pos = getIntent().getIntExtra("pos", 0);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        gender = new ArrayList<>();
        pbar = (ProgressBar) findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        txvMaintitle = (TextView) findViewById(R.id.txvMaintitle);
        imgBack.setOnClickListener(this);
        title = getIntent().getStringExtra("title");
        txvMaintitle.setText(title);
        linearLayoutManager = new LinearLayoutManager(GenderListActivity.this);
        rec = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(linearLayoutManager);
        assignLeadAdapter = new GenderListAdapter(GenderListActivity.this);
        rec.setAdapter(assignLeadAdapter);


        gender.add("Male");
        gender.add("Female");
        assignLeadAdapter.updateList(gender);

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

//    public void hitMetaData() {
//        try {
//            if (!ConnectivityUtils.isNetworkEnabled(this)) {
//                removeProgressDialog();
//                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            JSONObject requestParams = new JSONObject();
//            try {
//showProgressDialog();
//                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
//                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
//                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
//                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
//                requestParams.put(Constants.Params.REQUEST_ID, String.valueOf(Constants.RequestType.META_DATA));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.META_DATA, requestParams, this, Constants.RequestType.META_DATA);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }


    public void list(String gender) {
        Intent i = new Intent();
        i.putExtra(AppConstants.EXTRA_MULTIPLE_IMAGES, gender);
        i.putExtra("pos", pos);
        setResult(RESULT_OK, i);
        finish();
    }
//
//    @Override
//    public void onResultSuccess(boolean isSuccess, final String response, int responseCode) {
//        removeProgressDialog();
//     if(StringUtils.isNullOrEmptyOrZero(response))
//         return;
//        if (isSuccess) {
//            switch (responseCode) {
//                case Constants.RequestType.META_DATA:
//                            MetaDataResponseModel metaDataResponseModel = new Gson().fromJson(response, MetaDataResponseModel.class);
//                            assignLeadAdapter.updateList(metaDataResponseModel.getStateList());
//                            Log.d(TAG, "onResultSuccess: ");
//                    break;
//            }
//        }
//    }
}
