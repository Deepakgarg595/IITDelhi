package com.app.iitdelhicampus.activity.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.DashBoardActivityKotlin2;
import com.app.iitdelhicampus.activity.ScanQRCodeForAttendanceActivity;
import com.app.iitdelhicampus.adapter.CustPagerTransformer;
import com.app.iitdelhicampus.adapter.HomeBannerAdapter2;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.fragment.BaseFragment;
import com.app.iitdelhicampus.model.FcmDataModel;
import com.app.iitdelhicampus.model.MapModel;
import com.app.iitdelhicampus.model.MessageModel;
import com.app.iitdelhicampus.model.SOSModel;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.app.iitdelhicampus.constants.Constants.MAP;
import static com.app.iitdelhicampus.constants.Constants.MESSAGE;
import static com.app.iitdelhicampus.constants.Constants.SOS;
import static com.app.iitdelhicampus.constants.Constants.UPDATE_ATTENDANCE;


public class HomeFragmentMap extends BaseFragment implements View.OnClickListener, OnUpdateResponse, OnMapReadyCallback {
    private static HomeFragmentMap instance;
    final Handler handler
            = new Handler();
    public int currentPage = 0;
    public FrameLayout flSOS;
    public FrameLayout flMessage;
    public long DELAY_MS = 500;
    public long PERIOD_MS = 4000;
    public GoogleMap map;
    public ArrayList<FcmDataModel> bannerImages;
    Runnable runnable;
    Intent intent;
    private CustomTextView tvStartTime, tvTimer, tvEndTime, tvDate, tvCurrentTime;
    private long seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private String strInOut;
    private String strEmpCode;
    private View itemView;
    //    private ImageView ivPlayPause;
    private FrameLayout frameMap;
    private ViewPager viewPager;
    private HomeBannerAdapter2 homeBannerAdapter;
    private MapModel messageModel;

    public static HomeFragmentMap getInstance() {
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        instance = this;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_home_map, container, false);
        bannerImages = new ArrayList<>();
//        hitApiMap();
        hitApiMessage();
        flMessage = (FrameLayout) itemView.findViewById(R.id.flMessage);
        flSOS = (FrameLayout) itemView.findViewById(R.id.flSOS);
        flSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HitApiSOS();
            }
        });
        frameMap = (FrameLayout) itemView.findViewById(R.id.frameMap);
        tvStartTime = (CustomTextView) itemView.findViewById(R.id.tvStartTime);
        tvStartTime.setOnClickListener(this);
        tvTimer = (CustomTextView) itemView.findViewById(R.id.tvTimer);

        tvEndTime = (CustomTextView) itemView.findViewById(R.id.tvEndTime);
        tvEndTime.setOnClickListener(this);

//        tvStartStop = (CustomTextView) itemView.findViewById(R.id.tvStartStop);
        tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);

        tvDate.setText(Utility.getDateFromSecForEvents(System.currentTimeMillis()));


        tvCurrentTime = (CustomTextView) itemView.findViewById(R.id.tvCurrentTime);


        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager2);
//        circlePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        homeBannerAdapter = new HomeBannerAdapter2(mActivity, null);
        viewPager.setAdapter(homeBannerAdapter);
        viewPager.setPageTransformer(false, new CustPagerTransformer(mActivity));

        intent = new Intent(mActivity, DashBoardActivityKotlin2.class);
        FcmDataModel fcmDataModel = new FcmDataModel();
        fcmDataModel.setUrl(R.drawable.banner);
        bannerImages.add(fcmDataModel);

        fcmDataModel = new FcmDataModel();
        fcmDataModel.setUrl(R.drawable.banner);
        bannerImages.add(fcmDataModel);

        fcmDataModel = new FcmDataModel();
        fcmDataModel.setUrl(R.drawable.banner);
        bannerImages.add(fcmDataModel);

        fcmDataModel = new FcmDataModel();
        fcmDataModel.setUrl(R.drawable.banner);
        bannerImages.add(fcmDataModel);

        homeBannerAdapter.updateList(bannerImages);
        viewPager.setCurrentItem(1);
//        CustomTextView tvVersion = (CustomTextView) itemView.findViewById(R.id.tvVersion);
//        tvVersion.setText("Version: " + BuildConfig.VERSION_NAME + "");
//        tvStartTime.setVisibility(View.GONE);
//        tvTimer.setVisibility(View.GONE);

//        ivPlayPause = (ImageView) itemView.findViewById(R.id.ivPlayPause);
//        ivPlayPause.setOnClickListener(this);


//        tvStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(mActivity, MarkedAttendanceListActivity.class);
//                startActivity(intent);
//            }
//        });


        if (preferences.getAttendanceStartTime() != 0) {
            seconds = getSeconds(preferences.getAttendanceStartTime(), System.currentTimeMillis());
            onClickStart();
//            tvStartStop.setText("Click to Stop");
//            ivPlayPause.setImageResource(R.mipmap.stop_icon);
            tvStartTime.setText(Utility.getEventTime(preferences.getAttendanceStartTime()));
        }
        loadMap();

        return itemView;
    }


    public void updateSOS(boolean b) {
        if (b) {
            flSOS.setVisibility(View.VISIBLE);
            flMessage.setVisibility(View.GONE);
            ((DashBoardActivityKotlin2) mActivity).updateHome();
        } else {
            flSOS.setVisibility(View.GONE);
            flMessage.setVisibility(View.VISIBLE);
            ((DashBoardActivityKotlin2) mActivity).updateHome_();
        }

    }

    public void updateCurrentTime() {
        if (tvCurrentTime != null)
            tvCurrentTime.setText(Utility.getEventTime(System.currentTimeMillis()));
    }

    private void initMultiSelectionDialog(SOSModel messageModel) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sos);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        TextView txtCode = (TextView) dialog.findViewById(R.id.txtCode);
        LinearLayout llShare = (LinearLayout) dialog.findViewById(R.id.llShare);
        LinearLayout llCall = (LinearLayout) dialog.findViewById(R.id.llCall);
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBtnClick();
            }
        });
        txtCode.setText(messageModel.getSosCode());
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "HELP ME!! IT'S AN EMERGENCY!!\n\nPlease Reach ASAP to the location below.\n\nhttps://www.google.com/maps/search/?api=1&query=" + preferences.getCurrentLatitude() + "," + preferences.getCurrentLongitude());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        TextView cross = (TextView) dialog.findViewById(R.id.txtOK);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (preferences.getInOut().equalsIgnoreCase("I")) {
            flSOS.setVisibility(View.VISIBLE);
            flMessage.setVisibility(View.GONE);
        } else {
            flSOS.setVisibility(View.GONE);
            flMessage.setVisibility(View.VISIBLE);
        }
    }


    private void loadMap() {
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_frame, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    public void hitApiUpdateAttendance() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
//            strInOut = inOut;
//            strEmpCode = empCode;

            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("punch_date", Utility.getDate(System.currentTimeMillis()));
                requestParams.put("punch_time", Utility.getTime24Hours(System.currentTimeMillis()));
                requestParams.put("in_out", "O");
                requestParams.put("employee_card", preferences.getEmpCode());
                requestParams.put("branch_id", preferences.getBranchId());
                requestParams.put("latitude", preferences.getCurrentLatitude());
                requestParams.put("longitude", preferences.getCurrentLongitude());
                requestParams.put("image_url", preferences.getEmpProfileURL());
                requestParams.put("created_by", preferences.getEmpCode());

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (preferences.getRoleName().equalsIgnoreCase("Guards")) {
                LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_ATTENDANCE, requestParams, this, Constants.RequestType.UPDATE_AUTO_OUT_IN12HOURS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            // Continue with delete operation
            // A null listener allows the button to dismiss the dialog and take no further action.
            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                    .setTitle("SOS")
                    .setMessage("We received SOS message. We will contact you shortly !")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            dialog.dismiss();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .show();
        } catch (Exception ex) {
            Toast.makeText(mActivity, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void HitApiSOS() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                onmessage();
                return;
            }
//            strInOut = inOut;
//            strEmpCode = empCode;

            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("sos_datetime", System.currentTimeMillis() / 1000 + "");
                requestParams.put("message_id", preferences.getMessage());
                requestParams.put("type", "");
                requestParams.put("employee_card", preferences.getEmpCode());
                requestParams.put("latitude", preferences.getCurrentLatitude());
                requestParams.put("longitude", preferences.getCurrentLongitude());
                requestParams.put("branch_id", preferences.getBranchId());

            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(SOS, requestParams, this, Constants.RequestType.SOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hitApiMessage() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
//            strInOut = inOut;
//            strEmpCode = empCode;


            LoopJRequestHandler.getInstance().hitApiGetMethod(MESSAGE, this, Constants.RequestType.MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hitApiMap() {
        try {

            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
//            strInOut = inOut;
//            strEmpCode = empCode;


            LoopJRequestHandler.getInstance().hitApiGetMethod(MAP, this, Constants.RequestType.MAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void attInSelf(String qrCode) {
        tvStartTime.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.VISIBLE);
        preferences.setAttendanceId(System.currentTimeMillis());
        tvStartTime.setText(Utility.getEventTime(System.currentTimeMillis()));
        tvEndTime.setText("--:--");
//        tvStartStop.setText("Click to Stop");
//        ivPlayPause.setImageResource(R.mipmap.stop_icon);
//        hitApiUpdateAttendance(preferences.getEmpCode(), "I");
//        onClickStart();

//Update required in case of out, endTime will be updated
//      todo  submitToFireStore(preferences.getEmpCode(), false);


    }


    public void attOutSelf(String qrCode, String out) {//out key is used as empty in case of self out so that another auto punch stopped
        tvEndTime.setText(Utility.getEventTime(System.currentTimeMillis()));

//        ivPlayPause.setImageResource(R.mipmap.start_icon);
//        tvStartStop.setText("Click to Start");
//        onClickStop();
//                        onClickReset();
        preferences.setAttendanceId(0L);
        seconds = 0;
        preferences.setPunchingDate(Utility.getReportDate(System.currentTimeMillis()));
//    todo    submitToFireStore(preferences.getEmpCode(), true);
//        hitApiUpdateAttendance(preferences.getEmpCode(), out);

    }


    public void attInOutOthers(String qrCode, String otherEmpCode) {
//        hitApiUpdateAttendance(otherEmpCode.trim(), preferences.getOtherEmpShift().equalsIgnoreCase("OUT") ? "O" : "I");
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
//            case R.id.ivPlayPause:
//                if (tvStartStop.getText().toString().equalsIgnoreCase("Click to Start")) {
//                    scanQrCode(Constants.REQUEST_CODE_IN_SELF, false);
////                    onClickPopupMenu(view);
//                } else {
//                    showAlertPunchOut(mActivity, "", "Are you sure to mark Out Attendance?");
////                    mActivity.showAlertDialogCustom(mActivity, "", "Are you sure to mark Out Attendance?");
//
//                }
//
//                break;
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
                    tvTimer.setText(time);
                    if (hours >= 12) {
                        onClickStop();
                        attOutSelf("", "");
                        hitApiUpdateAttendance();
                        tvStartTime.setText("--:--");
                        tvEndTime.setText("--:--");
                        tvTimer.setText("--:--");
                    }
                    // Set the text view text.
//                    tvTimer.setText(time);
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

    public void attMarkedInOut() {
        hitApiUpdateAttendance();
        if (preferences.getInOut().equalsIgnoreCase("I")) {
            onClickStart();
            attInSelf("");
            flSOS.setVisibility(View.VISIBLE);
            flMessage.setVisibility(View.GONE);
            ((DashBoardActivityKotlin2) mActivity).updateHome();
//            mActivity.showAlertDialogAutoDismiss(true, "Attendance Marked in Successfully...", R.color.green, R.drawable.tick_green, "green");
        } else {
            seconds = 0;
            onClickStop();
            attOutSelf("", "");
            ((DashBoardActivityKotlin2) mActivity).updateHome_();
//            mActivity.showAlertDialogAutoDismiss(true, "Attendance Marked out Successfully...", R.color.red, R.drawable.cross_red, "red");
            flSOS.setVisibility(View.VISIBLE);
            flMessage.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {

        if (responseCode == Constants.RequestType.MAP) {
            messageModel = gson.fromJson(response.toString(), MapModel.class);
        }
        if (responseCode == Constants.RequestType.SOS) {
            SOSModel messageModel = gson.fromJson(response.toString(), SOSModel.class);
            initMultiSelectionDialog(messageModel);
        }
        if (responseCode == Constants.RequestType.MESSAGE) {
            MessageModel messageModel = gson.fromJson(response.toString(), MessageModel.class);
            preferences.setMessage(messageModel.getSuccess().get(0).getMessageId());
        }

//
//            UpdateAttendanceModel updateAttendanceModel = gson.fromJson(response, UpdateAttendanceModel.class);
//            if (updateAttendanceModel != null) {
//                if (preferences.getEmpCode().equalsIgnoreCase(strEmpCode)) {
//                    if(strInOut.equalsIgnoreCase("I")){
//                        onClickStart();
//                        Utility.showNotification(mActivity,"Wach","Attendance Marked in Successfully...",intent);
//                        mActivity.showAlertDialogAutoDismiss(true, "Attendance Marked in Successfully...", R.color.green, R.drawable.tick_green, "green");
//
//                    }else {
//                        onClickStop();
//                        Utility.showNotification(mActivity,"Wach","Attendance Marked out Successfully...",intent);
//                        mActivity.showAlertDialogAutoDismiss(true, "Attendance Marked out Successfully...", R.color.red, R.drawable.cross_red, "red");
//
//                    }
//                }else {
////                    mActivity.showAlertDialogCustom(true, updateAttendanceModel.getMessage(), R.color.green, R.drawable.tick_green, "green");
//
//                    String name="";
//                    EmployeeModel.EmpList employeeData = Datastatic.getInstance().getEmpDataList(strEmpCode.trim());
//                    if (employeeData != null) {
//                        name=employeeData.getEmployeeName();
//                    }
//                    if(strInOut.equalsIgnoreCase("I")){
//                        mActivity.showAlertDialogCustom(true, "IN success @ "+Utility.getEventTime(System.currentTimeMillis())+"\n Emp Id: "+strEmpCode+"\n Emp Name: "+name, R.color.green, R.drawable.tick_green, "green");
//
//                    }else {
//                        mActivity.showAlertDialogCustom(true, "OUT success @ "+Utility.getEventTime(System.currentTimeMillis())+"\n Emp Id: "+strEmpCode+"\n Emp Name: "+name, R.color.green, R.drawable.tick_green, "green");
//
//                    }
//                }
//
//            } else {
//                mActivity.showAlertDialogCustom(true, "Some error occurred.", R.color.red, R.drawable.cross_red, "red");
//
//            }
//        }
    }

    public void refreshMap() {
        if (map != null) {
            map.clear();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.map = googleMap;
//        map.setOnMapClickListener(this);
        map.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(preferences.getStaticLatitude(), preferences.getStaticLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.5F));

        BitmapDescriptor marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        /*BitmapDescriptorFactory.fromResource(R.mipmap.location_pointer_icon)*/
//        LatLng latLngCurrent = new LatLng(preferences.getCurrentLatitude(), preferences.getCurrentLongitude());
//        BitmapDescriptor markerCurrent = BitmapDescriptorFactory.fromResource(R.mipmap.dot_current_location);

        map.addMarker(new MarkerOptions().title("IIT Delhi Campus").position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
//        map.addMarker(new MarkerOptions().position(latLngCurrent).icon(markerCurrent));
        map.setMyLocationEnabled(true);
//        ArrayList<LatLng> latLngs=new ArrayList<>();
//        for (int i=0;i<messageModel.getLatlong().size();i++)
//        {
//            latLngs.add(new LatLng(Double.parseDouble(messageModel.getLatlong().get(i).getLatitude()),Double.parseDouble(messageModel.getLatlong().get(i).getLongitude())));
//        }
        map.addPolygon(new PolygonOptions()
                .add(new LatLng(28.55109, 77.182254))
                .add(new LatLng(28.545841, 77.196517))
                .add(new LatLng(28.544710, 77.200755))
                .add(new LatLng(28.544409, 77.200926))
                .add(new LatLng(28.543994, 77.200926))
                .add(new LatLng(28.539564, 77.198920))
                .add(new LatLng(28.542064, 77.192107))
                .add(new LatLng(28.541320, 77.189929))
                .add(new LatLng(28.542328, 77.188299))
                .add(new LatLng(28.543111, 77.186936))
                .add(new LatLng(28.543563, 77.187054))
                .add(new LatLng(28.543742, 77.186711))
                .add(new LatLng(28.544063, 77.186818))
                .add(new LatLng(28.544386, 77.186091))
                .add(new LatLng(28.543943, 77.185759))
                .add(new LatLng(28.543745, 77.185544))
                .add(new LatLng(28.543651, 77.185072))
                .add(new LatLng(28.543660, 77.184718))
                .add(new LatLng(28.543802, 77.184074))
                .add(new LatLng(28.543802, 77.183924))
                .add(new LatLng(28.543585, 77.183828))
                .add(new LatLng(28.543389, 77.183707))
                .add(new LatLng(28.542548, 77.183852))
                .add(new LatLng(28.541712, 77.183369))
                .add(new LatLng(28.542303, 77.182368))
                .add(new LatLng(28.543545, 77.180261))
                .add(new LatLng(28.544068, 77.179269))
                .add(new LatLng(28.544522, 77.178389))
                .add(new LatLng(28.545957, 77.179210))
                .add(new LatLng(28.547892, 77.179902))
                .add(new LatLng(28.547822, 77.180028))
                .add(new LatLng(28.547520, 77.180715))
                .add(new LatLng(28.547327, 77.181305))
                .add(new LatLng(28.547221, 77.181662))
                .add(new LatLng(28.547157, 77.181858))
                .add(new LatLng(28.547120, 77.181935))
                .add(new LatLng(28.547084, 77.182067))
                .add(new LatLng(28.547153, 77.182069))
                .add(new LatLng(28.547525, 77.182391))
                .add(new LatLng(28.547700, 77.182488))
                .add(new LatLng(28.547797, 77.182539))
                .add(new LatLng(28.547874, 77.182550))
                .add(new LatLng(28.548439, 77.182898))
                .add(new LatLng(28.548788, 77.183048))
                .add(new LatLng(28.549089, 77.183099))
                .add(new LatLng(28.549200, 77.183046))
                .add(new LatLng(28.549240, 77.183000))
                .add(new LatLng(28.549504, 77.182315))
                .add(new LatLng(28.549742, 77.182066))
                .add(new LatLng(28.549902, 77.182060))
                .add(new LatLng(28.550338, 77.181382))
                .add(new LatLng(28.551297, 77.182143))
                .add(new LatLng(28.551320, 77.182320))
                .add(new LatLng(28.551304, 77.182514))
                .add(new LatLng(28.551103, 77.182830))
                .add(new LatLng(28.550540, 77.183704))
                .add(new LatLng(28.550265, 77.184093))
                .add(new LatLng(28.549895, 77.184906))
                .add(new LatLng(28.549374, 77.186159))
                .add(new LatLng(28.549350, 77.186212))
                .add(new LatLng(28.549105, 77.186816))
                .add(new LatLng(28.548783, 77.187647))
                .add(new LatLng(28.548608, 77.188109))
                .add(new LatLng(28.548191, 77.189141))
                .add(new LatLng(28.548043, 77.189546))
                .add(new LatLng(28.547826, 77.189914))
                .add(new LatLng(28.547489, 77.190954))
                .add(new LatLng(28.547435, 77.191078))
                .add(new LatLng(28.546577, 77.193889))
                .add(new LatLng(28.546332, 77.194725))
                .add(new LatLng(28.545842, 77.196496))
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.parseColor("#c3d5ff"))
                .clickable(true));

        map.getUiSettings().setAllGesturesEnabled(true);
        UserLoginModel employeeModel = gson.fromJson(preferences.getBannerResponse(), UserLoginModel.class);
        if (employeeModel != null && employeeModel.getSuccess() != null && employeeModel.getSuccess().get(0) != null && employeeModel.getSuccess().get(0).getBranch().size() != 0) {
            for (int i = 0; i < employeeModel.getSuccess().get(0).getBranch().size(); i++) {

                map.addCircle(new CircleOptions()
                        .center(new LatLng(Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getLatitude()), Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getLongitude())))
                        .radius(Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getRadius()))
                        .strokeWidth(0)
//                        .strokeColor(Color.BLUE)
//                .fillColor(Color.parseColor("#c3d5ff"))
                        .clickable(true));
            }
        }

//
        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
//        animation.cancel();

    }

    private void onCallBtnClick() {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        } else {

            if (ActivityCompat.checkSelfPermission(mActivity,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    private void onmessage() {
        if (Build.VERSION.SDK_INT < 23) {
            sendSMS("9990920539", "HELP ME!! IT'S AN EMERGENCY!!\n\nPlease Reach ASAP to the location below.\n\nhttps://www.google.com/maps/search/?api=1&query=" + preferences.getCurrentLatitude() + "," + preferences.getCurrentLongitude());
        } else {

            if (ActivityCompat.checkSelfPermission(mActivity,
                    Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                sendSMS("9990920539", "HELP ME!! IT'S AN EMERGENCY!!\n\nPlease Reach ASAP to the location below.\n\nhttps://www.google.com/maps/search/?api=1&query=" + preferences.getCurrentLatitude() + "," + preferences.getCurrentLongitude());
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.SEND_SMS};
                //Asking request Permissions
                ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, 7);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        boolean permissionGrant = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case 7:
                permissionGrant = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
            sendSMS("9990920539", "HELP ME!! IT'S AN EMERGENCY!!\n\nPlease Reach ASAP to the location below.\n\nhttps://www.google.com/maps/search/?api=1&query=" + preferences.getCurrentLatitude() + "," + preferences.getCurrentLongitude());
        } else {
            Toast.makeText(mActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
        if (permissionGrant) {
            phoneCall();
        } else {
            Toast.makeText(mActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode("1000")));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }
}