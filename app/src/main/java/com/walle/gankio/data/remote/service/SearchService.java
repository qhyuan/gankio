package com.walle.gankio.data.remote.service;

import com.walle.gankio.data.remote.model.SearchResult;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 搜索 API
 * data/{category}/{count}/{page}
 * 注：
 * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端
 * count 最大 50
 */
public interface SearchService {
    @Headers("Cache-Control: public, max-age=3600")
    @GET("data/{category}/{count}/{page} ")
    Observable<SearchResult> search(@Path("category") String category,
                                    @Path("count") int count, @Path("page") int page);
}
