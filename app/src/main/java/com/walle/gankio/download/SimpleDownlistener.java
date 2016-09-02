package com.walle.gankio.download;

import java.io.File;

/**
 * Created by void on 16/8/31.
 */
public class SimpleDownlistener implements DownLoadListener {
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

    }

    @Override
    public void onDownloadFailed(int reason) {

    }
}
