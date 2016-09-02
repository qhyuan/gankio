package com.walle.gankio.view.search;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.walle.gankio.R;
import com.walle.gankio.adapter.RandomAdapter;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.data.remote.model.Random;
import com.walle.gankio.listener.OnItemClickListener;
import com.walle.gankio.view.WebViewActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends BaseFragment implements View.OnClickListener, SearchContract.View {

    private View mChange;
    private RecyclerView mRecycleView;
    private SearchView mSearchView;
    private List<Random.Results> mData;
    private RandomAdapter mAdapter;
    private SearchPresenter mPresenter;
    private ProgressBar mLoadingView;

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_search;
    }
    @Override
    protected void findView(View view) {
        mSearchView = $(R.id.searchView);
        mChange = $(R.id.change);
        mRecycleView = $(R.id.recycleview);
        mLoadingView = $(R.id.loadingview);
    }
    @Override
    protected void initView() {

        mChange.setOnClickListener(this);

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("desc",query);
                intent.putExtra("url","http://gank.io/search?q="+query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mData = new ArrayList<>();
        mAdapter = new RandomAdapter(mData);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecycleView.setLayoutManager(manager);
        mAdapter.setOnClickListener(new OnItemClickListener<Random.Results>() {
            @Override
            public void onItemClick(View view, Random.Results item, int postion) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("desc",item.desc);
                intent.putExtra("url",item.url);
                startActivity(intent);
            }
        });
        mRecycleView.setAdapter(mAdapter);

        mPresenter.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                mPresenter.changeData(10);
                break;
            default:
        }
    }

    @Override
    public void showChangeData(List<Random.Results> data) {
        mData = data;
        mAdapter.resetData(data);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getApplicationContext(),"出错啦！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView(boolean isShow) {
        mRecycleView.setVisibility(isShow?View.GONE:View.VISIBLE);
        mLoadingView.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    @Override
    public void setPresenter(SearchPresenter presenter) {
        mPresenter = presenter;
    }
}
