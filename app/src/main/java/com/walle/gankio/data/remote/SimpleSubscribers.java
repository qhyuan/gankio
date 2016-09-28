package com.walle.gankio.data.remote;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import rx.Subscriber;

/**
 * Created by yqh on 2016/5/20.
 * 实现的Subscriber的4个方法，让子类可以只实现自己需要的方法
 */
public class SimpleSubscribers<T> extends Subscriber<T> {
    private static final String TAG = "SimpleSubscribers";
    private static final Handler UIHANDLER;

    static {
        UIHANDLER = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onStart() {
        UIHANDLER.post(this::onStartOnMainThread);
    }

    /**
     * 运行在UI线程中的onStart
     */
    public void onStartOnMainThread() {
        Log.i(TAG, "onStartOnMainThread");
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError=" + e.getMessage());
    }

    @Override
    public void onNext(T t) {
        Log.i(TAG, "onNext=" + t);
    }
}
