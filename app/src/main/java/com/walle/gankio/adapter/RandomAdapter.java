package com.walle.gankio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walle.gankio.R;
import com.walle.gankio.data.remote.model.Random;
import com.walle.gankio.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by void on 16/8/17
 */
public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {


    private  List<Random.Results> mValues;
    private OnItemClickListener<Random.Results> mClickListener;

    public RandomAdapter(List<Random.Results> values){
        mValues = values;
    }


    public void setOnClickListener(OnItemClickListener<Random.Results> listener){
        mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.random_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null)
                mClickListener.onItemClick(v,mValues.get(position),position);
            }
        });
        Random.Results item = mValues.get(position);
        holder.desc.setText(item.desc);
        holder.type.setText(item.type);
    }

    @Override
    public int getItemCount() {
        return mValues==null?0:mValues.size();
    }

    public void resetData(List<Random.Results> data) {
        mValues = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView type;
        private final TextView desc;
        private final View content;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            desc = (TextView) itemView.findViewById(R.id.desc);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}
