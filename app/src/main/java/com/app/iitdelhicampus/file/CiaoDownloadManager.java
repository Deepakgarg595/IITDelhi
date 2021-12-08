//package com.app.fullypetvetapp.file;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.chatmodule.realm.helper.RealmContactDBHelper;
//import com.chatmodule.realm.helper.RealmMessageDBHelper;
//import com.chatmodule.realm.models.RealmMessage;
//
//import java.io.File;
//import java.util.Map;
//
///**
// * Created by dharam on 30/12/16.
// */
//
//public class CiaoDownloadManager implements CiaoDownloader.CiaoDownloadListener {
//
//    private static final String TAG = "CiaoDownloadManager";
//    private static CiaoDownloadManager ciaoDownloadManager;
//    private static Context context;
//
//    private CiaoDownloadManager() {
//    }
//
//    public static CiaoDownloadManager getInstance(Context context1) {
//        context = context1;
//        if (ciaoDownloadManager == null) {
//            ciaoDownloadManager = new CiaoDownloadManager();
//        }
//        return ciaoDownloadManager;
//    }
//
//    protected boolean isOnGoingDownload(String requestId) {
//        Map<String, CiaoDownloader> progressMap = CiaoProgressManager.getInstance().getDownloadInfoMap();
//        return progressMap == null ? false : progressMap.containsKey(requestId);
//    }
//
//    public void downloadMedia(FileMeta fileMeta) {
//        File file = new File(fileMeta.getOutFile());
//        if (file.exists()) {
//            onDownloadCompleted(fileMeta, CiaoDownloader.DownloadStatus.SUCCESS);
//            return;
//        }
//        startDownload(fileMeta);
//    }
//
//    public void downloadProfilePic(FileMeta fileMeta) {
//        startDownload(fileMeta);
//    }
//
//    private void startDownload(FileMeta fileMeta) {
//        if (!isOnGoingDownload(fileMeta.getDownloadId())) {
//            Log.e(TAG, "download request for : " + fileMeta.getFileKey());
//            CiaoDownloader downloader = new CiaoDownloader(context, fileMeta, this);
//            CiaoProgressManager.getInstance().addDowloadInfo(fileMeta.getDownloadId(), downloader);
//            downloader.start();
//        }
//    }
//
//    @Override
//    public void onDownloadCompleted(final FileMeta fileMeta, CiaoDownloader.DownloadStatus status) {
//        CiaoProgressManager.getInstance().removeDownloadInfo(fileMeta.getDownloadId());
//        if (CiaoDownloader.DownloadStatus.SUCCESS == status) {
//            switch (fileMeta.getDirKey()) {
//                case PROFILE:
//                    if (fileMeta.isWriteInFile()) {
//                        if (Looper.myLooper() == null) {
//                            Looper.prepare();
//                            invokeWithHolder(context, fileMeta);
//                            Looper.loop();
//                        } else {
//                            invokeWithHolder(context, fileMeta);
//                        }
//                    } else {
//                        RealmContactDBHelper.updateProfilePic(fileMeta.getDownloadId(), fileMeta.getFileData(), false);
//                    }
//                    break;
//                case CHAT:
//                    RealmMessageDBHelper.updateFileStatus(context, fileMeta.getDownloadId(), RealmMessage.FILE_STATE.FILE_DOWNLOADED);
//                    break;
//                default:
//                    break;
//            }
//            return;
//        }
//        if (CiaoDownloader.DownloadStatus.S3_FAIL == status && fileMeta.getDirKey().equals(FileMeta.DIR.PROFILE)) {
//            RealmContactDBHelper.updateProfilePic(fileMeta.getDownloadId(), fileMeta.getFileData(), false);
//            return;
//        }
//        File file = new File(fileMeta.getOutFile());
//        if (file != null && file.exists()) {
//            file.delete();
//        }
//        RealmMessageDBHelper.updateFileStatus(context, fileMeta.getDownloadId(), RealmMessage.FILE_STATE.FILE_DOWNLOAD_FAILED);
//    }
//
//    private void invokeWithHolder(final Context context, final FileMeta fileMeta) {
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                FileUtils.displayProfilePic(context, fileMeta);
//            }
//        });
//    }
//
//    public boolean cancelDownlaoding(String downloadId) {
//        CiaoDownloader downloader = CiaoProgressManager.getInstance().getDownloader(downloadId);
//        if (downloader != null) {
//            downloader.setCancelled();
//            onDownloadCompleted(downloader.getFileMeta(), CiaoDownloader.DownloadStatus.S3_FAIL);
//            return true;
//        }
//        return false;
//    }
//}
