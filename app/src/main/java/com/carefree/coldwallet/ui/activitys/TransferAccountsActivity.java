package com.carefree.coldwallet.ui.activitys;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ErrorDialog;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.PasswordDialog;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.views.RangeSeekBar;
import com.carefree.coldwallet.commons.zxing.android.CaptureActivity;
import com.carefree.coldwallet.commons.zxing.bean.ZxingConfig;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferAccountsActivity extends BaseActivity {
    @BindView(R.id.iv_right)
    ImageView top_right_icon;
    @BindView(R.id.iv_left)
    ImageView top_left;
    private int REQUEST_CODE_SCAN = 111;
    @BindView(R.id.et_wallet_address)
    EditText et_wallet_address;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.et_tip)
    EditText et_tip;
    @BindView(R.id.seekBar)
    RangeSeekBar seekBar;
    @BindView(R.id.tv_kuangong_fei)
    TextView tv_kuangong_fei;
    private TextView title;
    private Dialog inputDialog;
    private TextView submit;
    private TextView cancel;
    private EditText et_input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_accounts);
        ButterKnife.bind(this);
        inittopbar();
        initView();
//        creteaInputDialog();
    }

    private void creteaInputDialog() {
        inputDialog = new Dialog(this,R.style.FullScreenDialog);
        inputDialog.setContentView(R.layout.dialog_input);
        inputDialog.setCancelable(false);
        et_input_password = inputDialog.findViewById(R.id.et_input_password);
        submit = inputDialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadding(getString(R.string.please_wait));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                }, 1000);

            }
        });
        cancel = inputDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDialog.dismiss();
            }
        });

    }

    private void inittopbar() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.transfer_accounts));
        top_left.setVisibility(View.VISIBLE);
        top_right_icon.setImageResource(R.mipmap.black_wallet_scan);
        top_right_icon.setVisibility(View.VISIBLE);
    }

    private void initView() {
        seekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                DecimalFormat df=new DecimalFormat("0.00000000");
                String ss = df.format((float)min/100000);
                tv_kuangong_fei.setText(ss+"");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                DecimalFormat df=new DecimalFormat("0.00000000");
//                String ss = df.format((float)i/100000);
//                tv_kuangong_fei.setText(ss+"");
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

    /**
     * 钱包密码弹框
     */
    private void showInputPwdDialog(){
        PasswordDialog dialog = new PasswordDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new PasswordDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.iv_right,R.id.iv_left,R.id.btn_next})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_right:
                if (ContextCompat.checkSelfPermission(TransferAccountsActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    intent = new Intent(TransferAccountsActivity.this, CaptureActivity.class);
                                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                                * 也可以不传这个参数
                                * 不传的话  默认都为默认不震动  其他都为true
                                * */
                    ZxingConfig config = new ZxingConfig();
                    config.setPlayBeep(true);
                    config.setShake(true);
                    config.setShowbottomLayout(false);
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_next:
                showInputPwdDialog();
                break;
            default:
                break;
        }

    }

    private void next() {
        if (TextUtils.isEmpty(et_wallet_address.getText().toString())) {
            ToastUtils.toastutils("请输入交易钱包地址",TransferAccountsActivity.this);
            return;
        }
        if (TextUtils.isEmpty(et_number.getText().toString())) {
            ToastUtils.toastutils("请输入转账金额",TransferAccountsActivity.this);
            return;
        }
        String wallet_password = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
        String str_input_p = et_input_password.getText().toString();
        String str_input_md5 = "";
        try {
            str_input_md5 = MD5.MD5Encode(str_input_p);
            if (!wallet_password.equals(str_input_md5)) {
                dismissLoadding();
                ToastUtils.toastutils("交易密码输入错误",TransferAccountsActivity.this);
                return;
            }
            inputDialog.dismiss();
            dismissLoadding();
            ToastUtils.toastutils("验证成功，广播接口开发中",TransferAccountsActivity.this);
            finish();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

}
