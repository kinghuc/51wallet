package com.carefree.coldwallet.commons.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：PasswordDialog
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/04/11 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：输入密码弹框dialog
---------------------------------------------------------------------*/
public class PasswordDialog extends Dialog{
    private View customView;
    private TextView sure;
    private TextView cancel;
    private EditText password;
    private Context mContext;
    private OnConfirmClickListener mOnConfirmClickListener;

    public PasswordDialog(Context context) {
        super(context,R.style.FullScreenDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_input_password, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        initView();
    }

    private void initView(){
        sure = customView.findViewById(R.id.dialog_sure);
        cancel = customView.findViewById(R.id.dialog_cancel);
        password = customView.findViewById(R.id.dialog_password);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().isEmpty()){
                    mOnConfirmClickListener.onConfirmClick(v);
                    dismiss();
                } else {
                    ToastUtils.toastutils(mContext.getString(R.string.please_input_wallet_password),mContext);
                }
            }
        });

    }

    public String getPwdEditxt(){
        return password.getText().toString();
    }
    /**
     * 询问弹框确定事件
     *
     * @param listener
     */
    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.mOnConfirmClickListener = listener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(View view);
    }
}
