package com.walle.gankio.listener;

/**
 * Created by void on 16/8/15.
 */
public interface OnLoadMoreListener {
    /**
     * @param lastPos 最后一个数据的位置
     */
    void onLoadMore(int lastPos);
}
