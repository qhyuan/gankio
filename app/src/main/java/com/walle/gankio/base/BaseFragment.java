package com.walle.gankio.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walle.gankio.R;
import com.walle.gankio.utils.LogUtil;

/**
 * Created by void on 16/3/9
 */
public abstract class BaseFragment extends Fragment {

    protected  final String TAG = getClass().getSimpleName();
    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getContentViewRes(),container,false);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        initView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LogUtil.i(TAG,"onSaveInstanceState");
    }
    protected <T> T $(int id){
      return  (T) mContentView.findViewById(id);
    }
    protected <T> T $(View view,int id){
        return  (T) view.findViewById(id);
    }
    protected Context getApplicationContext() {
        return getActivity().getApplication();
    }

    public abstract int getContentViewRes();

    protected abstract void findView(View view);

    protected abstract void initView();

}
