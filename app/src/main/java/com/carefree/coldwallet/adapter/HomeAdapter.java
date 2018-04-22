package com.carefree.coldwallet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.OnItemClickListener;

import java.util.List;


/**
 * Created by wb on 2018/3/26.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    private List<String> datas;
    private Context context;
    private OnItemClickListener<String> listener;

    public HomeAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void setData(List<String> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.homefragment_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_coinname.setText(datas.get(position));
        myViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(datas.get(position),view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public OnItemClickListener<String> getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener<String> listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_coinname;
        private LinearLayout ll_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_coinname = itemView.findViewById(R.id.tv_coinname);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }

}
