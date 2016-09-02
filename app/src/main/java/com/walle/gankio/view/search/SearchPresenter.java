package com.walle.gankio.view.search;

import com.walle.gankio.data.DataManager;
import com.walle.gankio.data.remote.SimpleSubscribers;
import com.walle.gankio.data.remote.model.Random;

/**
 * Created by void on 16/8/17
 */
public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View mView;

    public  SearchPresenter(SearchContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void changeData(int count) {
        changeData("all",count);
    }

    @Override
    public void changeData(String type, int count) {
        DataManager.getRandomData(type,count,new SimpleSubscribers<Random>(){
            @Override
            public void onStartOnMainThread() {
                super.onStartOnMainThread();
                mView.showLoadingView(true);
            }

            @Override
            public void onNext(Random o) {
                super.onNext(o);
                mView.showLoadingView(false);
                mView.showChangeData(o.results);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showLoadingView(false);
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void start() {
        changeData(10);
    }
}
