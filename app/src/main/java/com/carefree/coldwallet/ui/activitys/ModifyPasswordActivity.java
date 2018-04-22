package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ChangePwdErrorDialog;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.tv_title)
    TextView top_center_text;
    @BindView(R.id.iv_left)
    ImageView top_left;
    @BindView(R.id.et_current_password)
    EditText et_current_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_re_password)
    EditText et_re_password;
    @BindView(R.id.old_display)
    ImageView old_display;
    @BindView(R.id.new_display)
    ImageView new_display;
    @BindView(R.id.sure_new_display)
    ImageView sure_new_display;
    private String str_input_md5 = "";
    private boolean isOld = false;
    private boolean isNew = false;
    private boolean isRe = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        inittopbar();
    }
    private void inittopbar() {
        top_center_text.setText("更改密码");
        top_left.setVisibility(View.VISIBLE);
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complete();
            }
        });
    }

    @OnClick({R.id.tv_start_import,R.id.old_display,R.id.new_display,R.id.sure_new_display})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_start_import:
                intent = new Intent(ModifyPasswordActivity.this,ImportWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.old_display:
                if (isOld){
                    oldnodisPlay();
                    isOld = false;
                } else {
                    olddisPlay();
                    isOld = true;
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
            default:
                break;
        }
    }

    private void showErrorDialog(String content){
        ChangePwdErrorDialog dialog = new ChangePwdErrorDialog(this);
        dialog.show();
        dialog.setContent(content);
    }

    private void complete() {
        String curretnPassword = et_current_password.getText().toString();
        String newPassword = et_new_password.getText().toString();
        String repassword = et_re_password.getText().toString();
        if (TextUtils.isEmpty(curretnPassword)) {
            ToastUtils.toastutils("当前钱包密码为空",ModifyPasswordActivity.this);
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtils.toastutils("新的钱包密码为空",ModifyPasswordActivity.this);
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!newPassword.matches(regex)) {
            ToastUtils.toastutils(getString(R.string.wallet_passwrod),ModifyPasswordActivity.this);
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtils.toastutils("请确认您的密码",ModifyPasswordActivity.this);
            return;
        }
        if (!newPassword.equals(repassword)) {
//            ToastUtils.toastutils("两次密码输入不一致，请重新输入",ModifyPasswordActivity.this);
            showErrorDialog(getString(R.string.the_new_password_does_not_agree_with_repeat_password));
            return;
        }
        String wallet_password = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
        try {
            str_input_md5 = MD5.MD5Encode(curretnPassword);
            if (!wallet_password.equals(str_input_md5)) {
                showErrorDialog(getString(R.string.old_passwrod_error));
//                ToastUtils.toastutils("当前密码输入错误",ModifyPasswordActivity.this);
                return;
            }
            showLoadding("请稍候");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        String password = MD5.MD5Encode(newPassword);
                        MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_DEALPASSWORD,password);
                        dismissLoadding();
//                    ToastUtils.toastutils("修改成功",ModifyPasswordActivity.this);
                        Intent intent = new Intent(ModifyPasswordActivity.this,MineWalletActivity.class);
                        intent.putExtra(Constants.KEY_TYPE,1);
                        startActivity(intent);
                        finish();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示原密码
     */
    private void olddisPlay(){
        old_display.setImageResource(R.mipmap.pass_word_display);
        et_current_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示原密码
     */
    private void oldnodisPlay(){
        old_display.setImageResource(R.mipmap.pass_word_conceal);
        et_current_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    /**
     * 显示新密码
     */
    private void newnodisPlay(){
        new_display.setImageResource(R.mipmap.pass_word_display);
        et_new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示新密码
     */
    private void newdisPlay(){
        new_display.setImageResource(R.mipmap.pass_word_conceal);
        et_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    /**
     * 显示重复密码
     */
    private void redisPlay(){
        sure_new_display.setImageResource(R.mipmap.pass_word_display);
        et_re_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示重复密码
     */
    private void renodisPlay(){
        sure_new_display.setImageResource(R.mipmap.pass_word_conceal);
        et_re_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
