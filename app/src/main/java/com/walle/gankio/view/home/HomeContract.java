package com.walle.gankio.view.home;

import com.walle.gankio.base.BasePresenter;
import com.walle.gankio.base.BaseView;
import com.walle.gankio.data.remote.model.SearchResult;

import java.util.List;

/**
 * Created by yqh on 2016/8/11
 */
public class HomeContract {
    public interface View extends BaseView<Presenter>{
        void resetData(List<SearchResult.ResultsEntity> results);
        void loadMoreData(List<SearchResult.ResultsEntity> results);
        void showRefreshView(boolean isShow);
        void showLoadError(String errorMsg);
    }
    public interface Presenter extends BasePresenter{
        void loadData(String category,int count,int page);
        void loadMore();
        void refresh();
        void cancelLoad();
    }
}
