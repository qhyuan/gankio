package com.walle.gankio.data.remote.utils;

import android.content.Context;

import com.walle.gankio.utils.LogUtil;

import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by void on 16/8/22.
 */
public class OkHttpIntercepter {
    private static final String TAG = "OkHttpIntercepter";
    private static Interceptor mCache;
    private static Interceptor mLog;
    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */

    public static Interceptor getLogIntercepter(final Context context, final Cache cache){
            if(mLog==null){
                mLog = chain -> {
                    Request request = chain.request();
                    Logger.getLogger(TAG).info(String.format("CacheInfo  %s on %s%n%s", cache.hitCount(), cache.size(), cache.urls()));
                    long t1 = System.nanoTime();
                    Logger.getLogger(TAG).info(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();
                    Logger.getLogger(TAG).info(String.format("Received response for %s in %.1fms%n%s",
                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                    return response;
                };
            }
        return mLog;
    }

    public static  Interceptor getCacheIntercepter(final Context context) {
        if (mCache == null) {
            mCache = chain -> {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkUp(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    LogUtil.e(TAG, "no network");
                }
                Response originalResponse = chain.proceed(request);
                if (NetworkUtils.isNetworkUp(context)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                            .removeHeader("Pragma")
                            .build();
                }
            };
        }
        return mCache;
    }
}

