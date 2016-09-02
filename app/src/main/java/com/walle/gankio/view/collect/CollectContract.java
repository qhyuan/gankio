package com.walle.gankio.view.collect;

import com.walle.gankio.base.BasePresenter;
import com.walle.gankio.base.BaseView;
import com.walle.gankio.data.local.entity.Collect;

import java.util.List;

/**
 * Created by void on 16/8/19
 */
public class CollectContract {

    interface View extends BaseView<CollectContract.Presenter>{
        void showData(List<Collect> collects);
        void showNoData();
        void showErroe();
    }

    interface Presenter extends BasePresenter{
        void loadAllCollect();
        void deleteCollect(Collect collect);
        void insertCollect(Collect collect);
    }

}
