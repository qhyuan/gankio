package com.walle.gankio.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhang on 2015/5/19
 */
public class FileDownloadManager
{
	private static FileDownloadManager mManager;
	private android.app.DownloadManager mDownloadManager;

	private FileDownloadManager(Context context)
	{
		mDownloadManager = (android.app.DownloadManager) context
		        .getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public static FileDownloadManager getInstance(Context context)
	{
		if (mManager == null)
		{
			mManager = new FileDownloadManager(context);
		}
		return mManager;
	}

	public DownloadChangeObserver download(DownloadFileInfo info,
										   DownLoadListener listener)
	{
		// 把任务加入队列并获取id值便于取消任务等操作
		info.id = mDownloadManager.enqueue(new DownloadManager.Request(Uri
		        .parse(info.url)).setDestinationInExternalPublicDir(
		        Environment.DIRECTORY_DOWNLOADS, info.name));
		return new DownloadChangeObserver(new Handler(Looper.getMainLooper()),
		        info, mDownloadManager, listener);
	}

	public void cancelDownload(long id)
	{
		mDownloadManager.remove(id);
	}
}
