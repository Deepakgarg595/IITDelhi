package com.app.iitdelhicampus.activity.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.iitdelhicampus.BuildConfig;
import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ScanQRCodeForAttendanceActivity;
import com.app.iitdelhicampus.adapter.HomeBannerAdapter;
import com.app.iitdelhicampus.adapter.HomeIconAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.fragment.BaseFragment;
import com.app.iitdelhicampus.model.EmployeeData;
import com.app.iitdelhicampus.model.FcmDataModel;
import com.app.iitdelhicampus.model.MarkAttendanceModel;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.Datastatic;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.ToastUtils;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static HomeFragment instance;
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
//    private Timer timer;
    private HomeBannerAdapter homeBannerAdapter;
    private HomeIconAdapter homeIconAdapter;
    private ProgressBar pBar;
    private CirclePageIndicator circlePageIndicator;
    private CustomTextView tvStartTime, tvTimer, tvEndTime, tvStartStop, tvDate;
    private int countTime;
    private long seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;

    public static HomeFragment getInstance() {
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        instance = this;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_home_, container, false);
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
        viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        circlePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        homeBannerAdapter = new HomeBannerAdapter(mActivity, this);
//    todo uncomment it and make it visible in xml file
//        viewPager.setAdapter(homeBannerAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3);
        RecyclerView rec = (RecyclerView) itemView.findViewById(R.id.rec);
        rec.setLayoutManager(gridLayoutManager);
        homeIconAdapter = new HomeIconAdapter(mActivity);
        rec.setAdapter(homeIconAdapter);
        homeIconAdapter.updateList(Datastatic.getInstance().getHomeList());
        FcmDataModel fcmDataModel = new FcmDataModel();
        fcmDataModel.setUrl(R.mipmap.wach_banner1);
        bannerImages.add(fcmDataModel);
        pBar.setVisibility(View.GONE);
        homeBannerAdapter.updateList(bannerImages);
//        if (bannerImages != null && bannerImages.size() != 0) {
//            if (bannerImages.size() != 1) {
//                circlePageIndicator.setViewPager(viewPager);
//            }
//        }
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (currentPage == bannerImages.size()) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(runnable);
//            }
//        }, DELAY_MS, PERIOD_MS);

        if (preferences.getAttendanceStartTime() != 0) {
            seconds = getSeconds(preferences.getAttendanceStartTime(), System.currentTimeMillis());
            onClickStart();
            tvStartStop.setText("Click to Stop");
            ivPlayPause.setImageResource(R.mipmap.stop_icon);
            tvStartTime.setText(Utility.getEventTime(preferences.getAttendanceStartTime()));
        }

        return itemView;
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
        submitToFireStore(preferences.getEmpCode(), false);

    }


    public void attOutSelf(String qrCode) {
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
        submitToFireStore(preferences.getEmpCode(), true);

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
        submitToFireStore(otherEmpCode.trim(), preferences.getOtherEmpShift().equalsIgnoreCase("OUT"));

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
                    showAlertToStopTimer(mActivity, "", "Are you sure to stop timer?");
                }

                break;
        }
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

}