package com.carefree.coldwallet.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：TransferSuccessActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/20(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：转账成功界面
---------------------------------------------------------------------*/

public class TransferSuccessActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private TextView tv_right;
    private TextView transfer_money;
    private TextView transfer_unit;
    private TextView wallet_address;
    private TextView transfer_amount;
    private TextView cost_of_absenteeism;
    private TextView remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_success);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.transfer_success));
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getString(R.string.save));
        tv_right.setOnClickListener(this);
        transfer_money = (TextView) findViewById(R.id.transfer_money);
        transfer_unit = (TextView) findViewById(R.id.transfer_unit);
        wallet_address = (TextView) findViewById(R.id.wallet_address);
        transfer_amount = (TextView) findViewById(R.id.transfer_amount);
        cost_of_absenteeism = (TextView) findViewById(R.id.cost_of_absenteeism);
        remarks = (TextView) findViewById(R.id.remarks);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                finish();
                break;
        }
    }
}
