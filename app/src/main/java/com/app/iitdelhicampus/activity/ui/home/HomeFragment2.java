package com.app.iitdelhicampus.activity.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.iitdelhicampus.BuildConfig;
import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ScanQRCodeForAttendanceActivity;
import com.app.iitdelhicampus.adapter.CustPagerTransformer;
import com.app.iitdelhicampus.adapter.HomeBannerAdapter2;
import com.app.iitdelhicampus.adapter.HomeIconAdapter2;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.fragment.BaseFragment;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.EmployeeData;
import com.app.iitdelhicampus.model.EmployeeModel;
import com.app.iitdelhicampus.model.FcmDataModel;
import com.app.iitdelhicampus.model.MarkAttendanceModel;
import com.app.iitdelhicampus.model.UpdateAttendanceModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.Datastatic;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static com.app.iitdelhicampus.constants.Constants.UPDATE_ATTENDANCE;


public class HomeFragment2 extends BaseFragment implements View.OnClickListener, OnUpdateResponse {
    private static HomeFragment2 instance;
    final Handler handler
            = new Handler();
    public int currentPage = 0;
    public long DELAY_MS = 500;
    public long PERIOD_MS = 4000;
    public FirebaseFirestore db;
    public ArrayList<FcmDataModel> bannerImages;
    public ArrayList<FcmDataModel> categories;
    public ViewPager viewPager;
    View itemView;
    MarkAttendanceModel markAttendanceModel;
    ImageView ivPlayPause;
    Runnable runnable;
    private Timer timer;
    private HomeBannerAdapter2 homeBannerAdapter;
    private HomeIconAdapter2 homeIconAdapter;
    private ProgressBar pBar;
    //    private CirclePageIndicator circlePageIndicator;
    private CustomTextView tvStartTime, tvTimer, tvEndTime, tvStartStop, tvDate, tvCurrentTime;
    private int countTime;
    private long seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;
    private String strInOut;
    private String strEmpCode;


    public static HomeFragment2 getInstance() {
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        instance = this;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_home_2, container, false);
        categories = new ArrayList<>();
        bannerImages = new ArrayList<>();

        tvStartTime = (CustomTextView) itemView.findViewById(R.id.tvStartTime);
        tvStartTime.setOnClickListener(this);
        tvTimer = (CustomTextView) itemView.findViewById(R.id.tvTimer);

        tvEndTime = (CustomTextView) itemView.findViewById(R.id.tvEndTime);
        tvEndTime.setOnClickListener(this);

        tvStartStop = (CustomTextView) itemView.findViewById(R.id.tvStartStop);
        tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);

        tvDate.setText(Utility.getDateFromSecForEvents(System.currentTimeMillis()));


        tvCurrentTime = (CustomTextView) itemView.findViewById(R.id.tvCurrentTime);
        tvCurrentTime.setText(Utility.getEventTime(System.currentTimeMillis()));

        CustomTextView tvVersion = (CustomTextView) itemView.findViewById(R.id.tvVersion);
        tvVersion.setText("Version: " + BuildConfig.VERSION_NAME + "");
//        tvStartTime.setVisibility(View.GONE);
//        tvTimer.setVisibility(View.GONE);

        ivPlayPause = (ImageView) itemView.findViewById(R.id.ivPlayPause);
        ivPlayPause.setOnClickListener(this);


//        tvStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(mActivity, MarkedAttendanceListActivity.class);
//                startActivity(intent);
//            }
//        });


        pBar = (ProgressBar) itemView.findViewById(R.id.pBar);
        pBar.setVisibility(View.GONE);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager2);
//        circlePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        homeBannerAdapter = new HomeBannerAdapter2(mActivity, this);
        viewPager.setAdapter(homeBannerAdapter);
        viewPager.setPageTransformer(false, new CustPagerTransformer(mActivity));

//    todo uncomment it and make it visible in xml file


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3);
        RecyclerView rec = (RecyclerView) itemView.findViewById(R.id.rec);
        rec.setLayoutManager(gridLayoutManager);
        homeIconAdapter = new HomeIconAdapter2(mActivity,this);
        rec.setAdapter(homeIconAdapter);
        homeIconAdapter.updateList(Datastatic.getInstance().getHomeList());


        pBar.setVisibility(View.GONE);
        homeBannerAdapter.updateList(bannerImages);
        viewPager.setCurrentItem(1);

        if (preferences.getAttendanceStartTime() != 0) {
            seconds = getSeconds(preferences.getAttendanceStartTime(), System.currentTimeMillis());
            onClickStart();
            tvStartStop.setText("Click to Stop");
            ivPlayPause.setImageResource(R.mipmap.stop_icon);
            tvStartTime.setText(Utility.getEventTime(preferences.getAttendanceStartTime()));
        }

        return itemView;
    }

    public void updateView() {
        homeBannerAdapter.notifyDataSetChanged();
    }

    public void takePhoto() {
        ToastUtils.showToast(mActivity, "In Progress..");
    }

    public void showAlertToStopTimer(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        scanQrCode(Constants.REQUEST_CODE_OUT_SELF, false);

                    }
                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        alertDialog.show();
    }


    public void hitApiUpdateAttendance(String empCode, String inOut) {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            strInOut = inOut;
            strEmpCode = empCode;

            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("punch_date", Utility.getDate(System.currentTimeMillis()));
                requestParams.put("punch_time", Utility.getTime24Hours(System.currentTimeMillis()));
                requestParams.put("in_out", inOut);
                requestParams.put("employee_card", empCode);
                requestParams.put("branch_id", preferences.getBranchId());
                requestParams.put("latitude", preferences.getCurrentLatitude());
                requestParams.put("longitude", preferences.getCurrentLongitude());
                requestParams.put("image_url", preferences.getEmpProfileURL());
                requestParams.put("created_by", preferences.getEmpCode());

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (preferences.getRoleName().equalsIgnoreCase("Guards"))
            {
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_ATTENDANCE, requestParams, this, Constants.RequestType.UPDATE_ATTENDANCE);
        } }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void submitToFireStore(String empCode, boolean needUpdate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (needUpdate) {
//            Map<String, Object> city = new HashMap<>();
//            city.put("endTime", System.currentTimeMillis());
//            city.put("date", mActivity.currentDate);
            db.collection(Constants.COLLECTION_ATTENDANCE).document(preferences.getPunchingDate() + "-" + empCode)
                    .update("endTime", System.currentTimeMillis()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mActivity.showAlertDialog(mActivity, "", "Attendance marked OUT.");
                }
            });
        } else {
            markAttendanceModel.setProfileImage(CreateEventModel.getInstance(false).getPaymentUrl());
            markAttendanceModel.setUserId(preferences.getUserId());
            markAttendanceModel.setDate(Utility.getReportDate(System.currentTimeMillis()));
            db.collection(Constants.COLLECTION_ATTENDANCE).document(Utility.getReportDate(System.currentTimeMillis()) + "-" + empCode).set(markAttendanceModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mActivity.showAlertDialog(mActivity, "", "Attendance marked IN.");
                }
            });
        }

    }


    public void attInSelf(String qrCode) {
        tvStartTime.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.VISIBLE);
        preferences.setAttendanceId(System.currentTimeMillis());
        tvStartTime.setText(Utility.getEventTime(System.currentTimeMillis()));
        tvEndTime.setText("--:--");
        tvStartStop.setText("Click to Stop");
        ivPlayPause.setImageResource(R.mipmap.stop_icon);
        countTime = 0;

        onClickStart();

        markAttendanceModel = new MarkAttendanceModel();
        EmployeeData employeeData = Datastatic.getInstance().getEmpData(preferences.getEmpCode());
        if (employeeData != null) {
            markAttendanceModel.setUserName(employeeData.getEmpName());
            markAttendanceModel.setLocation(employeeData.getLocation());
        }
        markAttendanceModel.setStartTime(preferences.getAttendanceStartTime());
        markAttendanceModel.setUserName(preferences.getName());
        markAttendanceModel.setShift(getShift());
        markAttendanceModel.setEmpCode(preferences.getEmpCode());
        markAttendanceModel.setUserId(preferences.getUserId());
        markAttendanceModel.setQrCode(qrCode);
//Update required in case of out, endTime will be updated
//      todo  submitToFireStore(preferences.getEmpCode(), false);
        hitApiUpdateAttendance(preferences.getEmpCode(), "I");

    }


    public void attOutSelf(String qrCode, String out) {//out key is used as empty in case of self out so that another auto punch stopped
        markAttendanceModel = new MarkAttendanceModel();
        markAttendanceModel.setStartTime(preferences.getAttendanceStartTime());
        markAttendanceModel.setEndTime(System.currentTimeMillis());
        markAttendanceModel.setUserId(preferences.getUserId());
        markAttendanceModel.setEmpCode(preferences.getEmpCode());
        markAttendanceModel.setQrCode(qrCode);
        tvEndTime.setText(Utility.getEventTime(System.currentTimeMillis()));
        ivPlayPause.setImageResource(R.mipmap.start_icon);
        tvStartStop.setText("Click to Start");
        onClickStop();
//                        onClickReset();
        preferences.setAttendanceId(0L);
        seconds = 0;
        preferences.setPunchingDate(Utility.getReportDate(System.currentTimeMillis()));
//    todo    submitToFireStore(preferences.getEmpCode(), true);
        hitApiUpdateAttendance(preferences.getEmpCode(), out);

    }


    public void attInOutOthers(String qrCode, String otherEmpCode) {
        markAttendanceModel = new MarkAttendanceModel();
        EmployeeData employeeData = Datastatic.getInstance().getEmpData(otherEmpCode);
        if (employeeData != null) {
            markAttendanceModel.setUserName(employeeData.getEmpName());
            markAttendanceModel.setLocation(employeeData.getLocation());
        }

        markAttendanceModel.setEmpCode(otherEmpCode.trim());
        markAttendanceModel.setQrCode(qrCode.trim());

        if (preferences.getOtherEmpShift().equalsIgnoreCase("IN")) {
            markAttendanceModel.setShift(getShift());
            markAttendanceModel.setStartTime(System.currentTimeMillis());
        } else {
            markAttendanceModel.setEndTime(System.currentTimeMillis());
            // OUT means need to update endTimeStamp

        }

        hitApiUpdateAttendance(otherEmpCode.trim(), preferences.getOtherEmpShift().equalsIgnoreCase("OUT") ? "O" : "I");
//todo        submitToFireStore(otherEmpCode.trim(), preferences.getOtherEmpShift().equalsIgnoreCase("OUT"));

    }


    public void scanQrCode(int reqCode, boolean isOtherEmp) {
        // if isOtherEmp is false then open normal view of QR code else employee code view will be opened
        Intent intent = new Intent(mActivity, ScanQRCodeForAttendanceActivity.class);
        intent.putExtra(Constants.EXTRA_DATA, isOtherEmp);
        mActivity.startActivityForResult(intent, reqCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPlayPause:
                if (tvStartStop.getText().toString().equalsIgnoreCase("Click to Start")) {
                    scanQrCode(Constants.REQUEST_CODE_IN_SELF, false);
//                    onClickPopupMenu(view);
                } else {
                    showAlertPunchOut(mActivity, "", "Are you sure to mark Out Attendance?");
//                    mActivity.showAlertDialogCustom(mActivity, "", "Are you sure to mark Out Attendance?");

                }

                break;
        }
    }


    public void showAlertPunchOut(Context context, String title, String message) {


        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_out_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        final CustomTextView tvContent = (CustomTextView) dialog.findViewById(R.id.tvContent);
        tvContent.setText(message);
        Button btnEdit = (Button) dialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                scanQrCode(Constants.REQUEST_CODE_OUT_SELF, false);
            }
        });
        dialog.show();


    }

    private long getSeconds(long start, long end) {
        long diffInMs = end - start;
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        return diffInSec;
    }


    public void onClickStart() {

        runTimer();
        running = true;

    }

    public void onClickStop() {
        running = false;
        stopHandler();
    }

    public void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        try {
            runnable = new Runnable() {
                @Override
                public void run() {
                    int hours = (int) (seconds / 3600);
                    int minutes = (int) ((seconds % 3600) / 60);
                    int secs = (int) (seconds % 60);
                    // Format the seconds into hours, minutes,
                    // and seconds.
                    String time
                            = String
                            .format(Locale.getDefault(),
                                    "%2d:%02d:%02d", hours,
                                    minutes, secs);
                    // Set the text view text.
                    tvTimer.setText(time);
                    // If running is true, increment the
                    // seconds variable.
                    if (running) {
                        seconds++;
                    }
                    // Post the code again
                    // with a delay of 1 second.
                    handler.postDelayed(this, 1000);
                }
            };

            handler.post(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopHandler() {
        if (runnable != null)
            handler.removeCallbacks(runnable);
    }


    private String getShift() {
        String shift = "";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            shift = "Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            shift = "Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            shift = "Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            shift = "Night";
        }
        return shift;
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {

        if (responseCode == Constants.RequestType.UPDATE_ATTENDANCE) {
            if (preferences.getEmpCode().equalsIgnoreCase(strEmpCode)) {
                preferences.setInOut(strInOut);
                preferences.setEmpProfileURL("");
            }


            UpdateAttendanceModel updateAttendanceModel = gson.fromJson(response, UpdateAttendanceModel.class);
            if (updateAttendanceModel != null) {
                if (preferences.getEmpCode().equalsIgnoreCase(strEmpCode)) {
                 if(strInOut.equalsIgnoreCase("I")){
                     mActivity.showAlertDialogCustom(true, "Attendance Marked in Successfully...", R.color.green, R.drawable.tick_green, "green");

                 }else {
                     mActivity.showAlertDialogCustom(true, "Attendance Marked out Successfully...", R.color.green, R.drawable.tick_green, "green");

                 }
                }else {
//                    mActivity.showAlertDialogCustom(true, updateAttendanceModel.getMessage(), R.color.green, R.drawable.tick_green, "green");

                    String name="";
                    EmployeeModel.EmpList employeeData = Datastatic.getInstance().getEmpDataList(strEmpCode.trim());
                    if (employeeData != null) {
                        name=employeeData.getEmployeeName();
                    }
                    if(strInOut.equalsIgnoreCase("I")){
                        mActivity.showAlertDialogCustom(true, "IN success @ "+Utility.getEventTime(System.currentTimeMillis())+"\n Emp Id: "+strEmpCode+"\n Emp Name: "+name, R.color.green, R.drawable.tick_green, "green");

                    }else {
                        mActivity.showAlertDialogCustom(true, "OUT success @ "+Utility.getEventTime(System.currentTimeMillis())+"\n Emp Id: "+strEmpCode+"\n Emp Name: "+name, R.color.green, R.drawable.tick_green, "green");

                    }
                }

            } else {
                mActivity.showAlertDialogCustom(true, "Some error occurred.", R.color.red, R.drawable.cross_red, "red");

            }
        }
    }
}