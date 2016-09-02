package com.walle.gankio.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 应用程序设置信息。
 * @author jinwenchao
 *
 * sample:
 * //单例创建该类实例
 * Preferences cncConfig =
 * Preferences.build(context, GlobalConstant.CNC_GROUP);
 *
 * KEY_PUT为string常量
 *
 * 获取数据：
 *    cncConfig.getInt(“KEY_PUT”, 0);
 * 添加数据：
 *    cncConfig.putInt(“KEY_PUT”, 0);
 *
 *
 */
public class Preferences {

    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor = null;


    /**
     * 私有构造器。
     * 
     * @param context
     * @param cfgName
     * @version 1.0
     */
    private Preferences(Context context, String cfgName) {
        if(context == null)
            throw new IllegalArgumentException("the context is null");
        mSp = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
    }

    /**
     * 构建 Preferences 实例。
     * @param context Context
     * @param cfgName 配置文件名称
     */
    public static Preferences build(Context context, String cfgName) {
        return new Preferences(context, cfgName);
    }

    /**
     * 使用默认名称("CNC_GROUP") 构建 Preferences 实例。
     * @param context Context
     */
    public static Preferences build(Context context) {
        return new Preferences(context, "walle");
    }

    public boolean contains(String key) {
        return mSp.contains(key);
    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mSp.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mSp.getLong(key, defValue);
    }

    public boolean putInt(String key, int value) {
        mEditor = mSp.edit();
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public boolean putLong(String key, long value) {
        mEditor = mSp.edit();
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public boolean putBoolean(String key, boolean value) {
        mEditor = mSp.edit();
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public boolean putFloat(String key, float value) {
        mEditor = mSp.edit();
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    public boolean putString(String key, String value) {
        mEditor = mSp.edit();
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public boolean removeKey(String key){
        mEditor = mSp.edit();
        mEditor.remove(key);
        return mEditor.commit();
    }

    public Map<String,?> getAll(){
         return mSp.getAll();
    }
    
    public boolean clear() {
    	mEditor = mSp.edit();
    	mEditor.clear();
    	return mEditor.commit();
    }


    public boolean getBoolean(String key) {
        return getBoolean(key,false);
    }
}
