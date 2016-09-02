package com.walle.gankio.view.home;

import com.walle.gankio.data.DataManager;
import com.walle.gankio.data.remote.SimpleSubscribers;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.utils.LogUtil;

import rx.Subscription;

/**
 * Created by yqh on 2016/8/11
 */
public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private Subscription mSubscription;
    private int mCurrentPage = 1;
    private int mCount;
    private String mCategory;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(String category, int count, final int page) {
        LogUtil.i("category=" + category);
        mCategory = category;
        mCount = count;
        mSubscription = DataManager.searData(category, count, page, new SimpleSubscribers<SearchResult>() {
            @Override
            public void onStartOnMainThread() {
                if(page==1)
                    mView.showRefreshView(true);
            }

            @Override
            public void onNext(SearchResult result) {
                super.onNext(result);
                mView.showRefreshView(false);
                LogUtil.i("result.error=" + result.error+"--"+result.results.size());
                if(page==1) {
                    if (result.error) {
                        mView.showLoadError("Net Error!");
                    } else {
                        mView.resetData(result.results);
                        mCurrentPage = page;
                    }
                }
                else{
                    if (result.error) {
                        mView.showLoadError("Net Error!");
                    } else {
                        mView.loadMoreData(result.results);
                        mCurrentPage = page;
                    }
                }


            }

            @Override
            public void onError(Throwable e) {
                mView.showRefreshView(false);
                mView.showLoadError(e.getMessage());
            }
        });
    }

    @Override
    public void loadMore() {
        loadData(mCategory, mCount, mCurrentPage + 1);
    }

    @Override
    public void refresh() {
        loadData(mCategory, mCount, 1);
    }

    @Override
    public void cancelLoad() {
        if (mSubscription != null && mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }
}
