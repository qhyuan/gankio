package com.walle.gankio.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.viewpagerindicator.TabPageIndicator;
import com.walle.gankio.Config;
import com.walle.gankio.R;
import com.walle.gankio.adapter.FragmentPagerAdapterCompat;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.data.local.Preferences;
import com.walle.gankio.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HomeFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private List<String> mSortedTitle;

    private ViewPager mPager;
    private TabPageIndicator mIndicator;

    private FragmentPagerAdapterCompat mPagerAdapter;
    private int mSelectedPostion;
    private ImageView mAddmore;
    private boolean isNeednewInstance = false;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected void findView(View view) {
        mPager = $(R.id.pager);
        mIndicator = $(R.id.indicator);
        mAddmore = $(R.id.addmore);
    }

    @Override
    protected void initView() {
        mSortedTitle = new ArrayList<>();
        resetTitleData(mSortedTitle);

        mAddmore.setOnClickListener(this);

        mPagerAdapter = new FragmentPagerAdapterCompat(getChildFragmentManager()) {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                if (TextUtils.equals(fragment.getArguments().getString("name", null), mSortedTitle.get(position))) {
                }else {
                    String tag = fragment.getTag();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction remove = manager.beginTransaction();
                    remove.remove(fragment).commit();
                    fragment = ViewPagerFragment.newInstance(mSortedTitle.get(position));
                    new HomePresenter((HomeContract.View) fragment);
                    FragmentTransaction addTran = manager.beginTransaction();
                    addTran.add(container.getId(),fragment,tag).commit();
                }
                return fragment;
            }

            @Override
            public Fragment getItem(int position) {
                ViewPagerFragment fragment = ViewPagerFragment.newInstance(mSortedTitle.get(position));
                new HomePresenter(fragment);
                return fragment;
            }

            @Override
            public Parcelable saveState() {
                return super.saveState();
            }

            @Override
            public void restoreState(Parcelable state, ClassLoader loader) {
                super.restoreState(state, loader);
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public int getCount() {
                return mSortedTitle.size();
            }

            @Override
            public String getPageTitle(int position) {
                return mSortedTitle.get(position);
            }
        };
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setViewPager(mPager);
    }

    private void resetTitleData(List<String> mSortedTitle) {

        mSortedTitle.clear();
        mSortedTitle.add("all");
        mSortedTitle.add("Android");
        mSortedTitle.add("iOS");
        mSortedTitle.add("前端");
        mSortedTitle.add("App");
        mSortedTitle.add("拓展资源");
        mSortedTitle.add("瞎推荐");

        Map<String, Boolean> all = (Map<String, Boolean>) Preferences.build(getApplicationContext(), Config.TITLE_SP).getAll();
        Set<String> keys = all.keySet();
        for (String key : keys) {
            if (!all.get(key)) {
                mSortedTitle.remove(key);
            }
        }
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addmore:
                Intent intent = new Intent(getApplicationContext(), HomeSettingActivity.class);
                startActivityForResult(intent, 2);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d(TAG, "code = " + requestCode + "----" + resultCode);
        if (requestCode == 2) {
            refreshTitle();
        }
    }

    /**
     * 刷新标题列表
     */
    private void refreshTitle() {
        isNeednewInstance = true;
        resetTitleData(mSortedTitle);
        mPagerAdapter.notifyDataSetChanged();
        mPager.setCurrentItem(0);
        LogUtil.i(TAG, "count=" + mPager.getAdapter().getCount());
        mIndicator.notifyDataSetChanged();
        isNeednewInstance = false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectedPostion = position;
        ViewPagerFragment fragment = (ViewPagerFragment) mPagerAdapter.getFragment(position);
        if(fragment!=null)
            fragment.onSelected();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
