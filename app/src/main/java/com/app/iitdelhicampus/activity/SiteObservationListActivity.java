package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.SiteObservationListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.SiteObservationModel;
import com.app.iitdelhicampus.model.SiteVisitMode;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.app.iitdelhicampus.constants.Constants.RequestType.SITE_OBSERVATION_BY_USER_ID;


/**
 * Created by pawan on 25/10/2020.
 */

public class SiteObservationListActivity extends BaseActivity implements OnUpdateResponse {
    RecyclerView recyclerView;
    ArrayList<SiteVisitMode> listData = new ArrayList<>();
    private ProgressBar progressBar;
    private CustomEditText editSearch;
    private SiteObservationListAdapter reviewIncidentListAdapter;
    private ArrayList<SiteVisitMode> listTemp = new ArrayList();
    private CustomTextViewBold txtHeader;
    private ImageView ivCancel;
    private ImageView ivSearch;
    private ImageView ivPlus;
    private CustomTextViewBold tvDefault;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_observation_list);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);



        tvDefault = (CustomTextViewBold) findViewById(R.id.tvDefault);
        tvDefault.setVisibility(View.GONE);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivPlus = (ImageView) findViewById(R.id.ivPlus);
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SiteObservationListActivity.this, SiteObservationActivity.class);
                startActivity(intent);
            }
        });


        txtHeader = (CustomTextViewBold) findViewById(R.id.txtHeader);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivCancel.setVisibility(View.GONE);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivSearch.setVisibility(View.VISIBLE);
                ivPlus.setVisibility(View.VISIBLE);
                editSearch.getText().clear();
                txtHeader.setVisibility(View.VISIBLE);
                editSearch.setVisibility(View.GONE);
                ivCancel.setVisibility(View.GONE);
            }
        });

        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHeader.setVisibility(View.GONE);
                editSearch.setVisibility(View.VISIBLE);
                ivCancel.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.GONE);
                ivPlus.setVisibility(View.GONE);

            }
        });

        initRecycleViewData();
//        getExistingData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getExistingData();
        getSiteObservationList();
    }


    private void getSiteObservationList() {
        if (!Utility.isInternetAvailable(this)) {
            ToastUtils.showToast(this, "Please check internet connection.");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.SITE_OBSERVATION_BY_USER_ID + preferences.getUserId(), this, SITE_OBSERVATION_BY_USER_ID);

    }

    private void getExistingData() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("site_visit").whereEqualTo("mobile", preferences.getPhone()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                listData.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    SiteVisitMode taskModel = document.toObject(SiteVisitMode.class);
                                    String key = document.getId();
                                    taskModel.setReportId(key);
                                    listData.add(taskModel);


                                }
                                if (listData.size() > 0) {
                                    tvDefault.setVisibility(View.GONE);
                                }
                                reviewIncidentListAdapter.updateListData(listData);
                            } else {
                                tvDefault.setVisibility(View.VISIBLE);
                                Toast.makeText(SiteObservationListActivity.this, "No data found..", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(SiteObservationListActivity.this, "Error Getting Details.", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }


    private void initRecycleViewData() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        reviewIncidentListAdapter = new SiteObservationListAdapter(this);
        recyclerView.setAdapter(reviewIncidentListAdapter);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    listTemp.clear();
                    for (SiteVisitMode commonDropdownModel : listData) {
                        if (!StringUtils.isNullOrEmpty(commonDropdownModel.getClientName()))
                            if (commonDropdownModel.getClientName().toLowerCase().startsWith(charSequence.toString().trim())) {
                                listTemp.add(commonDropdownModel);
                            }
                    }
                    reviewIncidentListAdapter.updateListData(listTemp);
                } else {
                    listTemp.clear();
                    reviewIncidentListAdapter.updateListData(listData);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        progressBar.setVisibility(View.GONE);
        try {
            if (responseCode == SITE_OBSERVATION_BY_USER_ID) {
                SiteObservationModel siteObservationModel = gson.fromJson(response, SiteObservationModel.class);
                listData.clear();
                if (siteObservationModel.isStatus()) {
                    listData = siteObservationModel.getData();
                    if (listData.size() > 0) {
                        tvDefault.setVisibility(View.GONE);
                    } else {
                        tvDefault.setVisibility(View.VISIBLE);
                        Toast.makeText(SiteObservationListActivity.this, "No data found..", Toast.LENGTH_LONG).show();
                    }
                    reviewIncidentListAdapter.updateListData(listData);

                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

