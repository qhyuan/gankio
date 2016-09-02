package com.walle.gankio;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.walle.gankio.data.local.Preferences;
import com.walle.gankio.data.remote.NetWorkManager;
import com.walle.gankio.utils.LogUtil;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by yqh on 2016/8/12
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetWorkManager.getInstance().init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        initTitle();
    }

    private void initTitle() {
        Preferences sp = Preferences.build(getApplicationContext(), Config.TITLE_SP);
        Map<String, Boolean> titleConf =new HashMap<>();
        titleConf.put("all",true);
        titleConf.put("Android",true);
        titleConf.put("iOS",true);
        titleConf.put("前端",true);
        titleConf.put("App",true);
        titleConf.put("拓展资源",true);
        titleConf.put("瞎推荐",true);
        Set<String> keys = titleConf.keySet();
        for (String key:keys){
            sp.putBoolean(key,titleConf.get(key));
        }
    }

    public static Context getInstance() {
        return instance;
    }



}
