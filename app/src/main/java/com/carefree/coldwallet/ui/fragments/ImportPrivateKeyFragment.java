package com.carefree.coldwallet.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.sign.bip39.BIP39;
import com.carefree.coldwallet.commons.sign.bip39.Bip39FormatExceptipn;
import com.carefree.coldwallet.commons.sign.bitaddress.BitAddress;
import com.carefree.coldwallet.commons.sign.ecdsa.ECDSA;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.utils.Utils;
import com.carefree.coldwallet.constants.Constants;
import com.carefree.coldwallet.ui.activitys.MainActivity;
import com.carefree.coldwallet.ui.activitys.MemicTipsActivity;
import com.carefree.coldwallet.ui.activitys.PrivatekeyTipsActivity;

import org.apaches.commons.codec.binary.Hex;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ImportPrivateKeyFragment extends Fragment {

    private Unbinder unbinder;
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
    @BindView(R.id.btn_import)
    Button btn_import;
    @BindView(R.id.et_wallet_private)
    EditText et_wallet_private;
    private LoadingDialog.Builder builder;
    private LoadingDialog loadingDialog;
    private boolean select = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public ImportPrivateKeyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import_private_key, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.tv_privatekey_tip,R.id.ll_tiaokuang_click,R.id.btn_import})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_privatekey_tip:
                intent = new Intent(getActivity(), PrivatekeyTipsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_tiaokuang_click:
                if (!select) {
                    select = true;
                    btn_import.setEnabled(true);
                    btn_import.setBackgroundResource(R.drawable.button_round_blue);
                    img_gou.setImageResource(R.mipmap.gou_select);
                } else {
                    select = false;
                    btn_import.setEnabled(false);
                    btn_import.setBackgroundResource(R.drawable.button_round_gray_two);
                    img_gou.setImageResource(R.mipmap.gou_unselect);
                }
                break;
            case R.id.btn_import:
                // Check if we have write permission
                int permission = ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                } else {
                    startImport();
                }
                break;
            default:
                break;
        }
    }

    private void startImport() {
        if (TextUtils.isEmpty(et_wallet_private.getText().toString())) {
            ToastUtils.toastutils("明文私钥为空",getContext());
            return;
        }
        if (et_wallet_private.getText().toString().length() != 64) {
            ToastUtils.toastutils("私钥长度格式不对",getContext());
            return;
        }
        String wallet_name = et_wallet_name.getText().toString();
        if (TextUtils.isEmpty(wallet_name)) {
            ToastUtils.toastutils("钱包名称为空",getContext());
            return;
        }
        String password = et_wallet_password.getText().toString();
        String repassword = et_wallet_repassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.toastutils("钱包密码为空",getContext());
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!password.matches(regex)) {
            ToastUtils.toastutils("请输入6-16位字母、数字的密码",getContext());
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtils.toastutils("请确认您的密码",getContext());
            return;
        }

        if (!password.equals(repassword)) {
            ToastUtils.toastutils("两次密码输入不一致，请重新输入",getContext());
            return;
        }
        showLoadding("导入中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String privateKey = et_wallet_private.getText().toString();//"5502767ae95def4039ef1ee9f4ce46d25b0b1b240621e8b9d7743afa2a3eb5ae";
                    String address = bitAddress(privateKey);
                    if (TextUtils.isEmpty(address)) {
                        ToastUtils.toastutils("导入钱包失败,生成钱包地址失败",getActivity());
                        return;
                    }
                    String unqueid = Utils.getUniqueId(getActivity());
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
                        ToastUtils.toastutils("创建钱包地址失败",getActivity());
                        return;
                    }
                    String result = test.loadPrivateKeyFormLocal(privatekeyName);
                    System.out.print("文件读取私钥信息："+result);
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_ID,md5unqueid);
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_NAME,wallet_name);
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLETSITE,address);
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_DEALPASSWORD,passwormd5);
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_path,privatekeyName);
                    dismissLoadding();
                    ToastUtils.toastutils("创建钱包成功",getActivity());
                    toMain();
                    getActivity().finish();
                } catch (NoSuchAlgorithmException e) {
                    dismissLoadding();
                    ToastUtils.toastutils("导入钱包失败",getActivity());
                    e.printStackTrace();
                }
            }
        }, 2000);

    }

    /**
     * 加载菊花
     */
    protected void showLoadding(String message) {
        if (builder == null) {
            builder = new LoadingDialog.Builder(getActivity())
                    .setCancelable(false);
        }
        builder.setMessage(message);
        if (loadingDialog == null) {
            loadingDialog = builder.create();
        }
        loadingDialog.show();
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
     * 消失菊花
     */
    protected void dismissLoadding() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 跳转主界面
     */
    private void toMain(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }


}
