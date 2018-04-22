package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.NoticeAdapter;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：NoticeActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/13(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：公告界面
---------------------------------------------------------------------*/

public class NoticeActivity extends BaseActivity{
    private TextView title;
    private ImageView iv_left;
    private ListView listView;
    private NoticeAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
        initData();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.mine_notice));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        adapter = new NoticeAdapter(this);
    }

    private void initData(){
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        list.add("关于比特币在国内的相关发展");
        adapter.setData(list);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoticeActivity.this,NoticeDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
