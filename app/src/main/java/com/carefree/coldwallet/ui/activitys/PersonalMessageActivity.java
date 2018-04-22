package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：PersonalMessageActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/13(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：个人消息界面
---------------------------------------------------------------------*/

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.PersonalMessageAdapter;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class PersonalMessageActivity extends BaseActivity{
    private TextView title;
    private ImageView iv_left;
    private ListView listView;
    private PersonalMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_message);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.mine_message));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonalMessageAdapter(this);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        adapter.setData(list);
        listView.setAdapter(adapter);


    }
}
