package com.walle.gankio.download;

import android.app.DownloadManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;

import java.io.File;

/**
 * Created by zhang on 2015/5/19
 * .
 */
public class DownloadChangeObserver extends ContentObserver
{
	private DownloadManager mDownloadManager;
	private DownLoadListener mDownLoadListener;
	private DownloadFileInfo mInfo;

	public DownloadChangeObserver(Handler handler, DownloadFileInfo info,
	        DownloadManager manager, DownLoadListener listener)
	{
		super(handler);
		mInfo = info;
		mDownloadManager = manager;
		mDownLoadListener = listener;
	}

	@Override
	public void onChange(boolean selfChange)
	{
		super.onChange(selfChange);
		if (mDownLoadListener == null)
		{
			return;
		}
		queryDownloadStatus();
	}

	private void queryDownloadStatus()
	{
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(mInfo.id);
		Cursor cursor = mDownloadManager.query(query);
		if (cursor != null && cursor.moveToFirst())
		{
			String title = cursor.getString(cursor
			        .getColumnIndex(DownloadManager.COLUMN_TITLE));
			int size = cursor.getInt(cursor
			        .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
			int sofar = cursor
			        .getInt(cursor
			                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
			int reason = cursor.getInt(cursor
			        .getColumnIndex(DownloadManager.COLUMN_REASON));
			switch (cursor.getInt(cursor
			        .getColumnIndex(DownloadManager.COLUMN_STATUS)))
			{
				case DownloadManager.STATUS_PENDING:
					mDownLoadListener.onDownloadPending();
					break;
				case DownloadManager.STATUS_RUNNING:
					mDownLoadListener.onDownloadRunning(sofar, size);
					break;
				case DownloadManager.STATUS_PAUSED:
					mDownLoadListener.onDownloadPaused(reason);
					break;
				case DownloadManager.STATUS_SUCCESSFUL:
					mDownLoadListener
					        .onDownloadSuccessful(new File(
					                Environment
					                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					                title));
					break;
				case DownloadManager.STATUS_FAILED:
					mDownLoadListener.onDownloadFailed(reason);
					mDownloadManager.remove(mInfo.id);
					break;
			}
			cursor.close();
		}

	}
}
