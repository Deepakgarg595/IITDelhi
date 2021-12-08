package com.app.iitdelhicampus.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.MarkAttendanceListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.MarkAttendanceModel;
import com.app.iitdelhicampus.model.SortingComparator;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


/**
 * Created by pawan on 25/10/2020.
 */

public class MarkedAttendanceListActivity extends BaseActivity {
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    private CustomEditText editSearch;
    private MarkAttendanceListAdapter reviewIncidentListAdapter;
    private ArrayList<MarkAttendanceModel> listTemp = new ArrayList();
    private CustomTextViewBold txtHeader;
    private ImageView ivCancel;
    private ImageView ivSearch;
    ArrayList<MarkAttendanceModel> listData = new ArrayList<>();
    private ImageView ivPlus;
    private CustomTextViewBold tvDefault;
    private ImageView iv_filter;
    private CustomTextView tvDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        editSearch = (CustomEditText) findViewById(R.id.editSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        tvDate=(CustomTextView)findViewById(R.id.tvDate);
        tvDate.setText(Utility.getReportDate(System.currentTimeMillis()));
        iv_filter=(ImageView)findViewById(R.id.iv_filter);

        tvDefault = (CustomTextViewBold) findViewById(R.id.tvDefault);
        tvDefault.setVisibility(View.GONE);
        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         ivPlus=(ImageView)findViewById(R.id.ivPlus);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
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
                ivPlus.setVisibility(View.VISIBLE);
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
                ivPlus.setVisibility(View.GONE);

            }
        });

        initRecycleViewData();
//        getExistingData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExistingData(Utility.getReportDate(System.currentTimeMillis()));
    }

    private void getExistingData(String date){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.COLLECTION_ATTENDANCE).whereEqualTo("userId",preferences.getUserId()).whereEqualTo("date",date).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                listData.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    MarkAttendanceModel taskModel = document.toObject(MarkAttendanceModel.class);
                                    String key=document.getId();
//                                    taskModel.setReportId(key);
                                    listData.add(taskModel);
                                    Collections.sort(listData,new SortingComparator());


                                }
                                if(listData.size()>0){
                                    tvDefault.setVisibility(View.GONE);
                                }
                                reviewIncidentListAdapter.updateListData(listData);
                            } else {
                                listData.clear();
                                reviewIncidentListAdapter.notifyDataSetChanged();
                                tvDefault.setVisibility(View.VISIBLE);
                                Toast.makeText(MarkedAttendanceListActivity.this, "No data found..", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(MarkedAttendanceListActivity.this, "Error Getting Details.", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }




    private void initRecycleViewData() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        reviewIncidentListAdapter = new MarkAttendanceListAdapter(this);
        recyclerView.setAdapter(reviewIncidentListAdapter);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    listTemp.clear();
                    for (MarkAttendanceModel commonDropdownModel : listData) {
                        if(!StringUtils.isNullOrEmpty(commonDropdownModel.getUserName()))
                        if (commonDropdownModel.getUserName().toLowerCase().startsWith(charSequence.toString().trim())) {
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


    public void datePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        c.set(year, monthOfYear, dayOfMonth);
                        String date=Utility.getReportDate(c.getTimeInMillis());
                        tvDate.setText(date);
                        getExistingData(date.trim());

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }
}


