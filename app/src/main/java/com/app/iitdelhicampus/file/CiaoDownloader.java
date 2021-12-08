//package com.app.fullypetvetapp.file;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//
//import com.chatmodule.async.HttpRequest;
//import com.chatmodule.constants.AppConstants;
//import com.chatmodule.krapps.utils.StringUtils;
//import com.chatmodule.xmpp.packets.AmazonS3;
//
//import org.apache.http.HttpStatus;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by dharam on 30/12/16.
// */
//
//public class CiaoDownloader extends Thread implements AmazonS3.OnUrlFetchedListener {
//    private static final String TAG = "CiaoDownloader";
//    Intent intent = null;
//    private CiaoDownloadListener downloadListener;
//    private Context context;
//    private FileMeta fileMeta;
//    private int downloadedpercentage;
//    private boolean isCancelled;
//
//    public CiaoDownloader(Context context, FileMeta fileMeta, CiaoDownloadListener downloadListener) {
//        this.context = context;
//        this.fileMeta = fileMeta;
//        this.downloadListener = downloadListener;
//        intent = new Intent(AppConstants.LOCAL_BROADCAST_UPDATE_DOWNLOAD_STATUS);
//    }
//
//    @Override
//    public void run() {
//        AmazonS3.getS3Url(fileMeta.getFileKey().toLowerCase(), this, false, null).execute(context);
//    }
//
//    @Override
//    public void onUrlFetched(String token, String url, boolean status) {
//        if (StringUtils.isNullOrEmpty(url)) {
//            onDownloadCompleted(DownloadStatus.S3_FAIL);
//            return;
//        }
//        if (status) {
//            downloadFile(token, url);
//            return;
//        }
//        if (url.contains("Server Error")) {
//            CiaoProgressManager.getInstance().removeDownloadInfo(fileMeta.getDownloadId());
//            AmazonS3.getS3Url(fileMeta.getFileKey().toLowerCase(), this, true, token).execute(context);
//            return;
//        }
//        onDownloadCompleted(DownloadStatus.XMPP_FAIL);
//    }
//
//
//    private void downloadFile(String token, String url) {
//        try {
//            JSONObject data = new JSONObject();
//            data.put("token", token);
//            HttpRequest request = new HttpRequest(url).prepare(HttpRequest.Method.POST).withData(data.toString());
//            if (request.responseCode() == HttpStatus.SC_OK) {
//                switch (fileMeta.getDirKey()) {
//                    case PROFILE:
//                        if (fileMeta.isWriteInFile()) {
//                            InputStream inputStream = request.sendAndReadStream();
//                            long contentLength = request.getContentLength();
//                            writeInFile(inputStream, contentLength);
//                        } else {
//                            fileMeta.setFileData(request.sendAndReadBytes());
//                            onDownloadCompleted(DownloadStatus.SUCCESS);
//                        }
//                        break;
//                    case CHAT:
//                        InputStream inputStream = request.sendAndReadStream();
//                        long contentLength = request.getContentLength();
//                        request.getContentLength();
//                        writeInFile(inputStream, contentLength);
//                        break;
//                    default:
//                        break;
//                }
//                return;
//            }
//            Log.e(TAG, "Unable to download file, " + fileMeta.getFileKey() + ", response code: " + request.responseCode());
//            onDownloadCompleted(DownloadStatus.S3_FAIL);
//        } catch (Exception e) {
//            e.printStackTrace();
//            onDownloadCompleted(DownloadStatus.S3_FAIL);
//        }
//    }
//
//    private void writeInFile(InputStream inputStream, long contentLength) {
//        try {
//            ensureDir(fileMeta.getOutFile());
//            File file = new File(fileMeta.getOutFile());
//            file.createNewFile();
//            FileOutputStream fileOutput = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0;
//            int downloadedSize = 0;
//            while ((bufferLength = inputStream.read(buffer)) > 0 && !isCancelled) {
//                fileOutput.write(buffer, 0, bufferLength);
//                downloadedSize += bufferLength;
//                updateProgress(calculatePercentage(downloadedSize, contentLength));
//            }
//            fileOutput.close();
//            if (isCancelled) {
//                Log.e(TAG, "File was cancelled..");
//                if (file.exists()) {
//                    file.delete();
//                }
//                onDownloadCompleted(DownloadStatus.S3_FAIL);
//                return;
//            }
//            updateProgress(calculatePercentage(contentLength, contentLength));
//            onDownloadCompleted(DownloadStatus.SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            onDownloadCompleted(DownloadStatus.S3_FAIL);
//        }
//    }
//
//    private void ensureDir(String filePath) throws Exception {
//        if (StringUtils.isNullOrEmpty(filePath)) {
//            throw new Exception("Unable to download file, Filepath is null");
//        }
//        String dir = filePath.substring(0, filePath.lastIndexOf(File.separatorChar));
//        File file = new File(dir);
//        file.mkdirs();
//    }
//
//    private int calculatePercentage(long downloaded, long totalSize) {
//        if (totalSize == 0) return 100;
//        return (int) ((downloaded * 100) / totalSize);
//    }
//
//    private void updateProgress(int progress) {
//        if (downloadedpercentage == progress || (downloadedpercentage + 5) > progress) return;
//        ConcurrentHashMap<String, Integer> downloadProgressMap = new ConcurrentHashMap<String, Integer>();
//        downloadProgressMap.put(fileMeta.getDownloadId(), progress);
//        intent.putExtra(AppConstants.EXTRA_CHAT_DOWNLOAD_PROGRESS_MAP, downloadProgressMap);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//        downloadedpercentage = progress;
//    }
//
//    private void onDownloadCompleted(DownloadStatus status) {
//        if (downloadListener == null) return;
//        downloadListener.onDownloadCompleted(fileMeta, status);
//    }
//
//    public FileMeta getFileMeta() {
//        return fileMeta;
//    }
//
//    public void setCancelled() {
//        this.isCancelled = true;
//    }
//
//    public enum DownloadStatus {
//        SUCCESS, XMPP_FAIL, S3_FAIL;
//    }
//
//    public interface CiaoDownloadListener {
//        void onDownloadCompleted(FileMeta fileMeta, DownloadStatus status);
//    }
//}
