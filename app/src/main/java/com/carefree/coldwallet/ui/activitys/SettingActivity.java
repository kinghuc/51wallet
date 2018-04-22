package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.LoginOutDialog;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;

/*--------------------------------------------------------------------
  文 件 名：SettingActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：设置界面
---------------------------------------------------------------------*/

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private LinearLayout ll_currencyunit;
    private LinearLayout ll_fingerprint;
    private LinearLayout ll_system_seting;
    private LinearLayout ll_versions;
    private LinearLayout ll_contact;
    private LinearLayout ll_about;
    private LinearLayout ll_changelginpwd;
    private TextView loginOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();

    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.mine_setting));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        loginOut = (TextView) findViewById(R.id.login_out);
        ll_currencyunit = (LinearLayout) findViewById(R.id.ll_currencyunit);
        ll_currencyunit.setOnClickListener(this);
        ll_fingerprint = (LinearLayout) findViewById(R.id.ll_fingerprint);
        ll_fingerprint.setOnClickListener(this);
        ll_contact = (LinearLayout) findViewById(R.id.ll_contact);
        ll_contact.setOnClickListener(this);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);
        ll_system_seting = (LinearLayout) findViewById(R.id.ll_system_seting);
        ll_system_seting.setOnClickListener(this);
        ll_changelginpwd = (LinearLayout) findViewById(R.id.ll_changelginpwd);
        ll_changelginpwd.setOnClickListener(this);
        ll_versions = (LinearLayout) findViewById(R.id.ll_versions);
        ll_versions.setOnClickListener(this);
    }

    private void showLoginOutDialog(){
        LoginOutDialog dialog = new LoginOutDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new LoginOutDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                MyApplication.getSharedPrefrencesHelper().saveBoolean(Constants.KEY_ISLOGIN,false);
                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_USERNAME,"");
                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_PASSWORD,"");
                Intent loginIntent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_currencyunit:
                Intent currencyIntent = new Intent(SettingActivity.this,MonetaryUnitActivity.class);
                startActivity(currencyIntent);
                break;
            case R.id.ll_fingerprint:
                Intent fingerprintIntent = new Intent(SettingActivity.this,FingerprintActivity.class);
                startActivity(fingerprintIntent);
                break;
            case R.id.ll_system_seting:
                break;
            case R.id.ll_versions:
                Intent versionsIntent = new Intent(SettingActivity.this,VersionsActivity.class);
                startActivity(versionsIntent);
                break;
            case R.id.ll_contact:
                Intent contactIntent = new Intent(SettingActivity.this,ContactServiceActivity.class);
                startActivity(contactIntent);
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_changelginpwd:
                Intent changeIntent = new Intent(SettingActivity.this,ChangeLoginPwdActivity.class);
                startActivity(changeIntent);
                break;
            case R.id.login_out:
                showLoginOutDialog();
                break;
        }
    }
}
