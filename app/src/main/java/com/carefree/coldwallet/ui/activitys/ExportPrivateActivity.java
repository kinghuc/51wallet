package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：ExportPrivateActivity
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/04/13 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：导出私钥界面
---------------------------------------------------------------------*/

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;

public class ExportPrivateActivity extends BaseActivity{
    private TextView title;
    private TextView tv_private;
    private TextView copyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_private);
        initView();
    }

    private void initView(){
        findViewById(R.id.iv_left).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.export_private));
        tv_private = (TextView) findViewById(R.id.tv_private);
        copyBtn = (TextView) findViewById(R.id.btn_copy);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(tv_private.getText());
                ToastUtils.toastutils(getString(R.string.replicated),ExportPrivateActivity.this);
            }
        });
    }
}
