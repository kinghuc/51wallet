package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.LoginDialog;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.views.RoundImageView;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;

/*--------------------------------------------------------------------
  文 件 名：LoginActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：登录界面
---------------------------------------------------------------------*/

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private RoundImageView iv_icon;
    private ImageView iv_display;
    private TextView walletName;
    private EditText userPassWord;
    private TextView loginBtn;
    private TextView forgetPwd;
    private TextView register;
    private boolean isDisPlay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.transparencyBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        iv_icon = (RoundImageView) findViewById(R.id.iv_icon);
        walletName = (TextView) findViewById(R.id.wallet_name);
        userPassWord = (EditText) findViewById(R.id.user_password);
        forgetPwd = (TextView) findViewById(R.id.forget_password);
        register = (TextView) findViewById(R.id.register);
        loginBtn = (TextView) findViewById(R.id.login);
        iv_display = (ImageView) findViewById(R.id.iv_display);

        walletName.setText(MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_NAME));

        loginBtn.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        register.setOnClickListener(this);
        iv_display.setOnClickListener(this);

        userPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    loginBtn.setBackground(getResources().getDrawable(R.drawable.circular_bead_blue_10px));
                } else {
                    loginBtn.setBackground(getResources().getDrawable(R.drawable.circular_bead_gray_10px));
                }
            }
        });
    }

    private void login(){
        if (TextUtils.isEmpty(userPassWord.getText().toString())){
            ToastUtils.toastutils("密码不能为空",LoginActivity.this);
            return;
        }
        if (!MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_PASSWORD).equals(userPassWord.getText().toString())){
            ToastUtils.toastutils("密码错误",LoginActivity.this);
            return;
        }
        MyApplication.getSharedPrefrencesHelper().saveBoolean(Constants.KEY_ISLOGIN,true);

        String wallet_id = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_ID);
        if (!MyApplication.getSharedPrefrencesHelper().getBoolean(Constants.KEY_ISWALLET)) {
            Intent intent = new Intent(LoginActivity.this,SelectWalletModeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 显示密码
     */
    private void disPlay(){
        iv_display.setImageResource(R.mipmap.pass_word_display);
        userPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示密码
     */
    private void nodisPlay(){
        iv_display.setImageResource(R.mipmap.pass_word_conceal);
        userPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 弹框dialog
     */

    private void disDialog(){
        LoginDialog dialog = new LoginDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new LoginDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                MyApplication.getSharedPrefrencesHelper().saveBoolean(Constants.KEY_ISWALLET,false);
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                login();
                break;
            case R.id.forget_password:
                Intent forgetIntent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register:
                disDialog();
                break;
            case R.id.iv_display:
                if (isDisPlay){
                    nodisPlay();
                    isDisPlay = false;
                    userPassWord.setSelection(userPassWord.getText().toString().length());
                } else {
                    disPlay();
                    isDisPlay = true;
                    userPassWord.setSelection(userPassWord.getText().toString().length());
                }
                break;
        }
    }
}
