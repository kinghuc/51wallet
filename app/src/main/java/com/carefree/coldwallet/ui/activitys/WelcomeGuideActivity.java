package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：WelcomeGuideActivity
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0, 2018-03-29 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：app引导页，用户第一次使用安装App时就会启动这个引导页
---------------------------------------------------------------------*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.constants.Constants;

public class WelcomeGuideActivity extends BaseActivity{
    private TextView title;
    private TextView registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        registerBtn = (TextView) findViewById(R.id.register);
        title.setText(getString(R.string.register_explain));
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getSharedPrefrencesHelper().saveBoolean(Constants.KEY_THEFIRST,true);
                Intent intent = new Intent(WelcomeGuideActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
