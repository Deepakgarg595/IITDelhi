package com.app.iitdelhicampus.activity;

import static com.app.iitdelhicampus.constants.Constants.SHIFT_MASTER;
import static com.app.iitdelhicampus.constants.Constants.WAREHOUSE_LIST;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.EmployeeShiftListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.ManagerLoginModel;
import com.app.iitdelhicampus.model.ShiftMasterModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.service.MyBackgroundLocationService;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;

import org.json.JSONObject;

public class EmployeeShiftListActivity extends BaseActivity implements OnUpdateResponse {
    private RecyclerView recEmployees;
    private ImageView imgReload;
    private ImageView imgLogout;
    private TextView txtProfileName;
    private EmployeeShiftListAdapter adapter;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.app_icon)
                .setTitle("Wach")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_shift_list);
        imgLogout = findViewById(R.id.imgLogout);
        imgReload = findViewById(R.id.imgReload);
        recEmployees = findViewById(R.id.recEmployees);
        recEmployees.setHasFixedSize(true);
        recEmployees.setNestedScrollingEnabled(false);
        txtProfileName=findViewById(R.id.txtProfileName);
        txtProfileName.setText(preferences.getName()+" (PSWC Manager)");
        adapter = new EmployeeShiftListAdapter(EmployeeShiftListActivity.this);
        recEmployees.setLayoutManager(new LinearLayoutManager(EmployeeShiftListActivity.this, LinearLayoutManager.VERTICAL, false));
        recEmployees.setAdapter(adapter);
        hitApiUserLogin();
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitApiUserLogin();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EmployeeShiftListActivity.this)
                        .setIcon(R.mipmap.app_icon)
                        .setTitle("Wach")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               preferences.clear();
                                preferences.setAppRestarted(false);
                                stopRunnigService();

                                NotificationManager notificationManager  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.cancelAll();

                                Intent intent =new Intent(EmployeeShiftListActivity.this, LoginWithNumberManagerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public void stopRunnigService() {
        Intent intent1 = new Intent(EmployeeShiftListActivity.this, MyBackgroundLocationService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            stopService(intent1);
        } else {
            stopService(intent1);

        }

    }

    public void hitApiUserLogin() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog();
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("userid", preferences.getUserId());

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(WAREHOUSE_LIST, requestParams, this, Constants.RequestType.WAREHOUSE_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hitApiEmployeeShift() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog();
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("userid", preferences.getUserId());

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(SHIFT_MASTER, requestParams, this, Constants.RequestType.SHIFT_MASTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override

    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (isSuccess && responseCode == Constants.RequestType.SHIFT_MASTER) {
            removeProgressDialog();
            ShiftMasterModel otpResponseModel = gson.fromJson(response, ShiftMasterModel.class);
            if (otpResponseModel.getSuccess()!=null && otpResponseModel.getSuccess().size()!=0)
            {
                preferences.setShiftData(response);
            }
        }
        if (isSuccess && responseCode == Constants.RequestType.WAREHOUSE_LIST) {
            ManagerLoginModel otpResponseModel = gson.fromJson(response, ManagerLoginModel.class);
            if (otpResponseModel.getSuccess() != null) {
                adapter.updateListData(otpResponseModel.getSuccess());
            }
                hitApiEmployeeShift();
            }
    }
}