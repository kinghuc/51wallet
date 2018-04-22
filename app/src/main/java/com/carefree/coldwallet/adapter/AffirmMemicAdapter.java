
package com.carefree.coldwallet.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.carefree.coldwallet.R;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：AffirmMemicAdapter
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/11(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：助记词list适配器
---------------------------------------------------------------------*/
public class AffirmMemicAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context mcontext;

    public AffirmMemicAdapter(Context context) {
        this.mcontext = context;
    }
    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public List<String> getList(){
        return list;
    }
    // 获取列表列的数量
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.affirm_memic_list_item, null);
            holder.tv_content = convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(list.get(position));

        return convertView;
    }
    public final class ViewHolder {
        TextView tv_content;
    }
}
