package com.carefree.coldwallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.views.swipemenu.BaseSwipListAdapter;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：FingerprintAdapter
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：指纹列表适配器
---------------------------------------------------------------------*/

public class FingerprintAdapter extends BaseSwipListAdapter {
    private Context mContext;
    private List<String> list = new ArrayList<>();
    public FingerprintAdapter(Context context){
        mContext = context;
    }
    public void setData(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fingerprint_list_item, null);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.line = convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.tv_name.setText(list.get(position));
            if (position == list.size() - 1){
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }
        return convertView;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public boolean getSwipEnableByPosition(int position) {
        return true;
    }

    public class ViewHolder{
        TextView tv_name;
        View line;
    }
}
