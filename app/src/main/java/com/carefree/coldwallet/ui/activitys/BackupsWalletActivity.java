package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ErrorDialog;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.PasswordDialog;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;

/*--------------------------------------------------------------------
  文 件 名：BackupsWalletActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/10(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：备份钱包
---------------------------------------------------------------------*/

public class BackupsWalletActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private TextView backupsBtn;
    private String privateKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backups_wallet);
        privateKey = getIntent().getStringExtra("privateKey");
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.backups_wallet));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        backupsBtn = (TextView) findViewById(R.id.immediately_backups_wallet);
        backupsBtn.setOnClickListener(this);
    }

    /**
     * 密码输入弹框
     */
    private void showInputPwdDialog(){
        PasswordDialog dialog = new PasswordDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new PasswordDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                try {
                    String wallet_password = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
                    String input_passwrod = MD5.MD5Encode(dialog.getPwdEditxt().toString());
                    if (wallet_password.equals(input_passwrod)){
                        Intent intent = new Intent(BackupsWalletActivity.this,BackupMemicActivity.class);
                        intent.putExtra(Constants.KEY_VALUE,privateKey);
                        startActivity(intent);
                        finish();
                    } else {
                        showPwdErrorDialog();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 密码错误弹框
     */
    private void showPwdErrorDialog(){
        ErrorDialog dialog = new ErrorDialog(this);
        dialog.show();
        dialog.setContent(getString(R.string.password_error));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.immediately_backups_wallet:
                showInputPwdDialog();
                break;
        }
    }
}
