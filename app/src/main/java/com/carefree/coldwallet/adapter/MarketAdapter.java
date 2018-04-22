package com.carefree.coldwallet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carefree.coldwallet.R;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：MarketAdapter
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/03/28(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：行情列表适配器
---------------------------------------------------------------------*/

public class MarketAdapter extends BaseAdapter{
    private List<String> list = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public MarketAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    public void setData(List<String> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.market_list_item,null);
            holder.currency_name = view.findViewById(R.id.currency_name);
            holder.currency_price = view.findViewById(R.id.currency_price);
            holder.exchange_name = view.findViewById(R.id.exchange_name);
            holder.exchange_price = view.findViewById(R.id.exchange_price);
            holder.price_limit = view.findViewById(R.id.price_limit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.currency_name.setText(list.get(i));
        return view;
    }

    public class ViewHolder {
        TextView currency_name;
        TextView exchange_name;
        TextView currency_price;
        TextView exchange_price;
        TextView price_limit;
    }

}
