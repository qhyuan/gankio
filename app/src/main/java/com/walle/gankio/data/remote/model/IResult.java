package com.walle.gankio.data.remote.model;

/**
 * Created by yqh on 2016/8/10.
 */
public interface IResult<T> {
    int getResultCode();

    T getData();
}
