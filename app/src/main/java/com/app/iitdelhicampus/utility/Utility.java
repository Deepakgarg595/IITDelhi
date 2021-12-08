package com.app.iitdelhicampus.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.LoginWithNumberActivity;
import com.app.iitdelhicampus.constants.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;


public class Utility {
    public static final long MSPERDAY = 60 * 60 * 24 * 1000;
    public static final long MSPERHOUR = 60 * 60 * 24 * 1000;
    public static final long MSPERMIN = 60 * 60 * 24 * 1000;

    public final static String DATE_N_TIME_FORMAT = "dd/MM/yyyy , hh:mm a";
    public final static String DATE_N_TIME_FORMAT_ROSACH = "dd/MM/yyyy hh:mm a";
    public final static String DATE_FORMAT = "MMM dd, yyyy";
    public final static String DATE_N_TIME_FORMAT_EVENT = "MMM dd, yyyy, hh:mm a";
    public final static String DATE_FORMAT_EVENT = "MMM dd, yyyy";
    public final static String DATE_FORMAT_TRANSACTION = "EEEE, MMM dd, yyyy";

    public final static String DATE_FORMAT_COMMENT = "MMM dd 'at' hh:mm a";
    public final static String DATE_FORMAT_NOTIFICATION = "EEEE 'at' hh:mm a";
    public final static String DATE_DURATION = "MMM dd";
    public final static String DATE_FORMAT_NOTIFICATION_DAY= "EEEE";
    public final static String DATE_FORMAT_COUNTER= "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_ORION= "yyyy-MM-dd";
    public final static String DATE_ORION_PUNJAB_WAREHOUSE= "yyyy/MM/dd";
    public final static String DATE_N_TIME_UPDATE = "yyyy/MM/dd hh:mm";

//    "yyyy-MM-dd HH:mm:ss"

    public final static String DATE_FORMAT1 = "MMM";
    public final static String DATE_FORMAT2 = "yyyy";
    public final static String DATE_FORMAT3 = "d";

    public final static String timeFormatString = "h:mm aa";
    public final static String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
    public final static String PAYMENT_FORMAT = "MMM dd, yyyy h:mm aa";
    public final static String PAYMENT_TIME = "hh:mm aa";
    private static final String DATE_N_TIME_FORMAT_EVENT_NEW = "dd/M/yyyy hh:mm:ss";
    public final static String TIME_24 = "HH:mm";
    private Dialog mBottomSheetDialog;
    private RelativeLayout rlLogin;
    private TextView txtCancel;

    final long HOURS = 60 * 60 * 60;
    static HashMap<Integer, Activity> hashMap = null;
    static HashMap<Integer, AppCompatActivity> activityContainerMap = null;

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if is internet available.
     *
     * @param context the context
     * @return true, if is internet available
     */
    public static final boolean isInternetAvailable(final Context context) {
        final ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo[] info = connManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ProgressDialog getProgressDialog(final Context context,
                                                   String msg) {
        ProgressDialog progressDialog = null;
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            if (msg.isEmpty()) {
                progressDialog.setMessage("Please Wait..");
            } else {
                progressDialog.setMessage(msg);
            }
            progressDialog
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(final DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }





    public static String getandomNumber() {
        Random random = new Random();
        int rndm = 100000 + random.nextInt(900000);
        return String.valueOf(rndm);
    }

    public static void showSnackBar(View view, String pMessage) {
        Snackbar.make(view, pMessage, Snackbar.LENGTH_SHORT).show();
    }

    public static String getFormattedDate(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat("dd MMM,yyyy~h:mm a~EEEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis * 1000);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }

    public static String getFormattedDateAndTime(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT_EVENT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }

    public static String getFormattedDateTime(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis * 1000);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }

    public static String getAutoFillTime(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }


    public static String getDateTimeUpdate(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_UPDATE, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }







    public static String getTime24Hours(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(TIME_24, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }

    public static String getEventTime(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat(PAYMENT_TIME, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String strDate = formatter.format(calendar.getTime());
        return strDate;
    }

    public static String getDateFromSec(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }
    public static String getDate(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_ORION_PUNJAB_WAREHOUSE, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }



    public static String getDateFromSecNew(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateFromSecForComment(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_COMMENT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateForPayment(long milliSec) {
        DateFormat formatter = new SimpleDateFormat(PAYMENT_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateFromSecForNotification(long sec) {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        Calendar now = Calendar.getInstance();


        if (now.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
            return "Today " + df.format(timeFormatString, calendar);
        } else if (now.get(Calendar.DATE) - calendar.get(Calendar.DATE) == 1) {
            return "Yesterday " + df.format(timeFormatString, calendar);
        } else if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            return df.format(dateTimeFormatString, calendar).toString();
        } else {
            return df.format("MMM dd yyyy, h:mm aa", calendar).toString();
        }
    }


    public static String getDateDuration(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_DURATION, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }


    public static String getDateFromSecForEvents(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EVENT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateTransaction(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_TRANSACTION, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateTransactionDay(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_NOTIFICATION_DAY, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateCounter(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_COUNTER, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getReportDate(long milliSec) {
        DateFormat formatter = new SimpleDateFormat(DATE_ORION, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }



//


    public static String getDateAndTimeForEvents(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT_EVENT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateAndTimeForEventsNEWC(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT_EVENT_NEW, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((sec * 1000));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }
    public static String getDateAndTimeForEventsNEW(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT_EVENT_NEW, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }

    public static String getDateForEvents(long sec) {
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        return strDate;
    }


    public static String getDateAndTimeInSec(String formattedDateNtime) {
        String str_date = formattedDateNtime;
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Date date = null;

        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("Today is " + date.getTime());
        String sec = String.valueOf(date.getTime() / 1000);
        return sec;
    }

    public static long getTimeInMilliSec(String formattedDateNtime) {
        String str_date = formattedDateNtime;
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT, Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(str_date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime();
    }

    public static long getTimeForReminder(String formattedDateNtime) {
        String str_date = formattedDateNtime;
        DateFormat formatter = new SimpleDateFormat(DATE_N_TIME_FORMAT_ROSACH, Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(str_date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime();
    }


    public static String getDateFromSecWithTH(long sec) {
        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st"};

        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT3, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec * 1000);
        String strDate = formatter.format(calendar.getTime());
        Log.e("strDate", strDate);
        int day = Integer.parseInt(strDate);
        String dayStr = day + suffixes[day];
        DateFormat formatter1 = new SimpleDateFormat(DATE_FORMAT1, Locale.ENGLISH);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(sec * 1000);
        String strDate1 = formatter1.format(calendar.getTime());
        DateFormat formatter2 = new SimpleDateFormat(DATE_FORMAT2, Locale.ENGLISH);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(sec * 1000);
        String strDate2 = formatter2.format(calendar.getTime());
//        return strDate1 + " " + dayStr + " " + strDate2;
        return dayStr  + " " + strDate1 + " " + strDate2;
    }


//    private String getDateInMilliSecFromServer(String formattedDateNtime) {
//        String str_date = formattedDateNtime;
//        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//        Date date = null;
//        try {
//            date = (Date) formatter.parse(str_date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String timeInMills = String.valueOf(date.getTime() * 1000);
//        return timeInMills;
//    }

    public static String getDateInSec(String formattedDateNtime) {
        String str_date = formattedDateNtime;
        if (StringUtils.isNullOrEmpty(str_date))
            return "";
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sec = String.valueOf(date.getTime() / 1000);
        return sec;
    }


//    public static Drawable getDrawable(Context context, String drawableName) {
////        Drawable drawable = context.getResources().getDrawable(context.getResources()
////                .getIdentifier(drawableName, "mipmap", context.getPackageName()));
//
//        Resources resources = context.getResources();
//        final int resourceId = resources.getIdentifier(drawableName, "mipmap",
//                context.getPackageName());
//        Drawable drawable = null;
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                drawable = ResourcesCompat.getDrawable(context.getResources(), resourceId, null);
//            } else {
//                drawable = ContextCompat.getDrawable(context, context.getResources()
//                        .getIdentifier(drawableName, "mipmap", context.getPackageName()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return drawable;
//    }

    public static String getDefaultImageName(String eventName) {
        final String DEFAULT = "default";
        switch (eventName) {
            case Constants.EventCategory.CATEGORY_ART:
                return DEFAULT + Constants.EventCategory.CATEGORY_ART.toLowerCase();
            case Constants.EventCategory.CATEGORY_MUSIC:
                return DEFAULT + Constants.EventCategory.CATEGORY_MUSIC.toLowerCase();
            case Constants.EventCategory.CATAGORY_COOKING:
                return DEFAULT + Constants.EventCategory.CATAGORY_COOKING.toLowerCase();
            case Constants.EventCategory.CATAGORY_FASHION:
                return DEFAULT + Constants.EventCategory.CATAGORY_FASHION.toLowerCase();
            case Constants.EventCategory.CATAGORY_DANCE:
                return DEFAULT + Constants.EventCategory.CATAGORY_DANCE.toLowerCase();
            case Constants.EventCategory.CATAGORY_SPORTS:
                return DEFAULT + Constants.EventCategory.CATAGORY_SPORTS.toLowerCase();
            case Constants.EventCategory.CATAGORY_GAMING:
                return DEFAULT + Constants.EventCategory.CATAGORY_GAMING.toLowerCase();
            case Constants.EventCategory.CATAGORY_EDUCATION:
                return DEFAULT + Constants.EventCategory.CATAGORY_EDUCATION.toLowerCase();
            case Constants.EventCategory.CATAGORY_SOCIAL:
                return DEFAULT + Constants.EventCategory.CATAGORY_SOCIAL.toLowerCase();
        }
        return DEFAULT + Constants.EventCategory.CATAGORY_OTHERS.toLowerCase();
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

//    public static String getDateInReadableFormat(long milliSec) {
//        Calendar c = Calendar.getInstance();
////Set time in milliseconds
//        c.setTimeInMillis(milliSec);
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        int hr = c.get(Calendar.HOUR);
//        int min = c.get(Calendar.MINUTE);
//        int sec = c.get(Calendar.SECOND);
//
//       if(sec<60){
//           return
//       }
//
//        return "";
//    }

    public static void openLoginPage(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setMessage("Please LOGIN to explore more options");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "LOGIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, LoginWithNumberActivity.class);
                        context.startActivity(i);
                    }
                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static int dateDiff(long cratedTs) {
        long diff = 0L;
        diff =  cratedTs-(System.currentTimeMillis()/1000);
//        diff = diff / MSPERDAY;
        diff=(diff % 60);
        return (int) diff;
    }

    public static int hourDiff(long cratedTs) {
        long diff = 0L;
        diff =  cratedTs- System.currentTimeMillis();
//        diff = diff / MSPERHOUR;
        diff=(diff / (60 * 60)) % 24;

        return (int) diff;
    }

    public static int minDiff(long cratedTs) {
        long diff = 0L;
        diff =  cratedTs- System.currentTimeMillis();
//        diff = diff / MSPERMIN;
        diff=(diff / 60) % 60;
        return (int) diff;
    }


//    public void openLoginPagenjkj(final BaseActivity context) {
//        View view = context.getLayoutInflater().inflate(R.layout.login_sheet, null);
//        rlLogin = (RelativeLayout) view.findViewById(R.id.rlLogin);
//        txtCancel = (TextView) view.findViewById(R.id.txtCancel);
//
//        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
//        mBottomSheetDialog.setContenotView(view);
//        mBottomSheetDialog.setCancelable(true);
//        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
//        mBottomSheetDialog.show();
//
//        rlLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, LoginWithNumberActivity.class);
//                context.startActivity(i);
//            }
//        });
//        txtCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mBottomSheetDialog.dismiss();
//            }
//        });
//
//    }
//

    public static String replaceFirstZero(String str) {
        return str.replaceFirst("^0+(?!$)", "");
    }


    public static void openLoginPageWithoutDialog(Context context) {

        Intent i = new Intent(context, LoginWithNumberActivity.class);
        context.startActivity(i);
    }


    // Add activity
    public static void addActivities(int key, Activity _activity) {

        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(key, _activity);
    }

    // Remove Activity
    public static void removeActivity(int key) {
        if (hashMap != null && hashMap.size() > 0) {
            Activity _activity = hashMap.get(key);
            if (_activity != null) {
                _activity.finish();
            }
        }
    }


    // Add activity
    public static void addCurrentActivity(AppCompatActivity _activity) {

        if (activityContainerMap == null) {
            activityContainerMap = new HashMap<>();
        }
        activityContainerMap.clear();
        activityContainerMap.put(111, _activity);
    }

    public static AppCompatActivity getCurrentActivity() {
        if (activityContainerMap != null && activityContainerMap.size() > 0) {
            AppCompatActivity _activity = activityContainerMap.get(111);
            return _activity;
        }
        return null;
    }


    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }


    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 2, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

//    public static void shareRefferalCode(EventDetails details, BaseActivity mActivity) {
//        DataStatic.getInstance().openShareDialog(mActivity, details);
//        String message = String.format("%s %s %s%s%s%s %s%s",
//                "Check out"
//                , CreateEventModel.getInstance(false).getCategory()
//                , "on See Around!"
//                , "\n\n"
//                , "Date:"
//                , CreateEventModel.getInstance(false).getStartdateTime()
//                , "\n\n"
//                , details.getWebUrl());
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("text/plain");
//        share.putExtra(Intent.EXTRA_TEXT, message);
//        share.putExtra(Intent.EXTRA_TITLE, "See Around");
//        share.putExtra(Intent.EXTRA_SUBJECT, details.getEventName());
//        mActivity.startActivity(Intent.createChooser(share, "Share your event with friends."));

//    }

    public static SortedMap<Currency, Locale> currencyLocaleMap;

    static {
        currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
            }
        });
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyLocaleMap.put(currency, locale);
            } catch (Exception e) {
            }
        }
    }


    public static String getCurrencySymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }

//    public static List<MediaDetails> getEventsList(String category) {
//        List<MediaDetails> alImages = new ArrayList<>();
//        int[] data = null;
//        switch (category) {
//            case Constants.EventCategory.CATEGORY_ART:
//                data = DataStatic.images_arts;
//                break;
//            case Constants.EventCategory.CATAGORY_COOKING:
//                data = DataStatic.images_cooking;
//                break;
//            case Constants.EventCategory.CATEGORY_MUSIC:
//                data = DataStatic.images_music;
//                break;
//            case Constants.EventCategory.CATAGORY_FASHION:
//                data = DataStatic.images_fashion;
//                break;
//            case Constants.EventCategory.CATAGORY_SPORTS:
//                data = DataStatic.images_sports;
//                break;
//            case Constants.EventCategory.CATAGORY_DANCE:
//                data = DataStatic.images_dance;
//                break;
//            case Constants.EventCategory.CATAGORY_OTHERS:
//                data = DataStatic.images_others;
//                break;
//            case Constants.EventCategory.CATAGORY_GAMING:
//                data = DataStatic.images_gaming;
//                break;
//            case Constants.EventCategory.CATAGORY_SOCIAL:
//                data = DataStatic.images_social;
//                break;
//            case Constants.EventCategory.CATAGORY_EDUCATION:
//                data = DataStatic.images_education;
//                break;
//            default:
//                break;
//        }
//        for (int i = 0; i < 5; i++) {
//            if (i == 0) {
//                MediaDetails createEventNewImageModel = new MediaDetails();
////                createEventNewImageModel.setImage(data[i]);
//                createEventNewImageModel.setPosition(i);
//                createEventNewImageModel.setVideo(true);
//                createEventNewImageModel.setType(Constants.MediaType.DEFAULT_VIDEO);
//                createEventNewImageModel.setMedia("default" + category.toLowerCase() + "_" + i + ".mp4");
//                alImages.add(createEventNewImageModel);
//            } else {
//                MediaDetails createEventNewImageModel = new MediaDetails();
////                createEventNewImageModel.setImage(data[i]);
//                createEventNewImageModel.setPosition(i);
//                createEventNewImageModel.setVideo(false);
//                createEventNewImageModel.setType(Constants.MediaType.DEFAULT_IMAGE);
//                createEventNewImageModel.setMedia("default" + category.toLowerCase() + "_" + (i) + ".png");
//                alImages.add(createEventNewImageModel);
//            }
//        }
//        return alImages;
//    }

    public static String getTimeZone() {
        TimeZone defaultTimezone = TimeZone.getDefault();
        String timezone = defaultTimezone.getID();
        if (timezone.contains("Asia/Calcutta"))
            timezone = "Asia/Kolkata";
        return timezone;
    }

//    static public String getDefaultMediaPath(boolean isVideo, String mediaPath) {
//        if (mediaPath == null) return "";
//        String path = isVideo ? mediaPath : mediaPath.contains(".") ? mediaPath : mediaPath + ".png";
//        return AppConstants.MEDIA_URL + path;
//    }

//    static public String getCustomMediaPath(String mediaPath) {
//        if (mediaPath == null) return "";
//        return Nlh.i().mediaUrl() + mediaPath;
//    }
//
//    static public String getDefaultThumbNail(String mediaPath) {
//        if (mediaPath == null) return "";
//        return MEDIA_URL_DEFAULT_THUMBNAIL + mediaPath;
//    }

    public static void makeCustomPay(Context context, String url) {

        Uri webpage = Uri.parse(url);
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                ((Activity) context).startActivity(intent);
            }

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 4;
        String channelId = "channel-01";
        String channelName = "wach";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


}

