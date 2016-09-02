package com.walle.gankio.data.remote;

import android.util.Log;


import com.walle.gankio.data.remote.model.IResult;

import rx.functions.Func1;


/**
 * 用来统一处理Http的resultCode,并将 IResult 的Data部分剥离出来返回给subscriber
 *
 * @param <T> IResult ArrayList中实际存储的类型
 */
public class HttpResultFunc<T> implements Func1<IResult<T>, T> {

    private static final String TAG = "HttpResultFunc";

    @Override
    public T call(IResult<T> httpResult) {
        Log.i(TAG, "getResultCode = " + httpResult.getResultCode()
                + "---listCount=" + httpResult.getData());
        if (httpResult.getResultCode() != 200) {
            throw new HttpThrowable(httpResult.getResultCode());
        }
        return httpResult.getData();
    }
}
    