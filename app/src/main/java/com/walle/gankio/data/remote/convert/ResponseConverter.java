package com.walle.gankio.data.remote.convert;


import com.walle.gankio.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 用于文件下载
 * Created by yqh on 2016/7/20
 */
public class ResponseConverter implements Converter<ResponseBody, File> {

    private static final String TAG = "ResponseConverter";

    @Override
    public File convert(ResponseBody responseBody) throws IOException {

        DownloadListener listener = new DownloadListener() {
            @Override
            public void success(File file) {
                LogUtil.i(TAG, "file=" + file);
            }

            @Override
            public void update(long bytesRead, long contentLength) {
            }

            @Override
            public void error(Exception exception) {
                LogUtil.i(TAG, "exception=" + exception);
            }
        };
        String type = responseBody.contentType().subtype();
        long contentLength = responseBody.contentLength();
        byte[] bs = new byte[1024];
        int len;
        int readLen = 0;
        String name = "file" + System.currentTimeMillis();
        File file = new File("/sdcard/Imeitu/" + name + type);
        FileOutputStream os = new FileOutputStream(file);
        InputStream is = responseBody.byteStream();
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
            readLen = readLen + len;
            listener.update(readLen, contentLength);
        }
        os.flush();
        os.close();
        is.close();
        if (readLen != contentLength) {
            listener.error(new SocketException("download error :"));
        } else {
            listener.success(file);
        }
        return file;
    }
}
