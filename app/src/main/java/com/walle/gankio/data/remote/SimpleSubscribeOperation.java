package com.walle.gankio.data.remote;

import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by yqh on 2016/5/20.
 */
//常用的订阅操作
public class SimpleSubscribeOperation {
    protected static <T> Subscription toSubscribeOnIoWidthObserveOnMain(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
//                .retry(new Func2<Integer, Throwable, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer, Throwable throwable) {
//                        return (throwable instanceof HttpThrowable ||
//                                throwable instanceof SocketTimeoutException)
//                                && integer > 2;
//                    }
//                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    protected static <T> Subscription toSubscribeOnIoWidthObserveOnIo(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
//                .retry(new Func2<Integer, Throwable, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer, Throwable throwable) {
//                        return (throwable instanceof HttpThrowable ||
//                                throwable instanceof SocketTimeoutException)
//                                && integer > 2;
//                    }
//                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(s);
    }

    protected static <T> Subscription toSubscribeOnMainWidthObserveOnMain(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
