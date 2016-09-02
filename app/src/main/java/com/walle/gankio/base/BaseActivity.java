package com.walle.gankio.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.walle.gankio.R;
import com.walle.gankio.utils.WindowUtil;


public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        getIntentData(getIntent());
        setContentView(getContentViewRes());
        super.onCreate(savedInstanceState);
        findView();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void getIntentData(Intent intent) {

    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View view = $(R.id.statusbarview);
            if (view != null)
                view.setVisibility(View.GONE);
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransLucentStatus(color);
        }

    }

    private void setTransLucentStatus(int color) {
        View view = $(R.id.statusbarview);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (view == null)
            return;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) WindowUtil.getStatusBarHeight(getApplicationContext());
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(color);
    }


    protected abstract int getContentViewRes();

    protected void findView() {
    }

    protected abstract void initView();

    protected boolean isFullScreen() {
        return false;
    }

    protected <T extends View> T $(int id) {
        return (T) findViewById(id);
    }
}
