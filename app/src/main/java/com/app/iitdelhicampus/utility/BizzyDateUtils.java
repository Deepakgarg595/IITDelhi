package com.app.iitdelhicampus.utility;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint({"SimpleDateFormat"})
public class BizzyDateUtils {
    public static DateFormat DD_MMM_yy_HH_mm;
    public static DateFormat DD_MMM_yy_hh_mm;
    public static DateFormat DD_MMM_yy_hh_mm_aaa;
    public static DateFormat DD_MMM_yy_z;
    public static DateFormat DD_MMM_yy_z_hh_mm;
    public static DateFormat DD_MMM_yy_z_hh_mm_ss;
    public static SimpleDateFormat DD_MM_YYYY_HYPHEN;
    public static DateFormat MMM_yy;
    public static DateFormat MMM_yyyy = new SimpleDateFormat("MMM-yyyy");
    public static DateFormat MM_DD_YYYY;
    public static DateFormat MM_DD_YYYY_HH_mm_ss;
    public static DateFormat MM_DD_YYYY_hh_mm;
    public static DateFormat MM__DD__YYYY;
    public static DateFormat YYYY_MM_DD;
    public static DateFormat YYYY_MM_DD_HH_mm_ss_SSS;
    public static DateFormat YYYY_MM_DD_HYPHEN;
    public static DateFormat YYYY_MM_DD_hh_mm_ss;
    public static DateFormat yyyy;
    public static SimpleDateFormat DD_MMM_YYYY_HH_MM_AA;
    public static SimpleDateFormat YYYY_MM_DD_HH_MM_ss_HYPHEN;
    private static SimpleDateFormat DD_MM_YYYY;
    private static DateFormat DD__MM__YYYY;
    private static DateFormat DD__MM__YYYY_hh_mm = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    //public static final String DD_MMM_YYYY_HH_MM_AA = "dd-MMM-yyyy hh:mm aa";
    //public static final String DD_MMM_YYYY = "dd-MMM-yyyy";

    static {
        DD_MMM_YYYY_HH_MM_AA = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
        DD_MM_YYYY_HYPHEN = new SimpleDateFormat("dd-MM-yyyy");
        YYYY_MM_DD_HYPHEN = new SimpleDateFormat("yyyy-MM-dd");
        yyyy = new SimpleDateFormat("yyyy");
        DD_MMM_yy_z = new SimpleDateFormat("dd MMM, yyyy");
        DD_MMM_yy_z_hh_mm_ss = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
        DD_MMM_yy_z_hh_mm = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        DD_MMM_yy_hh_mm = new SimpleDateFormat("dd MMM, yyyy hh:mm");
        MMM_yy = new SimpleDateFormat("MMM-yy");
        MM_DD_YYYY = new SimpleDateFormat("MM-dd-yyyy");
        MM__DD__YYYY = new SimpleDateFormat("MM/dd/yyyy");
        YYYY_MM_DD_HH_MM_ss_HYPHEN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        YYYY_MM_DD_HH_mm_ss_SSS = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSS");
        YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
        MM_DD_YYYY_hh_mm = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        MM_DD_YYYY_HH_mm_ss = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        DD_MMM_yy_HH_mm = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        DD__MM__YYYY = new SimpleDateFormat("dd/MM/yyyy");
        DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy");
        DD_MMM_yy_hh_mm_aaa = new SimpleDateFormat("dd MMM, yyyy hh:mm aaa");
        YYYY_MM_DD_hh_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    public static int getCalendarDifferenceInDays(Calendar paramCalendar1, Calendar paramCalendar2) {
        int i = 0;
        if (paramCalendar1 != null) {
            i = 0;
            if (paramCalendar2 != null) {
                long l = paramCalendar1.getTimeInMillis();
                i = (int) ((paramCalendar2.getTimeInMillis() - l) / 86400000L);
            }
        }
        return i;
    }

    public static int getCalendarDifferenceInDaysRenew(Calendar paramCalendar1, Calendar paramCalendar2) {
        int i = -1;
        if ((paramCalendar1 != null) && (paramCalendar2 != null)) {
            long l = paramCalendar1.getTimeInMillis();
            i = (int) ((paramCalendar2.getTimeInMillis() - l) / 86400000L);
        }
        return i;
    }

    public static Calendar getCalendarForTimeStamp(Timestamp paramTimestamp) {
        if (paramTimestamp == null) {
            return null;
        }
        Calendar localCalendar = Calendar.getInstance();
        try {
            localCalendar.setTimeInMillis(paramTimestamp.getTime());
            return localCalendar;
        } catch (Exception localException) {
        }
        return null;
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getCurrentDateTimeWithMMDDYYYYHHMM() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date());
    }

    public static String getDateInDDMMYYYYInUIFormat(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return "";
        }
        return DD__MM__YYYY.format(paramCalendar.getTime());
    }

    public static String getDateInDDMMYYYYhhmmInUIFormat(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return "";
        }
        return DD__MM__YYYY_hh_mm.format(paramCalendar.getTime());
    }

    public static String getDateInDdMmAndYYYY(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return DD_MMM_yy_z.format(paramCalendar.getTime());
    }

    public static String getDateInDdMmAndYYYYHHMM(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return DD_MMM_yy_z_hh_mm.format(paramCalendar.getTime());
    }

    public static String getDateInDdMmAndYYYYHHMMSS(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return DD_MMM_yy_z_hh_mm_ss.format(paramCalendar.getTime());
    }

    public static String getDateInDdMmYyyy(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return DD_MM_YYYY_HYPHEN.format(paramCalendar.getTime());
    }

    public static String getDateInYYYYMMDDInUIFormat(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return "";
        }
        return YYYY_MM_DD.format(paramCalendar.getTime());
    }

    public static String getDateInYYYYMMDDhhmmssInUIFormat(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return "";
        }
        return YYYY_MM_DD_hh_mm_ss.format(paramCalendar.getTime());
    }

    public static String getDateInYYYYMmAndDd(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return YYYY_MM_DD_HYPHEN.format(paramCalendar.getTime());
    }

    public static String getDateWithTime(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return DD_MMM_yy_HH_mm.format(paramCalendar.getTime());
    }

    public static String getDate_IN_YYYY_MM_DD(Calendar paramCalendar) {
        return YYYY_MM_DD.format(paramCalendar.getTime()).trim();
    }

    public static String getMonthName(int paramInt) {
        String str = "";
        if (paramInt == 0) {
            str = "JAN";
        }
        if (paramInt == 1) {
            str = "FEB";
        }
        if (paramInt == 2) {
            str = "MAR";
        }
        if (paramInt == 3) {
            str = "APR";
        }
        if (paramInt == 4) {
            str = "MAY";
        }
        if (paramInt == 5) {
            str = "JUN";
        }
        if (paramInt == 6) {
            str = "JUL";
        }
        if (paramInt == 7) {
            str = "AUG";
        }
        if (paramInt == 8) {
            str = "SEP";
        }
        if (paramInt == 9) {
            str = "OCT";
        }
        if (paramInt == 10) {
            str = "NOV";
        }
        if (paramInt == 11) {
            str = "DEC";
        }

        return str;
    }

    public static int getMonthNumber(String paramString) {
        int i = -1;
        String str = paramString.substring(0, 3);
        if (str.equalsIgnoreCase("jan")) {
            i = 0;
        }
        if (str.equalsIgnoreCase("feb")) {
            i = 1;
        }
        if (str.equalsIgnoreCase("mar")) {
            i = 2;
        }
        if (str.equalsIgnoreCase("apr")) {
            i = 3;
        }
        if (str.equalsIgnoreCase("may")) {
            i = 4;
        }
        if (str.equalsIgnoreCase("jun")) {
            i = 5;
        }
        if (str.equalsIgnoreCase("jul")) {
            i = 6;
        }
        if (str.equalsIgnoreCase("aug")) {
            i = 7;
        }
        if (str.equalsIgnoreCase("sep")) {
            i = 8;
        }
        if (str.equalsIgnoreCase("oct")) {
            i = 9;
        }
        if (str.equalsIgnoreCase("nov")) {
            i = 10;
        }
        if (str.equalsIgnoreCase("dec")) {
            i = 11;
        }

        return i;
    }

    public static Timestamp getTimestampForCalendar(Calendar paramCalendar) {
        if (paramCalendar == null) {
            return null;
        }
        return new Timestamp(paramCalendar.getTimeInMillis());
    }

    public static void main(String[] paramArrayOfString) {
        System.out.println(stringToCalendar("2014-01-14"));
    }

    public static String pad(int paramInt) {
        if (paramInt >= 10) {
            return String.valueOf(paramInt);
        }
        return "0" + String.valueOf(paramInt);
    }

    public static Calendar stringToCalendar(String paramString) {
        try {
            Date localDate = YYYY_MM_DD.parse(paramString);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(localDate);
            return localCalendar;
        } catch (ParseException localParseException) {
        }
        return null;
    }

    public static Calendar stringToCalendarTime(String paramString) {
        try {
            Date localDate = YYYY_MM_DD_hh_mm_ss.parse(paramString);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(localDate);
            return localCalendar;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return null;
    }

    public static Calendar stringToCalendarTimeRenew(String paramString) {
        Calendar localCalendar = Calendar.getInstance();
        if ((paramString != null) && (!paramString.equals(""))) {
            String[] arrayOfString1 = paramString.split(" ");
            if ((arrayOfString1 != null) && (arrayOfString1.length > 0)) {
                String str1 = arrayOfString1[0];
                String str2 = arrayOfString1[1];
                String[] arrayOfString2 = str1.split("-");
                String[] arrayOfString3 = str2.split(":");
                String str3 = arrayOfString2[0];
                String str4 = arrayOfString2[1];
                String str5 = arrayOfString2[2];
                String str6 = arrayOfString3[0];
                String str7 = arrayOfString3[1];
                String str8 = arrayOfString3[2];
                int i = GenericUtils.getStringToInteger(str3);
                if (str4.startsWith("0")) {
                    str4 = str4.substring(1);
                }
                int j = -1 + GenericUtils.getStringToInteger(str4);
                if (str5.startsWith("0")) {
                    str5 = str5.substring(1);
                }
                int k = GenericUtils.getStringToInteger(str5);
                if (str6.startsWith("0")) {
                    str6 = str6.substring(1);
                }
                int m = GenericUtils.getStringToInteger(str6);
                if (str7.startsWith("0")) {
                    str7 = str7.substring(1);
                }
                int n = GenericUtils.getStringToInteger(str7);
                if (str8.startsWith("0")) {
                    str8 = str8.substring(1);
                }
                int i1 = GenericUtils.getStringToInteger(str8);
                localCalendar.set(Calendar.YEAR, i);
                localCalendar.set(Calendar.MONTH, j);
                localCalendar.set(Calendar.DATE, k);
                localCalendar.set(Calendar.HOUR_OF_DAY, m);
                localCalendar.set(Calendar.MINUTE, n);
                localCalendar.set(Calendar.SECOND, i1);
            }
        }
        return localCalendar;
    }
}
