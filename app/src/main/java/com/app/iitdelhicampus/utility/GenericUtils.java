package com.app.iitdelhicampus.utility;

import android.util.Pair;

import com.app.iitdelhicampus.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GenericUtils {
    public static final String EMPLOYEE_TARGET_ACHIEVEMENT_LIST = "EMPLOYEE_TARGET_ACHIEVEMENT_LIST";
    public static final String FIRST_HALF = "#YEAR#-01-01 00:00:00@@#YEAR#-06-30 23:59:59";
    public static final String FIRST_QUATER = "#YEAR#-01-01 00:00:00@@#YEAR#-03-31 23:59:59";
    public static final String FOURTH_QUATER = "#YEAR#-10-01 00:00:00@@#YEAR#-12-31 23:59:59";
    public static final String ONE_YEAR = "#YEAR#-01-01 00:00:00@@#YEAR#-12-31 23:59:59";
    public static final String SECOND_HALF = "#YEAR#-07-01 00:00:00@@#YEAR#-12-31 23:59:59";
    public static final String SECOND_QUATER = "#YEAR#-04-01 00:00:00@@#YEAR#-06-30 23:59:59";
    public static final String THIRD_QUATER = "#YEAR#-07-01 00:00:00@@#YEAR#-09-30 23:59:59";
    static int selectedParentProductId = 0;
    boolean directToHome = false;

    public static String changedHeaderHtml(String paramString) {
        return "<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /><script> $(function(){ $('#includedContent').load('" + paramString + "'); }); </script> </head> <body> <div id='includedContent'></div></body></html>";
    }


    public static String getStringFromIntList(List<Integer> paramList, String paramString) {
        StringBuffer localStringBuffer = new StringBuffer("");
        if ((paramList != null) && (paramList.size() > 0)) {
            Iterator localIterator = paramList.iterator();
            for (; ; ) {
                if (!localIterator.hasNext()) {
                    if (("".equals(localStringBuffer)) || (localStringBuffer.length() <= 0)) {
                        break;
                    }
                    return localStringBuffer.substring(0, localStringBuffer.length() - paramString.length());
                }
                Integer localInteger = (Integer) localIterator.next();
                localStringBuffer.append(localInteger + paramString);
            }
        }
        return localStringBuffer.toString();
    }

    public static double getStringToDouble(String paramString) {
        try {
            double d = Double.parseDouble(paramString);
            return d;
        } catch (Exception localException) {
        }
        return 0.0D;
    }

    public static float getStringToFloat(String paramString) {
        try {
            float f = Float.parseFloat(paramString);
            return f;
        } catch (Exception localException) {
        }
        return 0.0F;
    }

    public static int getStringToInteger(String paramString) {
        try {
            int i = Integer.parseInt(paramString);
            return i;
        } catch (Exception localException) {
        }
        return 0;
    }

    public static long getStringToLong(String paramString) {
        try {
            long l = Long.parseLong(paramString);
            return l;
        } catch (Exception localException) {
        }
        return 0L;
    }

    public static Pair<Date, Date> getMonthDateRange() {
        Date begining, end;

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            setTimeToBeginningOfDay(calendar);
            begining = calendar.getTime();
        }

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeToEndofDay(calendar);
            end = calendar.getTime();
        }

        return Pair.create(begining, end);
    }

    public static Pair<Calendar, Calendar> getMonthCalendarRange() {
        Calendar beginning, end;

        {
            beginning = getCalendarForNow();
            beginning.set(Calendar.DAY_OF_MONTH,
                    beginning.getActualMinimum(Calendar.DAY_OF_MONTH));
            setTimeToBeginningOfDay(beginning);
        }

        {
            end = getCalendarForNow();
            end.set(Calendar.DAY_OF_MONTH,
                    end.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeToEndofDay(end);
        }

        return Pair.create(beginning, end);
    }

    public static Pair<Calendar, Calendar> getTodayCalendarRange() {
        Calendar beginning, end;

        {
            beginning = getCalendarForNow();
            setTimeToBeginningOfDay(beginning);
        }

        {
            end = getCalendarForNow();
            setTimeToEndofDay(end);
        }

        return Pair.create(beginning, end);
    }

    public static Pair<String, String> getMonthDateRangeFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS);
        Pair<Date, Date> dates = getMonthDateRange();
        return Pair.create(dateFormat.format(dates.first), dateFormat.format(dates.second));
    }


    public static Calendar getCalendarForNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static Calendar getCalendarForMonthEnd() {
        Calendar end = getCalendarForNow();
        end.set(Calendar.DAY_OF_MONTH,
                end.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(end);
        return end;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public static Date getCurrentDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

    public static Calendar getCurrentDayStartCal() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;//.getTime();

    }

    public static String getCurrentDayStartString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS).format(calendar.getTime());

    }

    public static String getCurrentDayEndString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new SimpleDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS).format(calendar.getTime());
    }

    public static Pair<String, String> getCurrentDateRangeFormatted() {
        return Pair.create(getCurrentDayStartString(), getCurrentDayEndString());
    }
}

