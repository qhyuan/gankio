package com.walle.gankio.data.remote.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 获取某几日干货网站数据:
 * http://gank.io/api/history/content/2/1
 * 注： 2 代表 2 个数据，1 代表：取第一页数据
 */
public interface HistoryService {
    @GET("history/content/{count}/{page}")
    Observable<Object> getHistory(@Path("count") int count, @Path("page") int page);
}
