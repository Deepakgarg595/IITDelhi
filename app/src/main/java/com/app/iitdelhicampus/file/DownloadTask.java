//package com.app.fullypetvetapp.file;
//
//import android.app.DownloadManager;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.util.Log;
//import android.widget.ImageView;
//
//import com.android.volley.Response;
//import com.android.volley.toolbox.ImageRequest;
//import com.chatmodule.krapps.network.VolleyManager;
//import com.chatmodule.xmpp.packets.AmazonS3;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//
///**
// * Created by pankajsoni on 07/06/16.
// */
//public class DownloadTask implements AmazonS3.OnUrlFetchedListener {
//
//    private static String TAG = "DownloadTask";
//    protected String s3Key;
//    protected CharSequence description;
//    protected File downloadFile;
//    private boolean isThumb;
//    private OnDownloadIdFetchListener onDownloadIdFetched;
//    private DownloadManager downloadManager;
//    private Context context;
//
//    public DownloadTask(Context context, String key) {
//        this.context = context;
//        this.s3Key = key;
//    }
//
//    public DownloadTask(String s3Key, CharSequence description, File downloadFile, OnDownloadIdFetchListener onDownloadIdFetched) {
//        this.s3Key = s3Key;
//        this.description = description;
//        this.downloadFile = downloadFile;
//        this.onDownloadIdFetched = onDownloadIdFetched;
//    }
//
//    public DownloadTask(String s3Key, String description, File downloadFile, OnDownloadIdFetchListener onDownloadIdFetched, boolean isThumb) {
//        this.s3Key = s3Key;
//        this.description = description;
//        this.downloadFile = downloadFile;
//        this.onDownloadIdFetched = onDownloadIdFetched;
//        this.isThumb = isThumb;
//    }
//
//    public void download(Context context) {
//        this.context = context;
//        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        AmazonS3.getS3Url(s3Key, this, isThumb).execute(context);
//        onUrlFetched(s3Key, "", false);
//    }
//
//    public void download() {
//        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        AmazonS3.getS3Url(s3Key, this, isThumb).execute(context);
//    }
//
//    @Override
//    public void onUrlFetched(String token, String url, boolean status) {
//        Log.e(TAG, "status: " + status);
//        Log.e(TAG, "token: " + token);
//        Log.e(TAG, "url: " + url);
//
//
//        JSONObject param = new JSONObject();
//        try {
//            param.put("token", token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//              new ImageView(context).setImageBitmap(response);
//            }
//        }, 0, 0, null, null);
//
//
//        // Adding request to request queue
//        VolleyManager.getInstance(context).addToRequestQueue(ir, "Tag");
//
//
////        String downLoadKey= key.toLowerCase();
////        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Nlh.i().dp() + downLoadKey))
////                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
////                .setVisibleInDownloadsUi(false)
////                .setDescription(description)
////                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
////                .setDestinationUri(Uri.fromFile(downloadFile));
////
////
////        request.allowScanningByMediaScanner();
////        long id = downloadManager.enqueue(request);
////        if (onDownloadIdFetched != null)
////            onDownloadIdFetched.onDownloadIdFetched(id);
////        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(downloadFile)));
//    }
//
//    public interface OnDownloadIdFetchListener {
//        void onDownloadIdFetched(long downloadId);
//    }
//}
