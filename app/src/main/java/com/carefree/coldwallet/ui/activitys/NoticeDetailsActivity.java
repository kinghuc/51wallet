package com.carefree.coldwallet.ui.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;

/*--------------------------------------------------------------------
  文 件 名：NoticeDetailsActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/13(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：公告详情界面
---------------------------------------------------------------------*/

public class NoticeDetailsActivity extends BaseActivity{
    private TextView title;
    private ImageView iv_left;
    private TextView notice_title;
    private TextView notice_time;
    private TextView notice_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        initView();
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

    }
}
