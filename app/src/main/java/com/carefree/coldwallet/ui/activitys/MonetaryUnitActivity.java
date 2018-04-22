package com.carefree.coldwallet.ui.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：MonetaryUnitActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/13(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：货币单位
---------------------------------------------------------------------*/

public class MonetaryUnitActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private TextView iv_right;
    private TextView save;
    private RelativeLayout rmb_layout;
    private RelativeLayout btc_layout;
    private RelativeLayout fb_layout;
    private TextView tv_rmb;
    private TextView tv_btc;
    private TextView tv_fb;
    private ImageView iv_rmb;
    private ImageView iv_btc;
    private ImageView iv_fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monetary_unit);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        iv_right = (TextView) findViewById(R.id.iv_right);
        iv_right.setText(getString(R.string.save));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(this);
        save = (TextView) findViewById(R.id.iv_right);
        save.setOnClickListener(this);
        rmb_layout = (RelativeLayout) findViewById(R.id.rmb_layout);
        rmb_layout.setOnClickListener(this);
        btc_layout = (RelativeLayout) findViewById(R.id.btc_layout);
        btc_layout.setOnClickListener(this);
        fb_layout = (RelativeLayout) findViewById(R.id.fb_layout);
        fb_layout.setOnClickListener(this);
        tv_rmb = (TextView) findViewById(R.id.tv_rmb);
        tv_btc = (TextView) findViewById(R.id.tv_btc);
        tv_fb = (TextView) findViewById(R.id.tv_fb);
    }

    private void initState(){
        tv_rmb.setTextColor(getResources().getColor(R.color.text_color_666666));
        tv_btc.setTextColor(getResources().getColor(R.color.text_color_666666));
        tv_fb.setTextColor(getResources().getColor(R.color.text_color_666666));
        iv_rmb.setVisibility(View.GONE);
        iv_btc.setVisibility(View.GONE);
        iv_fb.setVisibility(View.GONE);
    }

    private void save(){
        showLoadding(getString(R.string.please_wait));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoadding();
                finish();
            }
        },2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                save();
                break;
            case R.id.rmb_layout:
                initState();
                tv_rmb.setTextColor(getResources().getColor(R.color.circular_bead_blue));
                iv_rmb.setVisibility(View.VISIBLE);
                break;
            case R.id.btc_layout:
                initState();
                tv_btc.setTextColor(getResources().getColor(R.color.circular_bead_blue));
                iv_btc.setVisibility(View.VISIBLE);
                break;
            case R.id.fb_layout:
                initState();
                tv_fb.setTextColor(getResources().getColor(R.color.circular_bead_blue));
                iv_fb.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
