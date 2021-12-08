package com.app.iitdelhicampus.activity;

import static com.app.iitdelhicampus.constants.Constants.SHIFT_DATA;
import static com.app.iitdelhicampus.constants.Constants.UPDATE_SHIFT_DATA;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.ShiftListAdapter;
import com.app.iitdelhicampus.adapter.ShiftMasterAdapter;
import com.app.iitdelhicampus.adapter.ShiftMasterListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.Employee;
import com.app.iitdelhicampus.model.MonthShiftDetailListModel;
import com.app.iitdelhicampus.model.MonthShiftListModel;
import com.app.iitdelhicampus.model.ShiftMasterDetailModel;
import com.app.iitdelhicampus.model.ShiftMasterModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeShiftActivity extends BaseActivity implements OnUpdateResponse {
    private Employee employee;

    private TextView txtProfileName;
    private TextView txtTotal;
    private TextView txtMonth;
    private ImageView imgLeft;
    private ImageView imgRight;
    private ImageView imgBack;
    private CustomTextViewBold tvEmpCode;
    //    private CustomTextView tvEmpName;
    private RecyclerView recEmployees;
    private CustomTextView tvEmpMobile;
    private CustomTextView tvEmpShift;
    private ShiftListAdapter adapter;
    private ShiftMasterModel otpResponseModel;
    private RecyclerView recyclerView;
    private String date;
    private String shiftId;
    private ShiftMasterDetailModel data_;
    private String startDateStr;
    private String endDateStr;
    private String month;
    private boolean isLast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_shift);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        recEmployees = findViewById(R.id.recEmployees);
        txtMonth = (TextView) findViewById(R.id.txtMonth);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        tvEmpCode = (CustomTextViewBold) findViewById(R.id.tvEmpCode);
//        tvEmpName = (CustomTextView) findViewById(R.id.tvEmpName);
        tvEmpMobile = (CustomTextView) findViewById(R.id.tvEmpMobile);
        tvEmpShift = (CustomTextView) findViewById(R.id.tvEmpShift);
        adapter = new ShiftListAdapter(EmployeeShiftActivity.this);
        recEmployees.setLayoutManager(new GridLayoutManager(EmployeeShiftActivity.this, 5));
        recEmployees.setAdapter(adapter);
        employee = getIntent().getParcelableExtra("data");
        txtProfileName = findViewById(R.id.txtProfileName);
        imgBack = findViewById(R.id.imgBack);
        recyclerView = (RecyclerView) findViewById(R.id.recShift);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        otpResponseModel = gson.fromJson(preferences.getShiftData(), ShiftMasterModel.class);
        for (int j = 0; j < otpResponseModel.getSuccess().size(); j++) {
            if (otpResponseModel.getSuccess().get(j).getShiftName().equalsIgnoreCase(employee.getShiftName())) {
                data_ = otpResponseModel.getSuccess().get(j);
            }
        }
        txtTotal.setText("Total Shift: " + otpResponseModel.getSuccess().size());
        txtTotal.setVisibility(View.GONE);
        final ShiftMasterAdapter singleSelectionAdapter = new ShiftMasterAdapter(this);
        recyclerView.setAdapter(singleSelectionAdapter);
        singleSelectionAdapter.updateListData(otpResponseModel.getSuccess());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    isLast = false;
                    currentMonth();
                } else {
                    isLast = true;
                    lastMonth();
                }
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    isLast = false;
                    currentMonth();
                } else {
                    isLast = true;
                    lastMonth();
                }
            }
        });

        if (employee != null) {
            currentMonth();
            txtProfileName.setText("Employee Shift Details");
            tvEmpCode.setText("Emp Name: " + employee.getEmployeeName());
            tvEmpMobile.setText("Mobile: " + employee.getPhone());
            tvEmpShift.setText("Shift: " + employee.getShiftName());

        }

    }

    public void getData(String date, MonthShiftDetailListModel monthShiftDetailListModel) {
        this.date = date;
        if (otpResponseModel.getSuccess() != null && otpResponseModel.getSuccess().size() != 0) {
            initMultiSelectionDialog(otpResponseModel.getSuccess(),monthShiftDetailListModel);
        }
    }

    public void lastMonth() {
        month="";
        endDateStr="";
        startDateStr="";

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();

        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal

        Date lastDateOfPreviousMonth = cal.getTime();


        SimpleDateFormat dfMonth = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
        month = dfMonth.format(firstDateOfPreviousMonth);
        startDateStr = dfDay.format(firstDateOfPreviousMonth);
        endDateStr = dfDay.format(lastDateOfPreviousMonth);
        txtMonth.setText(month);
        imgRight.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.GONE);
        hitApiGetShift(employee.getEmployeeCode());
    }

    public void currentMonth() {
        month="";
        endDateStr="";
        startDateStr="";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();
        SimpleDateFormat dfMonth = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        month = dfMonth.format(monthFirstDay);
        startDateStr = df.format(monthFirstDay);
        endDateStr = df.format(monthLastDay);
        txtMonth.setText(month);
        imgRight.setVisibility(View.GONE);
        imgLeft.setVisibility(View.VISIBLE);
        hitApiGetShift(employee.getEmployeeCode());
    }


    private void initMultiSelectionDialog(List<ShiftMasterDetailModel> success, MonthShiftDetailListModel monthShiftDetailListModel) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_list);
        CustomTextView tvEmpCode = (CustomTextView) dialog.findViewById(R.id.tvEmpCode);
        CustomTextView txtdate = (CustomTextView) dialog.findViewById(R.id.date);
        CustomTextView tvEmpMobile = (CustomTextView) dialog.findViewById(R.id.tvEmpMobile);
        CustomTextView tvEmpShift = (CustomTextView) dialog.findViewById(R.id.tvEmpShift);
        tvEmpCode.setText("Emp Name: " + employee.getEmployeeName());
        tvEmpMobile.setText("Mobile: " + employee.getPhone());
        txtdate.setText(this.date);
        tvEmpShift.setText("Shift: " + employee.getShiftName());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rclist);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        final ShiftMasterListAdapter singleSelectionAdapter = new ShiftMasterListAdapter(this);
        recyclerView.setAdapter(singleSelectionAdapter);
        singleSelectionAdapter.updateListData(success,monthShiftDetailListModel);
        ImageView cross = (ImageView) dialog.findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txtDone = (TextView) dialog.findViewById(R.id.buttonapply);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftId = singleSelectionAdapter.getSelectedShiftId();
                hitApiSaveShiftData(date, shiftId);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void hitApiSaveShiftData(String date, String shiftId) {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog();
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("employeeCode", employee.getEmployeeCode());
                requestParams.put("userId", preferences.getUserId());
                requestParams.put("fromDate", date);
                requestParams.put("shiftId", shiftId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_SHIFT_DATA, requestParams, this, Constants.RequestType.UPDATE_SHIFT_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hitApiGetShift(String employeeCode) {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog();
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("employeeCode", employeeCode);
                requestParams.put("userId", preferences.getUserId());
                requestParams.put("fromDate", startDateStr);
                requestParams.put("toDate", endDateStr);

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(SHIFT_DATA, requestParams, this, Constants.RequestType.SHIFT_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (isSuccess && responseCode == Constants.RequestType.UPDATE_SHIFT_DATA) {
            hitApiGetShift(employee.getEmployeeCode());
        }
        if (isSuccess && responseCode == Constants.RequestType.SHIFT_DATA) {
            removeProgressDialog();
            MonthShiftListModel otpResponseModel = gson.fromJson(response, MonthShiftListModel.class);
            if (otpResponseModel.getSuccess() != null ) {
                adapter.updateListData(otpResponseModel.getSuccess());
//                preferences.setShiftData(response);
            } else {

//                ArrayList<MonthShiftDetailListModel> monthShiftDetailListModels=new ArrayList<>();
//                for (int i=0;i<Integer.parseInt(toDate.substring(8));i++)
//                {
//                    MonthShiftDetailListModel monthShiftDetailListModel=new MonthShiftDetailListModel();
//                    if (data_!=null) {
//                        monthShiftDetailListModel.setShiftId(data_.getShiftId());
//                        monthShiftDetailListModel.setShiftName(data_.getShiftName());
//                        monthShiftDetailListModel.setShiftSName(data_.getShiftSName());
//                        monthShiftDetailListModel.setEmployeeCode(employee.getEmployeeCode());
//                    }
//                    int pos=i+1;
//                    String data="";
//                    String position=pos+"";
//                    if (position.length()==1)
//                    {
//                        data="0"+position;
//                    }
//                    else
//                    {
//                        data=position;
//                    }
//                    monthShiftDetailListModel.setPunchDate(toDate.substring(0,8)+data);
//
//                    monthShiftDetailListModels.add(monthShiftDetailListModel);
//                }
//                adapter.updateListData(monthShiftDetailListModels);
            }
        }
    }
}