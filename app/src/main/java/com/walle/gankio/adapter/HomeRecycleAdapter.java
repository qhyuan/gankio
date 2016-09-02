package com.walle.gankio.adapter;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walle.gankio.R;
import com.walle.gankio.data.remote.model.SearchResult;
import com.walle.gankio.listener.OnItemClickListener;

import java.util.List;

public class HomeRecycleAdapter extends BaseAdapterWithLoadmore<HomeRecycleAdapter.ViewHolder> {

    private static final String TAG = "HomeRecycleAdapter";
    private OnItemClickListener<SearchResult.ResultsEntity> mListener;


    public HomeRecycleAdapter(Fragment fragment, List<SearchResult.ResultsEntity> items) {
        super(fragment,items);
        mListener = (OnItemClickListener<SearchResult.ResultsEntity>) fragment;

    }

    @Override
    protected ViewHolder doCreatViewHolder(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    @Override
    protected int getItemView() {
        return R.layout.fragment_home_list;
    }



    @Override
    protected void doBindViewHolder(ViewHolder holder, final int position) {
        final SearchResult.ResultsEntity item = mValues.get(position);
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v,item,position);
            }
        });

        holder.desc.setText(item.desc);
        holder.type.setText(item.type);
        holder.publish.setText(item.publishedAt.substring(0,item.publishedAt.indexOf("T")));
        holder.who.setText(item.who);
    }


    public class ViewHolder extends BaseAdapterWithLoadmore.ViewHolder {
        private final TextView who;
        private final TextView publish;
        private final TextView type;
        private final View content;
        public TextView desc;
        public ViewHolder(View view) {
            super(view);
            content = itemView.findViewById(R.id.content);
            desc = (TextView) itemView.findViewById(R.id.desc);
            who = (TextView)itemView.findViewById(R.id.who);
            publish = (TextView)itemView.findViewById(R.id.publishedat);
            type= (TextView)itemView.findViewById(R.id.type);
        }

    }

}
