package com.walle.gankio.view.rest;


import android.os.Bundle;
import android.view.View;

import com.walle.gankio.R;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.view.home.HomePresenter;
import com.walle.gankio.view.home.ViewPagerFragment;


public class RestFragment extends BaseFragment {
    public static final String ARG_TYPE = "type";

    private String mType;
    private ViewPagerFragment mFragment;


    public RestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    protected void initView() {
        mFragment = ViewPagerFragment.newInstance("休息视频");
        new HomePresenter(mFragment);
        getFragmentManager().beginTransaction().replace(R.id.content,mFragment).commit();
    }

    @Override
    protected void findView(View view) {
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_rest;
    }

}
