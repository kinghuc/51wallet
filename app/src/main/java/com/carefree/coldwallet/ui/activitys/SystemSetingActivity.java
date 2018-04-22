package com.carefree.coldwallet.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：SystemSetingActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：系统设置界面
---------------------------------------------------------------------*/

public class SystemSetingActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private LinearLayout ll_display_and_luminance;
    private LinearLayout ll_date_and_time;
    private LinearLayout ll_language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_seting);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.system_setting));
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        ll_display_and_luminance = (LinearLayout) findViewById(R.id.ll_display_and_luminance);
        ll_display_and_luminance.setOnClickListener(this);
        ll_date_and_time = (LinearLayout) findViewById(R.id.ll_date_and_time);
        ll_date_and_time.setOnClickListener(this);
        ll_language = (LinearLayout) findViewById(R.id.ll_language);
        ll_language.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_display_and_luminance:
                break;
            case R.id.ll_date_and_time:
                break;
            case R.id.ll_language:
                break;
            default:
                break;
        }
    }
}
