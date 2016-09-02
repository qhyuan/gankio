package com.walle.gankio.view.home;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.walle.gankio.Config;
import com.walle.gankio.R;
import com.walle.gankio.base.BaseActivity;
import com.walle.gankio.data.local.Preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HomeSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private SwitchCompat mAndroid;
    private SwitchCompat mIos;
    private SwitchCompat mTuoZhan;
    private SwitchCompat mTuijian;
    private SwitchCompat mWeb;
    private SwitchCompat mApp;
    private Map<String,Boolean> mTitleConf;
    private Preferences mSp;

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_home_setting;
    }

    @Override
    protected void findView() {
        mAndroid = $(R.id.android);
        mIos = $(R.id.ios);
        mTuoZhan = $(R.id.tuozhan);
        mTuijian = $(R.id.tuijian);
        mWeb = $(R.id.web);
        mApp = $(R.id.app);
    }

    @Override
    protected void initView() {
        mTitleConf =new HashMap<>();
        mSp = Preferences.build(getApplicationContext(), Config.TITLE_SP);

        mAndroid.setChecked(mSp.getBoolean((String) mAndroid.getTag()));
        mIos.setChecked(mSp.getBoolean((String) mIos.getTag()));
        mTuoZhan.setChecked(mSp.getBoolean((String) mTuoZhan.getTag()));
        mTuijian.setChecked(mSp.getBoolean((String) mTuijian.getTag()));
        mWeb.setChecked(mSp.getBoolean((String) mWeb.getTag()));
        mApp.setChecked(mSp.getBoolean((String) mApp.getTag()));

        mAndroid.setOnCheckedChangeListener(this);
        mIos.setOnCheckedChangeListener(this);
        mTuoZhan.setOnCheckedChangeListener(this);
        mTuijian.setOnCheckedChangeListener(this);
        mWeb.setOnCheckedChangeListener(this);
        mApp.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String tag = (String) buttonView.getTag();
        mTitleConf.put(tag,isChecked);

    }

    @Override
    protected void onPause() {
        Set<String> keys = mTitleConf.keySet();
        for (String key:keys){
            mSp.putBoolean(key,mTitleConf.get(key));
        }
        super.onPause();
    }
}
