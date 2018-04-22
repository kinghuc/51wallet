package com.carefree.coldwallet.ui.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;

/*--------------------------------------------------------------------
  文 件 名：AddExchangeActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/13(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：添加交易所界面
---------------------------------------------------------------------*/

public class AddExchangeActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private TextView address;
    private TextView name;
    private TextView addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exchange);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.add_exchange));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        addBtn = (TextView) findViewById(R.id.add);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.add:

                break;
        }
    }
}
