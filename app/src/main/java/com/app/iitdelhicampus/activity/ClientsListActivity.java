package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ClientListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;

import java.util.ArrayList;

import static com.app.iitdelhicampus.constants.Constants.RequestType.GETALL_CLIENTLIST;


/**
 * Created by pawan on 18/12/2020.
 */

public class ClientsListActivity extends BaseActivity implements OnUpdateResponse {
    RecyclerView recyclerView;
    private AppPreferences preferences;
    private ProgressBar progressBar;
    private CustomEditText editSearch;
    private ClientListAdapter attendanceListAdapter;
    private ArrayList<ClientListModel.Data> listTemp = new ArrayList();
    private CustomTextViewBold txtHeader;
    private ImageView ivCancel;
    private ImageView ivSearch;
    private ArrayList<ClientListModel.Data> listData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_list);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

            }
        });

        listData=getIntent().getParcelableArrayListExtra(Constants.EXTRA_DATA);// uncomment it
        if (listData == null) {
            listData = new ArrayList<>();
            getClientList();
        }
        initRecycleViewData();
    }

    private void getClientList() {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            ToastUtils.showToast(this, "Please check your internet connection.");
        }
        progressBar.setVisibility(View.VISIBLE);
        LoopJRequestHandler.getInstance().hitApiGetMethod(Constants.GETALL_CLIENTLIST, this, GETALL_CLIENTLIST);

    }

    private void initRecycleViewData() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        attendanceListAdapter = new ClientListAdapter(this);
        recyclerView.setAdapter(attendanceListAdapter);
        attendanceListAdapter.updateListData(listData);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    listTemp.clear();
                    for (ClientListModel.Data commonDropdownModel : listData) {
                        if (commonDropdownModel.getUnit_name().toLowerCase().startsWith(charSequence.toString().trim())) {
                            listTemp.add(commonDropdownModel);
                        }
                    }
                    attendanceListAdapter.updateListData(listTemp);
                } else {
                    listTemp.clear();
                    attendanceListAdapter.updateListData(listData);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void setResultData(ClientListModel.Data data) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_DATA, data);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        progressBar.setVisibility(View.GONE);
        try {
            if (responseCode == GETALL_CLIENTLIST) {

                if (!StringUtils.isNullOrEmpty(response)) {
                    ClientListModel clientListModel = gson.fromJson(response, ClientListModel.class);

                    listData.addAll(clientListModel.getData());//uncomment is and comment above for loop
                    attendanceListAdapter.updateListData(listData);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


