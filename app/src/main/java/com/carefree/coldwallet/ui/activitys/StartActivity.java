package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：StartActivity
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0, 2018-03-29 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：app启动页，检测版本更新 检测账户有效性
---------------------------------------------------------------------*/

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;

public class StartActivity extends BaseActivity{
    private CountDownTimer timer = null;//启动定时器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
    }

    private void initView(){
        startTimer();
    }

    /**
     * 开始定时器
     */
    private void startTimer() {
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                stopTimer();
                intoProject();
           //     checkVersion();//获取版本信息
            }
        };
        timer.start();
    }

    /**
     * 结束定时器
     */
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 跳转到登录界面
     */
    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 进入程序
     */
    private void intoProject() {
        if (!MyApplication.getSharedPrefrencesHelper().getBoolean(Constants.KEY_THEFIRST)){
            Intent intent = new Intent(StartActivity.this,WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (MyApplication.getSharedPrefrencesHelper().getBoolean(Constants.KEY_ISLOGIN)){
                goLogin();
            } else {
                if (!MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_USERNAME).isEmpty() &&
                        !MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_PASSWORD).isEmpty()){
                    String wallet_id = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_ID);
                    if (!MyApplication.getSharedPrefrencesHelper().getBoolean(Constants.KEY_ISWALLET)) {
                        // 前往创建钱包
                        Intent intent = new Intent(StartActivity.this,SelectWalletModeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    goLogin();
                }
            }
        }
    }
}
