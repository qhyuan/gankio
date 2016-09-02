package com.walle.gankio.view.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walle.gankio.R;
import com.walle.gankio.adapter.HomeRecycleAdapter;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.listener.OnItemClickListener;
import com.walle.gankio.listener.OnLoadMoreListener;
import com.walle.gankio.utils.LogUtil;
import com.walle.gankio.view.WebViewActivity;
import com.walle.gankio.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;




public class ViewPagerFragment extends BaseFragment
        implements HomeContract.View,
        OnItemClickListener<SearchResult.ResultsEntity>,
        OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    private String name;
    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeRecycleAdapter mRecycleViewAdapter;
    private List<SearchResult.ResultsEntity> mData;
    private SwipeRefreshLayout mRefresh;

    public ViewPagerFragment() {
    }

    public static ViewPagerFragment newInstance(String all) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",all);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
        }
        LogUtil.i(TAG,"onCreate name="+name);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG,"onActivityCreated name="+name);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG,"onCreateView name="+name);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void findView(View view) {
        mRefresh = $(R.id.refresh);
        mRecycleView = $(R.id.recycleview);
    }
    @Override
    protected void initView() {

        mRefresh.setProgressViewOffset(false,0,60);
        mRefresh.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        mData = new ArrayList<>();
        mRecycleViewAdapter = new HomeRecycleAdapter(this,mData);
        mRecycleViewAdapter.setOnLoadMoreListener(this);
        mRecycleView.setAdapter(mRecycleViewAdapter);

        mPresenter.loadData(name,10,1);
    }

    @Override
    public void resetData(List<SearchResult.ResultsEntity> results) {
        mData = results;
        mRecycleViewAdapter.resetData(mData);
    }

    @Override
    public void loadMoreData(List<SearchResult.ResultsEntity> results) {
        mRecycleViewAdapter.addMoreData(results);
    }

    @Override
    public void showRefreshView(boolean isShow) {
        mRefresh.setRefreshing(isShow);
    }

    @Override
    public void showLoadError(String errorMsg) {
        mRecycleViewAdapter.showLoadError(errorMsg);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void onSelected() {
        LogUtil.i("onSelected name="+name);
    }

    @Override
    public void onAttach(Context context) {
        LogUtil.i(TAG,"onAttach name="+name);
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LogUtil.i(TAG,"onSaveInstanceState name="+name);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        LogUtil.i(TAG,"onDetach name="+name);
        super.onDetach();
    }

    @Override
    public void onItemClick(View view, SearchResult.ResultsEntity object, int postion) {
        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra("url",object.url);
        intent.putExtra("desc",object.desc);
        startActivity(intent);
    }

    /**
     * Adapter的回调，开始加载更多
     * @param lastPos 最后一个数据的位置
     */
    @Override
    public void onLoadMore(int lastPos) {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
