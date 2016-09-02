package com.walle.gankio.view.search;

import com.walle.gankio.base.BasePresenter;
import com.walle.gankio.base.BaseView;
import com.walle.gankio.data.remote.model.Random;

import java.util.List;

/**
 * Created by void on 16/8/17.
 */
public class SearchContract {
    interface View extends BaseView<SearchPresenter>{
        void showChangeData(List<Random.Results> data);
        void showError(String errorMsg);

        void showLoadingView(boolean isShow);
    }
    interface Presenter extends BasePresenter{
        /**
         *默认类别all
         */
        void changeData(int count);
        void changeData(String type, int count);
    }
}
