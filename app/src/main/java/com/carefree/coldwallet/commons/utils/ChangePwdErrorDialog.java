package com.carefree.coldwallet.commons.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：ChangePwdErrorDialog
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/04/12 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：修改密码错误弹框
---------------------------------------------------------------------*/
public class ChangePwdErrorDialog extends Dialog{
    private View customView;
    private TextView content;
    private Context mContext;

    public ChangePwdErrorDialog(Context context) {
        super(context,R.style.ErrorDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_change_pwd_error, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        initView();
    }

    private void initView(){
        content = customView.findViewById(R.id.content);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setContent(String text){
        content.setText(text);
    }
}
