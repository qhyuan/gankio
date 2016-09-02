package com.walle.gankio.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.greendao.CollectDao;
import com.walle.gankio.greendao.DaoMaster;
import com.walle.gankio.greendao.DaoSession;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import rx.Observable;

public class DBManager {
    private final static String dbName = "gank_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        return openHelper.getWritableDatabase();
    }
    /**
     * 插入一条记录
     */
    public Observable<Collect> insertCollect(Collect collect) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        return collectDao.rx().insert(collect);
    }

    public DaoSession getWritableDaoSession(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        return daoMaster.newSession();
    }
    /**
     * 插入集合
     */
    public Observable<Iterable<Collect>> insertCollectList(List<Collect> collects) {
        if (collects == null || collects.isEmpty()) {
            return null;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        return collectDao.rx().insertInTx(collects);
    }
    /**
     * 删除一条记录
     */
    public Observable<Void> deleteCollect(Collect collect) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        return collectDao.rx().delete(collect);
    }
    /**
     * 更新一条记录

     */
    public Observable<Collect> updateCollect(Collect collect) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        return collectDao.rx().update(collect);
    }
    /**
     * 查询列表
     */
    public Observable<List<Collect>> queryCollectList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        QueryBuilder<Collect> qb = collectDao.queryBuilder();
        return qb.rx().list();
    }

    /**
     * 查询列表
     */
    public Observable<List<Collect>> queryCollectList(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectDao collectDao = daoSession.getCollectDao();
        QueryBuilder<Collect> qb = collectDao.queryBuilder();
        qb.where(CollectDao.Properties.Url.eq(url)).orderAsc(CollectDao.Properties.Collect_date);
        return qb.rx().list();
    }
}