package com.eenet.androidbase.utils;

import com.eenet.androidbase.BaseApplication;
import com.eenet.androidbase.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Eriksson
 */
public class DateUtils {

    private static SimpleDateFormat sdf = null;

    private static long serverDelay = 0l;

    static {
        sdf = new SimpleDateFormat();
    }

    public static long markServerDelay(String serverTime) {
        if (VerifyTool.isNumeric(serverTime)) {
            serverDelay = (new Date().getTime()) - Long.parseLong(serverTime);
        }
        return serverDelay;
    }

    /**
     * 把日期转换成日期字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        String result = "";
        if (date == null) {
            return result;
        }
        if (pattern == null || "".equals(pattern)) {
            return result;
        }
        sdf.applyPattern(pattern);
        result = sdf.format(date);
        return result;
    }

    /**
     * 把日期字符串转换成java.util.Date
     *
     * @param source
     * @param pattern
     * @return
     */
    public static Date parse(String source, String pattern) {
        Date result = null;
        if (source == null || "".equals(source)) {
            return result;
        }
        if (pattern == null || "".equals(pattern)) {
            return result;
        }
        sdf.applyPattern(pattern);
        try {
            result = sdf.parse(source);
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 智能时间格式
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String formatSmartDate(String source, String pattern) {
        String result = "";
        Date _tempD = parse(source, pattern);
        if (_tempD == null) {
            return "";
        }
        long _d = _tempD.getTime();
        long _now = new Date().getTime() - serverDelay;
        long _range = _now - _d;
        if (_range < 0) {
            result = "刚刚";
        } else if (_range < 1000 * 60) {
            result = String.valueOf(((int) (_range % 1000 > 0 ? (_range / 1000 + 1) : (_range / 1000)))) + " 秒前";
        } else if (_range < 1000 * 60 * 60) {
            result = String.valueOf(((int) (_range % (1000 * 60) > 0 ? (_range / 1000 / 60 + 1) : (_range / 1000 / 60)))) + " 分钟前";
        } else if (_range < 1000 * 60 * 60 * 2) {
            result = String.valueOf(((int) (_range % (1000 * 60 * 60) > 0 ? (_range / 1000 / 60 / 60 + 1) : (_range / 1000 / 60 / 60)))) + "小时前";
        } else {
            result = format(_tempD, "MM-dd HH:mm");
        }
        return result;
    }

    /**
     * 智能时间格式
     *
     * @param time
     * @return
     */
    public static String formatSmartDate(long time, String pattern) {
        String result = "";
        long _d = time;
        long _now = new Date().getTime() - serverDelay;
        long _range = _now - _d;
        if (_range < 0) {
            result = "刚刚";
        } else if (_range < 1000 * 60) {
            result = String.valueOf(((int) (_range % 1000 > 0 ? (_range / 1000 + 1) : (_range / 1000)))) + " 秒前";
        } else if (_range < 1000 * 60 * 60) {
            result = String.valueOf(((int) (_range % (1000 * 60) > 0 ? (_range / 1000 / 60 + 1) : (_range / 1000 / 60)))) + " 分钟前";
        } else if (_range < 1000 * 60 * 60 * 2) {
            result = String.valueOf(((int) (_range % (1000 * 60 * 60) > 0 ? (_range / 1000 / 60 / 60 + 1) : (_range / 1000 / 60 / 60)))) + "小时前";
        } else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
            return sDateFormat.format(new Date(time + 0));
        }
        return result;
    }

    /**
     * 时间转化为显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (calendar.before(inputTime)) {
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "-" + "MM" + "-" + "dd",Locale.CHINA);
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.CHINA);
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            return BaseApplication.getContext().getResources().getString(R.string.time_yesterday);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + "-" + "d",Locale.CHINA);
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "-" + "MM" + "-" + "dd", Locale.CHINA);
                return sdf.format(currenTimeZone);

            }

        }

    }

    public static String getCurrentTimeFormat(String format){
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

}
