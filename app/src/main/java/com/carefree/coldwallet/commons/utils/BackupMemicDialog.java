package com.carefree.coldwallet.commons.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.R;

/*--------------------------------------------------------------------
  文 件 名：BackupMemicDialog
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/04/11 (版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：备份助记词弹框dialog
---------------------------------------------------------------------*/
public class BackupMemicDialog extends Dialog{
    private View customView;
    private TextView know;
    private TextView cancel;
    private Context mContext;
    private OnConfirmClickListener mOnConfirmClickListener;

    public BackupMemicDialog(Context context) {
        super(context,R.style.FullScreenDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_backup_memic, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        initView();
    }

    private void initView(){
        know = customView.findViewById(R.id.i_know);


        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnConfirmClickListener.onConfirmClick(v);
                dismiss();
            }
        });


    }

    /**
     * 询问弹框我知道事件
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
