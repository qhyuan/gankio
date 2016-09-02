package com.walle.gankio.view.collect;

import com.walle.gankio.data.DataManager;
import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.data.remote.SimpleSubscribers;

import java.util.List;

/**
 * Created by void on 16/8/19.
 */
public class CollectPresenter implements CollectContract.Presenter {

    private final CollectContract.View mView;

    public CollectPresenter(CollectContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadAllCollect() {
        DataManager.getAllCollectList(new SimpleSubscribers<List<Collect>>(){
            @Override
            public void onNext(List<Collect> collects) {
                super.onNext(collects);
                if(collects.isEmpty())
                    mView.showNoData();
                else
                    mView.showData(collects);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showErroe();
            }
        });
    }

    @Override
    public void deleteCollect(Collect collect) {

    }

    @Override
    public void insertCollect(Collect collect) {

    }

    @Override
    public void start() {
        loadAllCollect();
    }
}
