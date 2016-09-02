package com.walle.gankio.data.remote.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
 * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
 * 请求个数： 数字，大于0
 * 第几页：数字，大于0
 */
public interface DataSubService {
    @GET("data/{subject}/{count}/{page}")
    Observable<Object> getDataBySubject(@Path("subject") String subject, @Path("count") int count, @Path("page") int page);
}
