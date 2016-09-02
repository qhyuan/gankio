package com.walle.gankio.data.remote.service;

import com.walle.gankio.data.remote.model.Random;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by void on 16/8/17.
 */
public interface RandomService {
    @GET("random/data/{type}/{count}")
    Observable<Random> getRandomData(@Path("type") String type,@Path("count") int count);
}
