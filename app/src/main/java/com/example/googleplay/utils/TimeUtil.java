package com.example.googleplay.utils;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public  class TimeUtil {



    /* 将Server传送的UTC时间转换为指定时区的时间 */
    @SuppressLint("SimpleDateFormat")
    public static String converTime(String srcTime, TimeZone timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String convertTime;
        Date result_date;
        long result_time = 0;
        // 如果传入参数异常，使用本地时间
        if (null == srcTime)
            result_time = System.currentTimeMillis();
        else {
            try { // 将输入时间字串转换为UTC时间
                sdf.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = sdf.parse(srcTime);
                result_time = result_date.getTime();
            } catch (Exception e) { // 出现异常时，使用本地时间
                result_time = System.currentTimeMillis();
                dspFmt.setTimeZone(TimeZone.getDefault());
                convertTime = dspFmt.format(result_time);
                return convertTime;
            }
        }
        // 设定时区
        dspFmt.setTimeZone(timezone);
        convertTime = dspFmt.format(result_time);
        return convertTime;
    }



}


