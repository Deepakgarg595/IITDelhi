package com.app.iitdelhicampus.network;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.app.iitdelhicampus.BuildConfig;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.utility.Logger;
import com.app.iitdelhicampus.utility.Securities;
import com.app.iitdelhicampus.utility.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;

public class LoopJRequestHandler {
    private static int STATUS_SUCCESS = 200;
    private static LoopJRequestHandler loopJRequestHandler;
    private OnUpdateResponse onUpdateResponse;


    private LoopJRequestHandler() {
    }

    public static LoopJRequestHandler getInstance() {
        if (loopJRequestHandler == null)
            return loopJRequestHandler = new LoopJRequestHandler();
        return loopJRequestHandler;
    }


    public static void downloadMediaFile(final Context context, String url, String fileName) {

        ContextWrapper cw = new ContextWrapper(BaseApplication.getInstance());
        File directory = null;
//        if (fileName.contains("/")) {
//             directory = cw.getDir(fileName.split("/")[1], Context.MODE_PRIVATE);
//        }
//        else
//        {
        directory = cw.getDir("RiyalBanner", Context.MODE_PRIVATE);
//        }

        LoopJNetworkManager.getInstance().get(context, url, new FileAsyncHttpResponseHandler(directory) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.d("download failed", "onFailure: ");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                String filePath = "file:///" + response.getAbsolutePath();
                Log.e("O/P file:", filePath);


            }

            @Override
            public void onProgress(final long bytesWritten, final long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                final double progress = (bytesWritten * 1.0 / totalSize) * 100;

                Log.e("Download_progress:", String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? progress : -1));
            }

        });
    }

    public void hitApiPostMethod(final String requestUrl, final RequestParams requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {
        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_ENCRYPTED" + requestCode, requestParams.toString());
        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;

        LoopJNetworkManager.getInstance().post(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public Header[] getRequestHeaders() {
                return super.getRequestHeaders();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("API:", "Success..");
                updateResponse(statusCode, responseBody, requestCode);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("API:", "Failed..");
                updateResponse(statusCode, responseBody, requestCode);

            }

        });


    }

    public void hitApiPostMethodByRequestParam(final String requestUrl, final RequestParams requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {
//        try {
//            requestParams.put("appType", "apekshah");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_" + requestCode, requestParams.toString());

        String encryptedData = Securities.getInstance().encriptedData(requestParams.toString());
        RequestParams requestParamsEncrypted = new RequestParams();
        requestParamsEncrypted.put("requestData", encryptedData);
        requestParamsEncrypted.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
        requestParamsEncrypted.put(Constants.Params.REQUEST_ID, requestCode);
        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_ENCRYPTED" + requestCode, requestParamsEncrypted.toString());
        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;

        LoopJNetworkManager.getInstance().post(requestUrl, requestParamsEncrypted, new AsyncHttpResponseHandler() {
            @Override
            public Header[] getRequestHeaders() {
                return super.getRequestHeaders();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                updateResponse(statusCode, responseBody, requestCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateResponse(statusCode, responseBody, requestCode);

            }

        });


    }

    public void hitApiPostMethodByJsonObject(final String requestUrl, final JSONObject requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {

        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_" + requestCode, requestParams.toString());

        String encryptedData = Securities.getInstance().encriptedData(requestParams.toString());
        RequestParams requestParamsEncrypted = new RequestParams();
        requestParamsEncrypted.put("requestData", encryptedData);
        requestParamsEncrypted.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
        requestParamsEncrypted.put(Constants.Params.REQUEST_ID, requestCode);

        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_ENCRYPTED" + requestCode, requestParamsEncrypted.toString());
        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;

        LoopJNetworkManager.getInstance().post(requestUrl, requestParamsEncrypted, new AsyncHttpResponseHandler() {
            @Override
            public Header[] getRequestHeaders() {
                return super.getRequestHeaders();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                updateResponse(statusCode, responseBody, requestCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateResponse(statusCode, responseBody, requestCode);

            }

        });


    }

    public void hitApiPostMethodByJsonObjectWithoutEncryption(final String requestUrl, final RequestParams requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {
        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_" + requestCode, requestParams.toString());
        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;
        LoopJNetworkManager.getInstance().post(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public Header[] getRequestHeaders() {
                return super.getRequestHeaders();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                updateResponse(statusCode, responseBody, requestCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateResponse(statusCode, responseBody, requestCode);

            }

        });
    }



    public void hitApiGetMethod(final String requestUrl, final OnUpdateResponse onUpdateResponse, final int requestCode) {
        this.onUpdateResponse = onUpdateResponse;
        LoopJNetworkManager.getInstance().get(requestUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                updateResponse(statusCode, responseBody, requestCode);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateResponse(statusCode, responseBody, requestCode);

            }

        });
    }

    public void hitApiUploadMedia(final String requestUrl, final RequestParams requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {
        if (BuildConfig.DEBUG)
            Logger.printLog("REQUEST_" + requestCode, requestParams.toString());

        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;
        AsyncHttpClient client = LoopJNetworkManager.getInstance();
        client.post(requestUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public Header[] getRequestHeaders() {
                return super.getRequestHeaders();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                long progressPercentage = (long) 100 * bytesWritten / totalSize;
                if (BuildConfig.DEBUG)
                    Log.e("Percentage", progressPercentage + "");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                updateResponse(statusCode, responseBody, requestCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                updateResponse(statusCode, responseBody, requestCode);
                onUpdateResponse.onResultSuccess(false, null, requestCode);
            }

        });
    }

    public void hitApiPostMethodByJSON(final String requestUrl, final JSONObject requestParams, final OnUpdateResponse onUpdateResponse, final int requestCode) {

        this.onUpdateResponse = null;
        this.onUpdateResponse = onUpdateResponse;

        class HitAp extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                return callServiceJSORequest(requestUrl, requestParams);

            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                Log.e("API:", "Success..");
                if (StringUtils.isNullOrEmpty(response)) {
                    onUpdateResponse.onResultSuccess(false, response, requestCode);

                } else {
                    onUpdateResponse.onResultSuccess(true, response, requestCode);

                }

            }
        }

        new HitAp().execute();
    }

    private String callServiceJSORequest(String urls, JSONObject jsonObject) {
        HttpsURLConnection con = null;
        try {
            Log.e("API:", "Hit..");
            URL url = new URL(urls);
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(30000);
            con.setDoOutput(true);


            if (con instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) con;
                httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                httpsConn.setHostnameVerifier(/*new AllowAllHostnameVerifier()*/MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            }

            PrintStream ps = new PrintStream(con.getOutputStream());
            ps.println(jsonObject);
            ps.close();
            con.connect();
            String response = "";
            if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//                String headerToken = con.getHeaderField("authorization");
//                if(FileUtils.isNullOrEmpty(headerToken)) {
//                    AppPreferences.getInstance(ApplicationContext.getInstance()).setAuthToken(headerToken);
//                }
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(con.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    response =response+ line;
                }
                br.close();
                return response;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();
            System.gc();
        }
        return null;
    }


    public void updateResponse(int statusCode, byte[] responseBody, int requestCode) {

        try {
            String response;
            if (BuildConfig.DEBUG)
                Logger.printLog("RESPONSE_" + requestCode, responseBody.toString());
            response = new String(responseBody);

//            String responseEncrypted = new String(responseBody);
//            if(BuildConfig.DEBUG)
//            Logger.printLog("RESPONSE_ENCRYPTED", responseEncrypted);
//
//                EncryptedResponseModel encryptedResponseModel = new Gson().fromJson(
//                        responseEncrypted, EncryptedResponseModel.class);
//                response = Securities.getInstance().decriptedData(encryptedResponseModel.getResponse());
//            if(BuildConfig.DEBUG)
//            Logger.printLog("RESPONSE....." ,response);
//            if (response.contains("Error!! Authentication violation")) {
//                AppPreferences.getInstance(BaseApplication.getInstance()).clear();
//                AppPreferences.getInstance(BaseApplication.getInstance()).setTutorialRead(true);
//                Intent intent = new Intent(BaseApplication.getInstance(), LoginWithNumberActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                BaseApplication.getInstance().startActivity(intent);
//                return;
//            }
            onUpdateResponse.onResultSuccess(statusCode == STATUS_SUCCESS, response, requestCode);
            if (BuildConfig.DEBUG)
                Logger.printLog("RESPONSE_" + requestCode, response);
        } catch (NullPointerException e) {
            e.printStackTrace();
            onUpdateResponse.onResultSuccess(statusCode == STATUS_SUCCESS, "", requestCode);
        }

    }
}

