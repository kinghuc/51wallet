
package com.carefree.coldwallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.entity.AssetInfo;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：AssetsAdapter
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/19(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：新添资产list适配器
---------------------------------------------------------------------*/
public class AssetsAdapter extends BaseAdapter {
    private List<AssetInfo> list = new ArrayList<>();
    private Context mContext;

    public AssetsAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<AssetInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<AssetInfo> getList(){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_assets, null);
            holder.tv_name = convertView.findViewById(R.id.currency_name);
            holder.button = convertView.findViewById(R.id.toggleBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).getCurrency());
        holder.button.setChecked(list.get(position).isSelect());
        holder.button.setTag(position);
        ViewHolder finalHolder = holder;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                if (list.get(i).isSelect()){
                    finalHolder.button.setChecked(false);
                    list.get(i).setSelect(false);
                } else {
                    finalHolder.button.setChecked(true);
                    list.get(i).setSelect(true);
                }
            }
        });
//        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int i = (int) buttonView.getTag();
//                list.get(i).setSelect(isChecked);
//            }
//        });

        return convertView;
    }

    public final class ViewHolder {
        TextView tv_name;
        ToggleButton button;
    }
}
