package com.app.iitdelhicampus.utility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.DashBoardActivityKotlin2;
import com.app.iitdelhicampus.activity.LoginWithNumberActivity2;
import com.app.iitdelhicampus.activity.ui.home.HomeFragmentMap;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.model.EmployeeData;
import com.app.iitdelhicampus.model.EmployeeModel;
import com.app.iitdelhicampus.model.HomeImageDataModel;
import com.app.iitdelhicampus.model.UserLoginModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.service.MyBackgroundLocationService;
import com.app.iitdelhicampus.utility.Patrolling.PatrollingDatabaseHelper;
import com.app.iitdelhicampus.utility.Patrolling.PatrollingOfflineStreamModel;
import com.app.iitdelhicampus.utility.Patrolling.RecordDetailModel;
//import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.BATTERY_SERVICE;
import static com.app.iitdelhicampus.constants.Constants.OFFLINE_DATA;
import static com.app.iitdelhicampus.constants.Constants.UPDATE_ATTENDANCE;

public class Datastatic implements OnUpdateResponse {
    private static Datastatic instance;
    private static String version = "v-0.0.1";

    ArrayList<CommonDropdownModel> listIncidentType = new ArrayList<>();
    AppPreferences preferences;
    private int[] home_image = {R.drawable.dashboard, R.drawable.patrolling_icon, R.drawable.sims_icon, R.drawable.manpower_deployment_icon, R.drawable.duty_roster_icon, R.drawable.duty_roster_icon, R.drawable.visitor_tracking_icon, R.drawable.mock_drill_icon, R.drawable.electronics_surveillance_icon};
    private String[] home_content = {"Dashboard", "Patrolling", "SIMS", "Manpower Deployment", "Attendance\n for Others", "Employee\nAtt. List", "Visitor Tracking", "Training and Mock Drill", "Security Surveillance"};
    private int[] home_image_tl = {R.drawable.incident_review_icon_m, R.drawable.report_incident_icon_m, R.drawable.site_visit_icon_m, R.drawable.attendance_icon_m, R.drawable.leave_icon_m, R.drawable.emp_list_icon_m};
    private String[] home_content_tl = {"Incident Review", "Report Incident", "Site Observation", "Attendance", "Leave", "Emp List"};
    private int[] home_image_po = {R.drawable.report_incident_icon_m, R.drawable.sos2_m, R.drawable.incident_review_icon_m};
    private String[] home_content_po = {"Report Incident", "SOS", "Incident Review"};
    private HashMap<String, EmployeeModel.EmpList> hmEmpData = new HashMap<>();
    private String strInOut = "";
    private int[] incident_image = {R.drawable.fire, R.drawable.revolver_, R.drawable.car_collision, R.drawable.earthquake, R.drawable.theft};
    private int[] prof_image = {R.drawable.sos2_m, R.drawable.sos2_m, R.drawable.sos2_m};
    private String[] incident_name = {"FIRE", "CRIME", "ACCIDENT", "EARTHQUAKE", "THEFT"};
    private String[] prof_name = {"Notification Settings", "App Version", "Logout"};
    private String[] prof_content = {"", "", "", version, ""};

    private Context contextService;
    private JSONObject object;
    private Context context;

    private Datastatic() {
        instance = this;
        preferences = AppPreferences.getInstance(BaseApplication.getInstance());
    }

    public static Datastatic getInstance() {
        if (instance == null) {
            instance = new Datastatic();
        }
        return instance;
    }

    public ArrayList<HomeImageDataModel> getProfileList() {
        ArrayList<HomeImageDataModel> listData = new ArrayList<>();
        for (int i = 0; i < prof_name.length; i++) {
            HomeImageDataModel homeImageDataModel = new HomeImageDataModel();
            homeImageDataModel.setImage(prof_image[i]);
            homeImageDataModel.setName(prof_name[i]);
            homeImageDataModel.setContent(prof_content[i]);
            listData.add(homeImageDataModel);
        }
        return listData;
    }

    public ArrayList<HomeImageDataModel> getIncidentList() {
        ArrayList<HomeImageDataModel> listData = new ArrayList<>();
        for (int i = 0; i < incident_name.length; i++) {
            HomeImageDataModel homeImageDataModel = new HomeImageDataModel();
            homeImageDataModel.setImage(incident_image[i]);
            homeImageDataModel.setName(incident_name[i]);
            homeImageDataModel.setSelected(false);
            listData.add(homeImageDataModel);
        }
        return listData;
    }

    public ArrayList<HomeImageDataModel> getHomeList() {
        ArrayList<HomeImageDataModel> listData = new ArrayList<>();

        HomeImageDataModel homeImageDataModel = null;
        if (AppPreferences.getInstance(BaseApplication.getInstance()).getUserType().equalsIgnoreCase("client")) {
            for (int i = 0; i < home_image.length; i++) {
                homeImageDataModel = new HomeImageDataModel();
                homeImageDataModel.setImage(home_image[i]);
                homeImageDataModel.setName(home_content[i]);
                listData.add(homeImageDataModel);
            }
        } else if (AppPreferences.getInstance(BaseApplication.getInstance()).getUserType().equalsIgnoreCase("tl")) {
            for (int i = 0; i < home_image_tl.length; i++) {
                homeImageDataModel = new HomeImageDataModel();
                homeImageDataModel.setImage(home_image_tl[i]);
                homeImageDataModel.setName(home_content_tl[i]);
                listData.add(homeImageDataModel);
            }
        } else {//PO
            for (int i = 0; i < home_image_po.length; i++) {
                homeImageDataModel = new HomeImageDataModel();
                homeImageDataModel.setImage(home_image_po[i]);
                homeImageDataModel.setName(home_content_po[i]);
                listData.add(homeImageDataModel);
            }
        }


        return listData;
    }

    public void loadIncidentData() {

        String[] incidentType = BaseApplication.getInstance().getResources().getStringArray(R.array.list_incident_type);
        String[] sensitivity = BaseApplication.getInstance().getResources().getStringArray(R.array.list_sensitivity);
        for (int i = 0; i < incidentType.length; i++) {
            CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
            commonDropdownModel.setName(incidentType[i]);
            commonDropdownModel.setSeverity(sensitivity[i]);
            listIncidentType.add(commonDropdownModel);
        }
    }

    public ArrayList getListIncidentType() {
        return listIncidentType;
    }

    public void loadEmpData(ArrayList<EmployeeModel.EmpList> empLists) {
        for (int i = 0; i < empLists.size(); i++) {
            hmEmpData.put(empLists.get(i).getEmployeeCode(), empLists.get(i));
        }
    }

    public EmployeeModel.EmpList getEmpDataList(String empCode) {
        if (hmEmpData != null && hmEmpData.containsKey(empCode)) {
            return hmEmpData.get(empCode);

        } else {
            return null;
        }
    }

    public EmployeeData getEmpData(String empCode) {
        return null;
    }

    public void distance(double lat2, double lon2) {
        boolean status = false;
        DecimalFormat df = null;
        double dist = 0;
//        double lat1 = AppPreferences.getInstance(BaseApplication.getInstance()).getStaticLatitude();
//        double lon1 = AppPreferences.getInstance(BaseApplication.getInstance()).getStaticLongitude();
        UserLoginModel employeeModel = new Gson().fromJson(preferences.getBannerResponse(), UserLoginModel.class);
        if (employeeModel != null && employeeModel.getSuccess() != null && employeeModel.getSuccess().get(0) != null && employeeModel.getSuccess().get(0).getBranch().size() != 0) {
            for (int i = 0; i < employeeModel.getSuccess().get(0).getBranch().size(); i++) {
                float[] distance = new float[1];
                Location.distanceBetween(Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getLatitude()),
                        Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getLongitude()),
                        lat2,
                        lon2,
                        distance);
                if (distance[0] > Double.parseDouble(employeeModel.getSuccess().get(0).getBranch().get(i).getRadius())) {
                    status = false;
                } else {
                    status = true;
                    break;
                }
            }


            if (!status) {
                preferences.setInOut("O");
                HomeFragmentMap.getInstance().updateSOS(false);
                preferences.setAppRestarted(false);
            }

            if (status) {
                if (preferences.getInOut().equals("O") || preferences.getInOut().equals("")) {
                    preferences.setInOut("I");
                    HomeFragmentMap.getInstance().updateSOS(true);
                    hitApiUpdateAttendance(context, preferences.getInOut());
                }
            } else {
                if (preferences.getInOut().equals("I")) {
                    preferences.setInOut("O");
                    HomeFragmentMap.getInstance().updateSOS(false);
                    hitApiUpdateAttendance(context, preferences.getInOut());
                }
            }


        }
//        int meterInDec = Integer.valueOf(newFormat.format(dist));
//        return Double.parseDouble(df.format(dist));
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void updateLocation(Context context) {

        contextService = context;
        AppPreferences preferences = AppPreferences.getInstance(context);
        distance(preferences.getCurrentLatitude(), preferences.getCurrentLongitude());

//        if (radius >= (preferences.getRadiusInMeter() * 2) && preferences.isAppRestarted() && preferences.isLoggedIn()) {
//            HomeFragmentMap.getInstance().updateSOS(false);
//            preferences.setAppRestarted(false);
//            return;
//        }


//        if (radius >= preferences.getRadiusInMeter()) {
//            if (preferences.getInOut().equals("I")) {
//                preferences.setInOut("O");
//                HomeFragmentMap.getInstance().updateSOS(false);
//                hitApiUpdateAttendance(context, preferences.getInOut());
//            }
//            return;
//        } else {
//            if (preferences.getInOut().equals("O") || preferences.getInOut().equals("")) {
//                preferences.setInOut("I");
//                HomeFragmentMap.getInstance().updateSOS(true);
//                hitApiUpdateAttendance(context, preferences.getInOut());
//            }
//        }
    }

    public void hitApiUpdateAttendance(Context context, String inOut) {
        strInOut = inOut;
        JSONArray jsonArray = new JSONArray();
        AppPreferences preferences = AppPreferences.getInstance(context);
        try {
            JSONObject requestParams = new JSONObject();
            if (ConnectivityUtils.isNetworkEnabled(context)) {
                try {
                    requestParams.put("punch_date", Utility.getDate(System.currentTimeMillis()));
                    requestParams.put("punch_time", Utility.getTime24Hours(System.currentTimeMillis()));
                    requestParams.put("in_out", inOut);
                    requestParams.put("employee_card", preferences.getEmpCode());
                    requestParams.put("branch_id", preferences.getBranchId());
                    requestParams.put("latitude", preferences.getCurrentLatitude());
                    requestParams.put("longitude", preferences.getCurrentLongitude());
                    requestParams.put("image_url", preferences.getEmpProfileURL());
                    requestParams.put("created_by", preferences.getEmpCode());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                RecordDetailModel model = new RecordDetailModel();
                model.setPunchDate(Utility.getDate(System.currentTimeMillis()));
                model.setPunchTime(Utility.getTime24Hours(System.currentTimeMillis()));
                model.setInOut(inOut);
                model.setEmployeeCode(preferences.getEmpCode());
                model.setWarehouseId(preferences.getWarehouseCode());
                model.setLatitude(/*preferences.getCurrentLatitude()*/"0");
                model.setLongitude(/*preferences.getCurrentLongitude()*/"0");
                model.setImageURL(preferences.getEmpProfileURL());
                model.setUserId(preferences.getEmpCode());

                String data = new Gson().toJson(model, RecordDetailModel.class);
                JSONObject jsonObject = new JSONObject(data);

                jsonArray.put(jsonObject);

                try {
                    if (PatrollingDatabaseHelper.getInstance(context).getAllOfflineData() != null && PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().size() != 0) {
                        JSONObject object = new JSONObject(PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().get(0).getResponse());
                        if (object != null) {
                            JSONArray jsonArra = object.getJSONArray("data");
                            for (int i = 0; i < jsonArra.length(); i++) {
                                jsonArray.put(object.getJSONArray("data").get(i));
                            }
                            PatrollingOfflineStreamModel patrollingOfflineStreamModel = new PatrollingOfflineStreamModel();
                            patrollingOfflineStreamModel.setDate(System.currentTimeMillis() / 1000);
                            try {
                                patrollingOfflineStreamModel.setResponse(new JSONObject().put("data", jsonArray).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            PatrollingDatabaseHelper.getInstance(context).deleteOfflineTableData();
                            PatrollingDatabaseHelper.getInstance(context).insertOfflineData(patrollingOfflineStreamModel);
                        }
                    } else {
                        PatrollingOfflineStreamModel patrollingOfflineStreamModel = new PatrollingOfflineStreamModel();
                        patrollingOfflineStreamModel.setDate(System.currentTimeMillis() / 1000);
                        try {
                            patrollingOfflineStreamModel.setResponse(new JSONObject().put("data", jsonArray).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        PatrollingDatabaseHelper.getInstance(context).insertOfflineData(patrollingOfflineStreamModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI();
            } else {
                if (preferences.getRoleName().equalsIgnoreCase("Guards")) {
                    LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_ATTENDANCE, requestParams, this, Constants.RequestType.UPDATE_ATTENDANCE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateOffLineData(Context context) {
        try {
            if (PatrollingDatabaseHelper.getInstance(context).getAllOfflineData() != null && PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().size() != 0) {
                object = new JSONObject(PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().get(0).getResponse());
                if (object != null) {
                    hitApiUpdateOfflineData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitApiUpdateOfflineData() {
        LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(OFFLINE_DATA, object, this, Constants.RequestType.OFFLINE_DATA);
    }

    public int getBatteryPercentage(Context context) {

        if (Build.VERSION.SDK_INT >= 21) {

            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;

            return (int) (batteryPct * 100);
        }
    }

    public void updateUI() {
//        Intent intent = null;
//        if (contextService == null) {
//            intent = new Intent(BaseApplication.getInstance(), DashBoardActivityKotlin2.class);
//        } else {
//            intent = new Intent(contextService, DashBoardActivityKotlin2.class);
//        }
        if (strInOut.equalsIgnoreCase("I")) {
            preferences.setAttendanceId(System.currentTimeMillis());
            preferences.setInOut("I");
            HomeFragmentMap.getInstance().updateSOS(true);
//            if (contextService == null) {
////                Utility.showNotification(BaseApplication.getInstance(), "Wach (IIT Delhi)", "You are inside the Campus", intent);
//
//            } else {
////                Utility.showNotification(contextService, "Wach (IIT Delhi)", "You are Out Of Campus", intent);
//            }

        } else {
            preferences.setAttendanceId(0L);
            preferences.setPunchingDate(Utility.getReportDate(System.currentTimeMillis()));
            preferences.setInOut("O");
            HomeFragmentMap.getInstance().updateSOS(false);
//            if (contextService == null) {
////                Utility.showNotification(BaseApplication.getInstance(), "Wach (IIT Delhi)", "You are inside the Campus", intent);
//            } else {
////                Utility.showNotification(contextService, "Wach (IIT Delhi)", "You are Out Of Campus", intent);
//            }
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (isSuccess) {
            if (responseCode == Constants.RequestType.UPDATE_ATTENDANCE_LOGOUT) {
                if (context != null) {
                    AppPreferences.getInstance(context).clear();
                    AppPreferences.getInstance(context).setAppRestarted(false);

                    stopRunningService(BaseApplication.getInstance());

                    Intent intent = new Intent(context, LoginWithNumberActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((DashBoardActivityKotlin2) context).finish();
                }
            } else if (responseCode == Constants.RequestType.OFFLINE_DATA) {
                if (contextService != null) {
                    PatrollingDatabaseHelper.getInstance(contextService).deleteOfflineTableData();
                } else {
                    PatrollingDatabaseHelper.getInstance(BaseApplication.getInstance()).deleteOfflineTableData();
                }
                object = null;
            } else if (responseCode == Constants.RequestType.UPDATE_ATTENDANCE) {
                updateUI();
            } else {

            }
        }
    }

    public void stopRunningService(Context context) {
        if (preferences.getRoleName().equalsIgnoreCase("Guards")){
        Intent intent = new Intent(context, MyBackgroundLocationService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.stopService(intent);
        } else {
            context.stopService(intent);
        }
        }
    }

//    public void hitApiUpdateAttendanceLogout(Context context, String inOut) {
//        strInOut = inOut;
//        this.context = context;
//        JSONArray jsonArray = new JSONArray();
//        AppPreferences preferences = AppPreferences.getInstance(context);
//        try {
//            JSONObject requestParams = new JSONObject();
//            if (ConnectivityUtils.isNetworkEnabled(context)) {
//                try {
//                    requestParams.put("punch_date", Utility.getDate(System.currentTimeMillis()));
//                    requestParams.put("punch_time", Utility.getTime24Hours(System.currentTimeMillis()));
//                    requestParams.put("in_out", inOut);
//                    requestParams.put("employee_card", preferences.getEmpCode());
//                    requestParams.put("branch_id", preferences.getBranchId());
//                    requestParams.put("latitude", preferences.getCurrentLatitude());
//                    requestParams.put("longitude", preferences.getCurrentLongitude());
//                    requestParams.put("image_url", preferences.getEmpProfileURL());
//                    requestParams.put("created_by", preferences.getEmpCode());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (!ConnectivityUtils.isNetworkEnabled(context)) {
//                RecordDetailModel model = new RecordDetailModel();
//                model.setPunchDate(Utility.getDate(System.currentTimeMillis()));
//                model.setPunchTime(Utility.getTime24Hours(System.currentTimeMillis()));
//                model.setInOut(inOut);
//                model.setEmployeeCode(preferences.getEmpCode());
//                model.setWarehouseId(preferences.getWarehouseCode());
//                model.setLatitude(/*preferences.getCurrentLatitude()*/"0");
//                model.setLongitude(/*preferences.getCurrentLongitude()*/"0");
//                model.setImageURL(preferences.getEmpProfileURL());
//                model.setUserId(preferences.getEmpCode());
//
//                String data = new Gson().toJson(model, RecordDetailModel.class);
//                JSONObject jsonObject = new JSONObject(data);
//
//                jsonArray.put(jsonObject);
//
//                try {
//                    if (PatrollingDatabaseHelper.getInstance(context).getAllOfflineData() != null && PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().size() != 0) {
//                        JSONObject object = new JSONObject(PatrollingDatabaseHelper.getInstance(context).getAllOfflineData().get(0).getResponse());
//                        if (object != null) {
//                            JSONArray jsonArra = object.getJSONArray("data");
//                            for (int i = 0; i < jsonArra.length(); i++) {
//                                jsonArray.put(object.getJSONArray("data").get(i));
//                            }
//                            PatrollingOfflineStreamModel patrollingOfflineStreamModel = new PatrollingOfflineStreamModel();
//                            patrollingOfflineStreamModel.setDate(System.currentTimeMillis() / 1000);
//                            try {
//                                patrollingOfflineStreamModel.setResponse(new JSONObject().put("data", jsonArray).toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            PatrollingDatabaseHelper.getInstance(context).deleteOfflineTableData();
//                            PatrollingDatabaseHelper.getInstance(context).insertOfflineData(patrollingOfflineStreamModel);
//                        }
//                    } else {
//                        PatrollingOfflineStreamModel patrollingOfflineStreamModel = new PatrollingOfflineStreamModel();
//                        patrollingOfflineStreamModel.setDate(System.currentTimeMillis() / 1000);
//                        try {
//                            patrollingOfflineStreamModel.setResponse(new JSONObject().put("data", jsonArray).toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                        PatrollingDatabaseHelper.getInstance(context).insertOfflineData(patrollingOfflineStreamModel);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                updateUI();
//            } else {
//                if (preferences.getRoleName().equalsIgnoreCase("Guards")) {
//                    LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_ATTENDANCE, requestParams, this, Constants.RequestType.UPDATE_ATTENDANCE_LOGOUT);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
