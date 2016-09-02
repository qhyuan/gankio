package com.walle.gankio.data.remote.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils
{
	private final static String TAG = "NetworkUtils";

	public static boolean isNetworkUp(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
		        .getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null)
			return info.isAvailable();
		else
			return false;
	}



//	public static String getHttpDataInString()
//	{
//        String str = "";
//        try
//        {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpGet httpget = new HttpGet(API.URL);
//            HttpResponse response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();
//            if (entity != null)
//            {
//                String result = "";
//                InputStream instream = entity.getContent();
//                int l;
//                byte[] tmp = new byte[2048];
//                while ((l = instream.read(tmp)) != -1)
//                {
//                    result+=new String(tmp,API.ENCODING);
//                }
//                str = result;
//            }
//        } catch (Exception e)
//        {
//        }
//        return str;
//    }

	private static String changeInputStream(InputStream inputStream, String encode)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len;
		String result = "";
		if (inputStream != null)
		{
			try
			{
				while ((len = inputStream.read(data)) != -1)
				{
					outputStream.write(data, 0, len);
				}
				String str = new String(outputStream.toByteArray());
                result = new String(str.getBytes(),"UTF-8");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}



}