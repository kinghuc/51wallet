package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：AffirmMemicActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/11(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：确认助记词
---------------------------------------------------------------------*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.AffirmMemicAdapter;
import com.carefree.coldwallet.adapter.AssetsAdapter;
import com.carefree.coldwallet.commons.utils.ErrorDialog;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AffirmMemicActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private TextView affirmMenic;
    private TextView sure;
    private GridView gridView;
    private String menic;
    private AffirmMemicAdapter adapter;
    private Handler handler;
    private List<String> list = new ArrayList<>();
    private String content = null;//显示的助记词
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_memic);
        menic = getIntent().getStringExtra(Constants.KEY_VALUE);
        initView();
        if (menic != null && !menic.equals("")){
            initData();
        }
    }

    private void initView(){
        findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.affirm_memic));
        affirmMenic = (TextView) findViewById(R.id.affirm_menic);
        sure = (TextView) findViewById(R.id.sure);
        sure.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constants.KEY_UPDATA:
                        int position = (int) msg.obj;
                        upData(position);
                        break;
                }
            }
        };
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Message msg = handler.obtainMessage();
                    msg.what = Constants.KEY_UPDATA;
                    msg.obj = position;
                    handler.sendMessage(msg);
            }
        });
    }

    private void initData(){
        String[] strings = menic.split(" ");
        List<String> ls = new ArrayList<>();
        for (int i = 0;i < strings.length;i++){
            ls.add(strings[i]);
        }
        Collections.shuffle(ls);
        adapter = new AffirmMemicAdapter(this);
        adapter.setData(ls);
        gridView.setAdapter(adapter);
    }

    /**
     * 更新UI
     * @param position
     */
    private void upData(int position){
        list = adapter.getList();
        String memicText = list.get(position);
        list.remove(position);
        Collections.shuffle(list);
        adapter.setData(list);
        if (content != null){
            content = content + " " + memicText;
        } else {
            content = memicText;
        }
        affirmMenic.setText(content);
    }

    private void sure(){
        if (content.equals(menic)){
            MyApplication.getSharedPrefrencesHelper().saveBoolean(Constants.KEY_ISWALLET,true);
            Intent intent = new Intent(AffirmMemicActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog(){
        ErrorDialog dialog = new ErrorDialog(this);
        dialog.show();
        dialog.setContent(getString(R.string.error_menic));
        initData();
        content = null;
        affirmMenic.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                sure();
                break;
        }
    }
}
