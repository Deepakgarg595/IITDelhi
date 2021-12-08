//package com.app.fullypetvetapp.file;
//
//import android.app.DownloadManager;
//import android.content.Context;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.widget.ImageView;
//
//import com.bumptech.glide.DrawableRequestBuilder;
//import com.bumptech.glide.DrawableTypeRequest;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.RequestCreator;
//import com.squareup.picasso.Transformation;
//
//import java.io.File;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * Created by pankajsoni on 09/06/16.
// */
//public class PicLoader {
//
//    private static final String TAG = "PicLoader";
//    private static final int MAX_BUF_SIZE = 100;
//    private static ConcurrentHashMap<String, PicLoader> requests = new ConcurrentHashMap<>();
//    private static LinkedBlockingQueue<String> failedRequests;
//    private Context context;
//    private boolean centerCrop;
//    private Integer placeholder;
//    private boolean fitCenter;
//    private Integer overrideWidth;
//    private Integer overrideHeight;
//    private RequestListener<File, GlideDrawable> glideDrawableRequestListener;
//    private String fileKey, requestId;
//    private ImageView imageView;
//    private LOADER loader;
//    private boolean reloadFile;
//    private boolean reloadCache;
//    private Callback picassoCallback;
//    private Object picassoTag;
//    private Transformation picassoTransform;
//    private String directory = FileUtils.DIR_PROFILE;
//    private Handler handler;
//
//    private PicLoader(Context context, LOADER picasso) {
//        this.context = context;
//        loader = picasso;
//    }
//
//    public static PicLoader withPicasso(Context context) {
//        return new PicLoader(context, LOADER.picasso);
//    }
//
//    public static PicLoader withGlide(Context context) {
//        return new PicLoader(context, LOADER.glide);
//    }
//
//    public static boolean onFileFound(String requestId, int status) {
//        boolean isHandled = requests.containsKey(requestId);
//        PicLoader picLoader = requests.get(requestId);
//        if (picLoader == null || picLoader.imageView == null) return isHandled;
//        String tag = (String) picLoader.imageView.getTag();
//        if (requestId.equals(tag) && picLoader.requestId().equals(requestId))
//            picLoader.onFileFound(status);
//        return isHandled;
//    }
//
//    public String getFileKey() {
//        return fileKey;
//    }
//
//    public PicLoader centerCrop() {
//        this.centerCrop = true;
//        return this;
//    }
//
//    public PicLoader fitCenter() {
//        this.fitCenter = true;
//        return this;
//    }
//
//    public PicLoader load(File file) {
//        this.fileKey = file.getPath();
//        return this;
//    }
//
//    public PicLoader load(String fileKey) {
//        this.fileKey = fileKey;
//        try {
//            String[] fileParts = fileKey.split("/");
//            if (fileParts.length == 2) {
//                directory(fileParts[0]);
//                this.fileKey = fileParts[1];
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "error in load file"+ e.getMessage());
//        }
//        return this;
//    }
//
//    public PicLoader withRequestId(String requestId) {
//        this.requestId = requestId;
//        return this;
//    }
//
//    public PicLoader placeholder(int resId) {
//        this.placeholder = resId;
//        return this;
//    }
//
//    public PicLoader resize(int width, int height) {
//        return override(width, height);
//    }
//
//    public PicLoader override(int width, int height) {
//        this.overrideWidth = width;
//        this.overrideHeight = height;
//        return this;
//    }
//
//    public PicLoader tag(Object picassoTag) {
//        this.picassoTag = picassoTag;
//        return this;
//    }
//
//    public PicLoader transform(Transformation picassoTransform) {
//        this.picassoTransform = picassoTransform;
//        return this;
//    }
//
//    public PicLoader listener(RequestListener<File, GlideDrawable> glideDrawableRequestListener) {
//        this.glideDrawableRequestListener = glideDrawableRequestListener;
//        return this;
//    }
//
//    public PicLoader directory(String directory) {
//        this.directory = directory;
//        return this;
//    }
//
//    public String directory() {
//        return directory;
//    }
//
//    public PicLoader mayBeReloadCache(boolean reloadCache) {
//        if (reloadCache) return clearCache();
//        return this;
//    }
//
//    public PicLoader clearCache() {
//        this.reloadCache = true;
//        return this;
//    }
//
//    public PicLoader mayBeReDownload(boolean download) {
//        if (download) return reDownload();
//        return this;
//    }
//
//    public PicLoader reDownload() {
//        this.reloadFile = true;
//        return clearCache();
//    }
//
//    public boolean into(ImageView imageView) {
//        return into(imageView, null);
//    }
//
//    public boolean into(ImageView imageView, Callback picassoCallback) {
//        this.imageView = imageView;
//        this.picassoCallback = picassoCallback;
//
//        handler = new Handler();
//        checkForReload();
//
//        File file = getFile();
//
//        if (file.exists()) onFileFound(file);
//        else if (!isLocalFile()) downloadFile();
//        return true;
//    }
//
//    public boolean isLocalFile() {
//        if(fileKey == null){
//            return false;
//        }
//        return fileKey.split("/").length > 2;
//    }
//
//    public File getFile() {
//        if (isLocalFile())
//            return new File(fileKey);
//        return FileUtils.getFile(directory, fileKey);
//    }
//
//    private void checkForReload() {
//
//        File downloadFile = getFile();
//        if (downloadFile.exists() && reloadFile)
//            downloadFile.delete();
//    }
//
//    private void downloadFile() {
//        setPlaceHolder();
//        if (failedRequests != null && failedRequests.contains(fileKey)) return;
//        if (imageView != null) imageView.setTag(fileKey);
//        if (requests == null){ requests = new ConcurrentHashMap<>();}
//        if(this == null || requestId() == null) return;
//        requests.put(requestId(), this);
//
//        FilesDownloadManager.downloadPic(context, this);
//    }
//
//    public String requestId() {
//        return requestId == null || TextUtils.isEmpty(requestId) ? fileKey : requestId;
//    }
//
//    private void setPlaceHolder() {
//        if (placeholder != null && imageView != null) imageView.setImageResource(placeholder);
//    }
//
//    private void onFileFound(int status) {
//        switch (status) {
//            case DownloadManager.STATUS_SUCCESSFUL:
//                onFileFound(getFile());
//                break;
//            case DownloadManager.STATUS_FAILED:
//                if (placeholder != null && directory.equalsIgnoreCase(FileUtils.DIR_PROFILE)) {
//                    if (failedRequests == null) failedRequests = new LinkedBlockingQueue<>();
//                    if (failedRequests.size() > MAX_BUF_SIZE) failedRequests.poll();
//                    failedRequests.add(fileKey);
//                }
//                break;
//        }
//    }
//
//    private void onFileFound(final File file) {
//        if (context == null || imageView == null || handler == null) return;
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                displayFile(file);
//            }
//        });
//    }
//
//    private void displayFile(File file) {
//        switch (loader) {
//            case picasso:
//                loadPicasso(file);
//                break;
//            case glide:
//                loadGlide(file);
//                break;
//        }
//    }
//
//    private void loadGlide(File file) {
//        DrawableTypeRequest<File> request = Glide.with(context).load(file);
//        DrawableRequestBuilder<File> request2 = fitCenter ? request.fitCenter() : null;
//
//        if (centerCrop) {
//            request2 = (request2 != null) ? request2.centerCrop() : request.centerCrop();
//        }
//
//        if (overrideWidth != null) {
//            float density = getDensity();
//            int width = (int) (overrideWidth * density);
//            int height = (int) (overrideHeight * density);
//            request2 = (request2 != null) ? request2.override(width, height) : request.override(width, height);
//        }
//
//        if (glideDrawableRequestListener != null) {
//            request2 = (request2 != null) ? request2.listener(glideDrawableRequestListener) : request.listener(glideDrawableRequestListener);
//        }
//
//        if (request2 != null) {
//            request2.into(imageView);
//        } else {
//            request.into(imageView);
//        }
//    }
//
//    private void loadPicasso(File file) {
//
//        if (reloadCache) Picasso.with(context).invalidate(file);
//
//        RequestCreator request = Picasso.with(context).load(file);
//        if (centerCrop) request.centerCrop();
//
//        if (overrideWidth != null) {
//            float density = getDensity();
//            int width = (int) (overrideWidth * density);
//            int height = (int) (overrideHeight * density);
//            request.resize(width, height);
//        }
//
//        if (picassoTag != null) request.tag(picassoTag);
//        if (picassoTransform != null) request.transform(picassoTransform);
//
//        if (picassoCallback != null) request.into(imageView, picassoCallback);
//        else request.into(imageView);
//    }
//
//    private float getDensity() {
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        return metrics.density;
//    }
//
//    enum LOADER {
//        picasso,
//        glide
//    }
//}
