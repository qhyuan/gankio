package com.walle.gankio.download;

import java.io.File;

/**
 * Created by zhang on 2015/5/19
 */
public interface DownLoadListener
{
	void onDownloadPending();

	void onDownloadRunning(int sofar, int size);

	void onDownloadPaused(int reason);

	void onDownloadSuccessful(File file);

	void onDownloadFailed(int reason);
}