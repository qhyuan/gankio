package com.walle.gankio.view.beauty;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.walle.gankio.R;
import com.walle.gankio.adapter.BaseAdapterWithLoadmore;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.widget.glide.GlideRoundTransform;

import java.util.List;


class BeautyRecyclerViewAdapter extends BaseAdapterWithLoadmore<BeautyRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "BeautyRecyclerViewAdapter";

    BeautyRecyclerViewAdapter(Fragment fragment, List<SearchResult.ResultsEntity> items) {
        super(fragment,items);
    }

    @Override
    protected ViewHolder doCreatViewHolder(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }


    @Override
    protected int getItemView() {
        return R.layout.fragment_beauty_list_item;
    }

    @Override
    protected void doBindViewHolder(final BeautyRecyclerViewAdapter.ViewHolder holder, int position) {

        final SearchResult.ResultsEntity item = mValues.get(position);
        holder.mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.mImgView.getContext();
                Intent intent = new Intent(context,PicViewerActivity.class);
                intent.putExtra("url",item.url);
                context.startActivity(intent);
            }
        });

        Glide.with(mFragment).load(item.url).placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideRoundTransform(mFragment.getActivity().getApplication()))
                .into(holder.mImgView);
    }


    class ViewHolder extends BaseAdapterWithLoadmore.ViewHolder {

        ImageView mImgView;

        ViewHolder(View view) {
            super(view);
            mImgView = (ImageView) view.findViewById(R.id.img);
        }

    }
}
