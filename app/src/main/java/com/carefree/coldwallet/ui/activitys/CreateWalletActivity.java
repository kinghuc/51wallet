package com.carefree.coldwallet.ui.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.sign.bitaddress.BitAddress;
import com.carefree.coldwallet.commons.sign.ecdsa.ECDSA;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.SharedPrefrencesHelper;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.utils.Utils;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateWalletActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.et_wallet_name)
    EditText et_wallet_name;
    @BindView(R.id.et_wallet_password)
    EditText et_wallet_password;
    @BindView(R.id.et_wallet_repassword)
    EditText et_wallet_repassword;
    @BindView(R.id.et_wallet_password_tip)
    EditText et_wallet_password_tip;
    @BindView(R.id.img_gou)
    ImageView img_gou;
    @BindView(R.id.create_wallet)
    Button create_wallet;
    @BindView(R.id.pwd_display_icon)
    ImageView pwd_icon;
    @BindView(R.id.repwd_display_icon)
    ImageView repwd_icon;
    private boolean select = false;
    private boolean isPwdIcon = false;
    private boolean isRePwdIcon = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
        inittopbar();
    }

    private void inittopbar() {
        findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("创建钱包");

    }

    @OnClick({R.id.create_wallet,R.id.ll_tiaokuang_click,R.id.secret_tip,R.id.pwd_display_icon,R.id.repwd_display_icon})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.create_wallet:
                // Check if we have write permission
                int permission = ActivityCompat.checkSelfPermission(CreateWalletActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(CreateWalletActivity.this, PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                } else {
                    create();
                }
                break;
            case R.id.ll_tiaokuang_click:
                if (!select) {
                    select = true;
                    create_wallet.setEnabled(true);
                    create_wallet.setBackgroundResource(R.drawable.button_state_pressed);
                    img_gou.setImageResource(R.mipmap.gou_select);
                } else {
                    select = false;
                    create_wallet.setEnabled(false);
                    create_wallet.setBackgroundResource(R.drawable.circular_bead_gray_10px);
                    img_gou.setImageResource(R.mipmap.gou_unselect);
                }
                break;
            case R.id.secret_tip:
                Toast.makeText(CreateWalletActivity.this,"开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pwd_display_icon:
                if (isPwdIcon){
                    nodisPlay();
                    isPwdIcon = false;
                    et_wallet_password.setSelection(et_wallet_name.getText().toString().length());
                } else {
                    disPlay();
                    isPwdIcon = true;
                    et_wallet_password.setSelection(et_wallet_name.getText().toString().length());
                }
                break;
            case R.id.repwd_display_icon:
                if (isRePwdIcon){
                    redisPlay();
                    isRePwdIcon = false;
                    et_wallet_repassword.setSelection(et_wallet_repassword.getText().toString().length());
                } else {
                    noredisPlay();
                    isRePwdIcon = true;
                    et_wallet_repassword.setSelection(et_wallet_repassword.getText().toString().length());
                }
                break;
            default:
                break;
        }
    }

    private void create() {
        String wallet_name = et_wallet_name.getText().toString();
        if (TextUtils.isEmpty(wallet_name)) {
            ToastUtils.toastutils("钱包名称为空",CreateWalletActivity.this);
            return;
        }
        String password = et_wallet_password.getText().toString();
        String repassword = et_wallet_repassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.toastutils("钱包密码为空",CreateWalletActivity.this);
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!password.matches(regex)) {
            ToastUtils.toastutils("请输入6-16位字母、数字的密码",CreateWalletActivity.this);
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtils.toastutils("请确认您的密码",CreateWalletActivity.this);
            return;
        }

        if (!password.equals(repassword)) {
            ToastUtils.toastutils("两次密码输入不一致，请重新输入",CreateWalletActivity.this);
            return;
        }
        showLoadding("创建中...");
        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               try {
                   String privateKey = ECDSA.generatePrivateKey();/*"5502767ae95def4039ef1ee9f4ce46d25b0b1b240621e8b9d7743afa2a3eb5ae";*///
                   String address = bitAddress(privateKey);
                   if (TextUtils.isEmpty(address)) {
                       ToastUtils.toastutils("创建钱包地址失败",CreateWalletActivity.this);
                       return;
                   }
                   String unqueid = Utils.getUniqueId(CreateWalletActivity.this);
                   System.out.println("设备唯一标识"+unqueid);
                   String md5unqueid = "";
                   String passwormd5 = "";
                   String privatekeyName = "";
                   passwormd5 = MD5.MD5Encode(password);
                   md5unqueid = MD5.MD5Encode(unqueid);
                   // 保存私钥
                   ECDSA test = new ECDSA();
                   privatekeyName = Constants.KEY_PRIVATEKEY_NAME;
                   boolean flag = test.savePrivateKeyToLocal(privatekeyName, privateKey);
                   if (!flag) {
                       dismissLoadding();
                       ToastUtils.toastutils("创建钱包地址失败",CreateWalletActivity.this);
                       return;
                   }
                   String result = test.loadPrivateKeyFormLocal(privatekeyName);
                   System.out.print("文件读取私钥信息："+result);
                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_ID,md5unqueid);
                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_NAME,wallet_name);
                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLETSITE,address);
                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_DEALPASSWORD,passwormd5);
                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_path,privatekeyName);
//                   MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_PASSWORD,password);
                   dismissLoadding();
                   ToastUtils.toastutils("创建钱包成功",CreateWalletActivity.this);
                   Intent intent = new Intent(CreateWalletActivity.this,BackupsWalletActivity.class);
                   intent.putExtra("privateKey",privateKey);
                   startActivity(intent);
                   finish();
               } catch (NoSuchAlgorithmException e) {
                   dismissLoadding();
                   ToastUtils.toastutils("创建钱包地址失败",CreateWalletActivity.this);
                   e.printStackTrace();
               }
           }
        }, 2000);

    }

    /**
     * 显示密码
     */
    private void disPlay(){
        pwd_icon.setImageResource(R.mipmap.pass_word_display);
        et_wallet_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 不显示密码
     */
    private void nodisPlay(){
        pwd_icon.setImageResource(R.mipmap.pass_word_conceal);
        et_wallet_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 重复显示密码
     */
    private void redisPlay(){
        repwd_icon.setImageResource(R.mipmap.pass_word_display);
        et_wallet_repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 重复不显示密码
     */
    private void noredisPlay(){
        repwd_icon.setImageResource(R.mipmap.pass_word_conceal);
        et_wallet_repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /***
     * 创建钱包地址
     */
    private String bitAddress(String privateKey) {
        BitAddress s = new BitAddress();
        String publicKey = ECDSA.computePublicKeyWithoutCompressed(privateKey);
        System.out.println(publicKey);
        String result = s.calculateAddress(publicKey);
        System.out.println("钱包地址："+"0x"+result);
        return "0x"+result;
    }

    /**
     * 生成转出签名信息
     */
    private void sign() {
        ECDSA test = new ECDSA();
        // 生成随机数私钥
        String privateKey0 = test.generatePrivateKey();

        // 将私钥保存到本地
        // test.savePrivateKeyToLocal("E:/privateKey.dat", privateKey);

        // 从本地读取私钥
        String privateKey = "5502767ae95def4039ef1ee9f4ce46d25b0b1b240621e8b9d7743afa2a3eb5ae";//test.loadPrivateKeyFormLocal("E:/privateKey.dat");//

        // 根据私钥计算公钥
        String publicKey = test.computePublicKey(privateKey);
        System.out.println("长度："+publicKey.length()+":"+publicKey);

        // 根据私钥计算非压缩公钥
        String publicKey2 = test.computePublicKeyWithoutCompressed(privateKey);
        System.out.println("长度："+publicKey2.length()+":"+publicKey2);

        String data = "Hello ECC";
        // 生成数字签名
        String signdata = test.sign(data.getBytes(), privateKey);
        // 验证签名
        boolean result = test.verify(data.getBytes(), signdata, publicKey);
        System.out.println("签名数据："+signdata + "  "+  "验证签名"+result);
    }

    /**
     * 跳转主界面
     */
    private void toMain(){
        Intent intent = new Intent(CreateWalletActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
