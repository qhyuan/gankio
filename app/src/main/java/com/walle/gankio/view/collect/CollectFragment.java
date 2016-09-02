package com.walle.gankio.view.collect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walle.gankio.R;
import com.walle.gankio.adapter.CollectRecyclerViewAdapter;
import com.walle.gankio.base.BaseFragment;
import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.data.remote.model.Random;
import com.walle.gankio.listener.OnItemClickListener;
import com.walle.gankio.view.WebViewActivity;

import java.util.List;


public class CollectFragment extends BaseFragment implements CollectContract.View {

    private List<Collect> mData;
    private RecyclerView mRecyclerView;
    private CollectContract.Presenter mPresenter;
    private CollectRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void findView(View view) {
        mRecyclerView = $(mContentView,R.id.list);
    }

    @Override
    protected void initView() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.start();
            }
        });
    }

    @Override
    public void showData(List<Collect> collects) {
        mData = collects;
        mAdapter = new CollectRecyclerViewAdapter(mData);
        mAdapter.setOnClickListener(new OnItemClickListener<Collect>() {
            @Override
            public void onItemClick(View view, Collect item, int postion) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("desc",item.getDesc());
                intent.putExtra("url",item.getUrl());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showErroe() {

    }

    @Override
    public void setPresenter(CollectContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
