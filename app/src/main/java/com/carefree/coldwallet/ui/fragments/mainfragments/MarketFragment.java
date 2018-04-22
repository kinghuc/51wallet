package com.carefree.coldwallet.ui.fragments.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.MarketAdapter;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：MarketFragment
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：行情界面碎片
---------------------------------------------------------------------*/

public class MarketFragment extends Fragment{
    private View view;
    private MarketAdapter adapter;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    public MarketFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_market,container,false);
        initView();
        return view;
    }

    private void initView(){
        listView = view.findViewById(R.id.listView);
        adapter = new MarketAdapter(getActivity());

        list.add("比特币");
        list.add("莱特比");
        list.add("以太坊");
        list.add("比特币");
        list.add("莱特比");
        list.add("以太坊");
        list.add("比特币");
        list.add("莱特比");
        list.add("以太坊");
        list.add("比特币");
        list.add("莱特比");
        list.add("以太坊");

        adapter.setData(list);
        listView.setAdapter(adapter);


    }
}
