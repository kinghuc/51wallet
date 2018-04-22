package com.carefree.coldwallet.ui.activitys;

/*--------------------------------------------------------------------
  文 件 名：RegisterActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：注册界面
---------------------------------------------------------------------*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.constants.Constants;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView pass_disPlay_icon;
    private ImageView repass_disPlay_icon;
    private TextView nextStep;
    private EditText userName;
    private EditText passWord;
    private EditText passWordRep;
    private boolean isPassDis = false;
    private boolean isRePassDis = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.register));
        pass_disPlay_icon = (ImageView) findViewById(R.id.pass_word_display_icon);
        repass_disPlay_icon = (ImageView) findViewById(R.id.repass_word_display_icon);
        nextStep = (TextView) findViewById(R.id.next);
        userName = (EditText) findViewById(R.id.user_name);
        passWord = (EditText) findViewById(R.id.user_password);
        passWordRep = (EditText) findViewById(R.id.user_repetition_passwrod);

        pass_disPlay_icon.setOnClickListener(this);
        repass_disPlay_icon.setOnClickListener(this);
        nextStep.setOnClickListener(this);
    }

    /**
     * 跳转登录界面
     */
    private void toLogin(){
        Intent intent = new Intent(RegisterActivity.this,SetFingerprintActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 注册验证
     */
    private void register(){
        if (TextUtils.isEmpty(userName.getText().toString())){
            ToastUtils.toastutils("请输入正确的账户",RegisterActivity.this);
            return;
        }
        if (TextUtils.isEmpty(passWord.getText().toString())){
            ToastUtils.toastutils("请输入密码",RegisterActivity.this);
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
        if (!passWord.getText().toString().matches(regex)){
            ToastUtils.toastutils("请输入至少为8位，字母和数字组成的密码",RegisterActivity.this);
            return;
        }
        if (!passWordRep.getText().toString().equals(passWord.getText().toString())){
            ToastUtils.toastutils("重复密码错误",RegisterActivity.this);
            return;
        }
        MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_USERNAME,userName.getText().toString());
        MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_PASSWORD,passWord.getText().toString());
        toLogin();
    }

    /**
     * 显示密码
     */
    private void disPlay(){
        pass_disPlay_icon.setImageResource(R.mipmap.pass_word_display);
        passWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示密码
     */
    private void nodisPlay(){
        pass_disPlay_icon.setImageResource(R.mipmap.pass_word_conceal);
        passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 重复显示密码
     */
    private void redisPlay(){
        repass_disPlay_icon.setImageResource(R.mipmap.pass_word_display);
        passWordRep.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 重复不显示密码
     */
    private void noredisPlay(){
        repass_disPlay_icon.setImageResource(R.mipmap.pass_word_conceal);
        passWordRep.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                register();
                break;
            case R.id.pass_word_display_icon:
                if (isPassDis){
                    nodisPlay();
                    isPassDis = false;
                    passWord.setSelection(passWord.getText().toString().length());
                } else {
                    disPlay();
                    isPassDis = true;
                    passWord.setSelection(passWord.getText().toString().length());
                }
                break;
            case R.id.repass_word_display_icon:
                if (isRePassDis){
                    noredisPlay();
                    isRePassDis = false;
                    passWordRep.setSelection(passWordRep.getText().toString().length());
                } else {
                    redisPlay();
                    isRePassDis = true;
                    passWordRep.setSelection(passWordRep.getText().toString().length());
                }
                break;
        }
    }
}
