package com.walle.gankio.listener;

import android.view.View;

/*
 * Created by yqh on 2016/8/12
 */
public interface OnItemClickListener<D> {
    void onItemClick(View view, D object,int postion);
}
