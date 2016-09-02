package com.walle.gankio.utils;

import android.content.Context;


public class WindowUtil {
    public static float getStatusBarHeight(Context context) {
        float statusBarHeight;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimension(resourceId);
        } else {
            statusBarHeight = 30 * context.getResources().getDisplayMetrics().density;
        }
        return statusBarHeight;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;      // 屏幕宽（像素，如：480px）
    }
    public static float getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;      // 屏幕宽（像素，如：480px）
    }
}
