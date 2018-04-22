package com.carefree.coldwallet.ui.activitys;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.sign.ecdsa.ECDSA;
import com.carefree.coldwallet.commons.utils.ErrorDialog;
import com.carefree.coldwallet.commons.utils.MD5;
import com.carefree.coldwallet.commons.utils.PasswordDialog;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.utils.WalletPwdErrorDialog;
import com.carefree.coldwallet.constants.Constants;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineWalletActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView top_center_text;
    @BindView(R.id.iv_left)
    ImageView top_left;
    @BindView(R.id.wallet_address)
    TextView wallet_address;
    private Dialog msgDialog;
    private Dialog inputDialog;
    private Dialog privatekeyDialog;
    private EditText et_input_password;
    @BindView(R.id.et_wallet_name)
    TextView et_wallet_name;
    private String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_wallet);
        ButterKnife.bind(this);
        inittopbar();
//        creteaInputDialog();
        initview();
//        createMsgDialog();
//        createPrivatekeyDialog();
    }

    private void initview() {
        wallet_address.setText(MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLETSITE));
        et_wallet_name.setText(MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_NAME));
        top_left.setVisibility(View.VISIBLE);
    }

    private void createPrivatekeyDialog() {
        privatekeyDialog = new Dialog(this,R.style.FullScreenDialog);
        privatekeyDialog.setContentView(R.layout.dialog_privatekey);
        privatekeyDialog.setCancelable(false);
        ImageView iv_close = privatekeyDialog.findViewById(R.id.iv_close);
        TextView tv_privatekey = privatekeyDialog.findViewById(R.id.tv_privatekey);
        Button btn_copy = privatekeyDialog.findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(tv_privatekey.getText());
                btn_copy.setText("已复制");
                btn_copy.setBackgroundResource(R.drawable.button_round_gray_two);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_copy.setText("复制");
                        btn_copy.setBackgroundResource(R.drawable.button_round_blue);
                    }
                },1000);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privatekeyDialog.dismiss();
            }
        });
    }

    private void createMsgDialog() {
        msgDialog = new Dialog(this,R.style.FullScreenDialog);
        msgDialog.setContentView(R.layout.dialog_msg);
        msgDialog.setCancelable(false);
        TextView submit = msgDialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgDialog.dismiss();
                et_input_password.getText().clear();
                inputDialog.show();
            }
        });
        TextView cancel = msgDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgDialog.dismiss();
            }
        });
    }

    private void creteaInputDialog() {
        inputDialog = new Dialog(this,R.style.FullScreenDialog);
        inputDialog.setContentView(R.layout.dialog_input);
        inputDialog.setCancelable(false);
        et_input_password = inputDialog.findViewById(R.id.et_input_password);
        TextView submit = inputDialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("1")) {
                    showLoadding("导出中");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissLoadding();
                            inputDialog.dismiss();
                            privatekeyDialog.show();
                        }
                    },1000);
                } else if (type.equals("2")){
                    deleteWallet();
                }

            }
        });
        TextView cancel = inputDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDialog.dismiss();
            }
        });

    }

    private void showDialog(){
        PasswordDialog dialog = new PasswordDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new PasswordDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                try {
                    String wallet_passwrod = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
                    String input_passwrod = MD5.MD5Encode(dialog.getPwdEditxt());
                    if (wallet_passwrod.equals(input_passwrod)){
                        showExportPrivate();
                    } else {
                        walletPwdErrorDialog();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void walletPwdErrorDialog(){
        WalletPwdErrorDialog dialog = new WalletPwdErrorDialog(this);
        dialog.show();
        dialog.setContent(getString(R.string.wallet_passwrod_error));
    }

    private void showChangeSuccess(){
        ErrorDialog dialog = new ErrorDialog(this);
        dialog.show();
        dialog.setContent(getString(R.string.passwrod_success));
    }

    private void inittopbar() {
        top_center_text.setText("我的钱包");
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        top_right_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                modifyName();
//            }
//        });
//        top_right_text.setText("完成");
    }

    @Override
    protected void onResume() {
        super.onResume();
        int type = getIntent().getIntExtra(Constants.KEY_TYPE,0);
        if (type == 1){
            showChangeSuccess();
        }
    }

    @OnClick({R.id.btn_delete_wallet,R.id.ll_alter_password,R.id.ll_alter_private_key})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_alter_password:
                intent = new Intent(MineWalletActivity.this,ModifyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_alter_private_key:
                type = "1";
//                et_input_password.getText().clear();
//                inputDialog.show();
                showDialog();
                break;
            case R.id.btn_delete_wallet:
                type = "2";
//                msgDialog.show();
                delectWallet();
                break;
            default:
                break;
        }
    }

    private void modifyName() {
//        String wallet_name = et_wallet_name.getText().toString();
//        if (TextUtils.isEmpty(wallet_name)) {
//            ToastUtils.toastutils("请输入钱包名称",MineWalletActivity.this);
//            return;
//        }
        showLoadding("请稍候");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_NAME,wallet_name);
                dismissLoadding();
                ToastUtils.toastutils("修改成功",MineWalletActivity.this);
            }
        }, 1000);
    }

    private void showExportPrivate(){
//        ExportPrivateDialog dialog = new ExportPrivateDialog(this);
//        dialog.show();
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View viewDialog = inflater.inflate(R.layout.activity_export_private, null);
//        Display display = this.getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        //设置dialog的宽高为屏幕的宽高
//        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
//        dialog.setContentView(viewDialog, layoutParams);
        Intent intent = new Intent(MineWalletActivity.this,ExportPrivateActivity.class);
        startActivity(intent);
    }

    private void delectWallet(){
        PasswordDialog dialog = new PasswordDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new PasswordDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                try {
                    String wallet_passwrod = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
                    String input_passwrod = MD5.MD5Encode(dialog.getPwdEditxt());
                    if (wallet_passwrod.equals(input_passwrod)){
                        showLoadding("删除中");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadding();
                                // 删除私钥文件
                                ECDSA test = new ECDSA();
                                String privatekeyName = Constants.KEY_PRIVATEKEY_NAME;
                                boolean flag = test.deletePrivateKeyFromLocal(privatekeyName);
                                if (!flag) {
                                    dismissLoadding();
                                    ToastUtils.toastutils("删除钱包私钥文件失败",MineWalletActivity.this);
                                    return;
                                }
                                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_ID,"");
                                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_NAME,"");
                                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLETSITE,"");
                                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_DEALPASSWORD,"");
                                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_path,"");
                                ToastUtils.toastutils("删除成功",MineWalletActivity.this);
                                Intent intent = new Intent(MineWalletActivity.this,SelectWalletModeActivity.class);
                                startActivity(intent);
                                dismissLoadding();
                                finish();
                                MyApplication.getInstance().closeActivitys();
                            }
                        },1000);
                    } else {
                        walletPwdErrorDialog();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteWallet() {
        showLoadding("删除中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String wallet_password = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_DEALPASSWORD);
                String str_input_p = et_input_password.getText().toString();
                String str_input_md5 = "";
                try {
                    str_input_md5 = MD5.MD5Encode(str_input_p);
                    if (!wallet_password.equals(str_input_md5)) {
                        dismissLoadding();
                        ToastUtils.toastutils("交易密码输入错误",MineWalletActivity.this);
                        return;
                    }
                    inputDialog.dismiss();
                    // 删除私钥文件
                    ECDSA test = new ECDSA();
                    String privatekeyName = Constants.KEY_PRIVATEKEY_NAME;
                    boolean flag = test.deletePrivateKeyFromLocal(privatekeyName);
                    if (!flag) {
                        dismissLoadding();
                        ToastUtils.toastutils("删除钱包私钥文件失败",MineWalletActivity.this);
                        return;
                    }
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_ID,"");
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_NAME,"");
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLETSITE,"");
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_DEALPASSWORD,"");
                    MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_WALLET_path,"");
                    ToastUtils.toastutils("删除成功",MineWalletActivity.this);
                    Intent intent = new Intent(MineWalletActivity.this,SelectWalletModeActivity.class);
                    startActivity(intent);
                    dismissLoadding();
                    finish();
                    MyApplication.getInstance().closeActivitys();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

}
