package com.app.iitdelhicampus.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.NotificationListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.WeddingNotificationModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationFragment extends BaseFragment implements OnUpdateResponse,View.OnClickListener {
    View view;
    private RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    NotificationListAdapter notificationListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        init();
        hitNotificationList();
//        hitNotificationAcceptReject();
        return view;
    }


    private void init() {
        recyclerview= (RecyclerView)view.findViewById(R.id.recyclerview);
        linearLayoutManager= new LinearLayoutManager(mActivity);
        notificationListAdapter= new NotificationListAdapter(mActivity);
        recyclerview.setAdapter(notificationListAdapter);
        recyclerview.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    public void hitNotificationList() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.NOTIFICATION_LIST);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.NOTIFICATION_LIST, requestParams, this, Constants.RequestType.NOTIFICATION_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hitNotificationAcceptReject() {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put(Constants.Params.USER_ID, preferences.getUserId());
                requestParams.put(Constants.Params.REQUEST_ID, Constants.RequestType.ACCEPT_REJECT);
                requestParams.put(Constants.Params.DEVICE_ID, preferences.getDeviceId());
                requestParams.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                requestParams.put(Constants.Params.SESSION_TOCKEN, preferences.getSessionToken());
                requestParams.put(Constants.Params.REQUEST_DATE, Utility.getTimeZone());
                requestParams.put(Constants.Params.NOTIFICATION_ID, "");
                requestParams.put(Constants.Params.ACCEPT_REJECT, "");



            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJsonObject(Constants.Services.ACCEPT_REJECT, requestParams, this, Constants.RequestType.ACCEPT_REJECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        if (StringUtils.isNullOrEmpty(response))
        {
            return;
        }
        if (isSuccess) {
            switch (responseCode){
                case Constants.RequestType.NOTIFICATION_LIST:
                    WeddingNotificationModel weddingNotificationModel= gson.fromJson(response,WeddingNotificationModel.class);


                    break;

                case Constants.RequestType.ACCEPT_REJECT:

                    break;
            }
        }

    }

}
