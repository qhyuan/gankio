package com.walle.gankio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.walle.gankio.utils.LogUtil;

import java.util.HashMap;


public abstract class FragmentPagerAdapterCompat extends FragmentPagerAdapter {

    private SparseArray<Fragment> fragments;

    public FragmentPagerAdapterCompat(android.support.v4.app.FragmentManager fm) {
        super(fm);
        fragments = new SparseArray<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        LogUtil.d("instantiateItem pos=" + position);
        fragments.append(position,fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragments.remove(position);
        LogUtil.d("destroyItem pos=" + position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }


}
