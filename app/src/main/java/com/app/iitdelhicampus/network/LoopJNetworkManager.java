package com.app.iitdelhicampus.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;

import java.security.KeyStore;


/**
 * Created by Donny Dominic on 31-03-2017.
 */

public class LoopJNetworkManager {

    private static AsyncHttpClient asyncHttpClient;
    private static final int TIME_OUT = 300000;

    public static AsyncHttpClient getInstance() {
        if (asyncHttpClient == null) {

            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.addHeader("Accept", "application/json");
            asyncHttpClient.addHeader("Content-Type", "application/json");
            asyncHttpClient.setEnableRedirects(true);
            asyncHttpClient.setResponseTimeout(TIME_OUT);
//            asyncHttpClient.setURLEncodingEnabled(false);
            asyncHttpClient.setMaxRetriesAndTimeout(3, TIME_OUT);

            // MAKE SELF SIGNED
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                asyncHttpClient.setSSLSocketFactory(sf);
            } catch (Exception e) {
            }
        }

        return asyncHttpClient;
    }

    public void cancelRequest() {
        asyncHttpClient.cancelAllRequests(true);
    }
}
