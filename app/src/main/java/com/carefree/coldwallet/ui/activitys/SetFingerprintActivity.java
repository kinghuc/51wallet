package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.views.FingerPrinterView;

/*--------------------------------------------------------------------
  文 件 名：SetFingerprintActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：设置指纹界面
---------------------------------------------------------------------*/

public class SetFingerprintActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private LinearLayout ll_continue;
    private LinearLayout ll_entering;
    private FingerPrinterView fingerPrinterView;
    private TextView continueBtn;
    private TextView finishBtn;
    private int fingerErrorNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_fingerprint);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.set_fingerprint));
        ll_continue = (LinearLayout) findViewById(R.id.ll_continue);
        ll_entering = (LinearLayout) findViewById(R.id.ll_entering);
        fingerPrinterView = (FingerPrinterView) findViewById(R.id.fpv);
//        continueBtn = (TextView) findViewById(R.id.continue_btn);
        finishBtn = (TextView) findViewById(R.id.next);

//        continueBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        fingerPrinterView.startScaning();
        fingerPrinterView.setOnStateChangedListener(new FingerPrinterView.OnStateChangedListener() {
            @Override public void onChange(int state) {
                if (state == FingerPrinterView.STATE_CORRECT_PWD) {
                    fingerErrorNum = 0;
                    Toast.makeText(SetFingerprintActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
                }
                if (state == FingerPrinterView.STATE_WRONG_PWD) {
                    Toast.makeText(SetFingerprintActivity.this, "指纹识别失败，还剩" + "5" + "次机会",
                            Toast.LENGTH_SHORT).show();
                    fingerPrinterView.setState(FingerPrinterView.STATE_NO_SCANING);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.continue_btn:
//                ll_continue.setVisibility(View.GONE);
//                ll_entering.setVisibility(View.VISIBLE);
//                break;
            case R.id.next:
                Intent intent = new Intent(SetFingerprintActivity.this,SelectWalletModeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
