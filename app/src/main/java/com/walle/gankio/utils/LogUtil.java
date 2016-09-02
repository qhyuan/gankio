package com.walle.gankio.utils;

import android.util.Log;

/**
 * Created by void on 16/6/27
 */
public class LogUtil
{
	public static final String TAG = "LogUtil";
	public static final boolean DEBUG = true;

	public static final int I = 0;
	public static final int D = 1;
	public static final int E = 2;

	public static void printLog(String tag, String msg, int type)
	{
		if (!DEBUG)
		{
			return;
		}
		switch (type)
		{
			case I:
				Log.i(tag, msg);
				break;
			case D:
				Log.d(tag, msg);
				break;
			case E:
				Log.e(tag, msg);
				break;
		}
	}

	public static void i(String tag, String msg)
	{
		printLog(tag, msg, I);
	}

	public static void i(String msg)
	{
		i(TAG, msg);
	}

	public static void d(String tag, String msg)
	{
		printLog(tag, msg, D);
	}

	public static void d(String msg)
	{
		d(TAG, msg);
	}

	public static void e(String tag, String msg)
	{
		printLog(tag, msg, E);
	}

	public static void e(String msg)
	{
		e(TAG, msg);
	}
}