package com.walle.gankio.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.walle.gankio.R;
import com.walle.gankio.base.BaseActivity;
import com.walle.gankio.view.beauty.BeautyFragment;
import com.walle.gankio.view.collect.CollectFragment;
import com.walle.gankio.view.collect.CollectPresenter;
import com.walle.gankio.view.home.HomeFragment;
import com.walle.gankio.view.home.HomePresenter;
import com.walle.gankio.view.rest.RestFragment;
import com.walle.gankio.view.search.SearchFragment;
import com.walle.gankio.view.search.SearchPresenter;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private FrameLayout mContent;
    private RadioGroup mRadioGroup;
    private HomeFragment mHomeFragment;
    private RestFragment mRestFragment;
    private BeautyFragment mBeautyFragment;
    private SearchFragment mSearchFragment;
    private CollectFragment mCollectFragment;

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        mContent = $(R.id.content);
        mRadioGroup = $(R.id.radioGroup);
    }

    @Override
    protected void initView() {
        mRadioGroup.setOnCheckedChangeListener(this);
        RadioButton childAt = (RadioButton) mRadioGroup.getChildAt(0);
        childAt.setChecked(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_home:
                mHomeFragment = (HomeFragment) Fragment.instantiate(getApplicationContext(),HomeFragment.class.getName());
//                Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content);
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mHomeFragment).commit();
                break;
            case R.id.rbtn_rest:
                Bundle bundle = new Bundle();
                bundle.putString(RestFragment.ARG_TYPE,"休息视频");
                mRestFragment = (RestFragment) Fragment.instantiate(getApplicationContext(),
                        RestFragment.class.getName(),bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mRestFragment).commit();
                break;
            case R.id.rbtn_beautypic:
                mBeautyFragment = (BeautyFragment) BeautyFragment.instantiate(getApplicationContext(),
                        BeautyFragment.class.getName());
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mBeautyFragment).commit();
                new HomePresenter(mBeautyFragment);
                break;
            case R.id.rbtn_search:
                mSearchFragment = (SearchFragment) Fragment.instantiate(getApplicationContext(),SearchFragment.class.getName());
                new SearchPresenter(mSearchFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mSearchFragment).commit();
                break;
            case R.id.rbtn_other://collect 收藏
                mCollectFragment = (CollectFragment)Fragment.instantiate(getApplicationContext(),CollectFragment.class.getName());
                new CollectPresenter(mCollectFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mCollectFragment).commit();
                break;
        }
    }
}
