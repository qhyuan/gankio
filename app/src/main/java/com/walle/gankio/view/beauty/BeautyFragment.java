package com.walle.gankio.view.beauty;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.walle.gankio.R;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.listener.OnLoadMoreListener;
import com.walle.gankio.view.home.HomeContract;

import java.util.ArrayList;
import java.util.List;

public class BeautyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, HomeContract.View, OnLoadMoreListener {


    private RecyclerView mRecycyle;
    private BeautyRecyclerViewAdapter mRecycleViewAdapter;
    private SwipeRefreshLayout mRefresh;
    private List<SearchResult.ResultsEntity> mData;
    private static final String TYPE = "福利";
    private HomeContract.Presenter mPresenter;

    @Override
    protected void initView() {

        mData = new ArrayList<>();

        mRefresh.setProgressViewOffset(false,0,60);
        mRefresh.setOnRefreshListener(this);

        Context context = mRecycyle.getContext();
        mRecycyle.setLayoutManager(new LinearLayoutManager(context));
        mRecycleViewAdapter = new BeautyRecyclerViewAdapter(this,mData);
        mRecycleViewAdapter.setOnLoadMoreListener(this);
        mRecycyle.setAdapter(mRecycleViewAdapter);

        mPresenter.loadData(TYPE,10,1);

    }

    @Override
    protected void findView(View view) {
        mRefresh = $(R.id.refresh);
        mRecycyle = $(R.id.recycyleview);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_beauty_list;
    }

    @Override
    public void onRefresh() {
        mPresenter.loadData(TYPE,10,1);
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

    @Override
    public void onLoadMore(int lastPos) {
        mPresenter.loadMore();
    }
}
