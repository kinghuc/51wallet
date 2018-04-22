package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;

/*--------------------------------------------------------------------
  文 件 名：SelectWalletModeActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/03/29(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：选择钱包方式
---------------------------------------------------------------------*/

public class SelectWalletModeActivity extends BaseActivity implements View.OnClickListener{
    private TextView createWallet;
    private TextView leadWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.transparencyBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_wallet_mode);
        initView();
        MyApplication.getInstance().addActivity(this);
    }
    private void initView(){
        createWallet = (TextView) findViewById(R.id.create_wallet);
        leadWallet = (TextView) findViewById(R.id.to_lead_wallet);
        createWallet.setOnClickListener(this);
        leadWallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.create_wallet:
                intent = new Intent(SelectWalletModeActivity.this,CreateWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.to_lead_wallet:
                intent = new Intent(SelectWalletModeActivity.this,ImportWalletActivity.class);
                startActivity(intent);
                break;
        }
    }
}
