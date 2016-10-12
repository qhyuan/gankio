package com.walle.gankio.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.walle.gankio.App;
import com.walle.gankio.R;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.listener.OnLoadMoreListener;
import com.walle.gankio.utils.LogUtil;

import java.util.List;


public abstract class BaseAdapterWithLoadmore<H extends BaseAdapterWithLoadmore.ViewHolder> extends RecyclerView.Adapter<H> {
    private static final int PAGE_SIZE = 10;
    private static final int VIEW_TYPE = 0;
    private static final int VIEW_TYPE_END = 1;
    private static final int VIEW_LOADMORE = 2;
    private static final String TAG = "BaseAdapterWithLoadmore";
    private static final int VIEW_NONE = -1;
    protected final Fragment mFragment;


    private OnLoadMoreListener mOnLoadMoreListener;
    private BaseAdapterWithLoadmore.ViewHolder mLoadMoreHolder;

    protected   List<SearchResult.ResultsEntity> mValues;

    public BaseAdapterWithLoadmore(Fragment fragment, List<SearchResult.ResultsEntity> items) {
        mValues = items;
        mFragment = fragment;
    }


    public void resetData(List<SearchResult.ResultsEntity> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mOnLoadMoreListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return VIEW_LOADMORE;
        } else {
            return VIEW_TYPE;
        }
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d(TAG,"onCreateViewHolder");
        View view = null;
        if (VIEW_LOADMORE == viewType || viewType==VIEW_NONE) {
            view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loadmore, parent, false);
            if(viewType==VIEW_NONE)
                view.setVisibility(View.GONE);
        }
        if (VIEW_TYPE == viewType) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(getItemView(), parent, false);
        }
        return doCreatViewHolder(parent,viewType,view);
    }

    protected abstract H doCreatViewHolder(ViewGroup parent, int viewType, View view);

    protected abstract int getItemView();

    @Override
    public void onBindViewHolder(H holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.mLoadMore != null) {
            mLoadMoreHolder = holder;
            addData();
            return;
        }
        if(viewHolder.mNoMore!=null){
            return;
        }
        doBindViewHolder(holder,position);
    }

    protected abstract void doBindViewHolder(H holder, int position);

    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        if (mValues.size() == 0)
            return 0;
        return mValues.size()+1;
    }

    private void addData() {
        final int lastPos = mValues.size()-2;
        showLoadMore(true);
        if(mOnLoadMoreListener!=null){
            mOnLoadMoreListener.onLoadMore(lastPos);
        }
    }

    private void showLoadMore( boolean isShow) {
        if(isShow){
            mLoadMoreHolder.mProcessBar.setVisibility(View.VISIBLE);
            mLoadMoreHolder.mSuccess.setVisibility(View.GONE);
        }else {
            mLoadMoreHolder.mProcessBar.setVisibility(View.GONE);
            mLoadMoreHolder.mSuccess.setVisibility(View.VISIBLE);
        }
    }

    public void destory() {
        if (mValues != null) {
            mValues.clear();
            notifyDataSetChanged();
        }
    }

    public void addMoreData(List<SearchResult.ResultsEntity> results) {
        final int lastPos = mValues.size();
        showLoadMore(false);
        mValues.addAll(results);
        notifyItemRangeInserted(lastPos, results.size());
    }

    public void showLoadError(String errorMsg) {
        if(mLoadMoreHolder!=null ){
            showLoadMore(false);
        }
        Toast.makeText(App.getInstance(),"出错啦！",Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mSuccess;
        private final View mProcessBar;
        private  TextView mNoMore;
        private final TextView mLoadMore;

        public ViewHolder(View view) {
            super(view);

            mLoadMore = (TextView) view.findViewById(R.id.loadmore);
            mSuccess = view.findViewById(R.id.ivSuccess);
            mProcessBar = view.findViewById(R.id.progressbar);
        }

    }
}
