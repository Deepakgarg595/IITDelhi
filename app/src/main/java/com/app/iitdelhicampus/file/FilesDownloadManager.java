package com.app.iitdelhicampus.file;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.utility.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FilesDownloadManager extends BroadcastReceiver {

    public static final String KEY_DIR = "KEY_DIR";
    public static final String KEY_ID = "KEY_ID";

    private static final String LOG_TAG = "FilesDownloadManager";
    private static final long HUNDRED_MS = 500;
    private static File downloadFile;
    private static HandlerThread progressHandlerThread;

    public static Map<String, Integer> queryDownloadStatuses(Context context) {
        DownloadManager.Query query = new DownloadManager.Query();

        int flags =
                DownloadManager.STATUS_PAUSED
                        | DownloadManager.STATUS_PENDING
                        | DownloadManager.STATUS_RUNNING;

        query.setFilterByStatus(flags);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);

        final HashMap<String, Integer> fileProgressMap = new HashMap<>();

        if (cursor != null && cursor.moveToFirst()) {

            cursor.moveToFirst();
            do {
                String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));

                if (StringUtils.isNullOrEmpty(description)) continue;

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(description);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "queryFileState", e);
                }

                if (jsonObject == null) continue;

                String dir = jsonObject.optString(FilesDownloadManager.KEY_DIR);
                if (FileUtils.DIR_PROFILE.equals(dir)) continue;

                String key = jsonObject.optString(FilesDownloadManager.KEY_ID);

                int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);


                fileProgressMap.put(key, dl_progress);

            } while (cursor.moveToNext());
        }

        closeCursor(cursor);

        return fileProgressMap;
    }

//    protected static void downloadPic(Context context, final PicLoader picLoader) {
//        String requestId = picLoader.requestId();
//        Contact contact = RealmContactDBHelper.getContactByUserId(requestId);
//        if (contact != null) {
//            if (contact.getBlockStatus() == Contact.BLOCK_STATE.BLOCKED_BY_OTHER) return;
//            if (isOnGoingDownload(requestId)) return;
//        }
//        String s3FileKey = FileUtils.getS3FileKey(picLoader.directory(), picLoader.getFileKey(), null);
//
//        String description = getDescription(picLoader.directory(), requestId);
//
//        if (FileUtils.isProfilePicExist(requestId)) {
//            Log.e("EXISTING_IMAGE",picLoader.getFile().getAbsolutePath());
//            return;
//        }
//        new DownloadTask(s3FileKey, description, picLoader.getFile().getAbsoluteFile(), null, true).download(context);
//    }
//
//    public static void downloadAnyFile(final Context context,
//                                       final RealmMessage.MSG_TYPE msg_type,
//                                       final String id, final String fileName) {
//
//        if (isOnGoingDownload(id.toLowerCase())) return;
//
//        String directory = RealmMessage.getDirectory(false, msg_type);
//
//        String description = getDescription(directory, id.toLowerCase());
//
//        String s3Key = FileUtils.getS3FileKey(directory, id.toLowerCase(), fileName);
//        downloadFile = FileUtils.getFile(directory, fileName);
//
//        new DownloadTask(s3Key, description, downloadFile, new DownloadTask.OnDownloadIdFetchListener() {
//            @Override
//            public void onDownloadIdFetched(long downloadId) {
//                ProgressManager.getInstance().addDowloadInfo(id, downloadId+"");
//                if (progressHandlerThread == null) {
//                    startProgressHandler(context);
//                }
//            }
//        }, true).download(context);
//    }

//    public static void cancelDownload(Context context, String id) {
//        if (isOnGoingDownload(id)) {
//            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//            String downloadId = ProgressManager.getInstance().getDownloadId(id);
//            if (downloadId != "-1") {
//                //downloadManager.remove(downloadId);
//                //RealmMessageDBHelper.updateMsgStatus(context, id, TO_DOWNLOAD_FILE);
//                ProgressManager.getInstance().removeDownloadInfo(id);
//            }
//        }
//    }

    protected static boolean isOnGoingDownload(String requestId) {
        //Map<String, Long> progressMap = ProgressManager.getInstance().getDownloadInfoMap();
        //return progressMap == null ? false : progressMap.containsKey(requestId);
        return false;
    }

    private static String getDescription(String dir, String requestId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(KEY_DIR, dir);
            jsonObject.putOpt(KEY_ID, requestId);
        } catch (JSONException e) {
        }
        return jsonObject.toString();
    }

    private static void closeCursor(Cursor cursor) {
        if (cursor == null) return;
        if (!cursor.isClosed()) cursor.close();
    }

    private static void startProgressHandler(final Context context) {
        progressHandlerThread = new HandlerThread(FilesDownloadManager.class.getSimpleName());
        progressHandlerThread.start();
        Looper looper = progressHandlerThread.getLooper();

        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            private Boolean retry = true;

            @Override
            public void run() {

                while (retry) {
                    ConcurrentHashMap<String, Long> downloadInfoMap = null;//ProgressManager.getInstance().getDownloadInfoMap();
                    if (downloadInfoMap != null && !downloadInfoMap.isEmpty()) {
                        retry = true;
                        ConcurrentHashMap<String, Integer> downloadProgressMap =  queryDownloadPercentage(context);
                        Intent intent = new Intent(AppConstants.LOCAL_BROADCAST_UPDATE_DOWNLOAD_STATUS);
                        intent.putExtra(AppConstants.EXTRA_CHAT_DOWNLOAD_PROGRESS_MAP, downloadProgressMap);
                        intent.putExtra(AppConstants.EXTRA_DOWNLOAD_STATUS, DownloadManager.STATUS_RUNNING);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                    } else {
                        retry = false;
                        if (progressHandlerThread != null) {
                            progressHandlerThread.quit();
                            progressHandlerThread.interrupt();
                            progressHandlerThread = null;
                        }
                    }
                    if (retry) {
                        synchronized (retry) {
                            try {
                                retry.wait(HUNDRED_MS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });
    }

    private static synchronized ConcurrentHashMap<String, Integer> queryDownloadPercentage(final Context context) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        ConcurrentHashMap<String, Long> downloadInfoMap = null;//ProgressManager.getInstance().getDownloadInfoMap();

        final ConcurrentHashMap<String, Integer> fileProgressMap = new ConcurrentHashMap<String, Integer>();
        Iterator<ConcurrentHashMap.Entry<String, Long>> it = downloadInfoMap.entrySet().iterator();
        while (it.hasNext()) {
//            synchronized (context) {
                ConcurrentHashMap.Entry<String, Long> pair = it.next();
                final String packetId = pair.getKey();

                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById((Long) pair.getValue());

                Cursor cursor = downloadManager.query(q);
                if (cursor != null && cursor.moveToFirst()) {
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        Log.e("Download", "File Name --- " + fileName);
                        fileProgressMap.put(packetId, 100);
//                sendProgressBroadcast(DOWNLOAD_STATUS.COMPLETE, null, packetId);
                        it.remove();
                        continue;
                    } else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_FAILED) {
                        Log.e("Download", "Reason --- " + cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON)));
                        cursor.close();
                        fileProgressMap.put(packetId, 0);
//                sendProgressBroadcast(DOWNLOAD_STATUS.FAILED, null, packetId);
                        it.remove();

                        continue;
                    }

                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);

                    fileProgressMap.put(packetId, dl_progress);
                }

                closeCursor(cursor);
            }
//        }
            return fileProgressMap;

    }

    /**
     * Completion handling through BroadcastReceiver
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String action = intent.getAction();
        if (!action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) return;

        try {
            onDownloadCompleted(context, intent, downloadManager);
        } catch (Exception e) {
            Log.e(LOG_TAG, "onReceive", e);
        }
    }

    private void onDownloadCompleted(Context context, Intent intent, DownloadManager downloadManager) throws Exception {
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

        DownloadManager.Query query =
                new DownloadManager.Query().setFilterById(downloadId);

        Cursor cursor = downloadManager.query(query);
        if (cursor != null && cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

            String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));

            JSONObject jsonObject = new JSONObject(description);
            String directory = jsonObject.optString(FilesDownloadManager.KEY_DIR);
            String key = jsonObject.optString(FilesDownloadManager.KEY_ID);

            if (!directory.equals(FileUtils.DIR_PROFILE))
                publishFileDownloadStatus(context, key, status);
            else {

            }
//                if (PicLoader.onFileFound(key, status)) {
//                if (directory.equals(FileUtils.DIR_PROFILE) && !AppPreferences.getInstance(context).getUserId().equals(key)) {
////                    onDownloadedProfile(key, status);
//                }
//            }
        }

        closeCursor(cursor);
    }

    private void publishFileDownloadStatus(Context context, String packetId, int status) {
        switch (status) {
            case DownloadManager.STATUS_SUCCESSFUL:

//                ProgressManager.getInstance().removeDownloadInfo(packetId);
                //RealmMessageDBHelper.updateMsgStatus(context, packetId, FILE_DOWNLOADED);

                break;
            case DownloadManager.STATUS_FAILED:
                //RealmMessageDBHelper.updateMsgStatus(context, packetId, FILE_DOWNLOAD_FAILED);
//                ProgressManager.getInstance().removeDownloadInfo(packetId);
                break;
            default:
                break;
        }
    }

//    private void onDownloadedProfile(final String userId, int status) {
//        if (status != DownloadManager.STATUS_SUCCESSFUL) return;
//
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Contact contact = RealmContactDBHelper.getContactByUserId(realm, userId);
//                assert contact != null;
//                contact.setIsUpdated(true);
//            }
//        });
//        realm.close();
//    }
}
