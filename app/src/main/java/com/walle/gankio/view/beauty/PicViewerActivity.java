package com.walle.gankio.view.beauty;

import android.animation.ObjectAnimator;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.walle.gankio.R;
import com.walle.gankio.base.BaseActivity;
import com.walle.gankio.download.DownLoadListener;
import com.walle.gankio.download.DownloadChangeObserver;
import com.walle.gankio.download.DownloadFileInfo;
import com.walle.gankio.download.FileDownloadManager;
import com.walle.gankio.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


public class PicViewerActivity extends BaseActivity implements View.OnClickListener, DownLoadListener {

    private ImageView mImg;
    private String mUrl;
    private FloatingActionButton mFab;
    private View mMenu;
    private AppCompatButton mDownLoad;
    private AppCompatButton mSetWall;
    private boolean isSetWallPaper;
    private DownloadChangeObserver mObserver;


    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected void getIntentData(Intent intent) {
        mUrl = intent.getStringExtra("url");
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_pic_viewer;
    }

    @Override
    protected void findView() {
        mImg = $(R.id.img);
        mFab = $(R.id.fab);
        mMenu = $(R.id.menu);
        mDownLoad = $(R.id.download);
        mSetWall = $(R.id.setwall);
    }

    @Override
    protected void initView() {
        Glide.with(this).load(mUrl).into(mImg);

        mFab.setOnClickListener(this);
        mDownLoad.setOnClickListener(this);
        mSetWall.setOnClickListener(this);
        mMenu.post(new Runnable() {
            @Override
            public void run() {
                int w = mDownLoad.getWidth() + mSetWall.getWidth();
                mMenu.setTranslationX(w);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                int w = mDownLoad.getWidth() + mSetWall.getWidth();
                float transX = mMenu.getTranslationX();
                if(transX>0) {
                    ObjectAnimator.ofFloat(mFab,"rotation",0,315).start();
                    ObjectAnimator.ofFloat(mMenu, "translationX", transX,0).start();
                }else {
                    ObjectAnimator.ofFloat(mFab,"rotation",315,0).start();
                    ObjectAnimator.ofFloat(mMenu, "translationX",0, w).start();
                }
                break;
            case R.id.download:
                isSetWallPaper = false;
                download();
                break;
            case R.id.setwall:
                isSetWallPaper = true;
                download();
                break;
            default:
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mObserver!=null)
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void download() {
        if(mObserver!=null){
            getContentResolver().unregisterContentObserver(mObserver);
        }
        FileDownloadManager instance = FileDownloadManager.getInstance(getApplicationContext());
        DownloadFileInfo file = new DownloadFileInfo();
        file.url = mUrl;
        file.name=mUrl.substring(mUrl.lastIndexOf("/")+1);
        mObserver = instance.download(file, this);
        //注册ContentObserver
        getContentResolver().registerContentObserver(
                Uri.parse("content://downloads/my_downloads"), true, mObserver);
    }

    @Override
    public void onDownloadPending() {

    }

    @Override
    public void onDownloadRunning(int sofar, int size) {
    }

    @Override
    public void onDownloadPaused(int reason) {

    }

    @Override
    public void onDownloadSuccessful(File file) {
        Toast.makeText(this,"下载已完成",Toast.LENGTH_SHORT).show();
       if(isSetWallPaper){
           WallpaperManager mWallManager = WallpaperManager.getInstance(getApplicationContext());
           try {
               mWallManager.setBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
           } catch (IOException e) {
           }
       }
    }

    @Override
    public void onDownloadFailed(int reason) {
        Toast.makeText(this,"下载图片出错",Toast.LENGTH_SHORT).show();
    }
}
