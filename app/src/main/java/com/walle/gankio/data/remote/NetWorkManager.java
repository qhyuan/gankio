package com.walle.gankio.data.remote;

import android.content.Context;
import android.content.DialogInterface;


import com.walle.gankio.data.Constant;
import com.walle.gankio.data.remote.convert.FileConverterFactory;
import com.walle.gankio.data.remote.utils.OkHttpIntercepter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取Retrofit实例
 * Created by yqh on 2016/5/20.
 */
public class NetWorkManager {
    private static NetWorkManager manager = new NetWorkManager();
    private Retrofit retrofit;

    private NetWorkManager() {
    }

    public static NetWorkManager getInstance() {
        return manager;
    }

    public void init(Context context) {
        final Cache cache = new Cache(context.getCacheDir(), 1024 * 1024 * 8);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .addInterceptor(OkHttpIntercepter.getCacheIntercepter(context))
                .addNetworkInterceptor(OkHttpIntercepter.getCacheIntercepter(context))
                .addInterceptor(OkHttpIntercepter.getLogIntercepter(context,cache))
                .build();
        retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(FileConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BaseUrl).build();
    }

    public Retrofit getDefaultRetrofit() {
        checkInit();
        return retrofit;
    }

    private void checkInit() {
        if (null == retrofit) {
            throw new RuntimeException("You should call init(Context context) first!");
        }
    }
}
