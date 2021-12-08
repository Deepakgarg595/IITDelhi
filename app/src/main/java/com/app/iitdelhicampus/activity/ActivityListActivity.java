package com.app.iitdelhicampus.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ActivityListAdapter;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextViewBold;

import java.util.ArrayList;


/**
 * Created by pawan on 25/10/2020.
 */

public class ActivityListActivity extends BaseActivity {
    private AppPreferences preferences;
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    private CustomEditText editSearch;
    private ActivityListAdapter activityListAdapter;
    private ArrayList listTemp = new ArrayList();
    private CustomTextViewBold txtHeader;
    private ImageView ivCancel;
    private ImageView ivSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtHeader=(CustomTextViewBold)findViewById(R.id.txtHeader);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivCancel.setVisibility(View.GONE);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivSearch.setVisibility(View.VISIBLE);
                editSearch.getText().clear();
                txtHeader.setVisibility(View.VISIBLE);
                editSearch.setVisibility(View.GONE);
                ivCancel.setVisibility(View.GONE);
            }
        });

        ivSearch=(ImageView)findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHeader.setVisibility(View.GONE);
                editSearch.setVisibility(View.VISIBLE);
                ivCancel.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.GONE);

            }
        });

        initRecycleViewData();
    }

    private void initRecycleViewData() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        activityListAdapter = new ActivityListAdapter(this);
        recyclerView.setAdapter(activityListAdapter);
        final ArrayList<CommonDropdownModel> listData = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
            commonDropdownModel.setName("Employee" + i);
            commonDropdownModel.setSeverity("Designation");
            listData.add(commonDropdownModel);

        }
        activityListAdapter.updateListData(listData);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    listTemp.clear();
                    for (CommonDropdownModel commonDropdownModel : listData) {
                        if (commonDropdownModel.getName().toLowerCase().startsWith(charSequence.toString().trim())) {
                            listTemp.add(commonDropdownModel);
                        }
                    }
                    activityListAdapter.updateListData(listTemp);
                } else {
                    listTemp.clear();
                    activityListAdapter.updateListData(listData);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


}


