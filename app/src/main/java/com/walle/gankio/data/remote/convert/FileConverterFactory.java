package com.walle.gankio.data.remote.convert;


import com.walle.gankio.utils.LogUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by yqh on 2016/7/20
 */
public class FileConverterFactory extends Converter.Factory {

    public static FileConverterFactory create() {
        return new FileConverterFactory();
    }

    /**
     * @param type : Content-Type: *
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<ResponseBody, File> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        LogUtil.i("Type=" + type);
        for (Annotation ann :
                annotations) {
            LogUtil.i("test", "ann=" + ann);
        }
        if (type.equals(File.class)) {
            return new ResponseConverter();
        }
        return null;
    }

}
