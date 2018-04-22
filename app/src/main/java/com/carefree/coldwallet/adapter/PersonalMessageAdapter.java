
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
  文 件 名：PersonalMessageAdapter
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/11(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：个人消息list适配器
---------------------------------------------------------------------*/
public class PersonalMessageAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context mContext;

    public PersonalMessageAdapter(Context context) {
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.personal_messge_list_item, null);
            holder.mess_icon = convertView.findViewById(R.id.message_icon);
            holder.mess_time = convertView.findViewById(R.id.message_time);
            holder.mess_type = convertView.findViewById(R.id.message_type);
            holder.curr_number = convertView.findViewById(R.id.currency_number);
            holder.curr_type = convertView.findViewById(R.id.currency_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public final class ViewHolder {
        ImageView mess_icon;
        TextView mess_time;
        TextView mess_type;
        TextView curr_number;
        TextView curr_type;

    }
}
