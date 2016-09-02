package com.walle.gankio.view;

import android.annotation.SuppressLint;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.walle.gankio.R;
import com.walle.gankio.base.BaseActivity;
import com.walle.gankio.data.DataManager;
import com.walle.gankio.data.local.DBManager;
import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.utils.WindowUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WebViewActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, View.OnClickListener {

    private WebView mWebView;
    private String mUrl;
    private String mDesc;
    private Toolbar mToolbar;
    private ProgressBar mPb;
    private AppBarLayout mAppbar;
    private int mStatusBarHeight;
    private NestedScrollView mRootView;
    private FloatingActionButton mGoUp;
    private boolean isCollected;
    @Override
    protected void getIntentData(Intent intent) {
        mUrl = intent.getStringExtra("url");
        mDesc = intent.getStringExtra("desc");
    }

    @Override
    protected int getContentViewRes() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_web_view;
    }

    @Override
    protected void findView() {
        mRootView = $(R.id.nestedscrollview);
        mAppbar = $(R.id.appbar);
        mWebView = $(R.id.webView);
        mToolbar = $(R.id.toolbar);
        mPb = $(R.id.pb);
        mGoUp = $(R.id.goup);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {

        isCollected = !DataManager.getCollectByUrl(mUrl).isEmpty();

        mStatusBarHeight = (int) (WindowUtil.getStatusBarHeight(getApplicationContext()) + 0.5);

        mPb.setMax(100);
        mAppbar.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
                params.topMargin =  mStatusBarHeight;
                mToolbar.setLayoutParams(params);
            }
        });
        mToolbar.setTitle(mDesc);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGoUp.setOnClickListener(this);

        mRootView.setOnScrollChangeListener(this);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mPb.setVisibility(View.GONE);
                } else {
                    mPb.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(mUrl);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
               finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        menu.findItem(R.id.action_collect).setTitle(isCollected?"取消收藏":"收藏");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(mUrl);
            intent.setData(content_url);
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_collect){
            Collect collect = new Collect();
            collect.setCollect_date(Calendar.getInstance().getTimeInMillis());
            collect.setUrl(mUrl);
            collect.setDesc(mDesc);
           if(isCollected){
               DataManager.deleteCollect(collect,null);
           }else {
               DataManager.insertCollect(collect,null);
           }
            isCollected = !isCollected;
            item.setTitle(isCollected?"取消收藏":"收藏");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(mRootView.getScrollY()<=0) {
            showGoup(false);
            return;
        }
        int dis = scrollY - oldScrollY;
        if(dis>100)
            showGoup(false);
        if(dis<0)
            showGoup(true);
    }

    private void showGoup(boolean isShow) {
        mGoUp.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        mRootView.fullScroll(View.FOCUS_UP);
    }
}
