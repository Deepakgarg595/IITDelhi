//package com.app.fullypetvetapp.file;
//
//import com.chatmodule.listener.OnProgressReceiveListener;
//
//import java.util.ArrayList;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class CiaoProgressManager {
//	private static CiaoProgressManager progressManger;
//	private final ConcurrentHashMap<String, CiaoDownloader> downloadInfoMap;
//	private ArrayList<OnProgressReceiveListener> listenersList;
//
//	private CiaoProgressManager() {
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
//	public static CiaoProgressManager getInstance() {
//		if (progressManger == null) {
//			progressManger = new CiaoProgressManager();
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
//	public void addDowloadInfo(String downlaodId, CiaoDownloader downloader) {
//		downloadInfoMap.put(downlaodId, downloader);
//	}
//
//	public void removeDownloadInfo(String fileName) {
//		downloadInfoMap.remove(fileName);
//	}
//
//	public CiaoDownloader getDownloader(String downloadId) {
//		return downloadInfoMap.containsKey(downloadId) ? downloadInfoMap.get(downloadId) : null;
//	}
//
//	public ConcurrentHashMap<String, CiaoDownloader> getDownloadInfoMap() {
//		return downloadInfoMap;
//	}
//}
