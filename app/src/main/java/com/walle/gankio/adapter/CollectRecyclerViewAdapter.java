package com.walle.gankio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walle.gankio.R;
import com.walle.gankio.data.local.entity.Collect;
import com.walle.gankio.listener.OnItemClickListener;
import com.walle.gankio.utils.DateUtil;

import java.util.List;

public class CollectRecyclerViewAdapter extends RecyclerView.Adapter<CollectRecyclerViewAdapter.ViewHolder> {

    private final List<Collect> mValues;
    private OnItemClickListener<Collect> mClickListener;

    public CollectRecyclerViewAdapter(List<Collect> items) {
        mValues = items;
    }

    public void setOnClickListener(OnItemClickListener<Collect> listener){
        mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_collect_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onItemClick(v,mValues.get(position),position);
                }
            }
        });
        Collect item = mValues.get(position);
        holder.mDesc.setText(item.getDesc());
        holder.mTime.setText(DateUtil.toString(item.getCollect_date()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView mDesc;
        private final TextView mTime;
        private final View mContent;

        public ViewHolder(View view) {
            super(view);
            mContent = view.findViewById(R.id.content);
            mDesc = (TextView) view.findViewById(R.id.desc);
            mTime = (TextView) view.findViewById(R.id.time);
        }

      }
}
