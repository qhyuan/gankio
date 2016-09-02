package com.walle.gankio.data;


import android.content.Context;

import com.walle.gankio.App;
import com.walle.gankio.data.local.DBManager;
import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.data.remote.NetWorkManager;
import com.walle.gankio.data.remote.SimpleSubscribeOperation;
import com.walle.gankio.data.remote.SimpleSubscribers;
import com.walle.gankio.data.remote.model.Random;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.data.remote.service.RandomService;
import com.walle.gankio.data.remote.service.SearchService;
import com.walle.gankio.greendao.CollectDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public final class DataManager extends SimpleSubscribeOperation {

    private static Retrofit retrofit;
    private static final Context context;

    static {
        retrofit = NetWorkManager.getInstance().getDefaultRetrofit();
        context = App.getInstance();
    }

    public static Subscription searData(String category, int count, int page, Subscriber subscriber){
        Observable<SearchResult> observable = retrofit.create(SearchService.class).search(category, count, page);
        return toSubscribeOnIoWidthObserveOnMain(observable,subscriber);
    }

    public static Subscription getRandomData(String type,int count,Subscriber subscriber){
        Observable<Random> observable = retrofit.create(RandomService.class).getRandomData(type,count);
        return toSubscribeOnIoWidthObserveOnMain(observable,subscriber);
    }

    public static void insertCollect(final Collect collect, Subscriber subscriber){
        final DBManager instance = DBManager.getInstance(context);
        Observable<List<Collect>> collectsObs = instance.queryCollectList(collect.getUrl());
        toSubscribeOnIoWidthObserveOnIo(collectsObs,new SimpleSubscribers<List<Collect>>(){
            @Override
            public void onNext(List<Collect> collects) {
                super.onNext(collects);
                if(collects.isEmpty()){
                    instance.getWritableDaoSession().getCollectDao().insert(collect);
                }else {
                    instance.getWritableDaoSession().getCollectDao().update(collect);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    public static void deleteCollect(Collect collect,Subscriber subscriber){
        Observable<Void> observable = DBManager.getInstance(context).deleteCollect(collect);
        toSubscribeOnIoWidthObserveOnMain(observable,subscriber);
    }

    public static Subscription getAllCollectList(SimpleSubscribers<List<Collect>> simpleSubscribers){
        Observable<List<Collect>> listObservable = DBManager.getInstance(context).queryCollectList();
        return toSubscribeOnIoWidthObserveOnMain(listObservable,simpleSubscribers);
    }

    public static List<Collect> getCollectByUrl(String url){
        return DBManager.getInstance(context).getWritableDaoSession()
                .getCollectDao().queryBuilder().where(CollectDao.Properties.Url.eq(url)).build().list();
    }

}
