package com.app.iitdelhicampus.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for Bitmap related methods.
 *
 * @author kapil.vij
 */
public abstract class BitmapUtils {

    private final static int COMPRESSED = 30;
    private final static int SIZE = 8;
    private static String TAG = "BitmapUtils";

    /**
     * @param pSourceBmp
     * @param pNewWidth
     * @param pNewHeight
     * @return scaled bitmap, which is not less than required minimum dimensions
     */
    public static Bitmap getScaledBitmap(Bitmap pSourceBmp, int pNewWidth, int pNewHeight, boolean pIsUniform) {
        if (pSourceBmp == null || pNewWidth <= 0 || pNewHeight <= 0) {
            return null;
        }
        int actualWidth = pSourceBmp.getWidth();
        int actualHeight = pSourceBmp.getHeight();

        /**
         * Check if scaling is not required.
         */
        if (actualWidth == pNewWidth && actualHeight == pNewHeight) {
            return pSourceBmp;
        }

        int scalingToWidth, scalingToHeight;

        /**
         * If uniform scaling is required, then scaled Bitmap might not be of
         * pNewWidth and pNewHeight. Any one dimension will be as per respective
         * new dimension and other will be >= respective new dimension
         */
        if (pIsUniform) {
            // If scaling as per pNewWidth will be enough
            scalingToWidth = pNewWidth;
            scalingToHeight = actualHeight * scalingToWidth / actualWidth;

            // If scaling as per pNewHeight will be enough
            if (scalingToHeight < pNewHeight) {
                scalingToHeight = pNewHeight;
                scalingToWidth = actualWidth * scalingToHeight / actualHeight;
            }
        } else {
            scalingToWidth = pNewWidth;
            scalingToHeight = pNewHeight;
        }

        try {
            return Bitmap.createScaledBitmap(pSourceBmp, scalingToWidth, scalingToHeight, true);
        } catch (Throwable tr) {
            Log.e(TAG, "Error in scaling upto required width and/or height", tr);
            return null;
        }
    }
//
//    public static Bitmap getScaledBitmap(File f, int maxImageSize) {
//        System.gc();
//
//        //FileDescriptor fd;
//        try {
//           // FileInputStream fstream = new FileInputStream(f);
//            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromStream(f, maxImageSize);
//            return bitmap;
////            fd = fstream.getFD();
////            if (fd != null) {
////                return BitmapUtils.decodeSampledBitmapFromDescriptor(fd, maxImageSize);
////            } else {
////
////            }
//        } catch (Exception e) {
//            Log.e(TAG, "getScaledBitmap: ", e);
//        }
//        return null;
//    }

    public static void writeBitmapToFile(File file, Bitmap bitmap) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        bitmap.compress(CompressFormat.PNG, 100, fout);
        fout.flush();
        fout.close();
    }
//
//    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int maxFileSize) {
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inDither = false; // Disable Dithering mode
//        options.inPurgeable = true; // Tell to gc that whether it needs free
//        // memory, the Bitmap can be cleared
//        options.inInputShareable = true; // Which kind of reference will be used
//        // to recover the Bitmap data after
//        // being clear, when it will be used
//        // in the future
//        options.inTempStorage = new byte[32 * 1024];
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, maxFileSize, maxFileSize);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//
//        // If we're running on Honeycomb or newer, try to use inBitmap
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            addInBitmapOptions(options);
//        }
//
//        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
//    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    private static void addInBitmapOptions(BitmapFactory.Options options) {
//        // inBitmap only works with mutable bitmaps so force the decoder to
//        // return mutable bitmaps.
//        options.inMutable = true;
//    }

    public static Bitmap decodeSampledBitmapFromStream(File file, int maxImageSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        //BitmapFactory.decodeStream(stream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxImageSize, maxImageSize);

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            options.inMutable = true;
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

//    public static int calculateInSampleSize(BitmapFactory.Options options, int maxImageSize) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize;
//
//        inSampleSize = (int) Math.pow(2.0, (int) Math.round(Math.log(maxImageSize / (double) Math.max(height, width)) / Math.log(0.5)));
//
//        return inSampleSize;
//    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int getMaxSize(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        return Math.max((screenWidth - 75), ((int) (screenWidth * 0.7f)));
    }

    /**
     * @param pSourceBmp
     * @param pNewWidth
     * @param pNewHeight
     * @return
     */
    public static Bitmap getScaledCroppedBitmap(Bitmap pSourceBmp, int pNewWidth, int pNewHeight) {
        Bitmap uniScaledBitmap = getScaledBitmap(pSourceBmp, pNewWidth, pNewHeight, true);
        if (uniScaledBitmap == null) {
            return null;
        }
        int currentWidth = uniScaledBitmap.getWidth();
        int currentHeight = uniScaledBitmap.getHeight();
        int cropDiff;

        /**
         * Crop from left and right edges
         */
        if (currentWidth > pNewWidth) {
            cropDiff = currentWidth - pNewWidth;
            try {
                return Bitmap.createBitmap(uniScaledBitmap, cropDiff / 2, 0, currentWidth - cropDiff, currentHeight);
            } catch (Throwable tr) {
                Log.e(TAG, "Error in horizontal cropping", tr);
                return null;
            }
        }

        /**
         * Crop from top and bottom edges
         */
        if (currentHeight > pNewHeight) {
            cropDiff = currentHeight - pNewHeight;
            try {
                return Bitmap.createBitmap(uniScaledBitmap, 0, cropDiff / 2, currentWidth, currentHeight - cropDiff);
            } catch (Throwable tr) {
                Log.e(TAG, "Error in horizontal cropping", tr);
                return null;
            }
        }

        return uniScaledBitmap;
    }

    /**
     * @param pSourceBmp
     * @param radiusX
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap pSourceBmp, int radiusX, int radiusY) {
        if (pSourceBmp == null || radiusX <= 0 || radiusY <= 0) {
            return pSourceBmp;
        }
        Bitmap outputBmp = null;

        try {

            outputBmp = Bitmap.createBitmap(pSourceBmp.getWidth(), pSourceBmp.getHeight(), Config.ARGB_8888);

            Canvas canvas = new Canvas(outputBmp);

            int color = 0xffffffff;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, pSourceBmp.getWidth(), pSourceBmp.getHeight());
            RectF rectF = new RectF(rect);
            float roundPx = radiusX;

            paint.setAntiAlias(true);

            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(pSourceBmp, rect, rect, paint);
        } catch (Throwable tr) {
            Log.e(TAG, "Error in creating rounded bitmap", tr);
        }

        return outputBmp;
    }

    public static Bitmap rotateBitmapOrientation(Context context, String photoFilePath, int rotationAngle) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        int end = photoFilePath.lastIndexOf("/");
        String str1 = photoFilePath.substring(0, end);
        String str2 = photoFilePath.substring(end + 1, photoFilePath.length());
        File source = new File(str1, str2);

        Log.e("saved image path : ", source.getAbsolutePath());
        if (source.exists()) source.delete();
        try {
            FileOutputStream out = new FileOutputStream(source);
            rotatedBitmap.compress(CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    /**
     * @param pSourceBmp
     * @param radius
     * @return
     */
    public static Bitmap getRoundedCircularBitmap(Bitmap pSourceBmp, int radius) {
        if (pSourceBmp == null || radius <= 0) {
            return pSourceBmp;
        }
        Bitmap outputBmp = null;

        try {

            outputBmp = Bitmap.createBitmap(pSourceBmp.getWidth(), pSourceBmp.getHeight(), Config.ARGB_8888);

            Canvas canvas = new Canvas(outputBmp);

            int color = 0xffffffff;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, pSourceBmp.getWidth(), pSourceBmp.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(pSourceBmp, rect, rect, paint);
        } catch (Throwable tr) {
            Log.e(TAG, "Error in creating rounded bitmap", tr);
        }

        return outputBmp;
    }

    /**
     * @param pView
     * @param pWidth
     * @param pHeight
     * @return
     */
    public static Bitmap getBitmapFromView(View pView, int pWidth, int pHeight) {
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(pWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(pHeight, MeasureSpec.EXACTLY);

        pView.measure(widthMeasureSpec, heightMeasureSpec);
        pView.layout(0, 0, pView.getMeasuredWidth(), pView.getMeasuredHeight());

        pView.setDrawingCacheEnabled(true);
        try {
            return pView.getDrawingCache();
        } catch (Throwable tr) {
            Log.e(TAG, "Error in getDrawingCache", tr);
            return null;
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap, int margin) {
        int rad = bitmap.getWidth() <= bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(rad, rad, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, rad, rad);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(rad / 2, rad / 2, rad / 2 - margin, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * @param imageBitmap
     * @return compressed bytes of imageBitmap
     */
    public static byte[] getPNGImageData(Bitmap imageBitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        boolean isSuccess = imageBitmap.compress(CompressFormat.PNG, 0, os);
        byte[] imageData = null;
        if (isSuccess) {
            imageData = os.toByteArray();
        }
        try {
            os.close();
        } catch (Exception e) { /* ignore */
        }
        return imageData;
    }

    /**
     * @param pSourceBmp
     * @return resized imageBitmap
     */
    public static Bitmap resizeToFitInside(Bitmap pSourceBmp, int pMaxWidth, int pMaxHeight) {
        int imageWidth = pSourceBmp.getWidth();
        int imageHeight = pSourceBmp.getHeight();
        if (imageWidth < pMaxWidth && imageHeight < pMaxHeight) {
            return pSourceBmp;
        }
        double widthRatio = (double) pMaxWidth / imageWidth;
        double heightRatio = (double) pMaxWidth / imageHeight;
        if (widthRatio > heightRatio) {
            pMaxWidth = (int) (imageWidth * heightRatio);
        } else {
            pMaxHeight = (int) (imageHeight * widthRatio);
        }
        try {
            return Bitmap.createScaledBitmap(pSourceBmp, pMaxWidth, pMaxHeight, true);
        } catch (Throwable tr) {
            Log.e(TAG, "Error in scaling upto required width and/or height", tr);
            return null;
        }
    }

    /**
     * @param originalBmp
     * @param diameter
     * @return
     */
    public static Bitmap getCircularCenterCropBitmap(Bitmap originalBmp, int diameter) {
        Bitmap resizedBmp = BitmapUtils.getScaledCroppedBitmap(originalBmp, diameter, diameter);
        return BitmapUtils.getRoundedCircularBitmap(resizedBmp, diameter / 2);
    }

    public static Bitmap getOrientatedScaledBitmap(File file, Context context, boolean isProfilePic) {
        int maxImageSize = getMaxSize(context);
//        decodeSampledBitmapFromStream(file, maxImageSize)
        Bitmap sourceBitmap = isProfilePic
                ? FileUtils.shrinkBitmap(file.toString(), 500, 500)
                : FileUtils.shrinkBitmap(file.toString(), 100, 100);
//        Bitmap sourceBitmap = FileUtils.shrinkBitmap(file.toString(), 500, 500);

        if (sourceBitmap == null) return null;
        ExifInterface exif;

        try {
            exif = new ExifInterface(file.getPath());
        } catch (IOException e) {
            Log.e(TAG, "getOrientatedScaledBitmap: ", e);
            return null;
        }

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
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

        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
    }

//    public static Bitmap getOrientatedScaledBitmap(File file, Context context, boolean isProfilePic) {
//        int maxImageSize = isProfilePic ? 200 : getMaxSize(context);
//        Bitmap sourceBitmap = getScaledBitmap(file, maxImageSize);
//
//        ExifInterface exif;
//
//        try {
//            exif = new ExifInterface(file.getPath());
//        } catch (IOException e) {
//            Log.e(TAG, "getOrientatedScaledBitmap: ", e);
//            return null;
//        }
//
////        ExifInterface exif = new ExifInterface(file.getPath());
//        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        Matrix matrix = new Matrix();
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                matrix.postRotate(90);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                matrix.postRotate(180);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                matrix.postRotate(270);
//                break;
//        }
//
//        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
//    }

    public static Bitmap fastBlur(Bitmap sentBitmap, int radius) {

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public static Bitmap getBitmapFromFilepath(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        return BitmapFactory.decodeFile(filePath, opts);
    }

    public static Bitmap getBitmapFromByteArray(byte[] thumb) {
        if(thumb == null || thumb.length == 0) return null;
        return BitmapFactory.decodeByteArray(thumb, 0, thumb.length);
    }


    public static String getBase64(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = SIZE;
        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, COMPRESSED, baos);
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

    }

    public static byte[] getByteArray(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = SIZE;
        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, COMPRESSED, baos);
        return baos.toByteArray();
    }

    public static byte[] getThumbNail(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, COMPRESSED, baos);
        return baos.toByteArray();
    }

    public static String getBase64(byte[] byteArray) {
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap getScaledBitmap(File f, int maxImageSize) {
        System.gc();

        FileInputStream fstream = null;
        FileDescriptor fd = null;
        try {
            fstream = new FileInputStream(f);
            fd = fstream.getFD();
            if (fd != null) {
                return BitmapUtils.decodeSampledBitmapFromDescriptor(fd, maxImageSize);
            } else {
                return BitmapUtils.decodeSampledBitmapFromStream(fstream, maxImageSize);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream stream, int maxImageSize) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = false; // Disable Dithering mode
        options.inPurgeable = true; // Tell to gc that whether it needs free
        // memory, the Bitmap can be cleared
        options.inInputShareable = true; // Which kind of reference will be used
        // to recover the Bitmap data after
        // being clear, when it will be used
        // in the future
        options.inTempStorage = new byte[32 * 1024];
        BitmapFactory.decodeStream(stream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxImageSize);

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addInBitmapOptions(options);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(stream, null, options);
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream stream, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = false; // Disable Dithering mode
        options.inPurgeable = true; // Tell to gc that whether it needs free
        // memory, the Bitmap can be cleared
        options.inInputShareable = true; // Which kind of reference will be used
        // to recover the Bitmap data after
        // being clear, when it will be used
        // in the future
        options.inTempStorage = new byte[32 * 1024];
        BitmapFactory.decodeStream(stream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addInBitmapOptions(options);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(stream, null, options);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void addInBitmapOptions(BitmapFactory.Options options) {
        // inBitmap only works with mutable bitmaps so force the decoder to
        // return mutable bitmaps.
        options.inMutable = true;
    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int maxFileSize) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = false; // Disable Dithering mode
        options.inPurgeable = true; // Tell to gc that whether it needs free
        // memory, the Bitmap can be cleared
        options.inInputShareable = true; // Which kind of reference will be used
        // to recover the Bitmap data after
        // being clear, when it will be used
        // in the future
        options.inTempStorage = new byte[32 * 1024];
        options.inPreferredConfig = Config.ARGB_8888;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxFileSize);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addInBitmapOptions(options);
        }

        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int maxImageSize) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        inSampleSize = (int) Math.pow(2.0, (int) Math.round(Math.log(maxImageSize / (double) Math.max(height, width)) / Math.log(0.5)));

        return inSampleSize;
    }


    public static Bitmap getTransformedBitmap(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap
                .createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

}