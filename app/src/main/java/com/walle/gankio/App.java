package com.walle.gankio;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.walle.gankio.data.local.Preferences;
import com.walle.gankio.data.remote.NetWorkManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RunnableFuture;

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
        final PushAgent mPushAgent = PushAgent.getInstance(this);
       //注册推送服务，每次调用register方法都会回调该接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPushAgent.register(new IUmengRegisterCallback() {

                    @Override
                    public void onSuccess(String deviceToken) {
                        //注册成功会返回device token
                    }

                    @Override
                    public void onFailure(String s, String s1) {

                    }
                });
            }
        }).start();
    }

    private void initTitle() {
        Preferences sp = Preferences.build(getApplicationContext(), Constant.TITLE_SP);
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
