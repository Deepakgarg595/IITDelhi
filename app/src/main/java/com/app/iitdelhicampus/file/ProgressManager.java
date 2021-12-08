//package com.app.fullypetvetapp.file;
//
//import android.graphics.Bitmap;
//
//import com.chatmodule.listener.OnProgressReceiveListener;
//
//import java.util.ArrayList;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ProgressManager {
//	private static ProgressManager	progressManger;
//	private final ConcurrentHashMap<String, String> downloadInfoMap;
//	private ArrayList<OnProgressReceiveListener> listenersList;
//	private Bitmap bitmap;
//
//	private ProgressManager() {
//		listenersList = new ArrayList<OnProgressReceiveListener>();
//		downloadInfoMap = new ConcurrentHashMap<>();
//	}
//
//	public void updateAllListenersProgress(int progress) {
//		for (OnProgressReceiveListener onProgressReceiveListener : listenersList) {
//			onProgressReceiveListener.onProgressChange(progress);
//		}
//	}
//
//	public static ProgressManager getInstance() {
//		if (progressManger == null) {
//			progressManger = new ProgressManager();
//		}
//		return progressManger;
//	}
//
//	public void addProgressListener(OnProgressReceiveListener listener) {
//		listenersList.add(listener);
//	}
//
//	public void removeProgressListener(OnProgressReceiveListener listener) {
//		if (listenersList.contains(listener)) {
//			listenersList.remove(listener);
//		}
//	}
//
//	public void addDowloadInfo(String fileName, String downloadId) {
//		downloadInfoMap.put(fileName, downloadId);
//	}
//
//	public void removeDownloadInfo(String fileName) {
//		downloadInfoMap.remove(fileName);
//	}
//
//	public String getDownloadId(String id) {
//		return downloadInfoMap.containsKey(id) ? downloadInfoMap.get(id) : "-1";
//	}
//
//	public ConcurrentHashMap<String, String> getDownloadInfoMap() {
//		return downloadInfoMap;
//	}
//}
