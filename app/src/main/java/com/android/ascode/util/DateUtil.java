package com.android.ascode.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.text.TextUtils.isEmpty;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by yanfeng on 2017/5/22.
 */

public class DateUtil {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat format4 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    static SimpleDateFormat format5 = new SimpleDateFormat("yyyy年MM月dd日");
    static SimpleDateFormat format6 = new SimpleDateFormat("MM月dd日");
    static SimpleDateFormat format7 = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat format8 = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat getFormat(int type) {
        SimpleDateFormat sf = null;
        switch (type) {
            case 1:
                sf = format;
                break;
            case 2:
                sf = format2;
                break;
            case 3:
                sf = format3;
                break;
            case 4:
                sf = format4;
                break;
            case 5:
                sf = format5;
                break;
            case 6:
                sf = format6;
                break;
            case 7:
                sf = format7;
                break;
            case 8:
                sf = format8;
                break;

        }
        return sf;
    }

    public static String getTime(Calendar calendar) {
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getTime3(Calendar calendar) {
        String time = format3.format(calendar.getTime());
        return time;
    }

    public static String getTime2(Calendar calendar) {
        String time = format2.format(calendar.getTime());
        return time;
    }

    public static String getTime2(Date calendar) {
        String time = format2.format(calendar.getTime());
        return time;
    }

    public static String getTime(Date calendar) {
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getTime(String mills, int type) {
        if ( TextUtils.isEmpty(mills)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(mills));
        String time = getFormat(type).format(calendar.getTime());
        return time;
    }
    //java--》php
    public static String getTime(String datee) {
        String time = "";
        if(!isEmpty(datee)){
            try {
                Date date=getFormat(1).parse(datee);
                time = date.getTime()/1000+"";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    //包括今天
    public static boolean isYouXiaoDate(Calendar calendar) {
        Calendar calendarnow = Calendar.getInstance();
        //给出的时间在今天之前
        if (!calendar.before(calendarnow)) {
            return true;
        } else {
            return false;
        }
    }

    //今天大于传入的日期为过期日期
    public static boolean isYouXiaoDate2(Calendar calendar) {
        if (calendar.compareTo(Calendar.getInstance())==1) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean sameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static boolean isToday(Calendar selectedDate) {
        Calendar cal = Calendar.getInstance();
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static boolean betweenDates(Calendar cal, Calendar minCal, Calendar maxCal) {
        final Date date = cal.getTime();
        return betweenDates(date, minCal, maxCal);
    }

    public static boolean betweenDates(Date date, Calendar minCal, Calendar maxCal) {
        final Date min = minCal.getTime();
        return (date.equals(min) || date.after(min)) // >= minCal
                && date.before(maxCal.getTime()); // && < maxCal
    }

    //php---》java
    public static String toDate(String date, int type) {
        if (!isEmpty(date)) {
            if ("0".equals(date)) {
                return "";
            } else {
                return getTime(Long.parseLong(date) * 1000 + "",type);
            }
        } else {
            return "";
        }
    }
    //获取当天距月底还有多少天
    public static int getDaysToMouth(){
        Calendar c = Calendar.getInstance();
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);
        return d-now;
    }

}
