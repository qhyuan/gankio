package com.walle.gankio.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * Created by void on 16/8/19.
 */
public class DateUtil {
    public static String toString(long time){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(time);
    }
}
