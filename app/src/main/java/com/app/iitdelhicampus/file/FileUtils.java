package com.app.iitdelhicampus.file;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.app.iitdelhicampus.BuildConfig;
import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.picasso.BlurTransformation;
import com.app.iitdelhicampus.utility.BitmapUtils;
import com.app.iitdelhicampus.utility.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static com.app.iitdelhicampus.utility.FileUtils.isLocalStorageDocument;


public class FileUtils {

    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String HIDDEN_PREFIX = ".";
    /**
     * Constant for image loader directory
     */
    public static final String APP_DIRECTORY = "OrionSecure";
    public static final String DIR_VIDEO = "Videos";
    public static final String DIR_IMAGE = "Captured";
    public static final String DIR_VOICE = "Voice";
    public static final String DIR_OTHER = "File";
    public static final String DIR_NOTES = "Notes";
    public static final String DIR_DOODLE = "Doodle";
    public static final String DIR_PROFILE = "Profile";
    public static final String DIR_SENT = "Sent";
    public static final String DIR_THEME = "Theme";
    public static final String DIR_EVENT = "Event";

    /**
     * file type
     */
    public static final String TEXT = "txt";
    public static final String PDF = "pdf";
    public static final String DOCS = "docx";
    public static final String DOC = "doc";
    public static final String XLSX = "xlsx";
    public static final String XLS = "xls";
    public static final String PPT = "ppt";
    public static final String PPTX = "pptx";
    public static final String APK = "apk";
    private static final String TAG = "Fileutils";
    private static final boolean DEBUG = false;
    private static final String videoExtension = ".mp4";
    private static final String voiceExtension = ".opus";
    private static final String bitmapExtension = ".png";
    private static final String txtExtension = "txt";
    public static Comparator<File> sComparator = new Comparator<File>() {
        @Override
        public int compare(File f1, File f2) {
            // Sort alphabetically by lower case, which is much cleaner
            return f1.getName().toLowerCase().compareTo(
                    f2.getName().toLowerCase());
        }
    };
    /**
     * File (not directories) filter.
     *
     * @author paulburke
     */
    public static FileFilter sFileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return files only (not directories) and skip hidden files
            return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };
    /**
     * Folder (directories) filter.
     *
     * @author paulburke
     */
    public static FileFilter sDirFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return directories only and skip hidden directories
            return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };
    static float size = BaseApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;

    public static boolean isVideo(String filePath) {
        if (getExtension(filePath).contains(videoExtension)) {
            return true;
        } else if (getExtension(filePath).contains("3gp")) {
            return true;
        } else if (getExtension(filePath).contains("mkv")) {
            return true;
        } else {
            return false;
        }
//        switch (getExtension(filePath)) {
//            case videoExtension:
//                return true;
//            case "3gp":
//                return true;
//            case "mkv":
//                return true;
//            default:
//                return false;
//        }
    }

    // 1.8    0.008

    public static String getSize(String sizeInByte) {
        int SIZE = 1024;

        if (StringUtils.isNullOrEmpty(sizeInByte)) return "";
        double fileSize = Double.parseDouble(sizeInByte);
        if (fileSize >= (SIZE * SIZE)) {
            fileSize = fileSize / (SIZE * SIZE);
            return String.format("%.1f", fileSize) + " MB";
        } else if (fileSize >= SIZE) {
            fileSize = fileSize / SIZE;
            return String.valueOf((int) fileSize) + " KB";
        } else {
            return String.valueOf((int) fileSize) + " Byte";
        }


    }

    public static File getDirectory(String dir) {
        String root = Environment.getExternalStorageDirectory().toString();
        String directoryPath = String.format(Locale.ENGLISH, "%s/%s/%s", root, APP_DIRECTORY, dir);

//        ContextWrapper cw = new ContextWrapper(BaseApplication.getInstance());
//        File directory = cw.getDir(dir, Context.MODE_PRIVATE);

        File directory = new File(directoryPath);
        if (!directory.exists()) directory.mkdirs();
        return directory;
    }

    public static File getFile(String dir, String fileName) {
        String extension = getExtension(fileName);
        File directory = getDirectory(dir);

        if (!TextUtils.isEmpty(extension))
            return new File(directory, fileName);

        extension = getExtensionForDirectory(dir);

//        fileName = String.format(Locale.ENGLISH, "%s.%s", fileName, extension);
//        return new File(directory, fileName);
        try {
            return File.createTempFile(fileName, extension, directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getS3FileKey(String dir, String messageId, String fileName) {
        String extension = FileUtils.getExtension(fileName);
        if (TextUtils.isEmpty(extension))
            extension = FileUtils.getExtensionForDirectory(dir);
        return String.format("%s/%s.%s", dir, messageId, extension);
    }

    public static boolean deleteFile(File file) {
        return !file.exists() || file.delete();
    }

    public static boolean copyFile(String from, String directory) {
        File source = new File(from);
        return copyFile(source, directory);
    }

    public static boolean copyFile(File source, String directory) {
        File destination = getFile(directory, source.getName());
        return copyFile(source, destination);
    }

    public static boolean copyFile(File source, File destination) {
        File sd = Environment.getExternalStorageDirectory();

        if (destination.exists()) {
            Log.e(TAG, " File already exist");
            return true;
        }

        if (!sd.canWrite()) {
            Log.e(TAG, "File copying failed");
            return false;
        }

        if (!source.exists()) {
            Log.e(TAG, "Source not exist");
            return false;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            FileOutputStream fileOutputStream = new FileOutputStream(destination);
            FileChannel src = fileInputStream.getChannel();
            FileChannel dst = fileOutputStream.getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "copyFile", e);
            return false;
        }

        return true;
    }

    /**
     * Returns extension without .
     */
    public static String getExtension(String filename) {
        if (filename == null) return "";
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) return "";
        else return filename.substring(index + 1);
    }

    public static String getExtensionForDirectory(String directory) {
        String extension = "";

        switch (directory) {
            case DIR_PROFILE:
            case DIR_IMAGE:
            case DIR_DOODLE:
                extension = bitmapExtension;
                break;
            case DIR_VIDEO:
                extension = videoExtension;
                break;
            case DIR_VOICE:
                extension = voiceExtension;
                break;
            case DIR_NOTES:
                extension = txtExtension;
                break;
            case DIR_OTHER:


                break;
            default:
                break;
        }

        return extension;
    }


    public static File getResizedImageFile(String saveLocation, File file) {
        try {
            int maxImageSize = BitmapUtils.getMaxSize(BaseApplication.getInstance());
            Bitmap sourceBitmap = BitmapUtils.getScaledBitmap(file, maxImageSize);

            ExifInterface exif = new ExifInterface(file.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.e("Capture image", "oreination" + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
            }

            Bitmap scaledBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
            // create a file to write bitmap data
            File f = new File(saveLocation, file.getName());
            f.createNewFile();

            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50 /* ignored for PNG */, bos);
            byte[] bitmapdata = bos.toByteArray();
            Log.e("FILE_SIZE", FileUtils.getSize(bitmapdata.length + ""));

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static File getResizedEventImage(String saveLocation, File file) {
        try {
            int maxImageSize = BitmapUtils.getMaxSize(BaseApplication.getInstance());
            Bitmap sourceBitmap = BitmapUtils.getScaledBitmap(file, maxImageSize);

            ExifInterface exif = new ExifInterface(file.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.e("Capture image", "oreination" + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
            }

            Bitmap scaledBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
            // create a file to write bitmap data
            File f = new File(saveLocation, System.currentTimeMillis() + ".png");
            f.createNewFile();

            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /* ignored for PNG */, bos);
            byte[] bitmapdata = bos.toByteArray();
            Log.e("FILE_SIZE", FileUtils.getSize(bitmapdata.length + ""));

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static File getResizedTheme(String saveLocation, File file) {
        try {
            int maxImageSize = BitmapUtils.getMaxSize(BaseApplication.getInstance());
            Bitmap sourceBitmap = BitmapUtils.getScaledBitmap(file, maxImageSize);

            ExifInterface exif = new ExifInterface(file.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.e("Capture image", "oreination" + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
            }

            Bitmap scaledBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
            // create a file to write bitmap data
            File f = new File(saveLocation, System.currentTimeMillis() + ".png");
            f.createNewFile();

            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /* ignored for PNG */, bos);
            byte[] bitmapdata = bos.toByteArray();
            Log.e("FILE_SIZE", FileUtils.getSize(bitmapdata.length + ""));

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;
    }

    /**
     * @return True if Uri is a MediaStore Uri.
     * @author paulburke
     */
    public static boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    /**
     * Convert File into Uri.
     *
     * @param file
     * @return uri
     */
    public static Uri getUri(File file, Context context) {
//        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".fileprovider" /*+ "ibas.provider"*/
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".fileprovider"

//        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID /*+ "ibas.provider"*/
                    , file);
    }

    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

    /**
     * @return The MIME type for the give Uri.
     */
    public static String getMimeType(Context context, Uri uri) {
        File file = new File(getPath(context, uri));
        return getMimeType(file);
    }

    /**
     * @param uri The Uri to check.
//     * @return Whether the Uri authority is {@link LocalStorageProvider}.
     * @author paulburke
     */
//    public static boolean isLocalStorageDocument(Uri uri) {
//        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
//    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
//     * @see #isLocal(String)
     * @see #getFile(Context, Uri)
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        if (DEBUG)
            Log.d(TAG + " File -",
                    "Authority: " + uri.getAuthority() +
                            ", Fragment: " + uri.getFragment() +
                            ", Port: " + uri.getPort() +
                            ", Query: " + uri.getQuery() +
                            ", Scheme: " + uri.getScheme() +
                            ", Host: " + uri.getHost() +
                            ", Segments: " + uri.getPathSegments().toString()
            );

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Convert Uri into File, if possible.
     *
     * @return file A local file that the Uri was pointing to, or null if the
     * Uri is unsupported or pointed to a remote resource.
     * @author paulburke
     * @see #getPath(Context, Uri)
     */
    public static File getFile(Context context, Uri uri) {
        if (uri != null) {
            String path = getPath(context, uri);
            if (path != null && isLocal(path)) {
                return new File(path);
            }
        }
        return null;
    }

    public static Bitmap getThumbnail(Context context, Uri uri, String mimeType) {
        if (DEBUG)
            Log.d(TAG, "Attempting to get thumbnail");

        if (!isMediaUri(uri)) {
            Log.e(TAG, "You can only retrieve thumbnails for images and videos.");
            return null;
        }

        Bitmap bm = null;
        if (uri != null) {
            final ContentResolver resolver = context.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = resolver.query(uri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    final int id = cursor.getInt(0);
                    if (DEBUG)
                        Log.d(TAG, "Got thumb ID: " + id);

                    if (mimeType.contains("video")) {
                        bm = MediaStore.Video.Thumbnails.getThumbnail(
                                resolver,
                                id,
                                MediaStore.Video.Thumbnails.MINI_KIND,
                                null);
                    } else if (mimeType.contains(FileUtils.MIME_TYPE_IMAGE)) {
                        bm = MediaStore.Images.Thumbnails.getThumbnail(
                                resolver,
                                id,
                                MediaStore.Images.Thumbnails.MINI_KIND,
                                null);
                    }
                }
            } catch (Exception e) {
                if (DEBUG)
                    Log.e(TAG, "getThumbnail", e);
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return bm;
    }

    public static boolean isProfilePicExist(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_DIRECTORY + "/" + DIR_PROFILE + "/" + fileName + ".png");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static File getProfilePicPath(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_DIRECTORY + "/" + DIR_PROFILE + "/" + fileName + ".png");
        return file;
    }

    public static boolean isFileExist(String filePath) {
        if (filePath == null) return false;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public static Bitmap shrinkBitmap(String file, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

//    public static int getId(String fileName) {
//        String extension = getExtension(fileName);
//        int id;
//        switch (extension.toLowerCase()) {
//            case TEXT:
//                id = R.drawable.text_icon;
//                break;
//            case PDF:
//                id = R.drawable.pdf_icon;
//                break;
//            case DOCS:
//            case DOC:
//                id = R.drawable.doc_icon;
//                break;
//            case XLSX:
//            case XLS:
//                id = R.drawable.xls_icon;
//                break;
//            case PPT:
//            case PPTX:
//                id = R.drawable.ppt_icon;
//                break;
//            case APK:
//                id = R.drawable.apk_icon;
//                break;
//            default:
//                id = R.drawable.pins_icon;
//                break;
//        }
//        return id;
//    }

    public static String getFileName(String absolutePath) {
        return new File(absolutePath).getName();
    }

//    public static void deleteFile(String userId) {
//        File avatar = FileUtils.getFile(FileUtils.DIR_PROFILE, userId);
//        Picasso.with(BaseApplication.getInstance().getApplicationContext()).invalidate(avatar);
//        deleteFile(avatar);
//    }

    private static String getFilePath(String fileName) {
        return FileUtils.getDirectory("") + File.separator + fileName;
    }

    public static void setMyProfilePic(final Context context, final String userId, final ImageView imgProfile, int defaultImage, String timeStamp, final boolean needToDownload, final boolean isCircular, boolean isVideo) {
        FileMeta fileMeta = new FileMeta.FileBuilder()
                .setFileKey(getProfilePicKey(userId, isVideo))
                .setOutFile(getFilePath(getProfilePicKey(userId, isVideo)))
                .setImageView(imgProfile)
                .setDefaultImage(defaultImage)
                .setCircular(isCircular)
                .setDownloadId(userId)
                .setWriteInFile(true)
                .setDirKey(FileMeta.DIR.PROFILE)
                .setTimeStamp(timeStamp).build();
        if (!isVideo) {
            displayProfilePic(context, fileMeta);
        }
        if (needToDownload) {
//            CiaoDownloadManager.getInstance(context).downloadMedia(fileMeta);
            return;
        }
    }

    public static void displayProfilePic(final Context context, final FileMeta fileMeta) {
//        AppPreferences.getInstance(context).setProfilePicUpdated(false);
        if (fileMeta.getImageView() == null) return;
        String filePath = getDirectory("").getAbsolutePath() + File.separatorChar + fileMeta.getFileKey();
        byte[] fileData = loadAsBytes(filePath);
//        setProfilePic(context, "0", fileMeta.getImageView(), fileData, fileMeta.getDefaultImage(), false, fileMeta.isCircular());
    }

//    public static void setProfilePic(Context context, String userId, ImageView imageView, byte[] fileData, int defaultImage, boolean needToDownload, boolean isCircular) {
//        if (needToDownload) {
//            setDefaultPic(context, defaultImage, imageView, isCircular);
//            downlaodProfilePic(context, userId);
//            return;
//        }
//
//        if (fileData == null || fileData.length == 0) {
//            setDefaultPic(context, defaultImage, imageView, isCircular);
//            return;
//        }
//        if (isCircular) {
//            Bitmap bitmap = BitmapUtils.getCircularCenterCropBitmap(BitmapFactory.decodeByteArray(fileData, 0, fileData.length), (int) (50 * context.getResources().getDisplayMetrics().density));
//            imageView.setImageBitmap(bitmap);
//            return;
//        }
//        Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
//        imageView.setImageBitmap(bitmap);
//    }

    private static void setDefaultPic(Context context, int defaultImage, ImageView imageView, boolean isCircular) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), defaultImage);
        Bitmap bitmap = isCircular ? BitmapUtils.getCircularCenterCropBitmap(icon, (int) (50 * context.getResources().getDisplayMetrics().density))
                : icon;
        imageView.setImageBitmap(bitmap);
    }

//    private static void downlaodProfilePic(final Context context, String userId) {
//        FileMeta fileMeta = new FileMeta.FileBuilder()
//                .setFileKey(getProfilePicKey(userId, false))
//                .setDownloadId(userId)
//                .setDirKey(FileMeta.DIR.PROFILE).build();
//        CiaoDownloadManager.getInstance(context).downloadProfilePic(fileMeta);
//    }

//    public static void setMedia(final Context context, RealmMessage.MSG_TYPE msgType, String fileName, String key, String messageId) {
//        String directory = RealmMessage.getDirectory(false, msgType);
//        String fileKey = getS3FileKey(directory, key, fileName);
//        FileMeta fileMeta = new FileMeta.FileBuilder()
//                .setFileKey(fileKey.toLowerCase())
//                .setOutFile(getFilePath(directory + File.separatorChar + fileName))
//                .setDownloadId(messageId)
//                .setMsgType(msgType)
//                .setDirKey(FileMeta.DIR.CHAT).build();
//        CiaoDownloadManager.getInstance(context).downloadMedia(fileMeta);
//    }


//    public static boolean cancelDownloading(Context context, String downlaodId) {
//        return CiaoDownloadManager.getInstance(context).cancelDownlaoding(downlaodId);
//    }

    private static float getDensity() {
        DisplayMetrics displayMetrics = null;
        if (displayMetrics == null) {
            displayMetrics = BaseApplication.getInstance().getResources().getDisplayMetrics();
            return displayMetrics.density;
        } else {
            return displayMetrics.density;
        }
    }

    public static String getProfilePicKey(String userId, boolean isVideo) {
        return String.format("%s%s%s%s%s", DIR_PROFILE.toLowerCase(), File.separatorChar, userId.toLowerCase(), "", isVideo ? videoExtension : bitmapExtension);
    }

    private static byte[] loadAsBytes(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return new byte[0];
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }


    //    public static String getProfilePicUrl(String userId) {
//        return String.format("%s%s%s%s", Nlh.i().dpp(), userId.toLowerCase(), ".", bitmapExtension);
//    }
    public static String getDeviceDetails() {
        return String.format("%s%s%s%s", String.valueOf(Build.BRAND), ",", String.valueOf(android.os.Build.MODEL), getVersionCodeAndName());
    }

    public static String getVersionCodeAndName() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                return String.format("%s%s%s%s", ",", fieldName, "-", fieldValue);
            }
        }
        return "";
    }


    public static void getProfilePic(final Context context, String url, final ImageView imgProfile, int defaultImage, String timeStamp, final boolean isCircular) {
//        Contact contact = RealmContactDBHelper.getContactByUserId(userId);
//        if(contact==null){
//            imgProfile.setImageResource(defaultImage);
//        }else
//        setProfilePic(context, contact.getUserId(), imgProfile, contact.getProfilePic(), defaultImage, contact.isNeedToUpdatePic(), isCircular);

        String timeStamps = timeStamp != null ? timeStamp : String.valueOf(System.currentTimeMillis());
        if (!timeStamps.equalsIgnoreCase("0")) {
            Glide.with(context).load(url).asBitmap().centerCrop().override((int) (getDensity() * 200), (int) (getDensity() * 200)).placeholder(defaultImage).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(String.valueOf(timeStamps))).into(new BitmapImageViewTarget(imgProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(isCircular);
                    imgProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            imgProfile.setImageResource(defaultImage);
        }
    }

    public static void getProfilePicWithSize(final Context context, String url, final ImageView imgProfile, int defaultImage, String timeStamp, final boolean isCircular) {
//        Contact contact = RealmContactDBHelper.getContactByUserId(userId);
//        if(contact==null){
//            imgProfile.setImageResource(defaultImage);
//        }else
//        setProfilePic(context, contact.getUserId(), imgProfile, contact.getProfilePic(), defaultImage, contact.isNeedToUpdatePic(), isCircular);

        String timeStamps = timeStamp != null ? timeStamp : String.valueOf(System.currentTimeMillis());
        if (!timeStamps.equalsIgnoreCase("0")) {
            Glide.with(context).load(url).asBitmap().centerCrop().override((int) (getDensity() * 130), (int) (getDensity() * 130)).placeholder(defaultImage).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(String.valueOf(timeStamps))).into(new BitmapImageViewTarget(imgProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(isCircular);
                    imgProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            imgProfile.setImageResource(defaultImage);
        }
    }


    public static void getFullPic(final Context context, Object imageUrl, final ImageView imgProfile, int defaultImage, String timeStamp, final boolean isCircular) {
        Glide.with(context).load(imageUrl).asBitmap().placeholder(defaultImage).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(timeStamp)).into(new BitmapImageViewTarget(imgProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(isCircular);
                imgProfile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void getThumbNails(final Context context, String imageUrl, final ImageView imgProfile, int defaultImage, String timeStamp) {
        Glide.with(context).load(imageUrl).asBitmap().placeholder(defaultImage).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(timeStamp)).into(imgProfile);
    }


    public static void getBlurredImage(final Context context, String url, final ImageView imgProfile, int defaultImage, String timeStamp, final boolean isCircular) {
        Glide.with(context)
                .load(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .signature(new StringSignature(timeStamp))
                .placeholder(defaultImage)
                .bitmapTransform(new BlurTransformation(context, 75)).into(imgProfile);
    }



    public static String getProfilePicUrl(Context context, String userId) {
        if(!StringUtils.isNullOrEmpty(userId)) {
            return String.format("%s%s%s%s%s", Constants.MEDIA_URL, "profile/", userId.toLowerCase(), "", bitmapExtension);
        }else return "";
    }

    public static String dateFormat(long timestamp) {
        timestamp = timestamp * 1000;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public static String timeFormat(long timestamp) {
        timestamp = timestamp * 1000;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(d);
    }

    public static File saveToInternalStorage(Bitmap bitmapImage, String imageName) {
        ContextWrapper cw = new ContextWrapper(BaseApplication.getInstance());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mypath;
    }

    public static File getFileInternalStorage(String imageName) {
        ContextWrapper cw = new ContextWrapper(BaseApplication.getInstance());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, imageName);
        return mypath;

    }


}
