package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ChangePwdErrorDialog;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;

/*--------------------------------------------------------------------
  文 件 名：ChangeLoginPwdActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/11(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：修改登录密码
---------------------------------------------------------------------*/

public class ChangeLoginPwdActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private EditText curren_pwd;
    private EditText new_pwd;
    private EditText re_pwd;
    private ImageView curren_icon;
    private ImageView new_icon;
    private ImageView re_icon;
    private boolean isCurre = false;
    private boolean isNew = false;
    private boolean isRe = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_login_pwd);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.change_login_password));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        curren_pwd = (EditText) findViewById(R.id.et_current_password);
        new_pwd = (EditText) findViewById(R.id.et_new_password);
        re_pwd = (EditText) findViewById(R.id.et_re_password);
        curren_icon = (ImageView) findViewById(R.id.old_display);
        curren_icon.setOnClickListener(this);
        new_icon = (ImageView) findViewById(R.id.new_display);
        new_icon.setOnClickListener(this);
        re_icon = (ImageView) findViewById(R.id.sure_new_display);
        re_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.old_display:
                if (isCurre){
                    oldnodisPlay();
                    isCurre = false;
                } else {
                    olddisPlay();
                    isCurre = true;
                }
                break;
            case R.id.new_display:
                if (isNew){
                    newnodisPlay();
                    isNew = false;
                } else {
                    newdisPlay();
                    isNew = true;
                }
                break;
            case R.id.sure_new_display:
                if (isRe){
                    renodisPlay();
                    isRe = false;
                } else {
                    redisPlay();;
                    isRe = true;
                }
                break;
            case R.id.sure:
                sure();
                break;
            default:
                break;
        }
    }

    /**
     * 显示原密码
     */
    private void olddisPlay(){
        curren_icon.setImageResource(R.mipmap.pass_word_display);
        curren_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示原密码
     */
    private void oldnodisPlay(){
        curren_icon.setImageResource(R.mipmap.pass_word_conceal);
        curren_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    /**
     * 显示新密码
     */
    private void newnodisPlay(){
        new_icon.setImageResource(R.mipmap.pass_word_display);
        new_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示新密码
     */
    private void newdisPlay(){
        new_icon.setImageResource(R.mipmap.pass_word_conceal);
        new_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    /**
     * 显示重复密码
     */
    private void redisPlay(){
        re_icon.setImageResource(R.mipmap.pass_word_display);
        re_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示重复密码
     */
    private void renodisPlay(){
        re_icon.setImageResource(R.mipmap.pass_word_conceal);
        re_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void sure(){
        String curretnPassword = curren_pwd.getText().toString();
        String newPassword = new_pwd.getText().toString();
        String repassword = re_pwd.getText().toString();
        if (TextUtils.isEmpty(curretnPassword)) {
            ToastUtils.toastutils("密码不能为空",ChangeLoginPwdActivity.this);
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtils.toastutils("新密码不能为空",ChangeLoginPwdActivity.this);
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
        if (!newPassword.matches(regex)){
            ToastUtils.toastutils("请输入至少为8位，字母和数字组成的密码",ChangeLoginPwdActivity.this);
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtils.toastutils("请确认您的密码",ChangeLoginPwdActivity.this);
            return;
        }
        if (!newPassword.equals(repassword)) {
            showErrorDialog(getString(R.string.the_new_password_does_not_agree_with_repeat_password));
            return;
        }
        String loginpwd = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_PASSWORD);
        if (!loginpwd.equals(curretnPassword)){
            showErrorDialog(getString(R.string.old_passwrod_error));
            return;
        }
        showLoadding("请稍候");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_PASSWORD,newPassword);
                    dismissLoadding();
                    finish();
            }
        }, 1000);
    }

    private void showErrorDialog(String content){
        ChangePwdErrorDialog dialog = new ChangePwdErrorDialog(this);
        dialog.show();
        dialog.setContent(content);
    }
}
