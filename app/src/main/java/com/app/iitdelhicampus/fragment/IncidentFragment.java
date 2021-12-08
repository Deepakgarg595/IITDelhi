package com.app.iitdelhicampus.fragment;

import static com.app.iitdelhicampus.constants.Constants.SOS;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.IncidentAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.HomeImageDataModel;
import com.app.iitdelhicampus.model.SOSModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.Datastatic;
import com.app.iitdelhicampus.utility.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;


public class IncidentFragment extends BaseFragment implements OnUpdateResponse {
    private RecyclerView rec;
    private LinearLayout llLogin;
    String status;
    private View view;
    private IncidentAdapter incidentAdapter;
    private ArrayList<HomeImageDataModel> list;
    private ArrayList<HomeImageDataModel> list2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_incident, container, false);
        init();
        return view;
    }

    private void init() {
        status = "";
        rec = view.findViewById(R.id.rec);
        llLogin = view.findViewById(R.id.llLogin);
        rec.setLayoutManager(new GridLayoutManager(mActivity, 3));
        incidentAdapter = new IncidentAdapter(mActivity);
        rec.setAdapter(incidentAdapter);
        incidentAdapter.updateList(Datastatic.getInstance().getIncidentList());
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "";
                list = incidentAdapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelected()) {
                        status = status + list.get(i).getName() + ",";
                    }
                }
                if (status.length() == 0) {
                    ToastUtils.showToast(mActivity, "Please select min one Incident Type");
                    return;
                } else {
                    status = status.substring(0, status.length() - 1);
                }
                if (preferences.getInOut().equalsIgnoreCase("I")) {
                    hitApi(status);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(mActivity)
                            .setTitle("IIT Delhi")
                            .setMessage("You are out of campus.")
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
                }
//                initMultiSelectionDialog();
            }
        });
    }

    private void hitApi(String status) {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                onmessage(status);
                return;
            }
//            strInOut = inOut;
//            strEmpCode = empCode;

            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("sos_datetime", System.currentTimeMillis() / 1000 + "");
                requestParams.put("message_id", "0");
                requestParams.put("type", status);
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

    private void onmessage(String status) {
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

    private void phoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode("1000")));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
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

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                    .setTitle("Incident")
                    .setMessage("We received Incident message. We will contact you shortly !")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            list2 = Datastatic.getInstance().getIncidentList();
                            for (int i = 0; i < list2.size(); i++) {
                                list2.get(i).setSelected(false);
                            }
                            incidentAdapter.updateList(list2);
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

    private void initMultiSelectionDialog(SOSModel messageModel) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_incident);
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
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (responseCode == Constants.RequestType.SOS) {
            list2 = Datastatic.getInstance().getIncidentList();
            for (int i = 0; i < list2.size(); i++) {
                list2.get(i).setSelected(false);
            }
            incidentAdapter.updateList(list2);
            SOSModel messageModel = gson.fromJson(response.toString(), SOSModel.class);
            initMultiSelectionDialog(messageModel);
        }
    }
}